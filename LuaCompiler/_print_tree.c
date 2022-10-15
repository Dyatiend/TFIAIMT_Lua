#include "print_tree.h"

void print_program(struct chunk_node * node, FILE * file) {
    char a[1000];
    sprintf(a, "digraph G {\n");
    puts(a);

    sprintf(a, "ID%p [label=\"program\"]\n", node);
    puts(a);

    if (node->block != NULL) {
        print_stmt_seq_node(node->block, node, file);
    }
    sprintf(a, "}");
    puts(a);
}


void print_stmt_node(struct stmt_node * node, FILE * file) {
    char a[1000];
    switch (node->type) {
        case ASSIGNMENT:
            sprintf(a, "ID%p [label=\"ASSIGNMENT id %d\"]\n", node, node->id);
            puts(a);

            sprintf(a, "ID%p [label=\"VARS\"]\n", node->var_list);
            puts(a);

            sprintf(a, "ID%p [label=\"VALUES\"]\n", node->values);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->var_list);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->values);
            puts(a);

            print_expr_seq_node(node->var_list, node->var_list, file);
            print_expr_seq_node(node->values, node->values, file);
            break;
        case _FUNCTION_CALL:
            sprintf(a, "ID%p [label=\"_FUNCTION_CALL id %d\"]\n", node, node->id);
            puts(a);

            print_expr_node(node->function_call, file);
            sprintf(a, "ID%p->ID%p\n", node, node->function_call);
            puts(a);

            break;
        case _BREAK:
            sprintf(a, "ID%p [label=\"BREAK id %d\"]\n", node, node->id);
            puts(a);

            break;
        case DO_LOOP:
            sprintf(a, "ID%p [label=\"DO_LOOP id %d\"]\n", node, node->id);
            puts(a);

            sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->action_block);
            puts(a);

            print_stmt_seq_node(node->action_block, node->action_block, file);
            break;
        case WHILE_LOOP:
            sprintf(a, "ID%p [label=\"WHILE_LOOP id %d\"]\n", node, node->id);
            puts(a);

            print_expr_node(node->condition_expr, file);
            sprintf(a, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            puts(a);

            sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->action_block);
            puts(a);

            print_stmt_seq_node(node->action_block, node->action_block, file);
            break;
        case REPEAT_LOOP:
            sprintf(a, "ID%p [label=\"REPEAT_LOOP id %d\"]\n", node, node->id);
            puts(a);

            sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->action_block);
            puts(a);

            print_stmt_seq_node(node->action_block, node->action_block, file);
            print_expr_node(node->condition_expr, file);
            sprintf(a, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            puts(a);

            break;
        case _IF:
            sprintf(a, "ID%p [label=\"IF id %d\"]\n", node, node->id);
            puts(a);

            print_expr_node(node->condition_expr, file);
            sprintf(a, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            puts(a);


            sprintf(a, "ID%p [label=\"IF BLOCK\"]\n", node->if_block);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->if_block);
            puts(a);

            print_stmt_seq_node(node->if_block, node->if_block, file);

            if(node->elseif_seq != NULL && node->elseif_seq->first != NULL) {
                sprintf(a, "ID%p [label=\"ELSEIF SEQ\"]\n", node->elseif_seq);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->elseif_seq);
                puts(a);

                print_stmt_seq_node(node->elseif_seq, node->elseif_seq, file);
            }

            if(node->else_block != NULL) {
                sprintf(a, "ID%p [label=\"ELSE BLOCK\"]\n", node->else_block);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->else_block);
                puts(a);

                print_stmt_seq_node(node->else_block, node->else_block, file);
            }

            break;
        case _FOR:
            sprintf(a, "ID%p [label=\"FOR id %d\"]\n", node, node->id);
            puts(a);

            sprintf(a, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
            puts(a);

            sprintf(a, "ID%p->ID%p [label=\"VAR\"]\n", node, node->ident);
            puts(a);


            print_expr_node(node->initial_value, file);
            sprintf(a, "ID%p->ID%p [label=\"INIT VAL\"]\n", node, node->initial_value);
            puts(a);


            print_expr_node(node->condition_expr, file);
            sprintf(a, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);
            puts(a);


            if(node->step_expr != NULL) {
                print_expr_node(node->step_expr, file);
                sprintf(a, "ID%p->ID%p [label=\"STEP EXPR\"]\n", node, node->step_expr);
                puts(a);
            }


            sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->action_block);
            puts(a);

            print_stmt_seq_node(node->action_block, node->action_block, file);

            break;
        case FOR_IN:
            sprintf(a, "ID%p [label=\"FOREACH id %d\"]\n", node, node->id);
            puts(a);


            sprintf(a, "ID%p [label=\"IDENT LIST\"]\n", node->ident_list);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->ident_list);
            puts(a);

            print_ident_list_node(node->ident_list, node->ident_list, file);

            sprintf(a, "ID%p [label=\"VALUES\"]\n", node->values);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->values);
            puts(a);

            print_expr_seq_node(node->values, node->values, file);

            sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->action_block);
            puts(a);

            print_stmt_seq_node(node->action_block, node->action_block, file);

            break;
        case FUNCTION_DEF:
            if(node->is_local) {
                sprintf(a, "ID%p [label=\"LOCAL FUNC id %d\"]\n", node, node->id);
                puts(a);


                sprintf(a, "ID%p [label=\"PARAMS\"]\n", node->params);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->params);
                puts(a);

                print_param_list_node(node->params, node->params, file);

                sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->action_block);
                puts(a);

                print_stmt_seq_node(node->action_block, node->action_block, file);

            } else {
                sprintf(a, "ID%p [label=\"FUNC id %d\"]\n", node, node->id);
                puts(a);


                sprintf(a, "ID%p [label=\"PARAMS\"]\n", node->params);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->params);
                puts(a);

                print_param_list_node(node->params, node->params, file);

                sprintf(a, "ID%p [label=\"BLOCK\"]\n", node->action_block);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->action_block);
                puts(a);

                print_stmt_seq_node(node->action_block, node->action_block, file);
            }

            break;
        case VAR_DEF:
            sprintf(a, "ID%p [label=\"VAR DEF id %d\"]\n", node, node->id);
            puts(a);


            sprintf(a, "ID%p [label=\"IDENT LIST\"]\n", node->ident_list);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->ident_list);
            puts(a);

            print_ident_list_node(node->ident_list, node->ident_list, file);

            if(node->values != NULL) {
                sprintf(a, "ID%p [label=\"VALUES\"]\n", node->values);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->values);
                puts(a);

                print_expr_seq_node(node->values, node->values, file);
            }

            break;
        case _RETURN:
            sprintf(a, "ID%p [label=\"RETURN id %d\"]\n", node, node->id);
            puts(a);

            break;
        default:
            break;
    }
}

void print_stmt_seq_node(struct stmt_seq_node * node, void * parent, FILE * file) {
    char a[1000];
    struct stmt_node * current = node->first;
    while (current != NULL) {
        print_stmt_node(current, file);
        sprintf(a, "ID%p->ID%p\n", parent, current);
        puts(a);

        current = current->next;
    }
}

void print_expr_node(struct expr_node * node, FILE * file) {
    char a[1000];
    switch (node->type) {
        case _NIL:
            sprintf(a, "ID%p [label=\"NIL id %d\"]\n", node, node->id);
            puts(a);


            break;
        case BOOLEAN:
            if(node->bool_value) {
                sprintf(a, "ID%p [label=\"BOOL true id %d\"]\n", node, node->id);
                puts(a);
            }
            else {
                sprintf(a, "ID%p [label=\"BOOL false id %d\"]\n", node, node->id);
                puts(a);
            }

            break;
        case _NUMBER:
            sprintf(a, "ID%p [label=\"NUMBER %f id %d\"]\n", node, node->number_value, node->id);
            puts(a);


            break;
        case _STRING:
            sprintf(a, "ID%p [label=\"STRING %s id %d\"]\n", node, node->string_value, node->id);
            puts(a);


            break;
        case _VAR_ARG:
            sprintf(a, "ID%p [label=\"VAR_ARG id %d\"]\n", node, node->id);
            puts(a);


            break;
        case VAR:
            sprintf(a, "ID%p [label=\"VAR id %d\"]\n", node, node->id);
            puts(a);


            print_var_node(node->var, node, file);

            break;
        case FUNCTION_CALL:
            sprintf(a, "ID%p [label=\"FUNCTION CALL id %d\"]\n", node, node->id);
            puts(a);


            sprintf(a, "ID%p [label=\"name %s\"]\n", node->ident, node->ident);
            puts(a);

            sprintf(a, "ID%p->ID%p [label=\"name\"]\n", node, node->ident);
            puts(a);

            if(node->args != NULL) {
                sprintf(a, "ID%p [label=\"ARGS\"]\n", node->args);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->args);
                puts(a);

                print_expr_seq_node(node->args, node->args, file);
            }

            break;
        case ADJUST:
            sprintf(a, "ID%p [label=\"ADJUST id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->adjusted_expr, file);
            sprintf(a, "ID%p->ID%p\n", node, node->adjusted_expr);
            puts(a);


            break;
        case TABLE_CONSTRUCTOR:
            sprintf(a, "ID%p [label=\"TABLE id %d\"]\n", node, node->id);
            puts(a);

            if(node->table_constructor != NULL) {
                print_field_list_node(node->table_constructor, node, file);
            }

            break;
        case PLUS:
            sprintf(a, "ID%p [label=\"PLUS id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);



            break;
        case MINUS:
            sprintf(a, "ID%p [label=\"MINUS id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case MUL:
            sprintf(a, "ID%p [label=\"MUL id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case DIV:
            sprintf(a, "ID%p [label=\"DIV id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case _FLOOR_DIV:
            sprintf(a, "ID%p [label=\"FLOOR_DIV id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case POW:
            sprintf(a, "ID%p [label=\"POW id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case XOR:
            sprintf(a, "ID%p [label=\"XOR id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case MOD:
            sprintf(a, "ID%p [label=\"MOD id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case BIT_AND:
            sprintf(a, "ID%p [label=\"BIT_AND id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case BIT_OR:
            sprintf(a, "ID%p [label=\"BIT_OR id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case _CONCAT:
            sprintf(a, "ID%p [label=\"CONCAT id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case LESS:
            sprintf(a, "ID%p [label=\"LESS id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case _LE:
            sprintf(a, "ID%p [label=\"LE id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case GREATER:
            sprintf(a, "ID%p [label=\"GREATER id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case _GE:
            sprintf(a, "ID%p [label=\"GE id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case EQUAL:
            sprintf(a, "ID%p [label=\"EQUAL id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case NOT_EQUAL:
            sprintf(a, "ID%p [label=\"NOT_EQUAL id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case LOG_AND:
            sprintf(a, "ID%p [label=\"LOG_AND id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case LOG_OR:
            sprintf(a, "ID%p [label=\"LOG_OR id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            print_expr_node(node->second_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);
            puts(a);


            break;
        case UNARY_MINUS:
            sprintf(a, "ID%p [label=\"UNARY_MINUS id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            break;
        case _NOT:
            sprintf(a, "ID%p [label=\"NOT id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            break;
        case LEN:
            sprintf(a, "ID%p [label=\"LEN id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            break;
        case BIT_NOT:
            sprintf(a, "ID%p [label=\"BIT_NOT id %d\"]\n", node, node->id);
            puts(a);


            print_expr_node(node->first_operand, file);
            sprintf(a, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);
            puts(a);


            break;
        default:
            break;
    }
}

void print_expr_seq_node(struct expr_seq_node * node, void * parent, FILE * file) {
    char a[1000];
    struct expr_node * current = node->first;
    while (current != NULL) {
        print_expr_node(current, file);
        sprintf(a, "ID%p->ID%p\n", parent, current);
        puts(a);

        current = current->next;
    }
}

void print_var_item_node(struct var_item_node * node, FILE * file) {
    char a[1000];
    switch (node->type) {
        case _IDENT:
            sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "IDENT", node->id);
            puts(a);

            sprintf(a, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
            puts(a);

            sprintf(a, "ID%p->ID%p\n", node, node->ident);
            puts(a);

            break;
        case __VAR:
            if(node->is_map_key) {
                sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "VAR KEY", node->id);
                puts(a);

                sprintf(a, "ID%p [label=\"ident key %s\"]\n", node->ident, node->ident);
                puts(a);

                sprintf(a, "ID%p->ID%p\n", node, node->ident);
                puts(a);
            } else {
                sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "VAR EXPR", node->id);
                puts(a);

                print_expr_node(node->second_expr, file);
                sprintf(a, "ID%p->ID%p\n", node, node->second_expr);
                puts(a);
            }

            break;
        case __FUNCTION_CALL:
            if(node->is_map_key) {
                sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "FUNC CALL KEY", node->id);
                puts(a);

                print_expr_node(node->first_expr, file);
                sprintf(a, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                puts(a);

                sprintf(a, "ID%p->ID%p [label=\"fun call\"]\n", node, node->first_expr);
                puts(a);

                sprintf(a, "ID%p->ID%p [label=\"key ident\"]\n", node, node->ident);
                puts(a);
            } else {
                sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "FUNC CALL EXPR", node->id);
                puts(a);

                print_expr_node(node->first_expr, file);
                print_expr_node(node->second_expr, file);
                sprintf(a, "ID%p->ID%p [label=\"fun call\"]\n", node, node->first_expr);
                puts(a);

                sprintf(a, "ID%p->ID%p [label=\"key expr\"]\n", node, node->second_expr);
                puts(a);
            }

            break;
        case ADJUSTED_EXPR:
            if(node->is_map_key) {
                sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "ADJUSTED EXPR KEY", node->id);
                puts(a);

                print_expr_node(node->first_expr, file);
                sprintf(a, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                puts(a);

                sprintf(a, "ID%p->ID%p [label=\"expr\"]\n", node, node->first_expr);
                puts(a);

                sprintf(a, "ID%p->ID%p [label=\"key ident\"]\n", node, node->ident);
                puts(a);
            } else {
                sprintf(a, "ID%p [label=\"var_item type %s id %d\"]\n", node, "ADJUSTED EXPR EXPR", node->id);
                puts(a);

                print_expr_node(node->first_expr, file);
                print_expr_node(node->second_expr, file);
                sprintf(a, "ID%p->ID%p [label=\"expr\"]\n", node, node->first_expr);
                puts(a);

                sprintf(a, "ID%p->ID%p [label=\"key expr\"]\n", node, node->second_expr);
                puts(a);
            }

            break;
        default:
            break;
    }
}

void print_var_node(struct var_node * node, void * parent, FILE * file) {
    char a[1000];
    struct var_item_node * current = node->first;
    while (current != NULL) {
        print_var_item_node(current, file);
        sprintf(a, "ID%p->ID%p\n", parent, current);
        puts(a);

        current = current->next;
    }
}

void print_ident_node(struct ident_node * node, FILE * file) {
    char a[1000];
    sprintf(a, "ID%p [label=\"ident %s id %d\"]\n", node, node->ident, node->id);
    puts(a);
}


void print_ident_list_node(struct ident_list_node * node, void * parent, FILE * file) {
    char a[1000];
    struct ident_node * current = node->first;
    while (current != NULL) {
        print_ident_node(current, file);
        sprintf(a, "ID%p->ID%p\n", parent, current);
        puts(a);

        current = current->next;
    }
}

void print_param_list_node(struct param_list_node * node, void * parent, FILE * file) {
    char a[1000];
    print_ident_list_node(node->list, parent, file);
    if(node->has_var_arg) {
        sprintf(a, "ID%p [label=\"var_arg\"]\n", node);
        puts(a);

        sprintf(a, "ID%p->ID%p\n", parent, node);
        puts(a);
    }

}

void print_field_node(struct field_node * node, FILE * file) {
    char a[1000];
    if(node->ident != NULL) {
        sprintf(a, "ID%p [label=\"field type %s id %d\"]\n", node, "IDENT", node->id);
        puts(a);

        sprintf(a, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
        puts(a);

        print_expr_node(node->value, file);
        sprintf(a, "ID%p->ID%p [label=\"ident\"]\n", node, node->ident);
        puts(a);

        sprintf(a, "ID%p->ID%p [label=\"val\"]\n", node, node->value);
        puts(a);
    }

    else if(node->key != NULL) {
        sprintf(a, "ID%p [label=\"field type %s id %d\"]\n", node, "KEY", node->id);
        puts(a);

        print_expr_node(node->key, file);
        print_expr_node(node->value, file);
        sprintf(a, "ID%p->ID%p [label=\"key\"]\n", node, node->key);
        puts(a);

        sprintf(a, "ID%p->ID%p [label=\"val\"]\n", node, node->value);
        puts(a);
    }

    else {
        sprintf(a, "ID%p [label=\"field type %s id %d\"]\n", node, "EXPR", node->id);
        puts(a);

        print_expr_node(node->value, file);
        sprintf(a, "ID%p->ID%p\n", node, node->value);
        puts(a);
    }

}

void print_field_list_node(struct field_list_node * node, void * parent, FILE * file) {
    char a[1000];
    struct field_node * current = node->first;
    while (current != NULL) {
        print_field_node(current, file);
        sprintf(a, "ID%p->ID%p\n", parent, current);
        puts(a);

        current = current->next;
    }
}
