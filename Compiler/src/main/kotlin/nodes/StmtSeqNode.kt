package nodes

class StmtSeqNode(val startID: Int, var lastID: Int)    //Для Пустого elseIf и пустого stmt_seq
{
    var first: StmtNode? = null
    var last: StmtNode? = null
    fun addStmt(stmt: StmtNode?) {
        if (last == null) {
            first = stmt
            last = stmt
        } else {
            last!!.next = stmt
            last = stmt
        }
    }

    fun addElseifSeq(expr: ExprNode?, block: StmtSeqNode?) {
        val elseIf = StmtNode()
        elseIf.id = Utils.lastId
        elseIf.conditionExpr = expr
        elseIf.ifBlock = block
        if (last == null) {
            first = elseIf
            last = elseIf
        } else {
            last!!.next = elseIf
            last = elseIf
        }
    }
}