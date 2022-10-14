#pragma once

#include "tree_nodes.h"
#include <stdio.h>

void print_program(struct stmt_seq_node * node, FILE * file);
void print_stmt_node(struct stmt_node * node, FILE * file);
void print_stmt_seq_node(struct stmt_seq_node * node, void * parent, FILE * file);
void print_expr_node(struct expr_node * node, FILE * file);
void print_expr_seq_node(struct expr_seq_node * node, void * parent, FILE * file);
void print_var_item_node(struct var_item_node * node, FILE * file);
void print_var_node(struct var_node * node, void * parent, FILE * file);
void print_ident_node(struct ident_node * node, FILE * file);
void print_ident_list_node(struct ident_list_node * node, void * parent, FILE * file);
void print_param_list_node(struct param_list_node * node, void * parent, FILE * file);
void print_field_node(struct field_node * node, FILE * file);
void print_field_list_node(struct field_list_node * node, void * parent, FILE * file);