package nodes;

public class VarNode {
    public VarItemNode first;
    public VarItemNode last;

    public VarNode(VarItemNode item) {
        this.first = item;
        this.last = item;
    }

    public static VarNode createIdVarNode(String id) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.IDENT;
        item.ident = id;
        return new VarNode(item);
    }

    public void addExpr(ExprNode expr) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.VAR;
        item.second_expr = expr;
        this.last.next = item;
        this.last = item;
    }

    public void addId(String id) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.VAR;
        item.ident = id;
        item.is_map_key = true;
        this.last.next = item;
        this.last = item;
    }

    public static VarNode createFunctionWithExprVarNode(ExprNode function_call, ExprNode expr) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.FUNCTION_CALL;
        item.first_expr = function_call;
        item.second_expr = expr;
        return new VarNode(item);
    }

    public static VarNode createFunctionWithIdVarNode(ExprNode function_call, String id) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.FUNCTION_CALL;
        item.first_expr = function_call;
        item.ident = id;
        item.is_map_key = true;
        return new VarNode(item);
    }

    public static VarNode createExprWithExprVarNode(ExprNode expr1, ExprNode expr2) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.ADJUSTED_EXPR;
        item.first_expr = expr1;
        item.second_expr = expr2;
        return new VarNode(item);
    }

    public static VarNode createExprWithIdVarNode(ExprNode expr, String id) {
        VarItemNode item = new VarItemNode();
        item.id = Utils.getLastId();
        item.type = VarType.ADJUSTED_EXPR;
        item.first_expr = expr;
        item.ident = id;
        item.is_map_key = true;
        return new VarNode(item);
    }
}
