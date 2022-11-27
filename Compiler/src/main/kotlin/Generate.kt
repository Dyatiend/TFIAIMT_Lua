import nodes.*
import java.io.File

fun Int.to2ByteArray() : ByteArray = byteArrayOf(shr(8).toByte(), toByte())

fun Int.to4ByteArray() : ByteArray = byteArrayOf(shr(24).toByte(), shr(16).toByte(), shr(8).toByte(), toByte())

fun Float.to4ByteArray() : ByteArray = byteArrayOf(toInt().shr(24).toByte(), toInt().shr(16).toByte(), toInt().shr(8).toByte(), toInt().toByte())

fun generateProgram(program: ChunkNode) {
    val className = "__PROGRAM__"
    val classModel = classesTable[className]!!


    // ----------------

//    classModel.pushFieldRef("java/lang/System", "out", "Ljava/io/PrintStream;")
//    classModel.pushConstant(Constant.string(classModel.pushConstant(Constant.utf8("Hello world!"))))
//    classModel.pushMethRef("java/io/PrintStream", "println", "(Ljava/lang/String;)V")

    // ----------------


    val file = File("out/production/Compiler/$className.class")
    file.writeText("")

    // CAFEBABE
    file.appendBytes(byteArrayOf(0xCA.toByte(), 0xFE.toByte(), 0xBA.toByte(), 0xBE.toByte()))

    // Version
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x3F))

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
        // Flags (PUBLIC)
        file.appendBytes(byteArrayOf(0x00, 0x01))

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

    // Bytecode TODO
    val code = generate(program.block, classModel) + 0xB1.toByte() // return

    // Length
    file.appendBytes((code.size + 12).to4ByteArray())

    // Stack size (max)
    file.appendBytes(byteArrayOf(0xFF.toByte(), 0xFF.toByte()))

    // Locals count TODO
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Bytecode length
    file.appendBytes((code.size).to4ByteArray())

    // Bytecode TODO
    file.appendBytes(code)
//    file.appendBytes(byteArrayOf(0xB2.toByte())) // getstatic
//    file.appendBytes(classModel.pushFieldRef("java/lang/System", "out", "Ljava/io/PrintStream;").to2ByteArray())
//    file.appendBytes(byteArrayOf(0x12)) // ldc
//    file.appendBytes(byteArrayOf(classModel.pushConstant(Constant.string(classModel.pushConstant(Constant.utf8("Hello world!")))).toByte()))
//    file.appendBytes(byteArrayOf(0xB6.toByte())) // invokevirtual
//    file.appendBytes(classModel.pushMethRef("java/io/PrintStream", "println", "(Ljava/lang/String;)V").to2ByteArray())
//    file.appendBytes(byteArrayOf(0xB1.toByte())) // return

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
        else -> {
            return byteArrayOf()
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
        ExprType.ADJUST -> {
            var res = byteArrayOf()

            res += generate(exprNode.adjustedExpr!!, currentClass)

            res += byteArrayOf(0xB6.toByte()) // invokevirtual
            res += currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "()L__VALUE__;").to2ByteArray()
            return res
        }
        ExprType.FUNCTION_CALL -> {
            if (exprNode.ident == "print") {
                var res = byteArrayOf()

                if (exprNode.args != null) {
                    res += generate(exprNode.args!!, currentClass)
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
                res += currentClass.pushMethRef("__VALUE__", "print", "(L__VALUE__;)V").to2ByteArray()
                return res
            }
            return byteArrayOf()
        }
        else -> {
            return byteArrayOf()
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
