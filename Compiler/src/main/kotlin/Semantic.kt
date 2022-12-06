import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import nodes.*
import java.io.File
import kotlin.properties.Delegates

enum class ConstantType {
    CONSTANT_Utf8,
    CONSTANT_Integer,
    CONSTANT_Float,
    CONSTANT_String,
    CONSTANT_NameAndType,
    CONSTANT_Class,
    CONSTANT_Fieldref,
    CONSTANT_Methodref
}

data class Constant(
    var type: ConstantType = ConstantType.CONSTANT_Utf8,
    var intVal: Int = 0,
    var floatVal: Float = 0f,
    var stringVal: String = "",

    var utf8Id: Int = 0,
    var typeId: Int = 0,
    var nameId: Int = 0,
    var classNameId:Int = 0,
    var nameAndTypeId: Int = 0,
    var classId: Int = 0
) {
    fun write(file: File) {
        when (type) {
            ConstantType.CONSTANT_Utf8 -> {
                file.appendBytes(byteArrayOf(0x01))
                file.appendBytes(stringVal.length.to2ByteArray())
                file.appendBytes(stringVal.toByteArray())
            }
            ConstantType.CONSTANT_Integer -> {
                file.appendBytes(byteArrayOf(0x03))
                file.appendBytes(intVal.to4ByteArray())
            }
            ConstantType.CONSTANT_Float -> {
                file.appendBytes(byteArrayOf(0x04))
                file.appendBytes(floatVal.to4ByteArray())
            }
            ConstantType.CONSTANT_String -> {
                file.appendBytes(byteArrayOf(0x08))
                file.appendBytes(utf8Id.to2ByteArray())
            }
            ConstantType.CONSTANT_NameAndType -> {
                file.appendBytes(byteArrayOf(0x0C))
                file.appendBytes(nameId.to2ByteArray())
                file.appendBytes(typeId.to2ByteArray())
            }
            ConstantType.CONSTANT_Class -> {
                file.appendBytes(byteArrayOf(0x07))
                file.appendBytes(classNameId.to2ByteArray())
            }
            ConstantType.CONSTANT_Fieldref -> {
                file.appendBytes(byteArrayOf(0x09))
                file.appendBytes(classId.to2ByteArray())
                file.appendBytes(nameAndTypeId.to2ByteArray())
            }
            ConstantType.CONSTANT_Methodref -> {
                file.appendBytes(byteArrayOf(0x0A))
                file.appendBytes(classId.to2ByteArray())
                file.appendBytes(nameAndTypeId.to2ByteArray())
            }
        }
    }

    companion object {
        fun utf8(str: String): Constant {
            return Constant(type = ConstantType.CONSTANT_Utf8, stringVal = str)
        }

        fun integer(value: Int): Constant {
            return Constant(type = ConstantType.CONSTANT_Integer, intVal = value)
        }

        fun float(value: Float): Constant {
            return Constant(type = ConstantType.CONSTANT_Float, floatVal = value)
        }

        fun string(utf8Id: Int): Constant {
            return Constant(type = ConstantType.CONSTANT_String, utf8Id = utf8Id)
        }

        fun _class(utf8Id: Int): Constant {
            return Constant(type = ConstantType.CONSTANT_Class, classNameId = utf8Id)
        }

        fun fieldRef(classId: Int, nameAndTypeId: Int): Constant {
            return Constant(type = ConstantType.CONSTANT_Fieldref, classId = classId, nameAndTypeId = nameAndTypeId)
        }

        fun methodRef(classId: Int, nameAndTypeId: Int): Constant {
            return Constant(type = ConstantType.CONSTANT_Methodref, classId = classId, nameAndTypeId = nameAndTypeId)
        }

        fun nameAndType(nameId: Int, descriptorId: Int): Constant {
            return Constant(type = ConstantType.CONSTANT_NameAndType, nameId = nameId, typeId = descriptorId)
        }
    }
}

val classesTable = HashMap<String, ClassModel>()

fun HashMap<String, ClassModel>.toCSV() {
    classesTable.forEach {
        it.value.localsToCSV()
        it.value.constantsToCSV()
    }
}

class ClassModel(val name: String) {

    var constantsTable: MutableMap<Constant, Int> = HashMap()
    var localVarsTable: Table<Pair<Int, Int>, String, Int> = HashBasedTable.create()
    var globalVarsTable: MutableMap<String, Int> = HashMap()

    init {
        pushConstant(Constant.utf8("Code"))
        pushConstant(
            Constant._class(
                pushConstant(Constant.utf8(name))
            )
        )
        pushConstant(
            Constant._class(
                pushConstant(Constant.utf8("__VALUE__"))
            )
        )
        pushConstant(
            Constant._class(
                pushConstant(Constant.utf8("__FUN__"))
            )
        )
//        pushConstant(
//            Constant._class(
//                pushConstant(Constant.utf8("Ljava/lang/Object;"))
//            )
//        )
        pushConstant(
            Constant._class(
                pushConstant(Constant.utf8("java/lang/Object"))
            )
        )
        pushConstant( // Пустая константа для пустого print
            Constant.string(
                pushConstant(Constant.utf8(""))
            )
        )

        pushMethRef("__VALUE__", "<init>", "()V")
        pushMethRef(name, "<init>", "()V")
        pushMethRef("java/lang/Object", "<init>", "()V")
    }

    private var constantID = 0
        get() = ++field

    private var localVarID = 0
        get() = ++field

    private var globalVarID = 0
        get() = ++field

    fun pushLocalVar(scope: Pair<Int, Int>, name: String) {
        localVarsTable.put(scope, name, localVarID)
    }

    fun pushGlobalVar(name: String) {
        globalVarsTable[name] = globalVarID
    }

    fun pushConstant(constant: Constant): Int {
        if (constantsTable.containsKey(constant)) {
            return constantsTable[constant]!!
        }
        val id = constantID
        constantsTable[constant] = id
        return id
    }

    fun pushFieldRef(className: String, fieldName: String, type: String): Int {
        val name = this.pushConstant(Constant.utf8(fieldName))
        val typeId = this.pushConstant(Constant.utf8(type))
        val nameAndType = this.pushConstant(Constant.nameAndType(name, typeId))
        val _class = this.pushConstant(Constant._class(this.pushConstant(Constant.utf8(className))))
        return this.pushConstant(Constant.fieldRef(_class, nameAndType))
    }

    fun pushMethRef(className: String, fieldName: String, type: String): Int {
        val name = this.pushConstant(Constant.utf8(fieldName))
        val typeId = this.pushConstant(Constant.utf8(type))
        val nameAndType = this.pushConstant(Constant.nameAndType(name, typeId))
        val _class = this.pushConstant(Constant._class(this.pushConstant(Constant.utf8(className))))
        return this.pushConstant(Constant.methodRef(_class, nameAndType))
    }

    fun _contains(name: String, id: Int): Boolean {
        localVarsTable.column(name).forEach{
            val start = it.key.first
            val end = it.key.second
            if (id in (start + 1) until end) {
                return true
            }
        }
        return false
    }

    fun constantsToCSV() {
        val file = File("${name}_constants.csv")
        file.writeText("ID,Type,Value\n")

        constantsTable.forEach {
            val key = it.key
            val value = it.value

            when (key.type) {
                ConstantType.CONSTANT_Utf8 -> {
                    file.appendText("${value},${key.type},${key.stringVal}\n")
                }
                ConstantType.CONSTANT_Integer -> {
                    file.appendText("${value},${key.type},${key.intVal}\n")
                }
                ConstantType.CONSTANT_Float -> {
                    file.appendText("${value},${key.type},${key.floatVal}\n")
                }
                ConstantType.CONSTANT_String -> {
                    file.appendText("${value},${key.type},${key.utf8Id}\n")
                }
                ConstantType.CONSTANT_NameAndType -> {
                    file.appendText("${value},${key.type},${key.nameId};${key.typeId}\n")
                }
                ConstantType.CONSTANT_Class -> {
                    file.appendText("${value},${key.type},${key.classNameId}\n")
                }
                ConstantType.CONSTANT_Fieldref -> {
                    file.appendText("${value},${key.type},${key.classId};${key.nameAndTypeId}\n")
                }
                ConstantType.CONSTANT_Methodref -> {
                    file.appendText("${value},${key.type},${key.classId};${key.nameAndTypeId}\n")
                }
            }
        }
    }

    fun localsToCSV() {
        val file = File("${name}_locals.csv")
        file.writeText("ID,Name,Start,End\n")

        localVarsTable.cellSet().forEach {
            val col = it.columnKey
            val row = it.rowKey
            val value = it.value

            file.appendText("$value, $col, ${row.first}, ${row.second}\n")
        }
    }
}

var globalScopeStartId by Delegates.notNull<Int>()
var globalScopeEndId by Delegates.notNull<Int>()
var globalProgramClass by Delegates.notNull<ClassModel>()

fun fillTables(program: ChunkNode) {

    val programClass = ClassModel("__PROGRAM__")
    globalProgramClass = programClass

    classesTable["__PROGRAM__"] = programClass

    programClass.pushConstant(Constant.utf8("main"))
    programClass.pushConstant(Constant.utf8("([Ljava/lang/String;)V"))
    programClass.pushMethRef("__PROGRAM__", "main", "([Ljava/lang/String;)V")

    programClass.pushLocalVar(Pair(program.block.startID, program.block.lastID), "args")

    globalScopeStartId = program.block.startID
    globalScopeEndId = program.block.lastID

    fillTables(program.block, programClass)

    programClass.pushMethRef("__PROGRAM__", "<init>", "()V")
    programClass.pushMethRef("java/lang/Object", "<init>", "()V")
}

private fun fillTables(stmtSeqNode: StmtSeqNode, currentClass: ClassModel) {
    var current = stmtSeqNode.first
    current?.let {
        var prevID = stmtSeqNode.startID
        while (current != null) {
            fillTables(current!!, currentClass, prevID, stmtSeqNode.lastID)

            prevID = current!!.id
            current = current!!.next
        }
    }
}

private fun fillTables(stmtNode: StmtNode, currentClass: ClassModel, start: Int, end: Int) {
    when (stmtNode.type) {
        StmtType.UNINITIALIZED -> { // elseif
            fillTables(stmtNode.ifBlock!!, currentClass)
            fillTables(stmtNode.conditionExpr!!, currentClass)
        }
        StmtType.ASSIGNMENT -> {
            currentClass.pushMethRef("__VALUE__", "getFromSeq", "(I)L__VALUE__;")
            currentClass.pushMethRef("__VALUE__", "__adjust__", "()L__VALUE__;")
            fillTables(stmtNode.varList!!, currentClass)
            fillTables(stmtNode.values!!, currentClass)
        }
        StmtType.FUNCTION_CALL -> {
            when(stmtNode.functionCall?.ident) {
                "print" -> {
                    currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList"))))
                    currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V")
                    currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z")
                    currentClass.pushMethRef("__VALUE__", "print", "(Ljava/util/ArrayList;)V")
                }
                "read" -> {
                    currentClass.pushMethRef("__VALUE__", "read", "()L__VALUE__;")
                }
                "error" -> {
                    currentClass.pushMethRef("__VALUE__", "error", "(L__VALUE__;)V")
                }
                "assert" -> {
                    currentClass.pushMethRef("__VALUE__", "assert", "(L__VALUE__;L__VALUE__;)V")
                }
                "pcall" -> {
                    currentClass.pushMethRef("__VALUE__", "pcall", "(L__VALUE__;[L__VALUE__;)L__VALUE__;")
                }
                "xpcall" -> {
                    currentClass.pushMethRef("__VALUE__", "xpcall", "(L__VALUE__;L__VALUE__;[L__VALUE__;)L__VALUE__;")
                }
                "append" -> {
                    currentClass.pushMethRef("__VALUE__", "append", "(L__VALUE__;L__VALUE__;)V")
                }
                else -> {
                    currentClass.pushConstant(Constant.utf8(stmtNode.functionCall!!.ident))
                    if (!currentClass._contains(stmtNode.functionCall!!.ident, stmtNode.id)) {
//                        currentClass.pushFieldRef("__PROGRAM__", stmtNode.functionCall!!.ident, "L__VALUE__;")
//                        currentClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), stmtNode.functionCall!!.ident)
//                        globalProgramClass.pushFieldRef("__PROGRAM__", stmtNode.functionCall!!.ident, "L__VALUE__;")
//                        globalProgramClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), stmtNode.functionCall!!.ident)

                        globalProgramClass.pushFieldRef("__PROGRAM__", stmtNode.functionCall!!.ident, "L__VALUE__;")
                        globalProgramClass.pushGlobalVar(stmtNode.functionCall!!.ident)
                    }

                    // constantsTable.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;")
                    currentClass.pushMethRef("__VALUE__", "__invoke__", "([L__VALUE__;)L__VALUE__;")
                    currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z")
                }
            }
            stmtNode.functionCall!!.args?.let {
                fillTables(it, currentClass)
            }
        }
        StmtType.DO_LOOP -> { // Not loop))))
            fillTables(stmtNode.actionBlock!!, currentClass)
        }
        StmtType.WHILE_LOOP -> {
            currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I")
            fillTables(stmtNode.conditionExpr!!, currentClass)
            fillTables(stmtNode.actionBlock!!, currentClass)
        }
        StmtType.REPEAT_LOOP -> {
            currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I")
            fillTables(stmtNode.conditionExpr!!, currentClass)
            fillTables(stmtNode.actionBlock!!, currentClass)
        }
        StmtType.IF -> {
            currentClass.pushMethRef("__VALUE__", "__to_bool__", "()I")
            fillTables(stmtNode.conditionExpr!!, currentClass)
            fillTables(stmtNode.ifBlock!!, currentClass)
            if(stmtNode.elseifSeq != null && stmtNode.elseifSeq?.first != null) {
                fillTables(stmtNode.elseifSeq!!, currentClass)
            }
            if(stmtNode.elseBlock != null) {
                fillTables(stmtNode.elseBlock!!, currentClass)
            }
        }
        StmtType.FUNCTION_DEF -> {
            val funClass = ClassModel("__${stmtNode.ident}__${stmtNode.id}")

            classesTable["__${stmtNode.ident}__${stmtNode.id}"] = funClass

            currentClass.pushConstant(
                Constant._class(currentClass.pushConstant(
                        Constant.utf8(
                            "__${stmtNode.ident}__${stmtNode.id}"
                        )
                    )
                )
            )

            currentClass.pushMethRef("__VALUE__", "<init>", "()V")
            funClass.pushMethRef("__VALUE__", "<init>", "()V")
            currentClass.pushMethRef("__VALUE__", "<init>", "(L__FUN__;)V")
            currentClass.pushMethRef("__FUN__", "<init>", "()V")
            currentClass.pushMethRef("__${stmtNode.ident}__${stmtNode.id}", "<init>", "()V")
            currentClass.pushMethRef("__VALUE__", "__invoke__", "(Ljava/util/ArrayList;)L__VALUE__;")
            currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList"))))
            currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V")
            funClass.pushMethRef("__FUN__", "<init>", "()V")
            funClass.pushMethRef("__VALUE__", "__invoke__", "(Ljava/util/ArrayList;)L__VALUE__;")
            funClass.pushMethRef("__FUN__", "invoke", "(Ljava/util/ArrayList;)L__VALUE__;")
            funClass.pushMethRef("java/util/ArrayList", "get", "(I)Ljava/lang/Object;")
            funClass.pushConstant(Constant._class(funClass.pushConstant(Constant.utf8("__${stmtNode.ident}__${stmtNode.id}"))))
            funClass.pushConstant(Constant._class(funClass.pushConstant(Constant.utf8("__FUN__"))))
            funClass.pushMethRef("java/util/ArrayList", "size", "()I")
            funClass.pushMethRef("java/util/ArrayList", "subList", "(II)Ljava/util/List;")
            funClass.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V")

            if(stmtNode.isLocal) {
                currentClass.pushLocalVar(Pair(start, end), stmtNode.ident)
            } else {
//                currentClass.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;")
//                currentClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), stmtNode.ident)
//                globalProgramClass.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;")
//                globalProgramClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), stmtNode.ident)

                globalProgramClass.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;")
                globalProgramClass.pushGlobalVar(stmtNode.ident)
            }

            funClass.pushLocalVar(Pair(stmtNode.actionBlock!!.startID, stmtNode.actionBlock!!.lastID), "__args__")
            fillTables(stmtNode.params!!, funClass, stmtNode.actionBlock!!.startID, stmtNode.actionBlock!!.lastID)
            fillTables(stmtNode.actionBlock!!, funClass)
        }
        StmtType.FOR -> {
            currentClass.pushConstant(Constant.utf8(stmtNode.ident))
            currentClass.pushLocalVar(
                Pair(stmtNode.actionBlock!!.startID, stmtNode.actionBlock!!.lastID),
                stmtNode.ident
            )
            fillTables(stmtNode.initialValue!!, currentClass)
            fillTables(stmtNode.conditionExpr!!, currentClass)
            stmtNode.stepExpr?.let {
                fillTables(it, currentClass)
            }
            fillTables(stmtNode.actionBlock!!, currentClass)
        }
        StmtType.VAR_DEF -> {
            fillTables(stmtNode.identList!!, currentClass, start, end)

            stmtNode.values?.let {
                fillTables(it, currentClass)
            }
        }
        StmtType.RETURN -> {
            currentClass.pushMethRef("__VALUE__", "<init>", "()V")
            currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__"))))
            currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList"))))
            currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V")
            currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z")
            currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("__VALUE__"))))
            currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V")
            stmtNode.values?.let {
                fillTables(stmtNode.values!!, currentClass)
            }
        }
        else -> {}
    }
}

private fun fillTables(exprSeqNode: ExprSeqNode, currentClass: ClassModel) {
    var current: ExprNode? = exprSeqNode.first
    while (current != null) {
        fillTables(current, currentClass)
        current = current.next
    }
}

private fun fillTables(exprNode: ExprNode, currentClass: ClassModel) {
    when (exprNode.type) {
        ExprType.NIL -> {
            currentClass.pushMethRef("__VALUE__", "<init>", "()V")
        }
        ExprType.BOOLEAN -> {
            currentClass.pushConstant(Constant.integer(if(exprNode.boolValue) 1 else 0))
            currentClass.pushMethRef("__VALUE__", "<init>", "(Z)V")
        }
        ExprType.FLOAT_NUMBER -> {
            currentClass.pushConstant(Constant.float(exprNode.floatNumberValue.toFloat()))
            currentClass.pushMethRef("__VALUE__", "<init>", "(F)V")
        }
        ExprType.INT_NUMBER -> {
            currentClass.pushConstant(Constant.integer(exprNode.intNumberValue))
            currentClass.pushMethRef("__VALUE__", "<init>", "(I)V")
        }
        ExprType.STRING -> {
            currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(exprNode.stringValue))))
            currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
        }
        ExprType.VAR_ARG -> {
            currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V")
        }
        ExprType.VAR -> {
            fillTables(exprNode.varNode!!, currentClass)
        }
        ExprType.FUNCTION_CALL -> {
            when (exprNode.ident) {
                "print" -> {
                    currentClass.pushConstant(Constant._class(currentClass.pushConstant(Constant.utf8("java/util/ArrayList"))))
                    currentClass.pushMethRef("java/util/ArrayList", "<init>", "()V")
                    currentClass.pushMethRef("__VALUE__", "print", "(Ljava/util/ArrayList;)V")
                }
                "read" -> {
                    currentClass.pushMethRef("__VALUE__", "read", "()L__VALUE__;")
                }
                "error" -> {
                    currentClass.pushMethRef("__VALUE__", "error", "(L__VALUE__;)V")
                }
                "assert" -> {
                    currentClass.pushMethRef("__VALUE__", "assert", "(L__VALUE__;L__VALUE__;)V")
                }
                "pcall" -> {
                    currentClass.pushMethRef("__VALUE__", "pcall", "(L__VALUE__;[L__VALUE__;)L__VALUE__;")
                }
                "xpcall" -> {
                    currentClass.pushMethRef("__VALUE__", "xpcall", "(L__VALUE__;L__VALUE__;[L__VALUE__;)L__VALUE__;")
                }
                "append" -> {
                    currentClass.pushMethRef("__VALUE__", "append", "(L__VALUE__;L__VALUE__;)V")
                }
                else -> {
                    currentClass.pushConstant(Constant.utf8(exprNode.ident))
                    if (!currentClass._contains(exprNode.ident, exprNode.id)) {
//                        currentClass.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;")
//                        currentClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), exprNode.ident)
//                        globalProgramClass.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;")
//                        globalProgramClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), exprNode.ident)

                        globalProgramClass.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;")
                        globalProgramClass.pushGlobalVar(exprNode.ident)
                    }

                    // constantsTable.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;")
                    currentClass.pushMethRef("__VALUE__", "__invoke__", "([L__VALUE__;)L__VALUE__;")
                    currentClass.pushMethRef("java/util/ArrayList", "add", "(Ljava/lang/Object;)Z")
                }
            }
            exprNode.args?.let {
                fillTables(it, currentClass)
            }
        }
        ExprType.TABLE_CONSTRUCTOR -> {
            currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/util/HashMap;)V")
            currentClass.pushMethRef("java/util/HashMap", "<init>", "()V")
            if (exprNode.tableConstructor != null) {
                fillTables(exprNode.tableConstructor!!, currentClass)
            }
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
            fillTables(exprNode.firstOperand!!, currentClass)
            fillTables(exprNode.secondOperand!!, currentClass)
            currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "(L__VALUE__;)L__VALUE__;")
        }
        ExprType.UNARY_MINUS,
        ExprType.NOT,
        ExprType.LEN,
        ExprType.BIT_NOT -> {
            fillTables(exprNode.firstOperand!!, currentClass)
            currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "()L__VALUE__;")
        }
        ExprType.ADJUST -> {
            fillTables(exprNode.adjustedExpr!!, currentClass)
            currentClass.pushMethRef("__VALUE__", exprNode.type.getMethod(), "()L__VALUE__;")
        }
        else -> {}
    }
}

private fun fillTables(varItemNode: VarItemNode, currentClass: ClassModel) {
    when (varItemNode.type) {
        VarType.IDENT -> {
            currentClass.pushConstant(Constant.utf8(varItemNode.ident))
            currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(varItemNode.ident))))
            currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
            if (!currentClass._contains(varItemNode.ident, varItemNode.id)) {
//                currentClass.pushFieldRef("__PROGRAM__", varItemNode.ident, "L__VALUE__;")
//                currentClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), varItemNode.ident)
//                globalProgramClass.pushFieldRef("__PROGRAM__", varItemNode.ident, "L__VALUE__;")
//                globalProgramClass.pushLocalVar(Pair(globalScopeStartId, globalScopeEndId), varItemNode.ident)
                currentClass.pushFieldRef("__PROGRAM__", varItemNode.ident, "L__VALUE__;")
                globalProgramClass.pushFieldRef("__PROGRAM__", varItemNode.ident, "L__VALUE__;")
                globalProgramClass.pushGlobalVar(varItemNode.ident)
            }
        }
        VarType.VAR -> {
            if (varItemNode.isMapKey) {
                currentClass.pushConstant(Constant.utf8(varItemNode.ident))
                currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(varItemNode.ident))))
                currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
            } else {
                fillTables(varItemNode.secondExpr!!, currentClass)
            }
        }
        VarType.FUNCTION_CALL -> {
            if (varItemNode.isMapKey) {
                currentClass.pushConstant(Constant.utf8(varItemNode.ident))
                currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(varItemNode.ident))))
                currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
                fillTables(varItemNode.firstExpr!!, currentClass)
            } else {
                fillTables(varItemNode.firstExpr!!, currentClass)
                fillTables(varItemNode.secondExpr!!, currentClass)
            }
        }
        VarType.ADJUSTED_EXPR -> {
            if (varItemNode.isMapKey) {
                currentClass.pushConstant(Constant.utf8(varItemNode.ident))
                currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(varItemNode.ident))))
                currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
                fillTables(varItemNode.firstExpr!!, currentClass)
            } else {
                fillTables(varItemNode.firstExpr!!, currentClass)
                fillTables(varItemNode.secondExpr!!, currentClass)
            }
        }
        VarType.UNINITIALIZED -> { }
    }
}

private fun fillTables(varNode: VarNode, currentClass: ClassModel) {
    currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;L__VALUE__;)V")
    currentClass.pushMethRef("__VALUE__", "getByKey", "(L__VALUE__;)L__VALUE__;")
    var current: VarItemNode? = varNode.first
    while (current != null) {
        fillTables(current, currentClass)
        current = current.next
    }
}

private fun fillTables(identNode: IdentNode, currentClass: ClassModel, start: Int, end : Int) {
    currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(identNode.ident!!))))
    currentClass.pushLocalVar(Pair(start, end), identNode.ident!!)
}

private fun fillTables(identListNode: IdentListNode, currentClass: ClassModel, start: Int, end : Int) {
    var current: IdentNode? = identListNode.first
    while (current != null) {
        fillTables(current, currentClass, start, end)
        current = current.next
    }
}

private fun fillTables(paramListNode: ParamListNode, currentClass: ClassModel, start: Int, end: Int) {
    paramListNode.list?.let {
        fillTables(it, currentClass, start, end)
    }
    if (paramListNode.hasVarArg) {
        currentClass.pushConstant(Constant.utf8("..."))
        currentClass.pushLocalVar(Pair(start, end), "...")
    }
}

private fun fillTables(fieldNode: FieldNode, currentClass: ClassModel) {
    fieldNode.ident?.let {
        currentClass.pushConstant(Constant.string(currentClass.pushConstant(Constant.utf8(it))))
        currentClass.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
        currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;L__VALUE__;)V")
    }
    fieldNode.key?.let {
        fillTables(it, currentClass)
        currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;L__VALUE__;)V")
    }
    fieldNode.value?.let {
        fillTables(it, currentClass)
        currentClass.pushMethRef("__VALUE__", "__append__", "(L__VALUE__;)V")
    }
}

private fun fillTables(fieldListNode: FieldListNode, currentClass: ClassModel) {
    var current: FieldNode? = fieldListNode.first
    while (current != null) {
        fillTables(current, currentClass)
        current = current.next
    }
}
