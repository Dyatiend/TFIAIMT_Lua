#include "create_nodes.h"

unsigned int LAST_ID = 0;

struct stmt_node stmt_node_default = {
        _UNINITIALIZED, 0, false, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL
};

struct stmt_seq_node stmt_seq_node_default = { NULL, NULL };

struct expr_node expr_node_default = {
        UNINITIALIZED, 0, false, 0, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL
};

struct var_item_node var_item_node_default = {
        __UNINITIALIZED, 0, false, NULL, NULL, NULL, NULL
};

struct var_node var_node_default = { NULL, NULL };

struct expr_seq_node expr_seq_node_default = { NULL, NULL };

struct ident_node ident_node_default = { 0, NULL, NULL };

struct ident_list_node ident_list_node_default = { NULL, NULL };

struct param_list_node param_list_node_default = { false, NULL };

struct field_node field_node_default = { 0, NULL, NULL, NULL };

struct field_list_node field_list_node_default = { NULL, NULL };

struct expr_node* create_nil_expr_node() {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = _NIL;
    return result;
}

struct expr_node* create_bool_expr_node(bool val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = BOOLEAN;
    result->bool_value = val;
    return result;
}

struct expr_node* create_float_number_expr_node(double val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = _FLOAT_NUMBER;
    result->float_number_value = val;
    return result;
}

struct expr_node* create_int_number_expr_node(int val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = _INT_NUMBER;
    result->int_number_value = val;
    return result;
}

struct expr_node* create_string_expr_node(char* val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = _STRING;
    result->string_value = val; //TODO ???????????????? ?????????? strcpy ?????????????
    return result;
}

struct expr_node* create_var_arg_expr_node() {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = _VAR_ARG;
    return result;
}

struct expr_node* create_var_expr_node(struct var_node* var) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = VAR;
    result->var = var;
    return result;
}

struct expr_node* create_function_call_expr_node(char* ID, struct expr_seq_node* args) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = FUNCTION_CALL;
    result->ident = ID;
    result->args = args;
    return result;
}

struct expr_node* create_adjusting_expr_node(struct expr_node* expr) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = ADJUST;
    result->adjusted_expr = expr;
    return result;
}

struct expr_node* create_table_constructor_expr_node(struct field_list_node* table) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = TABLE_CONSTRUCTOR;
    result->table_constructor = table;
    return result;
}

struct expr_node* create_bin_expr_node(enum expr_type type_node, struct expr_node* first_operand, struct expr_node* second_operand) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = type_node;
    result->first_operand = first_operand;
    result->second_operand = second_operand;
    return result;
}

struct expr_node* create_unary_expr_Node(enum expr_type type_node, struct expr_node* operand) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    * result = expr_node_default;
    result->id = LAST_ID++;
    result->type = type_node;
    result->first_operand = operand;
    return result;
}

struct expr_seq_node* create_table_constructor_expr_seq_node(struct field_list_node* table_constructor) {
    struct expr_seq_node* result = (struct expr_seq_node*)malloc(sizeof(struct expr_seq_node));
    * result = expr_seq_node_default;
    struct expr_node* expr_node_table = (struct expr_node*)malloc(sizeof(struct expr_node));
    * expr_node_table = expr_node_default;
    expr_node_table->id = LAST_ID++;
    expr_node_table->type = TABLE_CONSTRUCTOR;
    expr_node_table->table_constructor = table_constructor;
    result->first = expr_node_table;
    result->last = expr_node_table;
    return result;
}

struct expr_seq_node* create_string_expr_seq_node(char* string) {
    struct expr_seq_node* result = (struct expr_seq_node*)malloc(sizeof(struct expr_seq_node));
    * result = expr_seq_node_default;
    struct expr_node* expr_node_string = (struct expr_node*)malloc(sizeof(struct expr_node));
    * expr_node_string = expr_node_default;
    expr_node_string->id = LAST_ID++;
    expr_node_string->type = _STRING;
    expr_node_string->string_value = string;
    result->first = expr_node_string;
    result->last = expr_node_string;
    return result;
}

struct expr_seq_node* create_expr_seq_node(struct expr_node* expr) {
    struct expr_seq_node* result = (struct expr_seq_node*)malloc(sizeof(struct expr_seq_node));
    * result = expr_seq_node_default;
    result->first = expr;
    result->last = expr;
    return result;
}

struct expr_seq_node* add_expr_to_expr_seq_node(struct expr_seq_node* list, struct expr_node* expr) {
    list->last->next = expr;
    list->last = expr;
    return list;
}

struct stmt_node* create_assign_stmt_node(struct expr_seq_node* var_list, struct expr_seq_node* expr_seq) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = ASSIGNMENT;
    result->var_list = var_list;
    result->values = expr_seq;
    return result;
}

struct stmt_node* create_function_call_stmt_node(struct expr_node* function_call) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = _FUNCTION_CALL;
    result->function_call = function_call;
    return result;
}

struct stmt_node* create_break_stmt_node() {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = _BREAK;
    return result;
}

struct stmt_node* create_do_stmt_node(struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = DO_LOOP;
    result->action_block = block;
    return result;
}

struct stmt_node* create_cycle_stmt_node(enum stmt_type type_node, struct expr_node* expr, struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = type_node;
    result->condition_expr = expr;
    result->action_block = block;
    return result;
}

struct stmt_node* create_if_stmt_node(struct expr_node* expr, struct stmt_seq_node* block, struct stmt_seq_node* elseif_seq, struct stmt_seq_node* else_block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = _IF;
    result->condition_expr = expr;
    result->if_block = block;
    result->elseif_seq = elseif_seq;
    result->else_block = else_block;
    return result;
}

struct stmt_node* create_for_stmt_node(char* ID, struct expr_node* initial_expr, struct expr_node* condition_expr, struct expr_node* step_expr, struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = _FOR;
    result->ident = ID; //TODO ???????????????? ?????????? strcpy ?????????????
    result->initial_value = initial_expr;
    result->condition_expr = condition_expr;
    result->step_expr = step_expr;
    result->action_block = block;
    return result;
}

struct stmt_node* create_foreach_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq, struct stmt_seq_node* block) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = FOR_IN;
    result->ident_list = ident_list;
    result->values = expr_seq;
    result->action_block = block;
    return result;
}

struct stmt_node* create_function_def_stmt_node(char* ID, struct param_list_node* param_list, struct stmt_seq_node* block, bool is_local) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = FUNCTION_DEF;
    result->ident = ID; //TODO ???????????????? ?????????? strcpy ?????????????
    result->params = param_list;
    result->action_block = block;
    result->is_local = is_local;
    return result;
}

struct stmt_node* create_local_var_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = VAR_DEF;
    result->ident_list = ident_list;
    result->values = expr_seq;
    result->is_local = true;
    return result;
}

struct stmt_node* create_return_stmt_node(struct expr_seq_node* expr_seq ) {
    struct stmt_node* result = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * result = stmt_node_default;
    result->id = LAST_ID++;
    result->type = _RETURN;
    result->values = expr_seq;
    return result;
}

struct stmt_seq_node* create_elseif_seq_stmt_seq_node() {
    struct stmt_seq_node* result = (struct stmt_seq_node*)malloc(sizeof(struct stmt_seq_node));
    * result = stmt_seq_node_default;
    return result;
}

struct stmt_seq_node* add_elseif_seq_stmt_seq_node(struct stmt_seq_node* elseif_seq, struct expr_node* expr, struct stmt_seq_node* block) {
    struct stmt_node* else_if = (struct stmt_node*)malloc(sizeof(struct stmt_node));
    * else_if = stmt_node_default;
    else_if->id = LAST_ID++;
    else_if->condition_expr = expr;
    else_if->if_block = block;

    if(elseif_seq->last == NULL) {
        elseif_seq->first = else_if;
        elseif_seq->last = else_if;
    }
    else {
        elseif_seq->last->next = else_if;
        elseif_seq->last = else_if;
    }
    return elseif_seq;
}

struct stmt_seq_node* create_stmt_seq_node() {
    struct stmt_seq_node* result = (struct stmt_seq_node*)malloc(sizeof(struct stmt_seq_node));
    * result = stmt_seq_node_default;
    return result;
}

struct stmt_seq_node* add_stmt_to_stmt_seq_node(struct stmt_seq_node* stmt_seq, struct stmt_node* stmt) {
    if(stmt_seq->last == NULL) {
        stmt_seq->first = stmt;
        stmt_seq->last = stmt;
    }
    else {
        stmt_seq->last->next = stmt;
        stmt_seq->last = stmt;
    }
    return stmt_seq;
}


struct field_node* create_field_node(char* ID, struct expr_node* expr_value, struct expr_node* expr_key) {
    struct field_node* result = (struct field_node*)malloc(sizeof(struct field_node));
    * result = field_node_default;
    result->id = LAST_ID++;
    result->ident = ID;
    result->key = expr_key;
    result->value = expr_value;
    return result;
}


struct field_list_node* create_field_list_node(struct field_node* field) {
    struct field_list_node* result = (struct field_list_node*)malloc(sizeof(struct field_list_node));
    * result = field_list_node_default;
    result->first = field;
    result->last = field;
    return result;
}

struct field_list_node* add_field_to_field_list_node(struct field_list_node* list, struct field_node* field) {
    list->last->next = field;
    list->last = field;
    return list;
}

struct ident_list_node* create_ident_list_node(char* ID) {
    struct ident_list_node* result = (struct ident_list_node*)malloc(sizeof(struct ident_list_node));
    * result = ident_list_node_default;
    struct ident_node* new_indent_node = (struct ident_node*)malloc(sizeof(struct ident_node));
    * new_indent_node = ident_node_default;
    new_indent_node->id = LAST_ID++;
    new_indent_node->ident = ID;
    result->first = new_indent_node;
    result->last = new_indent_node;
    return result;
}

struct ident_list_node* add_ident_to_ident_list_node(struct ident_list_node* list, char* ID) {
    struct ident_node* new_indent_node = (struct ident_node*)malloc(sizeof(struct ident_node));
    * new_indent_node = ident_node_default;
    new_indent_node->id = LAST_ID++;
    new_indent_node->ident = ID;
    list->last->next = new_indent_node;
    list->last = new_indent_node;
    return list;
}

struct param_list_node* create_param_list_node(struct ident_list_node* ident_list, bool var_arg) {
    struct param_list_node* result = (struct param_list_node*)malloc(sizeof(struct param_list_node));
    * result = param_list_node_default;
    result->has_var_arg = var_arg;
    result->list = ident_list;
    return result;
}

struct var_node* create_id_var_node(char* id) {
    struct var_node* result = (struct var_node*)malloc(sizeof(struct var_node));
    * result = var_node_default;
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = _IDENT;
    item->ident = id;
    result->first = item;
    result->last = item;
    return result;
}

struct var_node* add_expr_to_var_node(struct var_node* var, struct expr_node* expr) {
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = __VAR;
    item->second_expr = expr;
    var->last->next = item;
    var->last = item;
    return var;
}

struct var_node* add_id_to_var_node(struct var_node* var, char* id) {
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = __VAR;
    item->ident = id;
    item->is_map_key = true;
    var->last->next = item;
    var->last = item;
    return var;
}

struct var_node* create_function_with_expr_var_node(struct expr_node* function_call, struct expr_node* expr) {
    struct var_node* result = (struct var_node*)malloc(sizeof(struct var_node));
    * result = var_node_default;
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = __FUNCTION_CALL;
    item->first_expr = function_call;
    item->second_expr = expr;
    result->first = item;
    result->last = item;
    return result;
}

struct var_node* create_function_with_id_var_node(struct expr_node* function_call, char* id) {
    struct var_node* result = (struct var_node*)malloc(sizeof(struct var_node));
    * result = var_node_default;
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = __FUNCTION_CALL;
    item->first_expr = function_call;
    item->ident = id;
    item->is_map_key = true;
    result->first = item;
    result->last = item;
    return result;
}

struct var_node* create_expr_with_expr_var_node(struct expr_node* expr1, struct expr_node* expr2) {
    struct var_node* result = (struct var_node*)malloc(sizeof(struct var_node));
    * result = var_node_default;
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = ADJUSTED_EXPR;
    item->first_expr = expr1;
    item->second_expr = expr2;
    result->first = item;
    result->last = item;
    return result;
}

struct var_node* create_expr_with_id_var_node(struct expr_node* expr, char* id) {
    struct var_node* result = (struct var_node*)malloc(sizeof(struct var_node));
    * result = var_node_default;
    struct var_item_node* item = (struct var_item_node*)malloc(sizeof(struct var_item_node));
    * item = var_item_node_default;
    item->id = LAST_ID++;
    item->type = ADJUSTED_EXPR;
    item->first_expr = expr;
    item->ident = id;
    item->is_map_key = true;
    result->first = item;
    result->last = item;
    return result;
}


struct expr_seq_node* create_var_list_node(struct var_node* var) {
    struct expr_seq_node* result = (struct expr_seq_node*)malloc(sizeof(struct expr_seq_node));
    * result = expr_seq_node_default;
    struct expr_node* expr_var_node = (struct expr_node*)malloc(sizeof(struct expr_node));
    * expr_var_node = expr_node_default;
    expr_var_node->id = LAST_ID++;
    expr_var_node->type = VAR;
    expr_var_node->var = var;
    result->first = expr_var_node;
    result->last = expr_var_node;
    return result;
}

struct expr_seq_node* add_var_to_var_list_node(struct expr_seq_node* list, struct var_node* var) {
    struct expr_node* expr_var_node = (struct expr_node*)malloc(sizeof(struct expr_node));
    * expr_var_node = expr_node_default;
    expr_var_node->id = LAST_ID++;
    expr_var_node->type = VAR;
    expr_var_node->var = var;
    list->last->next = expr_var_node;
    list->last = expr_var_node;
    return list;
}

struct chunk_node* create_chunk_node(struct stmt_seq_node* block) {
    struct chunk_node* result = (struct chunk_node*)malloc(sizeof(struct chunk_node));
    result->block = block;
    return result;
}
