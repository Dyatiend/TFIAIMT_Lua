#include "create_tree.h"

struct expr_node* create_bool_expr_node(bool val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = BOOLEAN;
    result->boolean = val;
    return result;
}

struct expr_node* create_number_expr_node(float val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = NUMBER;
    result->floatVal = val;
    return result;
}

struct expr_node* create_string_expr_node(char* val) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = STRING;
    result->stringVal = val; //TODO возможно нужно strcpy юзать???
    return result;
}

struct expr_node* create_var_arg_expr_node() {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = VAR_ARG;
    return result;
}

struct expr_node* create_bin_expr_node(expr_type type_node, expr_node* first_operand, expr_node* second_operand) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = type_node;
    result->left = first_operand;
    result->right = second_operand;
    return result;
}

struct expr_node* create_unary_expr_Node(expr_type type_node, expr_node* operand) {
    struct expr_node* result = (struct expr_node*)malloc(sizeof(struct expr_node));
    result = expr_node_default;
    result->type = type_node;
    result->right = operand;
    return result;
}
