#include "convert_to_xml.h"

void program_to_xml(struct chunk_node * node, FILE * file) {
    fprintf(file, "<?xml version=\"1.0\"?>");
    fprintf(file, "<program>");
	// fprintf(file, "ID%p [label=\"program\"]", node);
	if (node->block != NULL) {
		stmt_seq_node_to_xml(node->block, node, file);
	}
	fprintf(file, "</program>");
}

void stmt_node_to_xml(struct stmt_node * node, FILE * file) {
	switch (node->type) {
        case ASSIGNMENT:
            fprintf(file, "<stmt type=\"ASSIGNMENT\">");
            fprintf(file, "<vars>");
            expr_seq_node_to_xml(node->var_list, node->var_list, file);
            fprintf(file, "</vars>");
            fprintf(file, "<values>");
            expr_seq_node_to_xml(node->values, node->values, file);
            fprintf(file, "</values>");
            fprintf(file, "</stmt>");
            break;
        case _FUNCTION_CALL:
            fprintf(file, "<stmt type=\"FUNCTION_CALL\">");
            expr_node_to_xml(node->function_call, file);
            fprintf(file, "</stmt>");
            break;
        case _BREAK:
            fprintf(file, "<stmt type=\"BREAK\">");
            fprintf(file, "</stmt>");
            break;
        case DO_LOOP:
            fprintf(file, "<stmt type=\"DO_LOOP\">");
            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>");
            fprintf(file, "</stmt>");
            break;
        case WHILE_LOOP:
            fprintf(file, "<stmt type=\"WHILE_LOOP\">");
            fprintf(file, "<condition>");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>");
            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>");
            fprintf(file, "</stmt>");
            break;
        case REPEAT_LOOP:
            fprintf(file, "<stmt type=\"REPEAT_LOOP\">");
            fprintf(file, "<condition>");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>");
            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>");
            fprintf(file, "</stmt>");
            break;
        case _IF:
            fprintf(file, "<stmt type=\"IF\">");
            fprintf(file, "<condition>");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>");

            fprintf(file, "<if_block>");
            stmt_seq_node_to_xml(node->if_block, node->if_block, file);
            fprintf(file, "</if_block>");
            
            if(node->elseif_seq != NULL && node->elseif_seq->first != NULL) {
                fprintf(file, "<elseif_seq>");
                stmt_seq_node_to_xml(node->elseif_seq, node->elseif_seq, file);
                fprintf(file, "</elseif_seq>");
            }

            if(node->else_block != NULL) {
                fprintf(file, "<else>");
                stmt_seq_node_to_xml(node->else_block, node->else_block, file);
                fprintf(file, "</else>");
            }
            fprintf(file, "</stmt>");
            break;
        case _FOR:
            fprintf(file, "<stmt type=\"FOR\">");
            fprintf(file, "<var ident=\"%s\"/>", node->ident);

            fprintf(file, "<init_val>");
            expr_node_to_xml(node->initial_value, file);
            fprintf(file, "</init_val>");

            fprintf(file, "<condition>");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>");

            if(node->step_expr != NULL) {
                fprintf(file, "<step_expr>");
                expr_node_to_xml(node->step_expr, file);
                fprintf(file, "</step_expr>");
            }

            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>");
            fprintf(file, "</stmt>");

            // fprintf(file, "ID%p [label=\"FOR id %d\"]", node, node->id);
            // fprintf(file, "ID%p [label=\"ident %s\"]", node->ident, node->ident);
            // fprintf(file, "ID%p->ID%p [label=\"VAR\"]", node, node->ident);

            // expr_node_to_xml(node->initial_value, file);
            // fprintf(file, "ID%p->ID%p [label=\"INIT VAL\"]", node, node->initial_value);

            // expr_node_to_xml(node->condition_expr, file);
            // fprintf(file, "ID%p->ID%p [label=\"CONDITION\"]", node, node->condition_expr);

            // if(node->step_expr != NULL) {
            //     expr_node_to_xml(node->step_expr, file);
            //     fprintf(file, "ID%p->ID%p [label=\"STEP EXPR\"]", node, node->step_expr);
            // }

            // fprintf(file, "ID%p [label=\"BLOCK\"]", node->action_block);
            // fprintf(file, "ID%p->ID%p", node, node->action_block);
            // stmt_seq_node_to_xml(node->action_block, node->action_block, file);

            break;
        case FOR_IN:
            fprintf(file, "<stmt type=\"FOREACH\">");
            fprintf(file, "<var ident=\"%s\"/>", node->ident);

            fprintf(file, "<ident_list>");
            ident_list_node_to_xml(node->ident_list, node->ident_list, file);
            fprintf(file, "</ident_list>");

            fprintf(file, "<values>");
            expr_seq_node_to_xml(node->values, node->values, file);
            fprintf(file, "</values>");

            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>");
            fprintf(file, "</stmt>");

            // fprintf(file, "ID%p [label=\"FOREACH id %d\"]", node, node->id);

            // fprintf(file, "ID%p [label=\"IDENT LIST\"]", node->ident_list);
            // fprintf(file, "ID%p->ID%p", node, node->ident_list);
            // ident_list_node_to_xml(node->ident_list, node->ident_list, file);

            // fprintf(file, "ID%p [label=\"VALUES\"]", node->values);
            // fprintf(file, "ID%p->ID%p", node, node->values);
            // expr_seq_node_to_xml(node->values, node->values, file);

            // fprintf(file, "ID%p [label=\"BLOCK\"]", node->action_block);
            // fprintf(file, "ID%p->ID%p", node, node->action_block);
            // stmt_seq_node_to_xml(node->action_block, node->action_block, file);

            break;
        case FUNCTION_DEF:
            if(node->is_local) {
                fprintf(file, "<stmt type=\"FUNCTION_DEF\" is_local=\"true\">");
            } else {
                fprintf(file, "<stmt type=\"FUNCTION_DEF\" is_local=\"false\">");
            }

            fprintf(file, "<name ident=\"%s\"/>", node->ident);
            fprintf(file, "<params>");
            param_list_node_to_xml(node->params, node->params, file);
            fprintf(file, "</params>");
            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>");            
            fprintf(file, "</stmt>");


            // if(node->is_local) {
            //     fprintf(file, "ID%p [label=\"LOCAL FUNC id %d\"]", node, node->id);

            //     fprintf(file, "ID%p [label=\"name %s\"]", node->ident, node->ident);
            //     fprintf(file, "ID%p->ID%p [label=\"name\"]", node, node->ident);

            //     fprintf(file, "ID%p [label=\"PARAMS\"]", node->params);
            //     fprintf(file, "ID%p->ID%p", node, node->params);
            //     param_list_node_to_xml(node->params, node->params, file);

            //     fprintf(file, "ID%p [label=\"BLOCK\"]", node->action_block);
            //     fprintf(file, "ID%p->ID%p", node, node->action_block);
            //     stmt_seq_node_to_xml(node->action_block, node->action_block, file);

            // } else {
            //     fprintf(file, "ID%p [label=\"FUNC id %d\"]", node, node->id);

            //     fprintf(file, "ID%p [label=\"name %s\"]", node->ident, node->ident);
            //     fprintf(file, "ID%p->ID%p [label=\"name\"]", node, node->ident);

            //     fprintf(file, "ID%p [label=\"PARAMS\"]", node->params);
            //     fprintf(file, "ID%p->ID%p", node, node->params);
            //     param_list_node_to_xml(node->params, node->params, file);

            //     fprintf(file, "ID%p [label=\"BLOCK\"]", node->action_block);
            //     fprintf(file, "ID%p->ID%p", node, node->action_block);
            //     stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            // }

            break;
        case VAR_DEF:
            fprintf(file, "<stmt type=\"VAR_DEF\">");
            fprintf(file, "<ident_list>");
            ident_list_node_to_xml(node->ident_list, node->ident_list, file);
            fprintf(file, "</ident_list>");

            if(node->values != NULL) {
                fprintf(file, "<values>");
                expr_seq_node_to_xml(node->values, node->values, file);
                fprintf(file, "</values>");
            }     
            fprintf(file, "</stmt>");
            
            // fprintf(file, "ID%p [label=\"VAR DEF id %d\"]", node, node->id);

            // fprintf(file, "ID%p [label=\"IDENT LIST\"]", node->ident_list);
            // fprintf(file, "ID%p->ID%p", node, node->ident_list);
            // ident_list_node_to_xml(node->ident_list, node->ident_list, file);

            // if(node->values != NULL) {
            //     fprintf(file, "ID%p [label=\"VALUES\"]", node->values);
            //     fprintf(file, "ID%p->ID%p", node, node->values);
            //     expr_seq_node_to_xml(node->values, node->values, file);
            // }

            break;
        case _RETURN:
            fprintf(file, "<stmt type=\"RETURN\">");
            
            if(node->values != NULL) {
                fprintf(file, "<values>");
                expr_seq_node_to_xml(node->values, node->values, file);
                fprintf(file, "</values>");
            }
            
            fprintf(file, "</stmt>");

            // fprintf(file, "ID%p [label=\"RETURN id %d\"]", node, node->id);

            // if(node->values != NULL) {
            //     fprintf(file, "ID%p [label=\"VALUES\"]", node->values);
            //     fprintf(file, "ID%p->ID%p", node, node->values);
            //     expr_seq_node_to_xml(node->values, node->values, file);
            // }

            break;
        default:
            // По никому не понятной (ага, да) причине elseif stmt имеет тип undetermined и попадает сюда))))))
            // Очень впадлу исправлять, поэтому костыли
            fprintf(file, "<stmt type=\"ELSEIF\">");
            fprintf(file, "<condition>");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>");
            fprintf(file, "<block>");
            stmt_seq_node_to_xml(node->if_block, node->if_block, file);
            fprintf(file, "</block>");
            fprintf(file, "</stmt>");
            break;
	}
}

void stmt_seq_node_to_xml(struct stmt_seq_node * node, void * parent, FILE * file) {
    struct stmt_node * current = node->first;
    fprintf(file, "<stmt_seq_node>");
	while (current != NULL) {
		stmt_node_to_xml(current, file);
		// fprintf(file, "ID%p->ID%p", parent, current);
		current = current->next;
	}
    fprintf(file, "</stmt_seq_node>");
}

void expr_node_to_xml(struct expr_node * node, FILE * file) {
    switch (node->type) {
        case _NIL:
            fprintf(file, "<expr_node type=\"NIL\"/>");
            
            // fprintf(file, "ID%p [label=\"NIL id %d\"]", node, node->id);

            break;
        case BOOLEAN:
            if(node->bool_value) {
                fprintf(file, "<expr_node type=\"BOOLEAN\" value=\"true\"/>");
                // fprintf(file, "ID%p [label=\"BOOL true id %d\"]", node, node->id);
            } else {
                fprintf(file, "<expr_node type=\"BOOLEAN\" value=\"false\"/>");
                // fprintf(file, "ID%p [label=\"BOOL false id %d\"]", node, node->id);
            }
            break;
        case _NUMBER:
            fprintf(file, "<expr_node type=\"NUMBER\" value=\"%f\"/>", node->number_value);
            // fprintf(file, "ID%p [label=\"NUMBER %f id %d\"]", node, node->number_value, node->id);
            break;
        case _STRING:
            fprintf(file, "<expr_node type=\"STRING\"><![CDATA[%s]]></expr_node>", node->string_value);
            // fprintf(file, "ID%p [label=\"STRING %s id %d\"]", node, node->string_value, node->id);

            break;
        case _VAR_ARG:
            fprintf(file, "<expr_node type=\"VAR_ARG\"/>", node->string_value);
            // fprintf(file, "ID%p [label=\"VAR_ARG id %d\"]", node, node->id);

            break;
        case VAR:
            fprintf(file, "<expr_node type=\"VAR\">", node->ident);
            var_node_to_xml(node->var, node, file);
            fprintf(file, "</expr_node>", node->ident);
            
            // fprintf(file, "ID%p [label=\"VAR id %d\"]", node, node->id);

            // var_node_to_xml(node->var, node, file);

            break;
        case FUNCTION_CALL:
            fprintf(file, "<expr_node type=\"FUNCTION_CALL\" ident=\"%s\">", node->ident);
            if(node->args != NULL) {
                fprintf(file, "<function_args>"); 
                expr_seq_node_to_xml(node->args, node->args, file);
                fprintf(file, "</function_args>"); 
            }
            fprintf(file, "</expr_node>"); 
            
            // fprintf(file, "ID%p [label=\"FUNCTION CALL id %d\"]", node, node->id);

            // fprintf(file, "ID%p [label=\"name %s\"]", node->ident, node->ident);
            // fprintf(file, "ID%p->ID%p [label=\"name\"]", node, node->ident);

            // if(node->args != NULL) {
            //     fprintf(file, "ID%p [label=\"ARGS\"]", node->args);
            //     fprintf(file, "ID%p->ID%p", node, node->args);
            //     expr_seq_node_to_xml(node->args, node->args, file);
            // }

            break;
        case ADJUST:
            fprintf(file, "<expr_node type=\"ADJUST\">");
            expr_node_to_xml(node->adjusted_expr, file);
            fprintf(file, "</expr_node>"); 

            // fprintf(file, "ID%p [label=\"ADJUST id %d\"]", node, node->id);

            // expr_node_to_xml(node->adjusted_expr, file);
            // fprintf(file, "ID%p->ID%p", node, node->adjusted_expr);

            break;
        case TABLE_CONSTRUCTOR:
            fprintf(file, "<expr_node type=\"TABLE_CONSTRUCTOR\">");
            if(node->table_constructor != NULL) {
                field_list_node_to_xml(node->table_constructor, node, file);
            }
            fprintf(file, "</expr_node>"); 
            
            // fprintf(file, "ID%p [label=\"TABLE id %d\"]", node, node->id);

            // if(node->table_constructor != NULL) {
            //     field_list_node_to_xml(node->table_constructor, node, file);
            // }

            break;
        case PLUS:
            fprintf(file, "<expr_node type=\"PLUS\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");  

            // fprintf(file, "ID%p [label=\"PLUS id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);


            break;
        case MINUS: 
            fprintf(file, "<expr_node type=\"MINUS\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"MINUS id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case MUL:
            fprintf(file, "<expr_node type=\"MUL\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"MUL id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case DIV:
            fprintf(file, "<expr_node type=\"DIV\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"DIV id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case _FLOOR_DIV:
            fprintf(file, "<expr_node type=\"FLOOR_DIV\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"FLOOR_DIV id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case POW:
            fprintf(file, "<expr_node type=\"POW\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"POW id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case XOR:
            fprintf(file, "<expr_node type=\"XOR\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"XOR id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case MOD:
            fprintf(file, "<expr_node type=\"MOD\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"MOD id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case BIT_AND:
            fprintf(file, "<expr_node type=\"BIT_AND\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"BIT_AND id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case BIT_OR:
            fprintf(file, "<expr_node type=\"BIT_OR\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"BIT_OR id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case _CONCAT:
            fprintf(file, "<expr_node type=\"CONCAT\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
 
            // fprintf(file, "ID%p [label=\"CONCAT id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case LESS:
            fprintf(file, "<expr_node type=\"LESS\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"LESS id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case _LE:
            fprintf(file, "<expr_node type=\"LE\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"LE id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case GREATER:
            fprintf(file, "<expr_node type=\"GREATER\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"GREATER id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case _GE:
            fprintf(file, "<expr_node type=\"GE\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"GE id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case EQUAL:
            fprintf(file, "<expr_node type=\"EQUAL\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"EQUAL id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case NOT_EQUAL:
            fprintf(file, "<expr_node type=\"NOT_EQUAL\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"NOT_EQUAL id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case LOG_AND:
            fprintf(file, "<expr_node type=\"LOG_AND\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"LOG_AND id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case LOG_OR:
            fprintf(file, "<expr_node type=\"LOG_OR\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"LOG_OR id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]", node, node->second_operand);

            break;
        case UNARY_MINUS:
            fprintf(file, "<expr_node type=\"UNARY_MINUS\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"UNARY_MINUS id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            break;
        case _NOT:
            fprintf(file, "<expr_node type=\"NOT\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"NOT id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            break;
        case LEN:
            fprintf(file, "<expr_node type=\"LEN\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"LEN id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            break;
        case BIT_NOT:
            fprintf(file, "<expr_node type=\"BIT_NOT\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"BIT_NOT id %d\"]", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]", node, node->first_operand);

            break;
        default:
            break;
    }
}

void expr_seq_node_to_xml(struct expr_seq_node * node, void * parent, FILE * file) {
    struct expr_node * current = node->first;
    fprintf(file, "<expr_seq_node>");
    while (current != NULL) {
        expr_node_to_xml(current, file);
        // fprintf(file, "ID%p->ID%p", parent, current);
        current = current->next;
    }
    fprintf(file, "</expr_seq_node>");
}

void var_item_node_to_xml(struct var_item_node * node, FILE * file) {
    switch (node->type) {
        case _IDENT:
            fprintf(file, "<var_item_node type=\"IDENT\" ident=\"%s\"/>", node->ident);

            // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "IDENT", node->id);
            // fprintf(file, "ID%p [label=\"ident %s\"]", node->ident, node->ident);
            // fprintf(file, "ID%p->ID%p", node, node->ident);
            break;
        case __VAR:
            if(node->is_map_key) {
                fprintf(file, "<var_item_node type=\"VAR\" is_map_key=\"true\" key=\"%s\"/>", node->ident);
                
                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "VAR KEY", node->id);
                // fprintf(file, "ID%p [label=\"ident key %s\"]", node->ident, node->ident);
                // fprintf(file, "ID%p->ID%p", node, node->ident);
            } else {
                fprintf(file, "<var_item_node type=\"VAR\" is_map_key=\"false\">");
                expr_node_to_xml(node->second_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "VAR EXPR", node->id);
                // expr_node_to_xml(node->second_expr, file);
                // fprintf(file, "ID%p->ID%p", node, node->second_expr);
            }
            break;
        case __FUNCTION_CALL:
            if(node->is_map_key) {
                fprintf(file, "<var_item_node type=\"FUNCTION_CALL\" is_map_key=\"true\" key=\"%s\">", node->ident);
                expr_node_to_xml(node->first_expr, file);
                fprintf(file, "</var_item_node>");
                
                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "FUNC CALL KEY", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // fprintf(file, "ID%p [label=\"ident %s\"]", node->ident, node->ident);
                // fprintf(file, "ID%p->ID%p [label=\"fun call\"]", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key ident\"]", node, node->ident);
            } else {
                fprintf(file, "<var_item_node type=\"FUNCTION_CALL\" is_map_key=\"false\">");
                expr_node_to_xml(node->first_expr, file);
                expr_node_to_xml(node->second_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "FUNC CALL EXPR", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // expr_node_to_xml(node->second_expr, file);
                // fprintf(file, "ID%p->ID%p [label=\"fun call\"]", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key expr\"]", node, node->second_expr);
            }
            break;
        case ADJUSTED_EXPR:
            if(node->is_map_key) {
                fprintf(file, "<var_item_node type=\"ADJUSTED_EXPR\" is_map_key=\"true\" key=\"%s\">", node->ident);
                expr_node_to_xml(node->first_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "ADJUSTED EXPR KEY", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // fprintf(file, "ID%p [label=\"ident %s\"]", node->ident, node->ident);
                // fprintf(file, "ID%p->ID%p [label=\"expr\"]", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key ident\"]", node, node->ident);
            } else {
                fprintf(file, "<var_item_node type=\"ADJUSTED_EXPR\" is_map_key=\"false\">");
                expr_node_to_xml(node->first_expr, file);
                expr_node_to_xml(node->second_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]", node, "ADJUSTED EXPR EXPR", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // expr_node_to_xml(node->second_expr, file);
                // fprintf(file, "ID%p->ID%p [label=\"expr\"]", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key expr\"]", node, node->second_expr);
            }
            break;
        default:
            break;
    }
}

void var_node_to_xml(struct var_node * node, void * parent, FILE * file) {
    struct var_item_node * current = node->first;
    fprintf(file, "<var_node>");
    while (current != NULL) {
        var_item_node_to_xml(current, file);
        //fprintf(file, "ID%p->ID%p", parent, current);
        current = current->next;
    }
    fprintf(file, "</var_node>");
}

void ident_node_to_xml(struct ident_node * node, FILE * file) {
    fprintf(file, "<ident_node ident=\"%s\"/>", node->ident);
    // fprintf(file, "ID%p [label=\"ident %s id %d\"]", node, node->ident, node->id);
}

void ident_list_node_to_xml(struct ident_list_node * node, void * parent, FILE * file) {
    struct ident_node * current = node->first;
    fprintf(file, "<ident_list_node>");
    while (current != NULL) {
        ident_node_to_xml(current, file);
        // fprintf(file, "ID%p->ID%p", parent, current);
        current = current->next;
    }
    fprintf(file, "</ident_list_node>");
}

void param_list_node_to_xml(struct param_list_node * node, void * parent, FILE * file) {
    if(node->has_var_arg) {
        fprintf(file, "<param_list_node has_var_arg=\"true\">");
    } else {
        fprintf(file, "<param_list_node has_var_arg=\"false\">");
    }
    
    ident_list_node_to_xml(node->list, parent, file);
    // if(node->has_var_arg) {
    //     fprintf(file, "ID%p [label=\"var_arg\"]", node);
    //     fprintf(file, "ID%p->ID%p", parent, node);
    // }
    fprintf(file, "</param_list_node>");
}

void field_node_to_xml(struct field_node * node, FILE * file) {
    if(node->ident != NULL) {
        fprintf(file, "<field_node ident=\"%s\">", node->ident);
        expr_node_to_xml(node->value, file);
        fprintf(file, "</field_node>");

        // fprintf(file, "ID%p [label=\"field type %s id %d\"]", node, "IDENT", node->id);
        // fprintf(file, "ID%p [label=\"ident %s\"]", node->ident, node->ident);
        // expr_node_to_xml(node->value, file);
        // fprintf(file, "ID%p->ID%p [label=\"ident\"]", node, node->ident);
        // fprintf(file, "ID%p->ID%p [label=\"val\"]", node, node->value);
    }
    else if(node->key != NULL) {
        fprintf(file, "<field_node>");
        expr_node_to_xml(node->key, file);
        expr_node_to_xml(node->value, file);
        fprintf(file, "</field_node>");

        // fprintf(file, "ID%p [label=\"field type %s id %d\"]", node, "KEY", node->id);
        // expr_node_to_xml(node->key, file);
        // expr_node_to_xml(node->value, file);
        // fprintf(file, "ID%p->ID%p [label=\"key\"]", node, node->key);
        // fprintf(file, "ID%p->ID%p [label=\"val\"]", node, node->value);
    }
    else {
        fprintf(file, "<field_node>");
        expr_node_to_xml(node->value, file);
        fprintf(file, "</field_node>");

        // fprintf(file, "ID%p [label=\"field type %s id %d\"]", node, "EXPR", node->id);
        // expr_node_to_xml(node->value, file);
        // fprintf(file, "ID%p->ID%p", node, node->value);
    }
}

void field_list_node_to_xml(struct field_list_node * node, void * parent, FILE * file) {
    struct field_node * current = node->first;
    fprintf(file, "<field_list_node>");
    while (current != NULL) {
        field_node_to_xml(current, file);
        // fprintf(file, "ID%p->ID%p", parent, current);
        current = current->next;
    }
    fprintf(file, "</field_list_node>");
}
