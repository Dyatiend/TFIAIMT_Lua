package nodes

class StmtNode {
    var type = StmtType.UNINITIALIZED
    var id = 0
    var isLocal = false // Является ли функция локальной
    var ident = "" // Имя переменной цикла или имя функции
    var varList: ExprSeqNode? = null // Список переменных при приравнивании
    var identList: IdentListNode? = null // Список переменных цикла или список переменных при объявлении
    var values: ExprSeqNode? = null // Значения при приравнивании или в цикле for in
    var conditionExpr: ExprNode? = null // Выражение условия для циков do, while, repeat, if, центральный оператор for
    var actionBlock: StmtSeqNode? = null // Действие циклов, функций и elseif блоков
    var ifBlock: StmtSeqNode? = null // IF true блок
    var elseifSeq: StmtSeqNode? = null // Список елзифов
    var elseBlock: StmtSeqNode? = null // Else блок
    var params: ParamListNode? = null // Параметры функции
    var functionCall: ExprNode? = null // Вызов функции
    var initialValue: ExprNode? = null // Начальное зачение переменной в цикле for
    var stepExpr: ExprNode? = null // Выражение в конце цикла for
    var next: StmtNode? = null

    companion object {
        fun createAssignStmtNode(var_list: ExprSeqNode?, expr_seq: ExprSeqNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.ASSIGNMENT
            result.varList = var_list
            result.values = expr_seq
            return result
        }

        fun createFunctionCallStmtNode(function_call: ExprNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.FUNCTION_CALL
            result.functionCall = function_call
            return result
        }

        fun createBreakStmtNode(): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.BREAK
            return result
        }

        fun createDoStmtNode(block: StmtSeqNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.DO_LOOP
            result.actionBlock = block
            return result
        }

        fun createCycleStmtNode(type_node: StmtType, expr: ExprNode?, block: StmtSeqNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = type_node
            result.conditionExpr = expr
            result.actionBlock = block
            return result
        }

        fun createIfStmtNode(
            expr: ExprNode?,
            block: StmtSeqNode?,
            elseif_seq: StmtSeqNode?,
            else_block: StmtSeqNode?
        ): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.IF
            result.conditionExpr = expr
            result.ifBlock = block
            result.elseifSeq = elseif_seq
            result.elseBlock = else_block
            return result
        }

        fun createForStmtNode(
            ID: String,
            initial_expr: ExprNode?,
            condition_expr: ExprNode?,
            step_expr: ExprNode?,
            block: StmtSeqNode?
        ): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.FOR
            result.ident = ID
            result.initialValue = initial_expr
            result.conditionExpr = condition_expr
            result.stepExpr = step_expr
            result.actionBlock = block
            return result
        }

        fun createForeachStmtNode(ident_list: IdentListNode?, expr_seq: ExprSeqNode?, block: StmtSeqNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.FOREACH
            result.identList = ident_list
            result.values = expr_seq
            result.actionBlock = block
            return result
        }

        fun createFunctionDefStmtNode(
            ID: String,
            param_list: ParamListNode?,
            block: StmtSeqNode?,
            is_local: Boolean
        ): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.FUNCTION_DEF
            result.ident = ID
            result.params = param_list
            result.actionBlock = block
            result.isLocal = is_local
            return result
        }

        fun createLocalVarStmtNode(ident_list: IdentListNode?, expr_seq: ExprSeqNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.VAR_DEF
            result.identList = ident_list
            result.values = expr_seq
            result.isLocal = true
            return result
        }

        fun createReturnStmtNode(expr_seq: ExprSeqNode?): StmtNode {
            val result = StmtNode()
            result.id = Utils.lastId
            result.type = StmtType.RETURN
            result.values = expr_seq
            return result
        }
    }
}