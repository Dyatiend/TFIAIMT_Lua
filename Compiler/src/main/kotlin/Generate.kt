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
    val code = generate(program.block, classModel) + 0xB1.toByte() // return

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

            code += byteArrayOf(0x2B) // aload_1 (args)

            code += byteArrayOf(0x11) // sipush
            code += argId.to2ByteArray()

            code += byteArrayOf(0xB6.toByte()) // invokevirtual
            code += classModel.pushMethRef("java/util/ArrayList", "get", "(I)Ljava/lang/Object;").to2ByteArray()

            code += byteArrayOf(0xC0.toByte()) // checkcast
            code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()

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

        code += byteArrayOf(0x2B) // aload_1 (args)

        code += byteArrayOf(0x11) // sipush
        code += argId.to2ByteArray()

        code += byteArrayOf(0x2B) // aload_1 (args)

        code += byteArrayOf(0xB6.toByte()) // invokevirtual
        code += classModel.pushMethRef("java/util/ArrayList", "size", "()I").to2ByteArray()

        code += byteArrayOf(0xB6.toByte()) // invokevirtual
        code += classModel.pushMethRef("java/util/ArrayList", "subList", "(II)Ljava/util/List;").to2ByteArray()

        code += byteArrayOf(0xB7.toByte()) // invokespecial
        code += classModel.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V").to2ByteArray() // MethodRef VALUE Init

        val num = classModel.localVarsTable.get(Pair(stmtNode.actionBlock?.startID!!, stmtNode.actionBlock?.lastID!!), "...")!!

        code += byteArrayOf(0x3A) // astore
        code += num.toByte()
    }

    // Генерим тело
    code += generate(stmtNode.actionBlock!!, classModel)

    // Return Nil по умолчанию, если нет другого ретурна раньше
    code += byteArrayOf(0xBB.toByte()) // NEW
    code += classModel.pushConstant(Constant._class(classModel.pushConstant(Constant.utf8("__VALUE__")))).to2ByteArray()
    code += byteArrayOf(0x59) // dub

    code += byteArrayOf(0xB7.toByte()) // invokespecial
    code += classModel.pushMethRef("__VALUE__", "<init>", "()V").to2ByteArray() // MethodRef VALUE Init

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
        StmtType.ASSIGNMENT -> TODO()
        StmtType.BREAK -> TODO()
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

            res += byteArrayOf(0x9A.toByte()) // ifne
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
        StmtType.FOR -> TODO()
        StmtType.FOREACH -> TODO()
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
                            num = n
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
        StmtType.VAR_DEF -> TODO()
        StmtType.RETURN -> TODO()
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
                    TODO()
                }
                "assert" -> {
                    TODO()
                }
                "pcall" -> {
                    TODO()
                }
                "xpcall" -> {
                    TODO()
                }
                "append" -> {
                    TODO()
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
                                num = n
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
                    var current: ExprNode? = exprNode.args!!.first
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
        ExprType.VAR_ARG -> { // TODO()
            // ХАРДКОД ДЛЯ ТЕСТОВ FIXME
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
                        num = n
                    }
                }
            }

            res += byteArrayOf(0x19.toByte()) // aload
            res += num!!.toByte()

            return res
        }
        ExprType.VAR -> { // TODO()
            // ХАРДКОД ДЛЯ ТЕСТОВ FIXME
            var res = byteArrayOf()

            var min = 100000
            var num : Int? = null
            for (it in currentClass.localVarsTable.column(exprNode.varNode?.first?.ident)) {
                val f = it.key.first
                val s = it.key.second
                val n = it.value

                if (exprNode.id in (f + 1) until s) {
                    if (min > s - f) {
                        min = s - f
                        num = n
                    }
                }
            }

            res += byteArrayOf(0x19.toByte()) // aload
            res += num!!.toByte()

            return res
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
    TODO()
}

private fun generate(varNode: VarNode, currentClass: ClassModel) : ByteArray {
    TODO()
}

private fun generate(identNode: IdentNode, currentClass: ClassModel) : ByteArray {
    TODO()
}

private fun generate(identListNode: IdentListNode, currentClass: ClassModel) : ByteArray {
    TODO()
}

private fun generate(paramListNode: ParamListNode, currentClass: ClassModel): ByteArray {
    TODO()
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
