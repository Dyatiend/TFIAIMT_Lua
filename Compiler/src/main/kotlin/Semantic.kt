import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import nodes.*
import org.checkerframework.checker.units.qual.K

private var constantID = 0
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
    val id = constantID
    this[constant] = id
    return id
}

var constantsTable: MutableMap<Constant, Int> = HashMap()
var localVarsTable: Table<Int, String, Int> = HashBasedTable.create()

var classProgramId: Int = 0
var valueClassId: Int = 0
var objectClassId: Int = 0

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
    objectClassId = constantsTable.push(
        Constant._class(
            constantsTable.push(Constant.utf8("Ljava/lang/Object;"))
        )
    )
    constantsTable.push(Constant.utf8("main"))
    constantsTable.push(Constant.utf8("([Ljava/lang/String;)V"))


    fillTables(program.block)
}

private var currentStmtSeqId: Int = 0

private fun fillTables(stmtSeqNode: StmtSeqNode) {
    var current = stmtSeqNode.first
    current?.let {
        currentStmtSeqId = it.id
        while (current != null) {
            fillTables(current!!)
            current = current!!.next
        }
    }
}

private fun fillTables(stmtNode: StmtNode) {
    when (stmtNode.type) {
        StmtType.UNINITIALIZED -> { // elseif
            fillTables(stmtNode.elseifSeq!!)
        }
        StmtType.ASSIGNMENT -> {
            TODO()
        }
        StmtType.FUNCTION_CALL -> {

        }
        StmtType.BREAK -> {

        }
        StmtType.DO_LOOP -> {

        }
        StmtType.WHILE_LOOP -> {

        }
        StmtType.REPEAT_LOOP -> {

        }
        StmtType.IF -> {

        }
        StmtType.FOR -> {

        }
        StmtType.FOREACH -> {

        }
        StmtType.FUNCTION_DEF -> {

        }
        StmtType.VAR_DEF -> {
            var current = stmtNode.identList?.first
            while (current != null) {
                val id = constantsTable.push(Constant.fieldRef(classProgramId,
                    constantsTable.push(Constant.nameAndType(
                        constantsTable.push(Constant.utf8(current.ident!!)),
                        valueClassId
                    ))))
                localVarsTable.put(currentStmtSeqId, current.ident, id)
                current = current.next
            }
        }
        StmtType.RETURN -> {
            fillTables(stmtNode.values!!)
        }
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
        ExprType.UNINITIALIZED -> TODO()
        ExprType.NIL -> TODO()
        ExprType.BOOLEAN -> TODO()
        ExprType.NUMBER -> TODO()
        ExprType.STRING -> TODO()
        ExprType.VAR_ARG -> TODO()
        ExprType.VAR -> TODO()
        ExprType.FUNCTION_CALL -> TODO()
        ExprType.ADJUST -> TODO()
        ExprType.TABLE_CONSTRUCTOR -> TODO()
        ExprType.PLUS -> TODO()
        ExprType.MINUS -> TODO()
        ExprType.MUL -> TODO()
        ExprType.DIV -> TODO()
        ExprType.FLOOR_DIV -> TODO()
        ExprType.POW -> TODO()
        ExprType.XOR -> TODO()
        ExprType.MOD -> TODO()
        ExprType.BIT_AND -> TODO()
        ExprType.BIT_OR -> TODO()
        ExprType.CONCAT -> TODO()
        ExprType.LESS -> TODO()
        ExprType.LE -> TODO()
        ExprType.GREATER -> TODO()
        ExprType.GE -> TODO()
        ExprType.EQUAL -> TODO()
        ExprType.NOT_EQUAL -> TODO()
        ExprType.LOG_AND -> TODO()
        ExprType.LOG_OR -> TODO()
        ExprType.UNARY_MINUS -> TODO()
        ExprType.NOT -> TODO()
        ExprType.LEN -> TODO()
        ExprType.BIT_NOT -> TODO()
    }
}

private fun fillTables(varItemNode: VarItemNode) {

}

private fun fillTables(varNode: VarNode) {
    var current: VarItemNode? = varNode.first
    while (current != null) {
        fillTables(current)
        current = current.next
    }
}

private fun fillTables(identNode: IdentNode) {

}

private fun fillTables(identListNode: IdentListNode) {
    var current: IdentNode? = identListNode.first
    while (current != null) {
        fillTables(current)
        current = current.next
    }
}

private fun fillTables(paramListNode: ParamListNode) {
    fillTables(paramListNode.list)
    if (paramListNode.hasVarArg) {
        TODO()
    }
}

private fun fillTables(fieldNode: FieldNode) {

}

private fun fillTables(fieldListNode: FieldListNode) {
    var current: FieldNode? = fieldListNode.first
    while (current != null) {
        fillTables(current)
        current = current.next
    }
}
