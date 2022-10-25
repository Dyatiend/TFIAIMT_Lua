package nodes;

public class ExprSeqNode {
    public ExprNode first;
    public ExprNode last;

    public ExprSeqNode(ExprNode expr) {
        this.first = expr;
        this.last = expr;
    }

    public void addExpr(ExprNode expr) {
        this.last.next = expr;
        this.last = expr;
    }

    public static ExprSeqNode createTableConstructorExprSeqNode(FieldListNode tableConstructor) {
        ExprNode exprNodeTable = new ExprNode();
        exprNodeTable.id = Utils.getLastId();
        exprNodeTable.type = ExprType.TABLE_CONSTRUCTOR;
        exprNodeTable.table_constructor = tableConstructor;
        return new ExprSeqNode(exprNodeTable);
    }

    public static ExprSeqNode createStringExprSeqNode(String string) {
        ExprNode exprNodeString = new ExprNode();
        exprNodeString.id = Utils.getLastId();
        exprNodeString.type = ExprType.STRING;
        exprNodeString.string_value = string;
        return new ExprSeqNode(exprNodeString);
    }

    public static ExprSeqNode createVarListNode(VarNode var) {
        ExprNode expr_var_node = new ExprNode();
        expr_var_node.id = Utils.getLastId();
        expr_var_node.type = ExprType.VAR;
        expr_var_node.var = var;
        return new ExprSeqNode(expr_var_node);
    }

    public void addVar(VarNode var) {
        ExprNode expr_var_node = new ExprNode();
        expr_var_node.id = Utils.getLastId();
        expr_var_node.type = ExprType.VAR;
        expr_var_node.var = var;
        this.last.next = expr_var_node;
        this.last = expr_var_node;
    }
}
