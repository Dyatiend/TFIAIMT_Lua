package nodes;

public class StmtNode {
    public StmtType type = StmtType.UNINITIALIZED;

    public int id = 0;

    public boolean is_local = false; // Является ли функция локальной
    public String ident = ""; // Имя переменной цикла или имя функции

    public ExprSeqNode var_list = null; // Список переменных при приравнивании
    public IdentListNode ident_list = null; // Список переменных цикла или список переменных при объявлении
    public ExprSeqNode values = null; // Значения при приравнивании или в цикле for in

    public ExprNode condition_expr = null; // Выражение условия для циков do, while, repeat, if, центральный оператор for
    public StmtSeqNode action_block = null;  // Действие циклов, функций и elseif блоков

    public StmtSeqNode if_block = null; // IF true блок
    public StmtSeqNode elseif_seq = null; // Список елзифов
    public StmtSeqNode else_block = null; // Else блок

    public ParamListNode params = null; // Параметры функции

    public ExprNode function_call = null; // Вызов функции

    public ExprNode initial_value = null; // Начальное зачение переменной в цикле for
    public ExprNode step_expr = null; // Выражение в конце цикла for

    public StmtNode next = null;

    public static StmtNode createAssignStmtNode(ExprSeqNode var_list, ExprSeqNode expr_seq) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.ASSIGNMENT;
        result.var_list = var_list;
        result.values = expr_seq;
        return result;
    }

    public static StmtNode createFunctionCallStmtNode(ExprNode function_call) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.FUNCTION_CALL;
        result.function_call = function_call;
        return result;
    }

    public static StmtNode createBreakStmtNode() {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.BREAK;
        return result;
    }

    public static StmtNode createDoStmtNode(StmtSeqNode block) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.DO_LOOP;
        result.action_block = block;
        return result;
    }

    public static StmtNode createCycleStmtNode(StmtType type_node, ExprNode expr, StmtSeqNode block) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = type_node;
        result.condition_expr = expr;
        result.action_block = block;
        return result;
    }

    public static StmtNode createIfStmtNode(ExprNode expr, StmtSeqNode block, StmtSeqNode elseif_seq, StmtSeqNode else_block) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.IF;
        result.condition_expr = expr;
        result.if_block = block;
        result.elseif_seq = elseif_seq;
        result.else_block = else_block;
        return result;
    }

    public static StmtNode createForStmtNode(String ID, ExprNode initial_expr, ExprNode condition_expr, ExprNode step_expr, StmtSeqNode block) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.FOR;
        result.ident = ID;
        result.initial_value = initial_expr;
        result.condition_expr = condition_expr;
        result.step_expr = step_expr;
        result.action_block = block;
        return result;
    }

    public static StmtNode createForeachStmtNode(IdentListNode ident_list, ExprSeqNode expr_seq, StmtSeqNode block) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.FOR_IN;
        result.ident_list = ident_list;
        result.values = expr_seq;
        result.action_block = block;
        return result;
    }

    public static StmtNode createFunctionDefStmtNode(String ID, ParamListNode param_list, StmtSeqNode block, boolean is_local) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.FUNCTION_DEF;
        result.ident = ID;
        result.params = param_list;
        result.action_block = block;
        result.is_local = is_local;
        return result;
    }

    public static StmtNode createLocalVarStmtNode(IdentListNode ident_list, ExprSeqNode expr_seq) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.VAR_DEF;
        result.ident_list = ident_list;
        result.values = expr_seq;
        result.is_local = true;
        return result;
    }

    public static StmtNode createReturnStmtNode(ExprSeqNode expr_seq ) {
        StmtNode result = new StmtNode();
        result.id = Utils.getLastId();
        result.type = StmtType.RETURN;
        result.values = expr_seq;
        return result;
    }
}
