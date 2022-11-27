import nodes.ChunkNode
import java.io.File

fun Int.to2ByteArray() : ByteArray = byteArrayOf(shr(8).toByte(), toByte())

fun Int.to4ByteArray() : ByteArray = byteArrayOf(toByte(), shr(8).toByte(), shr(16).toByte(), shr(24).toByte())

fun Float.to4ByteArray() : ByteArray = byteArrayOf(toInt().toByte(), toInt().shr(8).toByte(), toInt().shr(16).toByte(), toInt().shr(24).toByte())

fun generateProgram(program: ChunkNode) {
    val className = "__PROGRAM__"
    val classModel = classesTable[className]!!


    // ----------------

    classModel.pushFieldRef("java/lang/System", "out", "Ljava/io/PrintStream;")
    classModel.pushConstant(Constant.string(classModel.pushConstant(Constant.utf8("Hello world!"))))
    classModel.pushMethRef("java/io/PrintStream", "println", "(Ljava/lang/String;)V")

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

    // Length TODO
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x15))

    // Stack size (max)
    file.appendBytes(byteArrayOf(0xFF.toByte(), 0xFF.toByte()))

    // Locals count TODO
    file.appendBytes(byteArrayOf(0x00, 0x01))

    // Bytecode length TODO
    file.appendBytes(byteArrayOf(0x00, 0x00, 0x00, 0x09))

    // Bytecode TODO
    file.appendBytes(byteArrayOf(0xB2.toByte())) // getstatic
    file.appendBytes(classModel.pushFieldRef("java/lang/System", "out", "Ljava/io/PrintStream;").to2ByteArray())
    file.appendBytes(byteArrayOf(0x12)) // ldc
    file.appendBytes(byteArrayOf(classModel.pushConstant(Constant.string(classModel.pushConstant(Constant.utf8("Hello world!")))).toByte()))
    file.appendBytes(byteArrayOf(0xB6.toByte())) // invokevirtual
    file.appendBytes(classModel.pushMethRef("java/io/PrintStream", "println", "(Ljava/lang/String;)V").to2ByteArray())
    file.appendBytes(byteArrayOf(0xB1.toByte())) // return

    // Exceptions table length (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // Code attrs count (always 0)
    file.appendBytes(byteArrayOf(0x00, 0x00))

    // -----------------------------------------------------

    // Class attrs count
    file.appendBytes(byteArrayOf(0x00, 0x00))
}
