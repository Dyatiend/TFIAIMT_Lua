#pragma once

#include "tree_nodes.h"
#include <stdio.h>

void program_to_xml(struct chunk_node * node, FILE * file);
void stmt_node_to_xml(struct stmt_node * node, FILE * file);
void stmt_seq_node_to_xml(struct stmt_seq_node * node, void * parent, FILE * file);
void expr_node_to_xml(struct expr_node * node, FILE * file);
void expr_seq_node_to_xml(struct expr_seq_node * node, void * parent, FILE * file);
void var_item_node_to_xml(struct var_item_node * node, FILE * file);
void var_node_to_xml(struct var_node * node, void * parent, FILE * file);
void ident_node_to_xml(struct ident_node * node, FILE * file);
void ident_list_node_to_xml(struct ident_list_node * node, void * parent, FILE * file);
void param_list_node_to_xml(struct param_list_node * node, void * parent, FILE * file);
void field_node_to_xml(struct field_node * node, FILE * file);
void field_list_node_to_xml(struct field_list_node * node, void * parent, FILE * file);