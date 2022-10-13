#include "tree_nodes.h"
#include <malloc.h>

//expr
struct expr_node* create_bool_expr_node(bool val);
struct expr_node* create_number_expr_node(float val);
struct expr_node* create_string_expr_node(char* val);
struct expr_node* create_var_arg_expr_node();
struct expr_node* create_bin_expr_node(enum expr_type type_node, struct expr_node* first_operand, struct expr_node* second_operand);
struct expr_node* create_unary_expr_Node(enum expr_type type_node, struct expr_node* operand);

//stmt
struct stmt_node* create_assign_stmt_node(struct expr_seq_node* var_list, struct expr_seq_node* expr_seq);
struct stmt_node* create_function_call_stmt_node(struct expr_node* function_call);
//struct stmt_node* create_break_stmt_node();
struct stmt_node* create_do_stmt_node(struct stmt_seq_node* block);
struct stmt_node* create_cycle_stmt_node(enum stmt_type type_node, struct expr_node* expr, struct stmt_seq_node* block);
struct stmt_node* create_if_stmt_node(struct expr_node* expr, struct stmt_seq_node* block, struct stmt_seq_node* elseif_seq, struct stmt_seq_node* else_block);
struct stmt_node* create_for_stmt_node(char* ID, struct expr_node* initial_expr, struct expr_node* condition_expr, struct expr_node* step_expr, struct stmt_seq_node* block);
struct stmt_node* create_foreach_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq, struct stmt_seq_node* block);
struct stmt_node* create_function_def_stmt_node(char* ID, struct param_list_node* param_list, struct stmt_seq_node* block, bool is_local);
struct stmt_node* create_local_var_stmt_node(struct ident_list_node* ident_list, struct expr_seq_node* expr_seq);
