#pragma once

#include "tree_nodes.h"
#include <stdlib.h>

//expr
struct expr_node* create_nil_expr_node();
struct expr_node* create_bool_expr_node(bool val);
struct expr_node* create_float_number_expr_node(double val);
struct expr_node* create_int_number_expr_node(int val);
struct expr_node* create_string_expr_node(char* val);
struct expr_node* create_var_arg_expr_node();
struct expr_node* create_var_expr_node(struct var_node* var);
struct expr_node* create_function_call_expr_node(char* ID, struct expr_seq_node* args);
struct expr_node* create_adjusting_expr_node(struct expr_node* expr);
struct expr_node* create_table_constructor_expr_node(struct field_list_node* table);
struct expr_node* create_bin_expr_node(enum expr_type type_node, struct expr_node* first_operand, struct expr_node* second_operand);
struct expr_node* create_unary_expr_Node(enum expr_type type_node, struct expr_node* operand);

//expr_seq
struct expr_seq_node* create_table_constructor_expr_seq_node(struct field_list_node* table_constructor);
struct expr_seq_node* create_string_expr_seq_node(char* string);
struct expr_seq_node* create_expr_seq_node(struct expr_node* expr);
struct expr_seq_node* add_expr_to_expr_seq_node(struct expr_seq_node* list, struct expr_node* expr);

//stmt
struct stmt_node* create_assign_stmt_node(struct expr_seq_node* var_list, struct expr_seq_node* expr_seq);
struct stmt_node* create_function_call_stmt_node(struct expr_node* function_call);
struct stmt_node* create_break_stmt_node();
struct stmt_node* create_do_stmt_node(struct stmt_seq_node* block);
struct stmt_node* create_cycle_stmt_node(enum stmt_type type_node, struct expr_node* expr, struct stmt_seq_node* block);
struct stmt_node* create_if_stmt_node(struct expr_node* expr, struct stmt_seq_node* block, struct stmt_seq_node* elseif_seq, struct stmt_seq_node* else_block);
struct stmt_node* create_for_stmt_node(char* ID, struct expr_node* initial_expr, struct expr_node* condition_expr, struct expr_node* step_expr, struct stmt_seq_node* block);
struct stmt_node* create_foreach_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq, struct stmt_seq_node* block);
struct stmt_node* create_function_def_stmt_node(char* ID, struct param_list_node* param_list, struct stmt_seq_node* block, bool is_local);
struct stmt_node* create_local_var_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq);
struct stmt_node* create_return_stmt_node(struct expr_seq_node* expr_seq );

//stmt_seq
struct stmt_seq_node* create_elseif_seq_stmt_seq_node();
struct stmt_seq_node* add_elseif_seq_stmt_seq_node(struct stmt_seq_node* elseif_seq, struct expr_node* expr, struct stmt_seq_node* block);
struct stmt_seq_node* create_stmt_seq_node();
struct stmt_seq_node* add_stmt_to_stmt_seq_node(struct stmt_seq_node* stmt_seq, struct stmt_node* stmt);

//field
struct field_node* create_field_node(char* ID, struct expr_node* expr_value, struct expr_node* expr_key);

//field_list
struct field_list_node* create_field_list_node(struct field_node* field);
struct field_list_node* add_field_to_field_list_node(struct field_list_node* list, struct field_node* field);

//ident_list
struct ident_list_node* create_ident_list_node(char* ID);
struct ident_list_node* add_ident_to_ident_list_node(struct ident_list_node* list, char* ID);

//param_list
struct param_list_node* create_param_list_node(struct ident_list_node* ident_list, bool var_arg);

//var
struct var_node* create_id_var_node(char* id);
struct var_node* add_expr_to_var_node(struct var_node* var, struct expr_node* expr);
struct var_node* add_id_to_var_node(struct var_node* var, char* id);
struct var_node* create_function_with_expr_var_node(struct expr_node* function_call, struct expr_node* expr);
struct var_node* create_function_with_id_var_node(struct expr_node* function_call, char* id);
struct var_node* create_expr_with_expr_var_node(struct expr_node* expr1, struct expr_node* expr2);
struct var_node* create_expr_with_id_var_node(struct expr_node* expr, char* id);

//var_list
struct expr_seq_node* create_var_list_node(struct var_node* var);
struct expr_seq_node* add_var_to_var_list_node(struct expr_seq_node* list, struct var_node* var);

//chunk
struct chunk_node* create_chunk_node(struct stmt_seq_node* block);
