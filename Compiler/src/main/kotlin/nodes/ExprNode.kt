package nodes

class ExprNode {
    var type = ExprType.UNINITIALIZED
    var id = 0
    var boolValue = false // Значение бул
    var floatNumberValue = 0.0 // Число
    var intNumberValue = 0 // Число
    var stringValue = "" // Строка

    // Вызов функции
    var ident = ""
    var args: ExprSeqNode? = null
    var adjustedExpr: ExprNode? = null // Выражение в скобках
    var tableConstructor: FieldListNode? = null // Таблица
    var firstOperand: ExprNode? = null // Первый операнд и операнд для унарных
    var secondOperand: ExprNode? = null // Второй операнд
    var varNode: VarNode? = null // Переменная
    var next: ExprNode? = null

    companion object {
        fun createNilExprNode(): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.NIL
            return result
        }

        fun createBoolExprNode(value: Boolean): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.BOOLEAN
            result.boolValue = value
            return result
        }

        fun createFloatNumberExprNode(value: Double): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.FLOAT_NUMBER
            result.floatNumberValue = value
            return result
        }

        fun createIntNumberExprNode(value: Int): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.INT_NUMBER
            result.intNumberValue = value
            return result
        }

        fun createStringExprNode(value: String): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.STRING
            result.stringValue = value
            return result
        }

        fun createVarArgExprNode(): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.VAR_ARG
            return result
        }

        fun createVarExprNode(varNode: VarNode?): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.VAR
            result.varNode = varNode
            return result
        }

        fun createFunctionCallExprNode(ID: String, args: ExprSeqNode?): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.FUNCTION_CALL
            result.ident = ID
            result.args = args
            return result
        }

        fun createAdjustingExprNode(expr: ExprNode?): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.ADJUST
            result.adjustedExpr = expr
            return result
        }

        fun createTableConstructorExprNode(table: FieldListNode?): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = ExprType.TABLE_CONSTRUCTOR
            result.tableConstructor = table
            return result
        }

        fun createBinExprNode(type_node: ExprType, first_operand: ExprNode?, second_operand: ExprNode?): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = type_node
            result.firstOperand = first_operand
            result.secondOperand = second_operand
            return result
        }

        fun createUnaryExprNode(type_node: ExprType, operand: ExprNode?): ExprNode {
            val result = ExprNode()
            result.id = Utils.lastId
            result.type = type_node
            result.firstOperand = operand
            return result
        }
    }
}