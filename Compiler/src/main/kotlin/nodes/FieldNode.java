package nodes;

public class FieldNode {
    public int id;

    public String ident;
    public ExprNode key;
    public ExprNode value;

    public FieldNode next = null;

    public FieldNode(String ID, ExprNode expr_value, ExprNode expr_key) {
        this.id = Utils.getLastId();
        this.ident = ID;
        this.key = expr_key;
        this.value = expr_value;
    }
}
