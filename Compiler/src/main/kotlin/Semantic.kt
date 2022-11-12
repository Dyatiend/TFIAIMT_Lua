import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import nodes.*
import org.checkerframework.checker.units.qual.K

import kotlin.jvm.functions.FunctionN
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
            if(stmtNode.functionCall?.ident?.compareTo("print") == 0) {
                constantsTable.pushMethRef("__VALUE__", "print", "(L__VALUE__;)V")
            }
            else if(stmtNode.functionCall?.ident?.compareTo("read") == 0) {
                constantsTable.pushMethRef("__VALUE__", "read", "(L__VALUE__;)V")
            }
            else {
                constantsTable.pushMethRef("__VALUE__", "__invoke__",
                    functionDescription(stmtNode.functionCall?.args?.length()?:0))
            }
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
        ExprType.NIL -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "()V")
        }
        ExprType.BOOLEAN -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Z)V")
        }
        ExprType.FLOAT_NUMBER -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(F)V")
        }
        ExprType.INT_NUMBER -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(I)V")
        }
        ExprType.STRING -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Ljava/lang/String;)V")
        }
        ExprType.VAR_ARG -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Ljava.util.List;)V")
        }
        ExprType.VAR -> TODO()
        ExprType.FUNCTION_CALL -> {
            if(exprNode.ident == "print") {
                constantsTable.pushMethRef("__VALUE__", "print", "(L__VALUE__;)V")
            }
            else if(exprNode.ident == "read") {
                constantsTable.pushMethRef("__VALUE__", "read", "(L__VALUE__;)V")
            }
            else {
                constantsTable.pushMethRef("__VALUE__", "__invoke__",
                    functionDescription(exprNode.args?.length()?:0))
            }

            constantsTable.pushMethRef("__VALUE__", "<init>", "(L__FUN__;)V")
        }
        ExprType.TABLE_CONSTRUCTOR -> {
            constantsTable.pushMethRef("__VALUE__", "<init>", "(Ljava.util.HashMap;)V")
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


private fun functionDescription(countParams : Int) : String {
    var result = "("
    for(i in 0 until countParams) {
        result += "L__VALUE__;"
    }
    result += ")L__VALUE__;";
    return result
}
