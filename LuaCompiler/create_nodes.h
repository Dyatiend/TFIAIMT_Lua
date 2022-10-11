#include "tree_nodes.h"
#include <malloc.h>

struct expr_node* create_bool_expr_node(bool val);
struct expr_node* create_number_expr_node(float val);
struct expr_node* create_string_expr_node(char* val);
struct expr_node* create_var_arg_expr_node();
struct expr_node* create_bin_expr_node(expr_type type_node, expr_node* first_operand, expr_node* second_operand);
struct expr_node* create_unary_expr_Node(expr_type type_node, expr_node* operand);