package nodes

class FieldNode(ID: String?, exprValue: ExprNode?, exprKey: ExprNode?) {
    var id: Int = Utils.lastId
    var ident: String? = ID
    var key: ExprNode? = exprKey
    var value: ExprNode? = exprValue
    var next: FieldNode? = null
}