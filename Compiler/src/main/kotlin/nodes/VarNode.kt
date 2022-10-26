package nodes

class VarNode(var first: VarItemNode) {
    var last: VarItemNode = first

    fun add(varItemNode: VarItemNode) {
        last.next = varItemNode
        last = varItemNode
    }

    fun addExpr(expr: ExprNode?) {
        val item = VarItemNode()
        item.id = Utils.lastId
        item.type = VarType.VAR
        item.secondExpr = expr
        last.next = item
        last = item
    }

    fun addId(id: String?) {
        val item = VarItemNode()
        item.id = Utils.lastId
        item.type = VarType.VAR
        item.ident = id!!
        item.isMapKey = true
        last.next = item
        last = item
    }

    companion object {
        fun createIdVarNode(id: String?): VarNode {
            val item = VarItemNode()
            item.id = Utils.lastId
            item.type = VarType.IDENT
            item.ident = id!!
            return VarNode(item)
        }

        fun createFunctionWithExprVarNode(function_call: ExprNode?, expr: ExprNode?): VarNode {
            val item = VarItemNode()
            item.id = Utils.lastId
            item.type = VarType.FUNCTION_CALL
            item.firstExpr = function_call
            item.secondExpr = expr
            return VarNode(item)
        }

        fun createFunctionWithIdVarNode(function_call: ExprNode?, id: String?): VarNode {
            val item = VarItemNode()
            item.id = Utils.lastId
            item.type = VarType.FUNCTION_CALL
            item.firstExpr = function_call
            item.ident = id!!
            item.isMapKey = true
            return VarNode(item)
        }

        fun createExprWithExprVarNode(expr1: ExprNode?, expr2: ExprNode?): VarNode {
            val item = VarItemNode()
            item.id = Utils.lastId
            item.type = VarType.ADJUSTED_EXPR
            item.firstExpr = expr1
            item.secondExpr = expr2
            return VarNode(item)
        }

        fun createExprWithIdVarNode(expr: ExprNode?, id: String?): VarNode {
            val item = VarItemNode()
            item.id = Utils.lastId
            item.type = VarType.ADJUSTED_EXPR
            item.firstExpr = expr
            item.ident = id!!
            item.isMapKey = true
            return VarNode(item)
        }
    }
}