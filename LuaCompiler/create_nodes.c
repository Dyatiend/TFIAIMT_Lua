#include "create_tree.h"

struct expr_node* create_bool_expr_node(bool val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = BOOLEAN;
    result->bool_value = val;
    return result;
}

struct expr_node* create_number_expr_node(float val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = NUMBER;
    result->number_value = val;
    return result;
}

struct expr_node* create_string_expr_node(char* val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = STRING;
    result->string_value = val; //TODO возможно нужно strcpy юзать???
    return result;
}

struct expr_node* create_var_arg_expr_node() {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = VAR_ARG;
    return result;
}

struct expr_node* create_function_call_expr_node(struct expr_node* function_call) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = FUNCTION_CALL;
    result->function_call = function_call;
    return result;
}

struct expr_node* create_adjusting_expr_node(struct expr_node* expr) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = ADJUST;
    result->adjusted_expr = expr;
    return result;
}

struct expr_node* create_table_constructor_expr_node(struct expr_node* table) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = TABLE_CONSTRUCTOR;
    result->table_constructor = table;
    return result;
}

struct expr_node* create_bin_expr_node(enum expr_type type_node, expr_node* first_operand, expr_node* second_operand) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = type_node;
    result->first_operand = first_operand;
    result->second_operand = second_operand;
    return result;
}

struct expr_node* create_unary_expr_Node(enum expr_type type_node, expr_node* operand) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = type_node;
    result->first_operand = operand;
    return result;
}

struct stmt_node* create_assign_stmt_node(struct expr_seq_node* var_list, struct expr_seq_node* expr_seq) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = ASSIGNMENT;
    result->var_list = var_list;
    result->values = expr_seq;
    return result;
}

struct stmt_node* create_function_call_stmt_node(struct expr_node* function_call) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = FUNCTION_CALL;
    result->function_call = function_call;
    return result;
}

struct stmt_node* create_do_stmt_node(struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = DO_LOOP;
    result->action_block = block;
    return result;
}

struct stmt_node* create_cycle_stmt_node(enum stmt_type type_node, struct expr_node* expr, struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = type_node;
    result->condition_expr = expr;
    result->action_block = block;
    return result;
}

struct stmt_node* create_if_stmt_node(struct expr_node* expr, struct stmt_seq_node* block, struct stmt_seq_node* elseif_seq, struct stmt_seq_node* else_block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = IF;
    result->condition_expr = expr;
    result->if_block = block;
    result->elseif_seq = elseif_seq;
    result->else_block = else_block;
    return result;
}

struct stmt_node* create_for_stmt_node(char* ID, struct expr_node* initial_expr, struct expr_node* condition_expr, struct expr_node* step_expr, struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = FOR;
    result->ident = ID; //TODO возможно нужно strcpy юзать???
    result->initial_value = initial_expr;
    result->condition_expr = condition_expr;
    result->step_expr = step_expr;
    result->action_block = block;
    return result;
}

struct stmt_node* create_foreach_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq, struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = FOR_IN;
    result->ident_list = ident_list;
    result->values = expr_seq;
    result->action_block = block;
    return result;
}

struct stmt_node* create_function_def_stmt_node(char* ID, struct param_list_node* param_list, struct stmt_seq_node* block, bool is_local) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    result->type = FUNCTION_DEF;
    result->ident = ID; //TODO возможно нужно strcpy юзать???
    result->params = param_list;
    result->action_block = block;
    result->is_local = is_local;
    return result;
}

struct stmt_node* create_local_var_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    result = stmt_node_default;
    if(expr_seq != NULL) {
        result->type = VAR_DEF_WITH_ASSIGNMENT;
    } else {
        result->type = VAR_DEF;
    }
    result->ident_list = ident_list;
    result->values = expr_seq;
    result->is_local = true;
    return result;
}
