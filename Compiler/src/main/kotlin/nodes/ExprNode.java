package nodes;

public class ExprNode {
    public ExprType type = ExprType.UNINITIALIZED;

    public int id = 0;

    public boolean bool_value = false; // Значение бул
    public double number_value = 0.0; // Число
    public String string_value = ""; // Строка

    // Вызов функции
    public String ident = "";
    public ExprSeqNode args = null;

    public ExprNode adjusted_expr = null; // Выражение в скобках

    public FieldListNode table_constructor = null; // Таблица

    public ExprNode first_operand = null; // Первый операнд и операнд для унарных
    public ExprNode second_operand = null; // Второй операнд

    public VarNode var = null; // Переменная

    public ExprNode next = null;

    public static ExprNode createNilExprNode() {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.NIL;
        return result;
    }

    public static ExprNode createBoolExprNode(boolean val) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.BOOLEAN;
        result.bool_value = val;
        return result;
    }

    public static ExprNode createNumberExprNode(float val) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.NUMBER;
        result.number_value = val;
        return result;
    }

    public static ExprNode createStringExprNode(String val) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.STRING;
        result.string_value = val;
        return result;
    }

    public static ExprNode createVarArgExprNode() {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.VAR_ARG;
        return result;
    }

    public static ExprNode createVarExprNode(VarNode var) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.VAR;
        result.var = var;
        return result;
    }

    public static ExprNode createFunctionCallExprNode(String ID, ExprSeqNode args) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.FUNCTION_CALL;
        result.ident = ID;
        result.args = args;
        return result;
    }

    public static ExprNode createAdjustingExprNode(ExprNode expr) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.ADJUST;
        result.adjusted_expr = expr;
        return result;
    }

    public static ExprNode createTableConstructorExprNode(FieldListNode table) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = ExprType.TABLE_CONSTRUCTOR;
        result.table_constructor = table;
        return result;
    }

    public static ExprNode createBinExprNode(ExprType type_node, ExprNode first_operand, ExprNode second_operand) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = type_node;
        result.first_operand = first_operand;
        result.second_operand = second_operand;
        return result;
    }

    public static ExprNode createUnaryExprNode(ExprType type_node, ExprNode operand) {
        ExprNode result = new ExprNode();
        result.id = Utils.getLastId();
        result.type = type_node;
        result.first_operand = operand;
        return result;
    }
}
