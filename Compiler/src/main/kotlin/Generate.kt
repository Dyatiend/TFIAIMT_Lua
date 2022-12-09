import nodes.*
import java.io.File
import java.nio.ByteBuffer

fun Int.to2ByteArray() : ByteArray {
    //return ByteBuffer.allocate(2).putInt(this).array()
    return byteArrayOf(shr(8).toByte(), toByte())
}

fun Int.to4ByteArray() : ByteArray {
    //return ByteBuffer.allocate(4).putInt(this).array()
    return byteArrayOf(shr(24).toByte(), shr(16).toByte(), shr(8).toByte(), toByte())
}

fun Float.to4ByteArray() : ByteArray {
    return ByteBuffer.allocate(4).putFloat(this).array()
    //return byteArrayOf(toInt().shr(24).toByte(), toInt().shr(16).toByte(), toInt().shr(8).toByte(), toInt().toByte())
}

fun generateProgram(program: ChunkNode) {
    val className = "__PROGRAM__"
    val classModel = classesTable[className]!!

    val file = File("out/production/Compiler/$className.class")
    file.writeText("")

    // CAFEBABE
    file.appendBytes(byteArrayOf(0xCA.toByte(), 0xFE.toByte(), 0xBA.toByte(), 0xBE.toByte()))

    // Version
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x32))

    // Constants count
    val constants = classModel.constantsTable
    file.appendBytes((constants.size + 1).to2ByteArray())

    // Constants
    constants.toList().sortedBy { (k, v) -> v }.forEach {
        it.first.write(file)
    }

    // Flags (PUBLIC + SUPER)
    file.appendBytes(byteArrayOf(0x00, 0x03))

    // Current class
    file.appendBytes(classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8(className)))).to2ByteArray())

    // Parent class
    file.appendBytes(classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("java/lang/Object")))).to2ByteArray())

    // Interfaces
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Fields count
    val globals = classModel.globalVarsTable
    file.appendBytes((globals.size).to2ByteArray())

    // Fields table
    globals.forEach {
        // Flags (PUBLIC STATIC)
        file.appendBytes(byteArrayOf(0x00, 0x09))

        // Name
        file.appendBytes(classModel.pushConstant(Constant.utf8(it.key)).to2ByteArray())

        // Descriptor
        file.appendBytes(classModel.pushConstant(Constant.utf8("L__VALUE__;")).to2ByteArray())

        // Field attrs count (always 0)
        file.appendBytes(byteArrayOf(0x00, 0x00))
    }

    // Methods count (always 2 (constructor + main))
    file.appendBytes(byteArrayOf(0x00, 0x02))

    // Methods table
    // ------------------ Program constructor --------------

    // Flags (PUBLIC)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("<init>")).to2ByteArray())

    // Descriptor
    file.appendBytes(classModel.pushConstant(Constant.utf8("()V")).to2ByteArray())

    // Method attrs count (always 1)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Code attr
    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("Code")).to2ByteArray())

    // Length
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x11))

    // Stack size (max)
    file.appendBytes(byteArrayOf(0xFF.toByte(), 0xFF.toByte()))

    // Locals count (this)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Bytecode length
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x05))

    // Bytecode (Object constructor call)
    file.appendBytes(byteArrayOf(0x2A)) // aload_0
    file.appendBytes(byteArrayOf(0xB7.toByte())) // invokespecial
    file.appendBytes(classModel.pushMethRef("java/lang/Object", "<init>", "()V").to2ByteArray()) // MethodRef Obj Init
    file.appendBytes(byteArrayOf(0xB1.toByte())) // return

    // Exceptions table length (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Code attrs count (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // ------------------ Main --------------

    // Flags (PUBLIC STATIC)
    file.appendBytes(byteArrayOf(0x00, 0x09))

    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("main")).to2ByteArray())

    // Descriptor
    file.appendBytes(classModel.pushConstant(Constant.utf8("([Ljava/lang/String;)V")).to2ByteArray())

    // Method attrs count (always 1)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Code attr
    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("Code")).to2ByteArray())

    // Bytecode gen
    var code = byteArrayOf()

    globals.forEach {
        code += byteArrayOf(0xBB.toByte()) // NEW
        code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
        code += byteArrayOf(0x59) // dub

        code += byteArrayOf(0xB7.toByte()) // invokespecial
        code += classModel.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

        code += byteArrayOf(0xB3.toByte()) // putstatic
        code += classModel.pushFieldRef("__PROGRAM__", it.key, "L__VALUE__;").to2ByteArray()
    }

    code += generate(program.block, classModel) + 0xB1.toByte() // return

    // Length
    file.appendBytes((code.size + 12).to4ByteArray())

    // Stack size (max)
    file.appendBytes(byteArrayOf(0xFF.toByte(), 0xFF.toByte()))

    // Locals count
    file.appendBytes(classModel.localVarsTable.size().to2ByteArray())

    // Bytecode length
    file.appendBytes((code.size).to4ByteArray())

    // Bytecode
    file.appendBytes(code)

    // Exceptions table length (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Code attrs count (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // -----------------------------------------------------

    // Class attrs count
    file.appendBytes(byteArrayOf(0x00, 0x00))
}

private fun generateFun(stmtNode: StmtNode) {
    val className = "__${stmtNode.ident}__${stmtNode.id}"
    val classModel = classesTable[className]!!

    val file = File("out/production/Compiler/$className.class")
    file.writeText("")

    // CAFEBABE
    file.appendBytes(byteArrayOf(0xCA.toByte(), 0xFE.toByte(), 0xBA.toByte(), 0xBE.toByte()))

    // Version
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x32))

    // Constants count
    val constants = classModel.constantsTable
    file.appendBytes((constants.size + 1).to2ByteArray())

    // Constants
    constants.toList().sortedBy { (k, v) -> v }.forEach {
        it.first.write(file)
    }

    // Flags (PUBLIC + SUPER)
    file.appendBytes(byteArrayOf(0x00, 0x03))

    // Current class
    file.appendBytes(classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8(className)))).to2ByteArray())

    // Parent class
    file.appendBytes(classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__FUN__")))).to2ByteArray())

    // Interfaces
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Fields count
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Methods count (always 2 (constructor + invoke))
    file.appendBytes(byteArrayOf(0x00, 0x02))

    // Methods table
    // ------------------ Program constructor --------------

    // Flags (PUBLIC)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("<init>")).to2ByteArray())

    // Descriptor
    file.appendBytes(classModel.pushConstant(Constant.utf8("()V")).to2ByteArray())

    // Method attrs count (always 1)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Code attr
    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("Code")).to2ByteArray())

    // Length
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x11))

    // Stack size (max)
    file.appendBytes(byteArrayOf(0xFF.toByte(), 0xFF.toByte()))

    // Locals count (this)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Bytecode length
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x05))

    // Bytecode (Fun constructor call)
    file.appendBytes(byteArrayOf(0x2A)) // aload_0
    file.appendBytes(byteArrayOf(0xB7.toByte())) // invokespecial
    file.appendBytes(classModel.pushMethRef("__FUN__", "<init>", "()V").to2ByteArray()) // MethodRef FUN Init
    file.appendBytes(byteArrayOf(0xB1.toByte())) // return

    // Exceptions table length (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Code attrs count (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // ------------------ invoke --------------

    // Flags (PUBLIC)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("invoke")).to2ByteArray())

    // Descriptor
    file.appendBytes(classModel.pushConstant(Constant.utf8("(Ljava/util/ArrayList;)L__VALUE__;")).to2ByteArray())

    // Method attrs count (always 1)
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Code attr
    // Name
    file.appendBytes(classModel.pushConstant(Constant.utf8("Code")).to2ByteArray())

    // Bytecode gen
    var code = byteArrayOf()

    // Инициализируем переменные данными из массива __args__
    val args = stmtNode.params?.list
    var argId = 0
    args?.let {
        var current: IdentNode? = it.first
        while (current != null) {
            val ident = current.ident
            val num = classModel.localVarsTable.get(Pair(stmtNode.actionBlock?.startID!!, stmtNode.actionBlock?.lastID!!), ident)!!

            code += byteArrayOf(0x11) // sipush
            code += argId.to2ByteArray()

            code += byteArrayOf(0x2B) // aload_1 (args)

            code += byteArrayOf(0xB6.toByte()) // invokevirtual
            code += classModel.pushMethRef("java/util/ArrayList", "size", "()I").to2ByteArray()

            code += byteArrayOf(0xA2.toByte()) // if_icmpge
            code += (16).to2ByteArray()

            code += byteArrayOf(0x2B) // aload_1 (args)

            code += byteArrayOf(0x11) // sipush
            code += argId.to2ByteArray()

            code += byteArrayOf(0xB6.toByte()) // invokevirtual
            code += classModel.pushMethRef("java/util/ArrayList", "get", "(I)Ljava/lang/Object;").to2ByteArray()

            code += byteArrayOf(0xC0.toByte()) // checkcast
            code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()

            code += byteArrayOf(0xA7.toByte()) // goto
            code += (10).to2ByteArray()

            code += byteArrayOf(0xBB.toByte()) // NEW
            code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            code += byteArrayOf(0x59) // dub

            code += byteArrayOf(0xB7.toByte()) // invokespecial
            code += classModel.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

            code += byteArrayOf(0x3A) // astore
            code += num.toByte()

            argId += 1
            current = current.next
        }
    }

    if (stmtNode.params?.hasVarArg == true) {
        code += byteArrayOf(0xBB.toByte()) // NEW
        code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
        code += byteArrayOf(0x59) // dub

        code += byteArrayOf(0x11) // sipush
        code += argId.to2ByteArray()

        code += byteArrayOf(0x2B) // aload_1 (args)

        code += byteArrayOf(0xB6.toByte()) // invokevirtual
        code += classModel.pushMethRef("java/util/ArrayList", "size", "()I").to2ByteArray()

        code += byteArrayOf(0xA2.toByte()) // if_icmpge
        code += (17).to2ByteArray()

        code += byteArrayOf(0x2B) // aload_1 (args)

        code += byteArrayOf(0x11) // sipush
        code += argId.to2ByteArray()

        code += byteArrayOf(0x2B) // aload_1 (args)

        code += byteArrayOf(0xB6.toByte()) // invokevirtual
        code += classModel.pushMethRef("java/util/ArrayList", "size", "()I").to2ByteArray()

        code += byteArrayOf(0xB6.toByte()) // invokevirtual
        code += classModel.pushMethRef("java/util/ArrayList", "subList", "(II)Ljava/util/List;").to2ByteArray()

        code += byteArrayOf(0xA7.toByte()) // goto
        code += (10).to2ByteArray()

        code += byteArrayOf(0xBB.toByte()) // NEW
        code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("java/util/ArrayList")))).to2ByteArray()
        code += byteArrayOf(0x59) // dub

        code += byteArrayOf(0xB7.toByte()) // invokespecial
        code += classModel.pushMethRef("java/util/ArrayList", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

        code += byteArrayOf(0xB7.toByte()) // invokespecial
        code += classModel.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V").to2ByteArray() // MethodRef VALUE Init

        val num = classModel.localVarsTable.get(Pair(stmtNode.actionBlock?.startID!!, stmtNode.actionBlock?.lastID!!), "...")!!

        code += byteArrayOf(0x3A) // astore
        code += num.toByte()
    }

    // Генерим тело
    code += generate(stmtNode.actionBlock!!, classModel)

    // Return Nil по умолчанию, если нет другого ретурна раньше
//    code += byteArrayOf(0xBB.toByte()) // NEW
//    code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
//    code += byteArrayOf(0x59) // dub

//    code += byteArrayOf(0xB7.toByte()) // invokespecial
    code += byteArrayOf(0xB8.toByte()) // invokestatic
    code += classModel.pushMethRef("__VALUE__", "voidVal", "()L__VALUE__;").to2ByteArray() // MethodRef VALUE Init

    code += 0xB0.toByte() // areturn

    // Length
    file.appendBytes((code.size + 12).to4ByteArray())

    // Stack size (max)
    file.appendBytes(byteArrayOf(0xFF.toByte(), 0xFF.toByte()))

    // Locals count (+1 под this)
    file.appendBytes((classModel.localVarsTable.size() + 1).to2ByteArray())

    // Bytecode length
    file.appendBytes((code.size).to4ByteArray())

    // Bytecode
    file.appendBytes(code)

    // Exceptions table length (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Code attrs count (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // -----------------------------------------------------

    // Class attrs count
    file.appendBytes(byteArrayOf(0x00, 0x00))
}

private fun generate(stmtSeqNode: StmtSeqNode, currentClass: ClassModel): ByteArray {
    var current = stmtSeqNode.first
    var res = byteArrayOf()
    current?.let {
        while (current != null) {
            res += generate(current!!, currentClass)

            current = current!!.next
        }
    }

    return res
}

private fun generate(stmtNode: StmtNode, currentClass: ClassModel): ByteArray {
    when (stmtNode.type) {
        StmtType.FUNCTION_CALL -> {
            return generate(stmtNode.functionCall!!, currentClass)
        }
        StmtType.UNINITIALIZED -> { // elseif
            val condition = generate(stmtNode.conditionExpr!!, currentClass)
            val ifBlock = generate(stmtNode.ifBlock!!, currentClass)

            var res = byteArrayOf()

            res += condition

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I").to2ByteArray()

            res += byteArrayOf(0x99.toByte()) // ifeq
            res += (ifBlock.size + 6).to2ByteArray() // +6 т.к. ifeq 3 байта и goto 3 байта

            res += ifBlock // Goto добавляется в IF

            return res
        }
        StmtType.ASSIGNMENT -> {
            val vars = ArrayList<VarNode>()
            var current: ExprNode? = stmtNode.varList!!.first
            while (current != null) {
                vars.add(current.varNode!!)

                current = current.next
            }

            val values = ArrayList<ExprNode>()
            current = stmtNode.values!!.first
            while (current != null) {

                if (current.next == null) {
                    values.add(current)
                } else {
                    values.add(ExprNode.createAdjustingExprNode(current))
                }

                current = current.next
            }

            var res = byteArrayOf()

            for (i in vars.indices) {
                val `var` = vars[i];
                val `val` = if (i < values.size) values[i] else values.last()
                val seqItemId = i - values.lastIndex

                if (`var`.last.type != VarType.IDENT) {
                    var curVar: VarItemNode? = `var`.first
                    while (curVar != null) {
                        if (curVar.next == null) {
                            when (curVar.type) {
                                VarType.VAR -> {
                                    if (curVar.isMapKey) {
                                        res += byteArrayOf(0xBB.toByte()) // NEW
                                        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                                        res += byteArrayOf(0x59) // dub

                                        res += byteArrayOf(0x12.toByte()) // ldc
                                        res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(curVar.ident)))).toByte()

                                        res += byteArrayOf(0xB7.toByte()) // invokespecial
                                        res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
                                    } else {
                                        res += generate(curVar.secondExpr!!, currentClass)
                                    }
                                }
                                VarType.FUNCTION_CALL, VarType.ADJUSTED_EXPR -> {
                                    res += generate(curVar.firstExpr!!, currentClass)
                                    if (curVar.isMapKey) {
                                        res += byteArrayOf(0xBB.toByte()) // NEW
                                        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                                        res += byteArrayOf(0x59) // dub

                                        res += byteArrayOf(0x12.toByte()) // ldc
                                        res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(curVar.ident)))).toByte()

                                        res += byteArrayOf(0xB7.toByte()) // invokespecial
                                        res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
                                    } else {
                                        res += generate(curVar.secondExpr!!, currentClass)
                                    }
                                }
                                else -> {}
                            }
                        } else {
                            when (curVar.type) {
                                VarType.IDENT -> {
                                    // Поиск локалки с именем
                                    var min = 100000
                                    var num : Int? = null
                                    for (it in currentClass.localVarsTable.column(curVar.ident)) {
                                        val f = it.key.first
                                        val s = it.key.second
                                        val n = it.value

                                        if (curVar.id in (f + 1) until s) {
                                            if (min > s - f) {
                                                min = s - f
                                                num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                                            }
                                        }
                                    }

                                    // Если нашли - достаем из локалки, иначе - из статик поля
                                    if (num != null) {
                                        res += byteArrayOf(0x19.toByte()) // aload
                                        res += num.toByte()
                                    } else {
                                        res += byteArrayOf(0xB2.toByte()) // getstatic
                                        res += currentClass.pushFieldRef("__PROGRAM__", curVar.ident, "L__VALUE__;").to2ByteArray()
                                    }
                                }
                                VarType.VAR -> {
                                    if (curVar.isMapKey) {
                                        res += byteArrayOf(0xBB.toByte()) // NEW
                                        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                                        res += byteArrayOf(0x59) // dub

                                        res += byteArrayOf(0x12.toByte()) // ldc
                                        res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(curVar.ident)))).toByte()

                                        res += byteArrayOf(0xB7.toByte()) // invokespecial
                                        res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
                                    } else {
                                        res += generate(curVar.secondExpr!!, currentClass)
                                    }
                                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                                    res += currentClass.pushMethRef("__VALUE__", "getByKey", "(L__VALUE__;)L__VALUE__;").to2ByteArray()
                                }
                                VarType.FUNCTION_CALL, VarType.ADJUSTED_EXPR -> {
                                    res += generate(curVar.firstExpr!!, currentClass)
                                    if (curVar.isMapKey) {
                                        res += byteArrayOf(0xBB.toByte()) // NEW
                                        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                                        res += byteArrayOf(0x59) // dub

                                        res += byteArrayOf(0x12.toByte()) // ldc
                                        res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(curVar.ident)))).toByte()

                                        res += byteArrayOf(0xB7.toByte()) // invokespecial
                                        res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
                                    } else {
                                        res += generate(curVar.secondExpr!!, currentClass)
                                    }
                                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                                    res += currentClass.pushMethRef("__VALUE__", "getByKey", "(L__VALUE__;)L__VALUE__;").to2ByteArray()
                                }
                                else -> {}
                            }
                        }

                        curVar = curVar.next
                    }
                }

                res += generate(`val`, currentClass)
                if (seqItemId >= 0) {
                    res += byteArrayOf(0x11) // sipush
                    res += seqItemId.to2ByteArray()

                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                    res += currentClass.pushMethRef("__VALUE__", "getFromSeq", "(I)L__VALUE__;").to2ByteArray()
                }
            }

            for (i in vars.indices.reversed()) {
                val `var` = vars[i]

                if (`var`.last.type != VarType.IDENT) {
                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                    res += currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;L__VALUE__;)V").to2ByteArray()
                } else {
                    // Поиск локалки с именем
                    var min = 100000
                    var num : Int? = null
                    for (it in currentClass.localVarsTable.column(`var`.last.ident)) {
                        val f = it.key.first
                        val s = it.key.second
                        val n = it.value

                        if (`var`.last.id in (f + 1) until s) {
                            if (min > s - f) {
                                min = s - f
                                num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                            }
                        }
                    }

                    if (num != null) {
                        res += byteArrayOf(0x3A.toByte()) // astore
                        res += num.toByte()
                    } else {
                        res += byteArrayOf(0xB3.toByte()) // putstatic
                        res += currentClass.pushFieldRef("__PROGRAM__", `var`.last.ident, "L__VALUE__;").to2ByteArray()
                    }
                }
            }

            return res
        }
        StmtType.BREAK -> TODO("Break не делаем)")
        StmtType.DO_LOOP -> { // FIXME? постеститб как работает в оригинале
            return generate(stmtNode.actionBlock!!, currentClass)
        }
        StmtType.WHILE_LOOP -> {
            val condition = generate(stmtNode.conditionExpr!!, currentClass)
            var action = generate(stmtNode.actionBlock!!, currentClass)

            action += byteArrayOf(0xA7.toByte()) // goto
            action += (-action.size-condition.size-5).to2ByteArray() // -5 т.к. 2 бита адрес + 3 бита ifeq

            var res = byteArrayOf()

            res += condition

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I").to2ByteArray()

            res += byteArrayOf(0x99.toByte()) // ifeq
            res += (action.size + 3).to2ByteArray() // +3 т.к. ifeq 3 байта

            res += action

            return res
        }
        StmtType.REPEAT_LOOP -> {
            val action = generate(stmtNode.actionBlock!!, currentClass)
            var condition = generate(stmtNode.conditionExpr!!, currentClass)

            condition += byteArrayOf(0xB6.toByte()) // invokevirtual
            condition += currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I").to2ByteArray()

            var res = byteArrayOf()

            res += action
            res += condition

            res += byteArrayOf(0x99.toByte()) // ifeq
            res += (-action.size-condition.size).to2ByteArray()

            return res
        }
        StmtType.IF -> {
            val condition = generate(stmtNode.conditionExpr!!, currentClass)
            var ifBlock = generate(stmtNode.ifBlock!!, currentClass)

            var res = byteArrayOf()
            var currentJumpSize = 0

            val elseifs = mutableListOf<ByteArray>()
            var current = stmtNode.elseifSeq?.first
            current?.let {
                while (current != null) {
                    elseifs.add(generate(current!!, currentClass))

                    current = current!!.next
                }
            }

            val elseBlock = if (stmtNode.elseBlock != null) generate(stmtNode.elseBlock!!, currentClass) else null
            currentJumpSize += elseBlock?.size ?: 0

            var i = elseifs.size
            while (i > 0) {
                i -= 1

                var elseif = elseifs[i]
                elseif += byteArrayOf(0xA7.toByte()) // goto
                elseif += (currentJumpSize + 3).to2ByteArray() // +3 т.к. goto 3 байта
                elseifs.removeAt(i)
                elseifs.add(i, elseif)
                currentJumpSize += elseif.size
            }

            ifBlock += byteArrayOf(0xA7.toByte()) // goto
            ifBlock += (currentJumpSize + 3).to2ByteArray() // +3 т.к. goto 3 байта

            res += condition

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I").to2ByteArray()

            res += byteArrayOf(0x99.toByte()) // ifeq
            res += (ifBlock.size + 3).to2ByteArray() // +3 т.к. ifeq 3 байта

            res += ifBlock

            elseifs.forEach {
                res += it
            }

            elseBlock?.let {
                res += it
            }

            return res
        }
        StmtType.FOR -> {
            var num = currentClass.localVarsTable.get(Pair(stmtNode.actionBlock?.startID!!, stmtNode.actionBlock?.lastID!!), stmtNode.ident)!!

            if(currentClass.name == "__PROGRAM__") {
                num -= 1
            }

            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

            res += byteArrayOf(0x3A.toByte()) // astore
            res += num.toByte()

            val step = if (stmtNode.stepExpr == null) {
                var tmp = byteArrayOf(0x19.toByte()) // aload
                tmp += num.toByte()

                tmp += byteArrayOf(0xBB.toByte()) // NEW
                tmp += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                tmp += byteArrayOf(0x59) // dub

                tmp += byteArrayOf(0x04.toByte()) // iconst_1

                tmp += byteArrayOf(0xB7.toByte()) // invokespecial
                tmp += currentClass.pushMethRef("__VALUE__", "<init>", "(I)V").to2ByteArray() // MethodRef VALUE Init

                tmp += byteArrayOf(0xB6.toByte()) // invokevirtual
                tmp += currentClass.pushMethRef("__VALUE__", "__add__", "(L__VALUE__;)L__VALUE__;").to2ByteArray()

                tmp += byteArrayOf(0x3A.toByte()) // astore
                tmp += num.toByte()

                tmp
            } else {
                var tmp = byteArrayOf(0x19.toByte()) // aload
                tmp += num.toByte()

                tmp += generate(stmtNode.stepExpr!!, currentClass)

                tmp += byteArrayOf(0xB6.toByte()) // invokevirtual
                tmp += currentClass.pushMethRef("__VALUE__", "__add__", "(L__VALUE__;)L__VALUE__;").to2ByteArray()

                tmp += byteArrayOf(0x3A.toByte()) // astore
                tmp += num.toByte()

                tmp
            }

            val action = generate(stmtNode.actionBlock!!, currentClass)

            val init = generate(stmtNode.initialValue!!, currentClass)
            val limit = generate(stmtNode.conditionExpr!!, currentClass)

            res += init
            res += byteArrayOf(0x59) // dub
            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "checkNumber", "()V").to2ByteArray()

            res += limit
            res += byteArrayOf(0x59) // dub
            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "checkNumber", "()V").to2ByteArray()

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "needJmp", "(L__VALUE__;)I").to2ByteArray()

            if (stmtNode.stepExpr != null) {
                res += generate(stmtNode.stepExpr!!, currentClass)
                res += byteArrayOf(0x59) // dub
                res += byteArrayOf(0x59) // dub
                res += byteArrayOf(0xB6.toByte()) // invokevirtual
                res += currentClass.pushMethRef("__VALUE__", "checkNumber", "()V").to2ByteArray()
                res += byteArrayOf(0xB6.toByte()) // invokevirtual
                res += currentClass.pushMethRef("__VALUE__", "isZero", "()V").to2ByteArray()
                res += byteArrayOf(0xB6.toByte()) // invokevirtual
                res += currentClass.pushMethRef("__VALUE__", "isLessThanZero", "()I").to2ByteArray()

                res += byteArrayOf(0x9D.toByte()) // ifgt
                res += (init.size + action.size + step.size + limit.size + 16).to2ByteArray()
            }

            res += byteArrayOf(0x99.toByte()) // ifeq
            res += (init.size + action.size + step.size + limit.size + 13).to2ByteArray()

            res += init
            res += byteArrayOf(0x3A.toByte()) // astore
            res += num.toByte()

            res += action
            res += step

            res += byteArrayOf(0x19.toByte()) // aload
            res += num.toByte()

            res += limit
            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "needJmp", "(L__VALUE__;)I").to2ByteArray()
            res += byteArrayOf(0x9D.toByte()) // ifgt
            res += (-action.size - step.size - limit.size - 5).to2ByteArray()

            return res
        }
        StmtType.FOREACH -> TODO() // НЕ ДЕЛАЕМ
        StmtType.FUNCTION_DEF -> {
            generateFun(stmtNode)
            val className = "__${stmtNode.ident}__${stmtNode.id}"

            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8(className)))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef(className, "<init>", "()V").to2ByteArray() // MethodRef Init

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "(L__FUN__;)V").to2ByteArray() // MethodRef VALUE Init

            if (stmtNode.isLocal) {
                // Поиск ноиера локалки
                var min = 100000
                var num : Int? = null
                for (it in currentClass.localVarsTable.column(stmtNode.ident)) {
                    val f = it.key.first
                    val s = it.key.second
                    val n = it.value

                    if (stmtNode.id in (f + 1) until s) {
                        if (min > s - f) {
                            min = s - f
                            num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                        }
                    }
                }

                res += byteArrayOf(0x3A.toByte()) // astore
                res += num!!.toByte()

            } else {
                res += byteArrayOf(0xB3.toByte()) // putstatic
                res += currentClass.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;").to2ByteArray()
            }

            return res
        }
        StmtType.VAR_DEF -> {
            if (stmtNode.values == null) {
                var res = byteArrayOf()

                var current: IdentNode? = stmtNode.identList!!.first
                while (current != null) {
                    res += byteArrayOf(0xBB.toByte()) // NEW
                    res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                    res += byteArrayOf(0x59) // dub

                    res += byteArrayOf(0xB7.toByte()) // invokespecial
                    res += currentClass.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

                    // Поиск локалки с именем
                    var min = 100000
                    var num : Int? = null
                    for (it in currentClass.localVarsTable.column(current!!.ident)) {
                        val f = it.key.first
                        val s = it.key.second
                        val n = it.value

                        if (current!!.id in (f + 1) until s) {
                            if (min > s - f) {
                                min = s - f
                                num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                            }
                        }
                    }

                    res += byteArrayOf(0x3A.toByte()) // astore
                    res += num!!.toByte()

                    current = current!!.next
                }

                return res
            }

            val vars = ArrayList<IdentNode>()
            var current: IdentNode? = stmtNode.identList!!.first
            while (current != null) {
                vars.add(current!!)

                current = current!!.next
            }

            val values = ArrayList<ExprNode>()
            var currentt: ExprNode? = stmtNode.values!!.first
            while (currentt != null) {

                if (currentt.next == null) {
                    values.add(currentt)
                } else {
                    values.add(ExprNode.createAdjustingExprNode(currentt))
                }

                currentt = currentt.next
            }

            var res = byteArrayOf()

            for (i in vars.indices) {
                val `var` = vars[i];
                val `val` = if (i < values.size) values[i] else values.last()
                val seqItemId = i - values.lastIndex

                res += generate(`val`, currentClass)
                if (seqItemId >= 0) {
                    res += byteArrayOf(0x11) // sipush
                    res += seqItemId.to2ByteArray()

                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                    res += currentClass.pushMethRef("__VALUE__", "getFromSeq", "(I)L__VALUE__;").to2ByteArray()
                }
            }

            for (i in vars.indices.reversed()) {
                val item = vars[i]

                // Поиск локалки с именем
                var min = 100000
                var num : Int? = null
                for (it in currentClass.localVarsTable.column(item.ident)) {
                    val f = it.key.first
                    val s = it.key.second
                    val n = it.value

                    if (item.id in (f + 1) until s) {
                        if (min > s - f) {
                            min = s - f
                            num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                        }
                    }
                }

                res += byteArrayOf(0x3A.toByte()) // astore
                res += num!!.toByte()
            }

            return res
        }
        StmtType.RETURN -> {
            if (stmtNode.values == null) {
                var res = byteArrayOf()

//                res += byteArrayOf(0xBB.toByte()) // NEW
//                res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
//                res += byteArrayOf(0x59) // dub

//                res += byteArrayOf(0xB7.toByte()) // invokespecial
//                res += currentClass.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init
                res += byteArrayOf(0xB8.toByte()) // invokestatic
                res += currentClass.pushMethRef("__VALUE__", "voidVal", "()L__VALUE__;").to2ByteArray() // MethodRef VALUE Init

                res += 0xB0.toByte() // areturn

                return res
            } else {
                var res = byteArrayOf()

                res += byteArrayOf(0xBB.toByte()) // NEW
                res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                res += byteArrayOf(0x59) // dub

                res += byteArrayOf(0xBB.toByte()) // NEW
                res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList")))).to2ByteArray()
                res += byteArrayOf(0x59) // dub

                res += byteArrayOf(0xB7.toByte()) // invokespecial
                res += currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

                var current: ExprNode? = stmtNode.values!!.first
                while (current != null) {
                    res += byteArrayOf(0x59) // dub

                    res += generate(current!!, currentClass)

                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                    res += currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z").to2ByteArray()

                    res += byteArrayOf(0x57) // POP add result

                    current = current!!.next
                }

                res += byteArrayOf(0xB7.toByte()) // invokespecial
                res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V").to2ByteArray() // MethodRef VALUE Init

                res += 0xB0.toByte() // areturn

                return res
            }
        }
    }
}

private fun generate(exprNode: ExprNode, currentClass: ClassModel): ByteArray {
    when (exprNode.type) {
        ExprType.STRING -> {
            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0x12.toByte()) // ldc
            res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(exprNode.stringValue)))).toByte()

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init

            return res
        }
        ExprType.INT_NUMBER -> {
            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0x12.toByte()) // ldc
            res += currentClass.pushConstant(Constant.integer(exprNode.intNumberValue)).toByte()

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "(I)V").to2ByteArray() // MethodRef VALUE Init

            return res
        }
        ExprType.BOOLEAN -> {
            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0x12.toByte()) // ldc
            res += currentClass.pushConstant(Constant.integer(if(exprNode.boolValue) 1 else 0)).toByte()

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "(Z)V").to2ByteArray() // MethodRef VALUE Init

            return res
        }
        ExprType.PLUS,
        ExprType.MINUS,
        ExprType.MUL ,
        ExprType.DIV,
        ExprType.FLOOR_DIV,
        ExprType.POW,
        ExprType.XOR,
        ExprType.MOD,
        ExprType.BIT_AND,
        ExprType.BIT_OR,
        ExprType.CONCAT,
        ExprType.LESS,
        ExprType.LE,
        ExprType.GREATER,
        ExprType.GE,
        ExprType.EQUAL,
        ExprType.NOT_EQUAL,
        ExprType.LOG_AND,
        ExprType.LOG_OR -> {
            var res = byteArrayOf()

            res += generate(exprNode.firstOperand!!, currentClass)
            res += generate(exprNode.secondOperand!!, currentClass)

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "(L__VALUE__;)L__VALUE__;").to2ByteArray()
            return res
        }
        ExprType.UNARY_MINUS,
        ExprType.NOT,
        ExprType.LEN,
        ExprType.BIT_NOT -> {
            var res = byteArrayOf()

            res += generate(exprNode.firstOperand!!, currentClass)

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "()L__VALUE__;").to2ByteArray()
            return res
        }
        ExprType.ADJUST -> {
            var res = byteArrayOf()

            res += generate(exprNode.adjustedExpr!!, currentClass)

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "()L__VALUE__;").to2ByteArray()
            return res
        }
        ExprType.FUNCTION_CALL -> {
            when (exprNode.ident) {
                "print" -> {
                    var res = byteArrayOf()

                    if (exprNode.args != null) {

                        res += byteArrayOf(0xBB.toByte()) // NEW
                        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList")))).to2ByteArray()
                        res += byteArrayOf(0x59) // dub

                        res += byteArrayOf(0xB7.toByte()) // invokespecial
                        res += currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

                        var current: ExprNode? = exprNode.args!!.first
                        while (current != null) {
                            res += byteArrayOf(0x59) // dub

                            res += generate(current, currentClass)

                            res += byteArrayOf(0xB6.toByte()) // invokevirtual
                            res += currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z").to2ByteArray()

                            res += byteArrayOf(0x57) // POP add result

                            current = current.next
                        }

                        //res += generate(exprNode.args!!, currentClass)
                    } else {
                        res += byteArrayOf(0xBB.toByte()) // NEW
                        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).toByte()
                        res += byteArrayOf(0x59) // dub

                        res += byteArrayOf(0x12.toByte()) // ldc
                        res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8("")))).toByte()

                        res += byteArrayOf(0xB7.toByte()) // invokespecial
                        res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
                    }

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "print", "(Ljava/util/ArrayList;)V").to2ByteArray()
                    return res
                }
                "read" -> {
                    var res = byteArrayOf()

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "read", "()L__VALUE__;").to2ByteArray()

                    return res
                }
                "error" -> {
                    var res = byteArrayOf()

                    res += if (exprNode.args != null) {
                        generate(exprNode.args!!.first, currentClass)
                    } else {
                        generate(ExprNode.createNilExprNode(), currentClass)
                    }

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "error", "(L__VALUE__;)V").to2ByteArray()

                    return res
                }
                "assert" -> {
                    var res = byteArrayOf()

                    if (exprNode.args != null) {
                        res += generate(exprNode.args!!.first, currentClass)
                        res += if(exprNode.args!!.first.next != null) {
                            generate(exprNode.args!!.first.next!!, currentClass)
                        } else {
                            generate(ExprNode.createNilExprNode(), currentClass)
                        }
                    } else {
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                    }

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "__assert__", "(L__VALUE__;L__VALUE__;)L__VALUE__;").to2ByteArray()

                    return res
                }
                "pcall" -> {
                    var res = byteArrayOf()

                    res += if (exprNode.args != null) {
                        generate(exprNode.args!!.first, currentClass)
                    } else {
                        generate(ExprNode.createNilExprNode(), currentClass)
                    }

                    res += byteArrayOf(0xBB.toByte()) // NEW
                    res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList")))).to2ByteArray()
                    res += byteArrayOf(0x59) // dub

                    res += byteArrayOf(0xB7.toByte()) // invokespecial
                    res += currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

                    var current: ExprNode? = exprNode.args!!.first.next
                    while (current != null) {
                        res += byteArrayOf(0x59) // dub

                        res += generate(current, currentClass)

                        res += byteArrayOf(0xB6.toByte()) // invokevirtual
                        res += currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z").to2ByteArray()

                        res += byteArrayOf(0x57) // POP add result

                        current = current.next
                    }

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "pcall", "(L__VALUE__;Ljava/util/ArrayList;)L__VALUE__;").to2ByteArray()

                    return res
                }
                "xpcall" -> {
                    var res = byteArrayOf()

                    if (exprNode.args != null) {
                        res += generate(exprNode.args!!.first, currentClass)
                        res += if(exprNode.args!!.first.next != null) {
                            generate(exprNode.args!!.first.next!!, currentClass)
                        } else {
                            generate(ExprNode.createNilExprNode(), currentClass)
                        }
                    } else {
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                    }

                    res += byteArrayOf(0xBB.toByte()) // NEW
                    res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList")))).to2ByteArray()
                    res += byteArrayOf(0x59) // dub

                    res += byteArrayOf(0xB7.toByte()) // invokespecial
                    res += currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

                    var current: ExprNode? = exprNode.args!!.first.next?.next
                    while (current != null) {
                        res += byteArrayOf(0x59) // dub

                        res += generate(current, currentClass)

                        res += byteArrayOf(0xB6.toByte()) // invokevirtual
                        res += currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z").to2ByteArray()

                        res += byteArrayOf(0x57) // POP add result

                        current = current.next
                    }

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "xpcall", "(L__VALUE__;L__VALUE__;Ljava/util/ArrayList;)L__VALUE__;").to2ByteArray()

                    return res
                }
                "append" -> {
                    var res = byteArrayOf()

                    if (exprNode.args != null) {
                        res += generate(exprNode.args!!.first, currentClass)
                        res += if(exprNode.args!!.first.next != null) {
                            generate(exprNode.args!!.first.next!!, currentClass)
                        } else {
                            generate(ExprNode.createNilExprNode(), currentClass)
                        }
                    } else {
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                    }

                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                    res += currentClass.pushMethRef("__VALUE__", "append", "(L__VALUE__;)V").to2ByteArray()

                    return res
                }
                "setmetatable" -> {
                    var res = byteArrayOf()

                    if (exprNode.args != null) {
                        res += generate(exprNode.args!!.first, currentClass)
                        res += if(exprNode.args!!.first.next != null) {
                            generate(exprNode.args!!.first.next!!, currentClass)
                        } else {
                            generate(ExprNode.createNilExprNode(), currentClass)
                        }
                    } else {
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                        res += generate(ExprNode.createNilExprNode(), currentClass)
                    }

                    res += byteArrayOf(0xB8.toByte()) // invokestatic
                    res += currentClass.pushMethRef("__VALUE__", "setmetatable", "(L__VALUE__;L__VALUE__;)V").to2ByteArray()

                    return res
                }
                else -> {
                    var res = byteArrayOf()

                    // Поиск локалки с именем функции
                    var min = 100000
                    var num : Int? = null
                    for (it in currentClass.localVarsTable.column(exprNode.ident)) {
                        val f = it.key.first
                        val s = it.key.second
                        val n = it.value

                        if (exprNode.id in (f + 1) until s) {
                            if (min > s - f) {
                                min = s - f
                                num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                            }
                        }
                    }

                    // Если нашли - достаем функцию из локалки, иначе - из статик поля
                    if (num != null) {
                        res += byteArrayOf(0x19.toByte()) // aload
                        res += num.toByte()
                    } else {
                        res += byteArrayOf(0xB2.toByte()) // getstatic
                        res += currentClass.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;").to2ByteArray()
                    }

                    // Создаем аррей лист
                    res += byteArrayOf(0xBB.toByte()) // NEW
                    res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList")))).to2ByteArray()
                    res += byteArrayOf(0x59) // dub

                    res += byteArrayOf(0xB7.toByte()) // invokespecial
                    res += currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

                    // Пишем все аргументы в аррей лист
                    var current: ExprNode? = exprNode.args?.first
                    while (current != null) {
                        res += byteArrayOf(0x59) // dub

                        res += generate(current, currentClass)

                        res += byteArrayOf(0xB6.toByte()) // invokevirtual
                        res += currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z").to2ByteArray()

                        res += byteArrayOf(0x57) // POP add result

                        current = current.next
                    }

                    // Вызываем функцию
                    res += byteArrayOf(0xB6.toByte()) // invokevirtual
                    res += currentClass.pushMethRef("__VALUE__", "__invoke__", "(Ljava/util/ArrayList;)L__VALUE__;").to2ByteArray()

                    return res
                }
            }
        }
        ExprType.UNINITIALIZED -> {
            return byteArrayOf()
        }
        ExprType.NIL -> {
            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

            return res
        }
        ExprType.FLOAT_NUMBER -> {
            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0x12.toByte()) // ldc
            res += currentClass.pushConstant(Constant.float(exprNode.floatNumberValue.toFloat())).toByte()

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "(F)V").to2ByteArray() // MethodRef VALUE Init

            return res
        }
        ExprType.VAR_ARG -> { // FIXME?: не уверен
            var res = byteArrayOf()

            var min = 100000
            var num : Int? = null
            for (it in currentClass.localVarsTable.column("...")) {
                val f = it.key.first
                val s = it.key.second
                val n = it.value

                if (exprNode.id in (f + 1) until s) {
                    if (min > s - f) {
                        min = s - f
                        num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                    }
                }
            }

            res += byteArrayOf(0x19.toByte()) // aload
            res += num!!.toByte()

            return res
        }
        ExprType.VAR -> {
            return generate(exprNode.varNode!!, currentClass)
        }
        ExprType.TABLE_CONSTRUCTOR -> {
            var res = byteArrayOf()

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0xBB.toByte()) // NEW
            res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/HashMap")))).to2ByteArray()
            res += byteArrayOf(0x59) // dub

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("java/util/HashMap", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

            res += byteArrayOf(0xB7.toByte()) // invokespecial
            res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/util/HashMap;)V").to2ByteArray() // MethodRef VALUE Init

            exprNode.tableConstructor?.let {
                res += generate(exprNode.tableConstructor!!, currentClass)
            }

            return res
        }
    }
}

private fun generate(exprSeqNode: ExprSeqNode, currentClass: ClassModel): ByteArray {
    var current: ExprNode? = exprSeqNode.first
    var res = byteArrayOf()
    while (current != null) {
        res += generate(current, currentClass)

        current = current.next
    }

    return res
}

private fun generate(varItemNode: VarItemNode, currentClass: ClassModel) : ByteArray {
    var res = byteArrayOf()

    when (varItemNode.type) {
        VarType.IDENT -> {
            // Поиск локалки с именем
            var min = 100000
            var num : Int? = null
            for (it in currentClass.localVarsTable.column(varItemNode.ident)) {
                val f = it.key.first
                val s = it.key.second
                val n = it.value

                if (varItemNode.id in (f + 1) until s) {
                    if (min > s - f) {
                        min = s - f
                        num = if (currentClass.name == "__PROGRAM__") n - 1 else n
                    }
                }
            }

            // Если нашли - достаем из локалки, иначе - из статик поля
            if (num != null) {
                res += byteArrayOf(0x19.toByte()) // aload
                res += num.toByte()
            } else {
                res += byteArrayOf(0xB2.toByte()) // getstatic
                res += currentClass.pushFieldRef("__PROGRAM__", varItemNode.ident, "L__VALUE__;").to2ByteArray()
            }
        }
        VarType.VAR -> {
            if (varItemNode.isMapKey) {
                res += byteArrayOf(0xBB.toByte()) // NEW
                res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                res += byteArrayOf(0x59) // dub

                res += byteArrayOf(0x12.toByte()) // ldc
                res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(varItemNode.ident)))).toByte()

                res += byteArrayOf(0xB7.toByte()) // invokespecial
                res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
            } else {
                res += generate(varItemNode.secondExpr!!, currentClass)
            }
            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "getByKey", "(L__VALUE__;)L__VALUE__;").to2ByteArray()
        }
        VarType.FUNCTION_CALL, VarType.ADJUSTED_EXPR -> {
            res += generate(varItemNode.firstExpr!!, currentClass)
            if (varItemNode.isMapKey) {
                res += byteArrayOf(0xBB.toByte()) // NEW
                res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
                res += byteArrayOf(0x59) // dub

                res += byteArrayOf(0x12.toByte()) // ldc
                res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(varItemNode.ident)))).toByte()

                res += byteArrayOf(0xB7.toByte()) // invokespecial
                res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init
            } else {
                res += generate(varItemNode.secondExpr!!, currentClass)
            }
            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", "getByKey", "(L__VALUE__;)L__VALUE__;").to2ByteArray()
        }
        else -> {}
    }

    return res
}

private fun generate(varNode: VarNode, currentClass: ClassModel) : ByteArray {
    var current: VarItemNode? = varNode.first
    var res = byteArrayOf()
    while (current != null) {
        res += generate(current, currentClass)

        current = current.next
    }

    return res
}

private fun generate(fieldNode: FieldNode, currentClass: ClassModel) : ByteArray {
    var res = byteArrayOf(0x59) // dub

    if(fieldNode.ident != null) {
        // IDENT KEY
        res += byteArrayOf(0xBB.toByte()) // NEW
        res += currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
        res += byteArrayOf(0x59) // dub

        res += byteArrayOf(0x12.toByte()) // ldc
        res += currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(fieldNode.ident!!)))).toByte()

        res += byteArrayOf(0xB7.toByte()) // invokespecial
        res += currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V").to2ByteArray() // MethodRef VALUE Init

        // VALUE
        res += generate(fieldNode.value!!, currentClass)

        res += byteArrayOf(0xB6.toByte()) // invokevirtual
        res += currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;L__VALUE__;)V").to2ByteArray()
    }
    else if(fieldNode.key != null) {
        // KEY
        res += generate(fieldNode.key!!, currentClass)

        // VALUE
        res += generate(fieldNode.value!!, currentClass)

        res += byteArrayOf(0xB6.toByte()) // invokevirtual
        res += currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;L__VALUE__;)V").to2ByteArray()
    }
    else {
        // VALUE
        res += generate(fieldNode.value!!, currentClass)

        res += byteArrayOf(0xB6.toByte()) // invokevirtual
        res += currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;)V").to2ByteArray()
    }

    return res
}

private fun generate(fieldListNode: FieldListNode, currentClass: ClassModel) : ByteArray {
    var current: FieldNode? = fieldListNode.first
    var res = byteArrayOf()
    while (current != null) {
        res += generate(current, currentClass)

        current = current.next
    }
    return res
}
