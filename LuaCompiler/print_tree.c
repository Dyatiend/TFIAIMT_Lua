#include "print_tree.h"

void print_program(struct chunk_node * node, FILE * file) {
    fprintf(file, "digraph G {\n");
	fprintf(file, "ID%p [label=\"program\"]\n", node);
	if (node->block != NULL) {
		print_stmt_seq_node(node->block, node, file);
	}
	fprintf(file, "}");
}

void print_stmt_node(struct stmt_node * node, FILE * file) {
	switch (node->type) {
        case ASSIGNMENT:
            fprintf(file, "ID%p [label=\"ASSIGNMENT id %d\"]\n", node, node->id);
            fprintf(file, "ID%p [label=\"VARS\"]\n", node->var_list);
            fprintf(file, "ID%p [label=\"VALUES\"]\n", node->values);
            fprintf(file, "ID%p->ID%p\n", node, node->var_list);
            fprintf(file, "ID%p->ID%p\n", node, node->values);
            print_expr_seq_node(node->var_list, node->var_list, file);
            print_expr_seq_node(node->values, node->values, file);
            break;
        case _FUNCTION_CALL:
            fprintf(file, "ID%p [label=\"_FUNCTION_CALL id %d\"]\n", node, node->id);
            print_expr_node(node->function_call, file);
            fprintf(file, "ID%p->ID%p\n", node, node->function_call);
            break;
        case _BREAK:
            fprintf(file, "ID%p [label=\"BREAK id %d\"]\n", node, node->id);
            break;
        case DO_LOOP:
            fprintf(file, "ID%p [label=\"DO_LOOP id %d\"]\n", node, node->id);
            fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            print_stmt_seq_node(node->action_block, node->action_block, file);
            break;
        case WHILE_LOOP:
            fprintf(file, "ID%p [label=\"WHILE_LOOP id %d\"]\n", node, node->id);
            print_expr_node(node->condition_expr, file);
            fprintf(file, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            print_stmt_seq_node(node->action_block, node->action_block, file);
            break;
        case REPEAT_LOOP:
            fprintf(file, "ID%p [label=\"REPEAT_LOOP id %d\"]\n", node, node->id);
            fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            print_stmt_seq_node(node->action_block, node->action_block, file);
            print_expr_node(node->condition_expr, file);
            fprintf(file, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            break;
        case _IF:
            fprintf(file, "ID%p [label=\"IF id %d\"]\n", node, node->id);
            print_expr_node(node->condition_expr, file);
            fprintf(file, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            
            fprintf(file, "ID%p [label=\"IF BLOCK\"]\n", node->if_block);
            fprintf(file, "ID%p->ID%p\n", node, node->if_block);
            print_stmt_seq_node(node->if_block, node->if_block, file);
            
            if(node->elseif_seq != NULL) {
                fprintf(file, "ID%p [label=\"ELSEIF SEQ\"]\n", node->elseif_seq);
                fprintf(file, "ID%p->ID%p\n", node, node->elseif_seq);
                print_stmt_seq_node(node->elseif_seq, node->elseif_seq, file);
            }

            if(node->else_block != NULL) {
                fprintf(file, "ID%p [label=\"ELSE BLOCK\"]\n", node->else_block);
                fprintf(file, "ID%p->ID%p\n", node, node->else_block);
                print_stmt_seq_node(node->else_block, node->else_block, file);
            }

            break;
        case _FOR:
            fprintf(file, "ID%p [label=\"FOR id %d\"]\n", node, node->id);
            fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
            fprintf(file, "ID%p->ID%p [label=\"VAR\"]\n", node, node->ident);
            
            print_expr_node(node->initial_value, file);
            fprintf(file, "ID%p->ID%p [label=\"INIT VAL\"]\n", node, node->initial_value);
            
            print_expr_node(node->condition_expr, file);
            fprintf(file, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            
            if(node->step_expr != NULL) {
                print_expr_node(node->step_expr, file);
                fprintf(file, "ID%p->ID%p [label=\"STEP EXPR\"]\n", node, node->step_expr);
            }

            fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            print_stmt_seq_node(node->action_block, node->action_block, file);

            break;
        case FOR_IN:
            fprintf(file, "ID%p [label=\"FOREACH id %d\"]\n", node, node->id);

            fprintf(file, "ID%p [label=\"IDENT LIST\"]\n", node->ident_list);
            fprintf(file, "ID%p->ID%p\n", node, node->ident_list);
            print_ident_list_node(node->ident_list, node->ident_list, file);
            
            fprintf(file, "ID%p [label=\"VALUES\"]\n", node->values);
            fprintf(file, "ID%p->ID%p\n", node, node->values);
            print_expr_seq_node(node->values, node->values, file);

            fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            print_stmt_seq_node(node->action_block, node->action_block, file);
            
            break;
        case FUNCTION_DEF:
            if(node->is_local) {
                fprintf(file, "ID%p [label=\"LOCAL FUNC id %d\"]\n", node, node->id);

                fprintf(file, "ID%p [label=\"PARAMS\"]\n", node->params);
                fprintf(file, "ID%p->ID%p\n", node, node->params);
                print_stmt_seq_node(node->params, node->params, file);

                fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
                fprintf(file, "ID%p->ID%p\n", node, node->action_block);
                print_stmt_seq_node(node->action_block, node->action_block, file);
            
            } else {
                fprintf(file, "ID%p [label=\"LOCAL FUNC id %d\"]\n", node, node->id);

                fprintf(file, "ID%p [label=\"PARAMS\"]\n", node->params);
                fprintf(file, "ID%p->ID%p\n", node, node->params);
                print_stmt_seq_node(node->params, node->params, file);

                fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
                fprintf(file, "ID%p->ID%p\n", node, node->action_block);
                print_stmt_seq_node(node->action_block, node->action_block, file);
            }

            break;
        case VAR_DEF:
            fprintf(file, "ID%p [label=\"VAR DEF id %d\"]\n", node, node->id);

            fprintf(file, "ID%p [label=\"IDENT LIST\"]\n", node->ident_list);
            fprintf(file, "ID%p->ID%p\n", node, node->ident_list);
            print_ident_list_node(node->ident_list, node->ident_list, file);
            
            if(node->values != NULL) {
                fprintf(file, "ID%p [label=\"VALUES\"]\n", node->values);
                fprintf(file, "ID%p->ID%p\n", node, node->values);
                print_expr_seq_node(node->values, node->values, file);
            }

            break;
        case _RETURN:
            fprintf(file, "ID%p [label=\"RETURN id %d\"]\n", node, node->id);
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
        case _NIL:
            fprintf(file, "ID%p [label=\"NIL id %d\"]\n", node, node->id);
            
            break;
        case BOOLEAN:
            if(node->bool_value) {
                fprintf(file, "ID%p [label=\"BOOL true id %d\"]\n", node, node->id);
            } else {
                fprintf(file, "ID%p [label=\"BOOL false id %d\"]\n", node, node->id);
            }
            break;
        case _NUMBER:
            fprintf(file, "ID%p [label=\"NUMBER %f id %d\"]\n", node, node->number_value, node->id);
            
            break;
        case _STRING:
            fprintf(file, "ID%p [label=\"STRING %s id %d\"]\n", node, node->string_value, node->id);
            
            break;
        case _VAR_ARG:
            fprintf(file, "ID%p [label=\"VAR_ARG id %d\"]\n", node, node->id);
            
            break;
        case VAR:
            fprintf(file, "ID%p [label=\"VAR id %d\"]\n", node, node->id);
            
            print_var_node(node->var, node, file);

            break;
        case FUNCTION_CALL:
            fprintf(file, "ID%p [label=\"FUNCTION CALL id %d\"]\n", node, node->id);
            
            fprintf(file, "ID%p [label=\"name %s\"]\n", node->ident, node->ident);
            fprintf(file, "ID%p->ID%p [label=\"name\"]\n", node, node->ident);

            fprintf(file, "ID%p [label=\"ARGS\"]\n", node->args);
            fprintf(file, "ID%p->ID%p\n", node, node->args);
            print_expr_seq_node(node->args, node->args, file);

            break;
        case ADJUST:
            fprintf(file, "ID%p [label=\"ADJUST id %d\"]\n", node, node->id);
            
            print_expr_node(node->adjusted_expr, file);
            fprintf(file, "ID%p->ID%p\n", node, node->adjusted_expr);
            
            break;
        case TABLE_CONSTRUCTOR:
            fprintf(file, "ID%p [label=\"TABLE id %d\"]\n", node, node->id);
            
            print_field_list_node(node->table_constructor, node, file);
            fprintf(file, "ID%p->ID%p\n", node, node->table_constructor);
            

            break;
        case PLUS:
            fprintf(file, "ID%p [label=\"PLUS id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            

            break;
        case MINUS:
            fprintf(file, "ID%p [label=\"MINUS id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case MUL:
            fprintf(file, "ID%p [label=\"MUL id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case DIV:
            fprintf(file, "ID%p [label=\"DIV id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case _FLOOR_DIV:
            fprintf(file, "ID%p [label=\"FLOOR_DIV id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case POW:
            fprintf(file, "ID%p [label=\"POW id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case XOR:
            fprintf(file, "ID%p [label=\"XOR id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case MOD:
            fprintf(file, "ID%p [label=\"MOD id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case BIT_AND:
            fprintf(file, "ID%p [label=\"BIT_AND id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case BIT_OR:
            fprintf(file, "ID%p [label=\"BIT_OR id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case _CONCAT:
            fprintf(file, "ID%p [label=\"CONCAT id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case LESS:
            fprintf(file, "ID%p [label=\"LESS id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case _LE:
            fprintf(file, "ID%p [label=\"LE id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case GREATER:
            fprintf(file, "ID%p [label=\"GREATER id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case _GE:
            fprintf(file, "ID%p [label=\"GE id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case EQUAL:
            fprintf(file, "ID%p [label=\"EQUAL id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case NOT_EQUAL:
            fprintf(file, "ID%p [label=\"NOT_EQUAL id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case LOG_AND:
            fprintf(file, "ID%p [label=\"LOG_AND id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case LOG_OR:
            fprintf(file, "ID%p [label=\"LOG_OR id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            print_expr_node(node->second_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            
            break;
        case UNARY_MINUS:
            fprintf(file, "ID%p [label=\"UNARY_MINUS id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            break;
        case _NOT:
            fprintf(file, "ID%p [label=\"NOT id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            break;
        case LEN:
            fprintf(file, "ID%p [label=\"LEN id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
            break;
        case BIT_NOT:
            fprintf(file, "ID%p [label=\"BIT_NOT id %d\"]\n", node, node->id);
            
            print_expr_node(node->first_operand, file);
            fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            
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
        case _IDENT:
            if(node->is_map_key) {
                fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "IDENT KEY", node->id);
                fprintf(file, "ID%p [label=\"ident key %s\"]\n", node->ident, node->ident);
                fprintf(file, "ID%p->ID%p\n", node, node->ident);
            } else {
                fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "IDENT", node->id);
                fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                fprintf(file, "ID%p->ID%p\n", node, node->ident);
            }
            break;
        case EXPR:
            fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "VAR EXPR", node->id);
            print_expr_node(node->second_expr, file);
            fprintf(file, "ID%p->ID%p\n", node, node->second_expr);
            break;
        case __FUNCTION_CALL:
            if(node->is_map_key) {
                fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "FUNC CALL KEY", node->id);
                print_expr_node(node->first_expr, file);
                fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                printf(file, "ID%p->ID%p [label=\"fun call\"]\n", node, node->first_expr);
                printf(file, "ID%p->ID%p [label=\"key ident\"]\n", node, node->ident);
            } else {
                fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "FUNC CALL EXPR", node->id);
                print_expr_node(node->first_expr, file);
                print_expr_node(node->second_expr, file);
                printf(file, "ID%p->ID%p [label=\"fun call\"]\n", node, node->first_expr);
                printf(file, "ID%p->ID%p [label=\"key expr\"]\n", node, node->second_expr);
            }
            break;
        case ADJUSTED_EXPR:
            if(node->is_map_key) {
                fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "ADJUSTED EXPR KEY", node->id);
                print_expr_node(node->first_expr, file);
                fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                printf(file, "ID%p->ID%p [label=\"expr\"]\n", node, node->first_expr);
                printf(file, "ID%p->ID%p [label=\"key ident\"]\n", node, node->ident);
            } else {
                fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "ADJUSTED EXPR EXPR", node->id);
                print_expr_node(node->first_expr, file);
                print_expr_node(node->second_expr, file);
                printf(file, "ID%p->ID%p [label=\"expr\"]\n", node, node->first_expr);
                printf(file, "ID%p->ID%p [label=\"key expr\"]\n", node, node->second_expr);
            }
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
        fprintf(file, "ID%p [label=\"field type %s id %d\"]\n", node, "IDENT", node->id);
        fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
        print_expr_node(node->value, file);
        fprintf(file, "ID%p->ID%p [label=\"ident\"]\n", node, node->ident);
        fprintf(file, "ID%p->ID%p [label=\"val\"]\n", node, node->value);
    }
    else if(node->key != NULL) {
        fprintf(file, "ID%p [label=\"field type %s id %d\"]\n", node, "KEY", node->id);
        print_expr_node(node->key, file);
        print_expr_node(node->value, file);
        fprintf(file, "ID%p->ID%p [label=\"key\"]\n", node, node->key);
        fprintf(file, "ID%p->ID%p [label=\"val\"]\n", node, node->value);
    }
    else {
        fprintf(file, "ID%p [label=\"field type %s id %d\"]\n", node, "EXPR", node->id);
        print_expr_node(node->value, file);
        fprintf(file, "ID%p->ID%p\n", node, node->value);
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
