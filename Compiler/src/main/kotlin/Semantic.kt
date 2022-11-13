import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import nodes.*
import java.io.File

private var constantID = 0
    get() = ++field

private var localVarID = 0
    get() = ++field

private var funID = 0
    get() = ++field

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

private fun MutableMap<Constant, Int>.push(constant: Constant): Int {
    if (this.containsKey(constant)) {
        return this[constant]!!
    }
    val id = constantID
    this[constant] = id
    return id
}

private fun MutableMap<Constant, Int>.pushFieldRef(className: String, fieldName: String, type: String): Int {
    val name = this.push(Constant.utf8(fieldName))
    val typeId = this.push(Constant.utf8(type))
    val nameAndType = this.push(Constant.nameAndType(name, typeId))
    val _class = this.push(Constant._class(this.push(Constant.utf8(className))))
    return this.push(Constant.fieldRef(_class, nameAndType))
}

private fun MutableMap<Constant, Int>.pushMethRef(className: String, fieldName: String, type: String): Int {
    val name = this.push(Constant.utf8(fieldName))
    val typeId = this.push(Constant.utf8(type))
    val nameAndType = this.push(Constant.nameAndType(name, typeId))
    val _class = this.push(Constant._class(this.push(Constant.utf8(className))))
    return this.push(Constant.methodRef(_class, nameAndType))
}

var constantsTable: MutableMap<Constant, Int> = HashMap()
var localVarsTable: Table<Int, String, Int> = HashBasedTable.create()

var classProgramId: Int = 0
var valueClassId: Int = 0
var functionClassId: Int = 0
var objectClassId: Int = 0
var programStmtId: Int = 0

fun fillTables(program: ChunkNode) {
    constantsTable.push(Constant.utf8("CODE"))
    classProgramId = constantsTable.push(
        Constant._class(
            constantsTable.push(Constant.utf8("__PROGRAM__"))
        )
    )
    valueClassId = constantsTable.push(
        Constant._class(
            constantsTable.push(Constant.utf8("__VALUE__"))
        )
    )
    functionClassId = constantsTable.push(
        Constant._class(
            constantsTable.push(Constant.utf8("__FUN__"))
        )
    )
    objectClassId = constantsTable.push(
        Constant._class(
            constantsTable.push(Constant.utf8("Ljava/lang/Object;"))
        )
    )
    constantsTable.push(Constant.utf8("main"))
    constantsTable.push(Constant.utf8("([Ljava/lang/String;)V"))
    constantsTable.pushMethRef("__PROGRAM__", "main", "([Ljava/lang/String;)V")

    localVarsTable.put(program.block.first?.id, "args", localVarID)

    program.block.first?.let {
        programStmtId = it.id
    }

    fillTables(program.block)

    constantsTable.pushMethRef("__PROGRAM__", "<init>", "()V")
    constantsTable.pushMethRef("java/lang/Object", "<init>", "()V")
}

var currentStmtSeqId = 0

private fun fillTables(stmtSeqNode: StmtSeqNode) {
    var current = stmtSeqNode.first
    current?.let {
        //currentStmtSeqId = it.id
        while (current != null) {
            currentStmtSeqId = it.id
            fillTables(current!!, it.id)
            current = current!!.next
        }
    }
}

private fun fillTables(stmtNode: StmtNode, ownerId: Int) {
    when (stmtNode.type) {
        StmtType.UNINITIALIZED -> { // elseif
            fillTables(stmtNode.ifBlock!!)
            fillTables(stmtNode.conditionExpr!!)
        }
        StmtType.ASSIGNMENT -> {
            fillTables(stmtNode.varList!!)
            fillTables(stmtNode.values!!)
        }
        StmtType.FUNCTION_CALL -> {
            when {
                stmtNode.functionCall?.ident?.compareTo("print") == 0 -> {
                    constantsTable.pushMethRef("__VALUE__", "print", "(L__VALUE__;)V")
                }
                stmtNode.functionCall?.ident?.compareTo("read") == 0 -> {
                    constantsTable.pushMethRef("__VALUE__", "read", "(L__VALUE__;)V")
                }
                else -> {
                    constantsTable.push(Constant.utf8(stmtNode.functionCall!!.ident))
                    if (!localVarsTable.contains(ownerId, stmtNode.functionCall!!.ident)) {
                        constantsTable.pushFieldRef("__PROGRAM__", stmtNode.functionCall!!.ident, "L__VALUE__;")
                        localVarsTable.put(programStmtId, stmtNode.functionCall!!.ident, localVarID)
                    }

                    // constantsTable.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;")
                    constantsTable.pushMethRef("__VALUE__", "__invoke__", "([L__VALUE__;)L__VALUE__;")
                }
            }
            stmtNode.functionCall!!.args?.let {
                fillTables(it)
            }
        }
        StmtType.DO_LOOP -> { // Not loop))))
            fillTables(stmtNode.actionBlock!!)
        }
        StmtType.WHILE_LOOP -> {
            fillTables(stmtNode.conditionExpr!!)
            fillTables(stmtNode.actionBlock!!)
        }
        StmtType.REPEAT_LOOP -> {
            fillTables(stmtNode.conditionExpr!!)
            fillTables(stmtNode.actionBlock!!)
        }
        StmtType.IF -> {
            fillTables(stmtNode.conditionExpr!!)
            fillTables(stmtNode.ifBlock!!)
            if(stmtNode.elseifSeq != null && stmtNode.elseifSeq?.first != null) {
                fillTables(stmtNode.elseifSeq!!)
            }
            if(stmtNode.elseBlock != null) {
                fillTables(stmtNode.elseBlock!!)
            }
        }
        StmtType.FUNCTION_DEF -> {
            // TODO? нужны вложенные классы или  нет ?????
            constantsTable.push(Constant._class(constantsTable.push(Constant.utf8(
                "__${stmtNode.ident}__$funID"
            ))))
            constantsTable.pushMethRef("__VALUE__", "<init>", "(L__FUN__;)V")

            if(stmtNode.isLocal) {
                localVarsTable.put(ownerId, stmtNode.ident, localVarID)
            } else {
                constantsTable.pushFieldRef("__PROGRAM__", stmtNode.ident, "L__VALUE__;")
                localVarsTable.put(programStmtId, stmtNode.ident, localVarID)
            }

            stmtNode.actionBlock!!.first?.let {
                fillTables(stmtNode.params!!, it.id)
            }
            fillTables(stmtNode.actionBlock!!)
        }
        StmtType.FOR -> {
            constantsTable.push(Constant.utf8(stmtNode.ident))
            stmtNode.actionBlock!!.first?.let {
                localVarsTable.put(it.id, stmtNode.ident, localVarID)
            }
            fillTables(stmtNode.initialValue!!)
            fillTables(stmtNode.conditionExpr!!)
            stmtNode.stepExpr?.let {
                fillTables(it)
            }
            fillTables(stmtNode.actionBlock!!)
        }
        // TODO: FOREACH?????????????
        StmtType.VAR_DEF -> {
            fillTables(stmtNode.identList!!, ownerId)

            stmtNode.values?.let {
                fillTables(it)
            }
        }
        StmtType.RETURN -> {
            fillTables(stmtNode.values!!)
        }
        else -> {}
    }
}

private fun fillTables(exprSeqNode: ExprSeqNode) {
    var current: ExprNode? = exprSeqNode.first
    while (current != null) {
        fillTables(current)
        current = current.next
    }
}

private fun fillTables(exprNode: ExprNode) {
    when (exprNode.type) {
        ExprType.NIL -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "()V")
        }
        ExprType.BOOLEAN -> {
            constantsTable.push(Constant.integer(if(exprNode.boolValue) 1 else 0))
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Z)V")
        }
        ExprType.FLOAT_NUMBER -> {
            constantsTable.push(Constant.float(exprNode.floatNumberValue.toFloat()))
            constantsTable.pushMethRef("__VALUE__", "<init>", "(F)V")
        }
        ExprType.INT_NUMBER -> {
            constantsTable.push(Constant.integer(exprNode.intNumberValue))
            constantsTable.pushMethRef("__VALUE__", "<init>", "(I)V")
        }
        ExprType.STRING -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
        }
        ExprType.VAR_ARG -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Ljava/util/List;)V")
        }
        ExprType.VAR -> {
            fillTables(exprNode.varNode!!, currentStmtSeqId)
        }
        ExprType.FUNCTION_CALL -> {
            when (exprNode.ident) {
                "print" -> {
                    constantsTable.pushMethRef("__VALUE__", "print", "(L__VALUE__;)V")
                }
                "read" -> {
                    constantsTable.pushMethRef("__VALUE__", "read", "(L__VALUE__;)V")
                }
                else -> {
                    constantsTable.push(Constant.utf8(exprNode.ident))
                    if (!localVarsTable.contains(currentStmtSeqId, exprNode.ident)) {
                        constantsTable.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;")
                        localVarsTable.put(programStmtId, exprNode.ident, localVarID)
                    }

                    // constantsTable.pushFieldRef("__PROGRAM__", exprNode.ident, "L__VALUE__;")
                    constantsTable.pushMethRef("__VALUE__", "__invoke__", "([L__VALUE__;)L__VALUE__;")
                }
            }
            exprNode.args?.let {
                fillTables(it)
            }
        }
        ExprType.TABLE_CONSTRUCTOR -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Ljava/util/HashMap;)V")
            exprNode.tableConstructor?.let {
                fillTables(it)
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
            constantsTable.pushMethRef("__VALUE__", exprNode.type.getMethod(), "(L__VALUE__;)L__VALUE__;")
        }
        ExprType.UNARY_MINUS,
        ExprType.NOT,
        ExprType.LEN,
        ExprType.BIT_NOT,
        ExprType.ADJUST -> {
            constantsTable.pushMethRef("__VALUE__", exprNode.type.getMethod(), "()L__VALUE__;")
        }
        else -> {}
    }
}

private fun fillTables(varItemNode: VarItemNode, ownerId: Int) {
    when (varItemNode.type) {
        VarType.IDENT -> {
            constantsTable.push(Constant.utf8(varItemNode.ident))
            if (!localVarsTable.contains(ownerId, varItemNode.ident)) {
                constantsTable.pushFieldRef("__PROGRAM__", varItemNode.ident, "L__VALUE__;")
                localVarsTable.put(programStmtId, varItemNode.ident, localVarID)
            }
        }
        VarType.VAR -> {
            if (varItemNode.isMapKey) {
                constantsTable.push(Constant.utf8(varItemNode.ident))
            } else {
                fillTables(varItemNode.secondExpr!!)
            }
        }
        VarType.FUNCTION_CALL -> {
            if (varItemNode.isMapKey) {
                constantsTable.push(Constant.utf8(varItemNode.ident))
                fillTables(varItemNode.firstExpr!!)
            } else {
                fillTables(varItemNode.firstExpr!!)
                fillTables(varItemNode.secondExpr!!)
            }
        }
        VarType.ADJUSTED_EXPR -> {
            if (varItemNode.isMapKey) {
                constantsTable.push(Constant.utf8(varItemNode.ident))
                fillTables(varItemNode.firstExpr!!)
            } else {
                fillTables(varItemNode.firstExpr!!)
                fillTables(varItemNode.secondExpr!!)
            }
        }
        VarType.UNINITIALIZED -> { }
    }
}

private fun fillTables(varNode: VarNode, ownerId: Int) {
    var current: VarItemNode? = varNode.first
    while (current != null) {
        fillTables(current, ownerId)
        current = current.next
    }
}

private fun fillTables(identNode: IdentNode, parentStmtId: Int) {
    constantsTable.push(Constant.utf8(identNode.ident!!))
    localVarsTable.put(parentStmtId, identNode.ident, localVarID)
}

private fun fillTables(identListNode: IdentListNode, parentStmtId: Int) {
    var current: IdentNode? = identListNode.first
    while (current != null) {
        fillTables(current, parentStmtId)
        current = current.next
    }
}

private fun fillTables(paramListNode: ParamListNode, parentStmtId: Int) {
    paramListNode.list?.let {
        fillTables(it, parentStmtId)
        if (paramListNode.hasVarArg) {
            constantsTable.push(Constant.utf8("..."))
            localVarsTable.put(parentStmtId, "...", localVarID)
        }
    }

}

private fun fillTables(fieldNode: FieldNode) {
    fieldNode.ident?.let {
        constantsTable.push(Constant.utf8(it))
    }
    fieldNode.key?.let {
        fillTables(it)
    }
    fieldNode.value?.let {
        fillTables(it)
    }
}

private fun fillTables(fieldListNode: FieldListNode) {
    var current: FieldNode? = fieldListNode.first
    while (current != null) {
        fillTables(current)
        current = current.next
    }
}

fun MutableMap<Constant, Int>.toCSV() {
    val file = File("constants.csv")
    file.writeText("ID,Type,Value\n")

    this.forEach {
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

fun Table<Int, String, Int>.toCSV() {
    val file = File("locals.csv")
    file.writeText("ID,Name,Owner\n")

    this.cellSet().forEach {
        val col = it.columnKey
        val row = it.rowKey
        val value = it.value

        file.appendText("$value, $col, $row\n")
    }
}
