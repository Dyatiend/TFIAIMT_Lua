package nodes;

public class StmtSeqNode {
    public StmtNode first;
    public StmtNode last;

    //Для Пустого elseIf и пустого stmt_seq
    public StmtSeqNode() {
        this.first = null;
        this.last = null;
    }

    public void addStmt(StmtNode stmt) {
        if(this.last == null) {
            this.first = stmt;
            this.last = stmt;
        }
        else {
            this.last.next = stmt;
            this.last = stmt;
        }
    }

    public void addElseifSeq(ExprNode expr, StmtSeqNode block) {
        StmtNode else_if = new StmtNode();
        else_if.id = Utils.getLastId();
        else_if.condition_expr = expr;
        else_if.if_block = block;

        if(this.last == null) {
            this.first = else_if;
            this.last = else_if;
        }
        else {
            this.last.next = else_if;
            this.last = else_if;
        }
    }
}
