package nodes

class ExprSeqNode(var first: ExprNode) {
    var last: ExprNode = first

    fun addExpr(expr: ExprNode) {
        last.next = expr
        last = expr
    }

    fun addVar(varNode: VarNode?) {
        val exprVarNode = ExprNode()
        exprVarNode.id = Utils.lastId
        exprVarNode.type = ExprType.VAR
        exprVarNode.varNode = varNode
        last.next = exprVarNode
        last = exprVarNode
    }

    companion object {
        fun createTableConstructorExprSeqNode(tableConstructor: FieldListNode?): ExprSeqNode {
            val exprNodeTable = ExprNode()
            exprNodeTable.id = Utils.lastId
            exprNodeTable.type = ExprType.TABLE_CONSTRUCTOR
            exprNodeTable.tableConstructor = tableConstructor
            return ExprSeqNode(exprNodeTable)
        }

        fun createStringExprSeqNode(string: String?): ExprSeqNode {
            val exprNodeString = ExprNode()
            exprNodeString.id = Utils.lastId
            exprNodeString.type = ExprType.STRING
            exprNodeString.stringValue = string!!
            return ExprSeqNode(exprNodeString)
        }

        fun createVarListNode(varNode: VarNode?): ExprSeqNode {
            val exprVarNode = ExprNode()
            exprVarNode.id = Utils.lastId
            exprVarNode.type = ExprType.VAR
            exprVarNode.varNode = varNode
            return ExprSeqNode(exprVarNode)
        }
    }
}