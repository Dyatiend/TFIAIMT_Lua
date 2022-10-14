#include "print_tree.h"

void print_program(struct stmt_seq_node * node, FILE * file) {
    fprintf(file, "digraph G {\n");
	fprintf(file, "ID%p [label=\"program\"]\n", node);
	if (node->first != NULL) {
		print_stmt_seq_node(node, NULL, file);
	}
	fprintf(file, "}");
}

void print_stmt_node(struct stmt_node * node, FILE * file) {
	switch (node->type) {
        case ASSIGNMENT:
            break;
        case _FUNCTION_CALL:
            break;
        case BREAK:
            break;
        case DO_LOOP:
            break;
        case WHILE_LOOP:
            break;
        case REPEAT_LOOP:
            break;
        case IF:
            break;
        case FOR:
            break;
        case FOR_IN:
            break;
        case FUNCTION_DEF:
            break;
        case VAR_DEF:
            break;
        case RETURN:
            break;
        default:
            break;
	}
}

void print_stmt_seq_node(struct stmt_seq_node * node, void * parent, FILE * file) {
    struct stmt_node * current = node->first;
	while (current != NULL) {
		print_stmt_node(current, file);
		fprintf(file, "ID%p->ID%p\n", parent, current);
		current = current->next;
	}
}

void print_expr_node(struct expr_node * node, FILE * file) {
    switch (node->type) {
        case NIL:
            break;
        case BOOLEAN:
            break;
        case NUMBER:
            break;
        case STRING:
            break;
        case VAR_ARG:
            break;
        case VAR:
            break;
        case FUNCTION_CALL:
            break;
        case ADJUST:
            break;
        case TABLE_CONSTRUCTOR:
            break;
        case PLUS:
            break;
        case MINUS:
            break;
        case MUL:
            break;
        case DIV:
            break;
        case FLOOR_DIV:
            break;
        case POW:
            break;
        case XOR:
            break;
        case MOD:
            break;
        case BIT_AND:
            break;
        case BIT_OR:
            break;
        case CONCAT:
            break;
        case LESS:
            break;
        case LE:
            break;
        case GREATER:
            break;
        case GE:
            break;
        case EQUAL:
            break;
        case NOT_EQUAL:
            break;
        case LOG_AND:
            break;
        case LOG_OR:
            break;
        case UNARY_MINUS:
            break;
        case NOT:
            break;
        case LEN:
            break;
        case BIT_NOT:
            break;
        default:
            break;
    }
}

void print_expr_seq_node(struct expr_seq_node * node, void * parent, FILE * file) {
    struct expr_node * current = node->first;
    while (current != NULL) {
        print_expr_node(current, file);
        fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
}

void print_var_item_node(struct var_item_node * node, FILE * file) {
    switch (node->type) {
        case IDENT:
            break;
        case __VAR:
            break;
        case __FUNCTION_CALL:
            break;
        case ADJUSTED_EXPR:
            break;
        default:
            break;
    }
}

void print_var_node(struct var_node * node, void * parent, FILE * file) {
    struct var_item_node * current = node->first;
    while (current != NULL) {
        print_var_item_node(current, file);
        fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
}

void print_ident_node(struct ident_node * node, FILE * file) {
    fprintf(file, "ID%p [label=\"ident %s id %d\"]\n", node, node->ident, node->id);
}

void print_ident_list_node(struct ident_list_node * node, void * parent, FILE * file) {
    struct ident_node * current = node->first;
    while (current != NULL) {
        print_ident_node(current, file);
        fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
}

void print_param_list_node(struct param_list_node * node, void * parent, FILE * file) {
    print_ident_list_node(node->list, parent, file);
    if(node->has_var_arg) {
        fprintf(file, "ID%p [label=\"var_arg\"]\n", node);
        fprintf(file, "ID%p->ID%p\n", parent, node);
    }
}

void print_field_node(struct field_node * node, FILE * file) {
    if(node->ident != NULL) {
        
    }
    else if(node->key != NULL) {
       
    }
    else {
        
    }
}

void print_field_list_node(struct field_list_node * node, void * parent, FILE * file) {
    struct field_node * current = node->first;
    while (current != NULL) {
        print_field_node(current, file);
        fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
}
