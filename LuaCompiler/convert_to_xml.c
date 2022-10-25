#include "convert_to_xml.h"

void program_to_xml(struct chunk_node * node, FILE * file) {
    fprintf(file, "<?xml version=\"1.0\"?>");
    fprintf(file, "<program>\n");
	// fprintf(file, "ID%p [label=\"program\"]\n", node);
	if (node->block != NULL) {
		stmt_seq_node_to_xml(node->block, node, file);
	}
	fprintf(file, "</program>");
}

void stmt_node_to_xml(struct stmt_node * node, FILE * file) {
	switch (node->type) {
        case ASSIGNMENT:
            fprintf(file, "<stmt type=\"ASSIGNMENT\">\n");
            fprintf(file, "<vars>\n");
            expr_seq_node_to_xml(node->var_list, node->var_list, file);
            fprintf(file, "</vars>\n");
            fprintf(file, "<values>\n");
            expr_seq_node_to_xml(node->values, node->values, file);
            fprintf(file, "</values>\n");
            fprintf(file, "</stmt>\n");
            break;
        case _FUNCTION_CALL:
            fprintf(file, "<stmt type=\"_FUNCTION_CALL\">\n");
            expr_node_to_xml(node->function_call, file);
            fprintf(file, "</stmt>\n");
            break;
        case _BREAK:
            fprintf(file, "<stmt type=\"BREAK\">\n");
            fprintf(file, "</stmt>\n");
            break;
        case DO_LOOP:
            fprintf(file, "<stmt type=\"DO_LOOP\">\n");
            fprintf(file, "<block>\n");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>\n");
            fprintf(file, "</stmt>\n");
            break;
        case WHILE_LOOP:
            fprintf(file, "<stmt type=\"WHILE_LOOP\">\n");
            fprintf(file, "<condition>\n");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>\n");
            fprintf(file, "<block>\n");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>\n");
            fprintf(file, "</stmt>\n");
            break;
        case REPEAT_LOOP:
            fprintf(file, "<stmt type=\"REPEAT_LOOP\">\n");
            fprintf(file, "<block>\n");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>\n");
            fprintf(file, "<condition>\n");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>\n");
            fprintf(file, "</stmt>\n");
            break;
        case _IF:
            fprintf(file, "<stmt type=\"IF\">\n");
            fprintf(file, "<condition>\n");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>\n");

            fprintf(file, "<if_block>\n");
            stmt_seq_node_to_xml(node->if_block, node->if_block, file);
            fprintf(file, "</if_block>\n");
            
            if(node->elseif_seq != NULL && node->elseif_seq->first != NULL) {
                fprintf(file, "<elseif_seq>\n");
                stmt_seq_node_to_xml(node->elseif_seq, node->elseif_seq, file);
                fprintf(file, "</elseif_seq>\n");
            }

            if(node->else_block != NULL) {
                fprintf(file, "<else>\n");
                stmt_seq_node_to_xml(node->else_block, node->else_block, file);
                fprintf(file, "</else>\n");
            }
            fprintf(file, "</stmt>\n");
            break;
        case _FOR:
            fprintf(file, "<stmt type=\"FOR\">\n");
            fprintf(file, "<var ident=\"%s\"/>\n", node->ident);

            fprintf(file, "<init_val>\n");
            expr_node_to_xml(node->initial_value, file);
            fprintf(file, "</init_val>\n");

            fprintf(file, "<condition>\n");
            expr_node_to_xml(node->condition_expr, file);
            fprintf(file, "</condition>\n");

            if(node->step_expr != NULL) {
                fprintf(file, "<step_expr>\n");
                expr_node_to_xml(node->step_expr, file);
                fprintf(file, "</step_expr>\n");
            }

            fprintf(file, "<block>\n");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>\n");
            fprintf(file, "</stmt>\n");

            // fprintf(file, "ID%p [label=\"FOR id %d\"]\n", node, node->id);
            // fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
            // fprintf(file, "ID%p->ID%p [label=\"VAR\"]\n", node, node->ident);

            // expr_node_to_xml(node->initial_value, file);
            // fprintf(file, "ID%p->ID%p [label=\"INIT VAL\"]\n", node, node->initial_value);

            // expr_node_to_xml(node->condition_expr, file);
            // fprintf(file, "ID%p->ID%p [label=\"CONDITION\"]\n", node, node->condition_expr);

            // if(node->step_expr != NULL) {
            //     expr_node_to_xml(node->step_expr, file);
            //     fprintf(file, "ID%p->ID%p [label=\"STEP EXPR\"]\n", node, node->step_expr);
            // }

            // fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            // fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            // stmt_seq_node_to_xml(node->action_block, node->action_block, file);

            break;
        case FOR_IN:
            fprintf(file, "<stmt type=\"FOREACH\">\n");
            fprintf(file, "<var ident=\"%s\"/>\n", node->ident);

            fprintf(file, "<ident_list>\n");
            ident_list_node_to_xml(node->ident_list, node->ident_list, file);
            fprintf(file, "</ident_list>\n");

            fprintf(file, "<values>\n");
            expr_seq_node_to_xml(node->values, node->values, file);
            fprintf(file, "</values>\n");

            fprintf(file, "<block>\n");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>\n");
            fprintf(file, "</stmt>\n");

            // fprintf(file, "ID%p [label=\"FOREACH id %d\"]\n", node, node->id);

            // fprintf(file, "ID%p [label=\"IDENT LIST\"]\n", node->ident_list);
            // fprintf(file, "ID%p->ID%p\n", node, node->ident_list);
            // ident_list_node_to_xml(node->ident_list, node->ident_list, file);

            // fprintf(file, "ID%p [label=\"VALUES\"]\n", node->values);
            // fprintf(file, "ID%p->ID%p\n", node, node->values);
            // expr_seq_node_to_xml(node->values, node->values, file);

            // fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            // fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            // stmt_seq_node_to_xml(node->action_block, node->action_block, file);

            break;
        case FUNCTION_DEF:
            if(node->is_local) {
                fprintf(file, "<stmt type=\"FUNCTION_DEF\" is_local=\"true\">\n");
            } else {
                fprintf(file, "<stmt type=\"FUNCTION_DEF\" is_local=\"false\">\n");
            }

            fprintf(file, "<name ident=\"%s\"/>\n", node->ident);
            fprintf(file, "<params>\n");
            param_list_node_to_xml(node->params, node->params, file);
            fprintf(file, "</params>\n");
            fprintf(file, "<block>\n");
            stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            fprintf(file, "</block>\n");            
            fprintf(file, "</stmt>\n");


            // if(node->is_local) {
            //     fprintf(file, "ID%p [label=\"LOCAL FUNC id %d\"]\n", node, node->id);

            //     fprintf(file, "ID%p [label=\"name %s\"]\n", node->ident, node->ident);
            //     fprintf(file, "ID%p->ID%p [label=\"name\"]\n", node, node->ident);

            //     fprintf(file, "ID%p [label=\"PARAMS\"]\n", node->params);
            //     fprintf(file, "ID%p->ID%p\n", node, node->params);
            //     param_list_node_to_xml(node->params, node->params, file);

            //     fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            //     fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            //     stmt_seq_node_to_xml(node->action_block, node->action_block, file);

            // } else {
            //     fprintf(file, "ID%p [label=\"FUNC id %d\"]\n", node, node->id);

            //     fprintf(file, "ID%p [label=\"name %s\"]\n", node->ident, node->ident);
            //     fprintf(file, "ID%p->ID%p [label=\"name\"]\n", node, node->ident);

            //     fprintf(file, "ID%p [label=\"PARAMS\"]\n", node->params);
            //     fprintf(file, "ID%p->ID%p\n", node, node->params);
            //     param_list_node_to_xml(node->params, node->params, file);

            //     fprintf(file, "ID%p [label=\"BLOCK\"]\n", node->action_block);
            //     fprintf(file, "ID%p->ID%p\n", node, node->action_block);
            //     stmt_seq_node_to_xml(node->action_block, node->action_block, file);
            // }

            break;
        case VAR_DEF:
            fprintf(file, "<stmt type=\"VAR_DEF\">\n");
            fprintf(file, "<ident_list>\n");
            ident_list_node_to_xml(node->ident_list, node->ident_list, file);
            fprintf(file, "</ident_list>\n");

            if(node->values != NULL) {
                fprintf(file, "<values>\n");
                expr_seq_node_to_xml(node->values, node->values, file);
                fprintf(file, "</values>\n");
            }     
            fprintf(file, "</stmt>\n");
            
            // fprintf(file, "ID%p [label=\"VAR DEF id %d\"]\n", node, node->id);

            // fprintf(file, "ID%p [label=\"IDENT LIST\"]\n", node->ident_list);
            // fprintf(file, "ID%p->ID%p\n", node, node->ident_list);
            // ident_list_node_to_xml(node->ident_list, node->ident_list, file);

            // if(node->values != NULL) {
            //     fprintf(file, "ID%p [label=\"VALUES\"]\n", node->values);
            //     fprintf(file, "ID%p->ID%p\n", node, node->values);
            //     expr_seq_node_to_xml(node->values, node->values, file);
            // }

            break;
        case _RETURN:
            fprintf(file, "<stmt type=\"RETURN\">\n");
            
            if(node->values != NULL) {
                fprintf(file, "<values>\n");
                expr_seq_node_to_xml(node->values, node->values, file);
                fprintf(file, "</values>\n");
            }
            
            fprintf(file, "</stmt>\n");

            // fprintf(file, "ID%p [label=\"RETURN id %d\"]\n", node, node->id);

            // if(node->values != NULL) {
            //     fprintf(file, "ID%p [label=\"VALUES\"]\n", node->values);
            //     fprintf(file, "ID%p->ID%p\n", node, node->values);
            //     expr_seq_node_to_xml(node->values, node->values, file);
            // }

            break;
        default:
            break;
	}
}

void stmt_seq_node_to_xml(struct stmt_seq_node * node, void * parent, FILE * file) {
    struct stmt_node * current = node->first;
    fprintf(file, "<stmt_seq_node>");
	while (current != NULL) {
		stmt_node_to_xml(current, file);
		// fprintf(file, "ID%p->ID%p\n", parent, current);
		current = current->next;
	}
    fprintf(file, "</stmt_seq_node>");
}

void expr_node_to_xml(struct expr_node * node, FILE * file) {
    switch (node->type) {
        case _NIL:
            fprintf(file, "<expr_node type=\"NIL\"/>");
            
            // fprintf(file, "ID%p [label=\"NIL id %d\"]\n", node, node->id);

            break;
        case BOOLEAN:
            if(node->bool_value) {
                fprintf(file, "<expr_node type=\"BOOL\" value=\"true\"/>");
                // fprintf(file, "ID%p [label=\"BOOL true id %d\"]\n", node, node->id);
            } else {
                fprintf(file, "<expr_node type=\"BOOL\" value=\"false\"/>");
                // fprintf(file, "ID%p [label=\"BOOL false id %d\"]\n", node, node->id);
            }
            break;
        case _NUMBER:
            fprintf(file, "<expr_node type=\"NUMBER\" value=\"%d\"/>", node->number_value);
            // fprintf(file, "ID%p [label=\"NUMBER %f id %d\"]\n", node, node->number_value, node->id);
            break;
        case _STRING:
            fprintf(file, "<expr_node type=\"STRING\"><![CDATA[%s]]></expr_node>", node->string_value);
            // fprintf(file, "ID%p [label=\"STRING %s id %d\"]\n", node, node->string_value, node->id);

            break;
        case _VAR_ARG:
            fprintf(file, "<expr_node type=\"VAR_ARG\"/>", node->string_value);
            // fprintf(file, "ID%p [label=\"VAR_ARG id %d\"]\n", node, node->id);

            break;
        case VAR:
            fprintf(file, "<expr_node type=\"FUNCTION_CALL\">", node->ident);
            var_node_to_xml(node->var, node, file);
            fprintf(file, "</expr_node>", node->ident);
            
            // fprintf(file, "ID%p [label=\"VAR id %d\"]\n", node, node->id);

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
            
            // fprintf(file, "ID%p [label=\"FUNCTION CALL id %d\"]\n", node, node->id);

            // fprintf(file, "ID%p [label=\"name %s\"]\n", node->ident, node->ident);
            // fprintf(file, "ID%p->ID%p [label=\"name\"]\n", node, node->ident);

            // if(node->args != NULL) {
            //     fprintf(file, "ID%p [label=\"ARGS\"]\n", node->args);
            //     fprintf(file, "ID%p->ID%p\n", node, node->args);
            //     expr_seq_node_to_xml(node->args, node->args, file);
            // }

            break;
        case ADJUST:
            fprintf(file, "<expr_node type=\"ADJUST\">");
            expr_node_to_xml(node->adjusted_expr, file);
            fprintf(file, "</expr_node>"); 

            // fprintf(file, "ID%p [label=\"ADJUST id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->adjusted_expr, file);
            // fprintf(file, "ID%p->ID%p\n", node, node->adjusted_expr);

            break;
        case TABLE_CONSTRUCTOR:
            fprintf(file, "<expr_node type=\"TABLE_CONSTRUCTOR\">");
            if(node->table_constructor != NULL) {
                field_list_node_to_xml(node->table_constructor, node, file);
            }
            fprintf(file, "</expr_node>"); 
            
            // fprintf(file, "ID%p [label=\"TABLE id %d\"]\n", node, node->id);

            // if(node->table_constructor != NULL) {
            //     field_list_node_to_xml(node->table_constructor, node, file);
            // }

            break;
        case PLUS:
            fprintf(file, "<expr_node type=\"PLUS\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");  

            // fprintf(file, "ID%p [label=\"PLUS id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);


            break;
        case MINUS: 
            fprintf(file, "<expr_node type=\"MINUS\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"MINUS id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case MUL:
            fprintf(file, "<expr_node type=\"MUL\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"MUL id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case DIV:
            fprintf(file, "<expr_node type=\"DIV\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"DIV id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case _FLOOR_DIV:
            fprintf(file, "<expr_node type=\"_FLOOR_DIV\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"FLOOR_DIV id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case POW:
            fprintf(file, "<expr_node type=\"POW\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"POW id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case XOR:
            fprintf(file, "<expr_node type=\"XOR\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"XOR id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case MOD:
            fprintf(file, "<expr_node type=\"MOD\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"MOD id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case BIT_AND:
            fprintf(file, "<expr_node type=\"BIT_AND\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"BIT_AND id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case BIT_OR:
            fprintf(file, "<expr_node type=\"BIT_OR\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"BIT_OR id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case _CONCAT:
            fprintf(file, "<expr_node type=\"_CONCAT\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"CONCAT id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case LESS:
            fprintf(file, "<expr_node type=\"LESS\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"LESS id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case _LE:
            fprintf(file, "<expr_node type=\"_LE\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"LE id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case GREATER:
            fprintf(file, "<expr_node type=\"GREATER\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"GREATER id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case _GE:
            fprintf(file, "<expr_node type=\"_GE\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"GE id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case EQUAL:
            fprintf(file, "<expr_node type=\"EQUAL\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"EQUAL id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case NOT_EQUAL:
            fprintf(file, "<expr_node type=\"NOT_EQUAL\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"NOT_EQUAL id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case LOG_AND:
            fprintf(file, "<expr_node type=\"LOG_AND\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"LOG_AND id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case LOG_OR:
            fprintf(file, "<expr_node type=\"LOG_OR\">");
            expr_node_to_xml(node->first_operand, file);
            expr_node_to_xml(node->second_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"LOG_OR id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            // expr_node_to_xml(node->second_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"SECOND\"]\n", node, node->second_operand);

            break;
        case UNARY_MINUS:
            fprintf(file, "<expr_node type=\"UNARY_MINUS\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"UNARY_MINUS id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            break;
        case _NOT:
            fprintf(file, "<expr_node type=\"_NOT\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");
            
            // fprintf(file, "ID%p [label=\"NOT id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            break;
        case LEN:
            fprintf(file, "<expr_node type=\"LEN\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"LEN id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

            break;
        case BIT_NOT:
            fprintf(file, "<expr_node type=\"BIT_NOT\">");
            expr_node_to_xml(node->first_operand, file);
            fprintf(file, "</expr_node>");

            // fprintf(file, "ID%p [label=\"BIT_NOT id %d\"]\n", node, node->id);

            // expr_node_to_xml(node->first_operand, file);
            // fprintf(file, "ID%p->ID%p [label=\"FIRST\"]\n", node, node->first_operand);

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
        // fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
    fprintf(file, "</expr_seq_node>");
}

void var_item_node_to_xml(struct var_item_node * node, FILE * file) {
    switch (node->type) {
        case _IDENT:
            fprintf(file, "<var_item_node type=\"_IDENT\" ident=\"%s\"/>", node->ident);

            // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "IDENT", node->id);
            // fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
            // fprintf(file, "ID%p->ID%p\n", node, node->ident);
            break;
        case __VAR:
            if(node->is_map_key) {
                fprintf(file, "<var_item_node type=\"__VAR\" is_map_key=\"true\" key=\"%s\"/>", node->ident);
                
                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "VAR KEY", node->id);
                // fprintf(file, "ID%p [label=\"ident key %s\"]\n", node->ident, node->ident);
                // fprintf(file, "ID%p->ID%p\n", node, node->ident);
            } else {
                fprintf(file, "<var_item_node type=\"__VAR\" is_map_key=\"false\">");
                expr_node_to_xml(node->second_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "VAR EXPR", node->id);
                // expr_node_to_xml(node->second_expr, file);
                // fprintf(file, "ID%p->ID%p\n", node, node->second_expr);
            }
            break;
        case __FUNCTION_CALL:
            if(node->is_map_key) {
                fprintf(file, "<var_item_node type=\"__FUNCTION_CALL\" is_map_key=\"true\" key=\"%s\">", node->ident);
                expr_node_to_xml(node->first_expr, file);
                fprintf(file, "</var_item_node>");
                
                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "FUNC CALL KEY", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                // fprintf(file, "ID%p->ID%p [label=\"fun call\"]\n", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key ident\"]\n", node, node->ident);
            } else {
                fprintf(file, "<var_item_node type=\"__FUNCTION_CALL\" is_map_key=\"false\">");
                expr_node_to_xml(node->first_expr, file);
                expr_node_to_xml(node->second_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "FUNC CALL EXPR", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // expr_node_to_xml(node->second_expr, file);
                // fprintf(file, "ID%p->ID%p [label=\"fun call\"]\n", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key expr\"]\n", node, node->second_expr);
            }
            break;
        case ADJUSTED_EXPR:
            if(node->is_map_key) {
                fprintf(file, "<var_item_node type=\"ADJUSTED_EXPR\" is_map_key=\"true\" key=\"%s\">", node->ident);
                expr_node_to_xml(node->first_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "ADJUSTED EXPR KEY", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
                // fprintf(file, "ID%p->ID%p [label=\"expr\"]\n", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key ident\"]\n", node, node->ident);
            } else {
                fprintf(file, "<var_item_node type=\"ADJUSTED_EXPR\" is_map_key=\"false\">");
                expr_node_to_xml(node->first_expr, file);
                expr_node_to_xml(node->second_expr, file);
                fprintf(file, "</var_item_node>");

                // fprintf(file, "ID%p [label=\"var_item type %s id %d\"]\n", node, "ADJUSTED EXPR EXPR", node->id);
                // expr_node_to_xml(node->first_expr, file);
                // expr_node_to_xml(node->second_expr, file);
                // fprintf(file, "ID%p->ID%p [label=\"expr\"]\n", node, node->first_expr);
                // fprintf(file, "ID%p->ID%p [label=\"key expr\"]\n", node, node->second_expr);
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
        //fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
    fprintf(file, "</var_node>");
}

void ident_node_to_xml(struct ident_node * node, FILE * file) {
    fprintf(file, "<ident_node ident=\"%s\"/>", node->ident);
    // fprintf(file, "ID%p [label=\"ident %s id %d\"]\n", node, node->ident, node->id);
}

void ident_list_node_to_xml(struct ident_list_node * node, void * parent, FILE * file) {
    struct ident_node * current = node->first;
    fprintf(file, "<ident_list_node>");
    while (current != NULL) {
        ident_node_to_xml(current, file);
        // fprintf(file, "ID%p->ID%p\n", parent, current);
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
    //     fprintf(file, "ID%p [label=\"var_arg\"]\n", node);
    //     fprintf(file, "ID%p->ID%p\n", parent, node);
    // }
    fprintf(file, "</param_list_node>");
}

void field_node_to_xml(struct field_node * node, FILE * file) {
    if(node->ident != NULL) {
        fprintf(file, "<field_node ident=\"%s\">", node->ident);
        expr_node_to_xml(node->value, file);
        fprintf(file, "</field_node>");

        // fprintf(file, "ID%p [label=\"field type %s id %d\"]\n", node, "IDENT", node->id);
        // fprintf(file, "ID%p [label=\"ident %s\"]\n", node->ident, node->ident);
        // expr_node_to_xml(node->value, file);
        // fprintf(file, "ID%p->ID%p [label=\"ident\"]\n", node, node->ident);
        // fprintf(file, "ID%p->ID%p [label=\"val\"]\n", node, node->value);
    }
    else if(node->key != NULL) {
        fprintf(file, "<field_node>");
        expr_node_to_xml(node->key, file);
        expr_node_to_xml(node->value, file);
        fprintf(file, "</field_node>");

        // fprintf(file, "ID%p [label=\"field type %s id %d\"]\n", node, "KEY", node->id);
        // expr_node_to_xml(node->key, file);
        // expr_node_to_xml(node->value, file);
        // fprintf(file, "ID%p->ID%p [label=\"key\"]\n", node, node->key);
        // fprintf(file, "ID%p->ID%p [label=\"val\"]\n", node, node->value);
    }
    else {
        fprintf(file, "<field_node>");
        expr_node_to_xml(node->value, file);
        fprintf(file, "</field_node>");

        // fprintf(file, "ID%p [label=\"field type %s id %d\"]\n", node, "EXPR", node->id);
        // expr_node_to_xml(node->value, file);
        // fprintf(file, "ID%p->ID%p\n", node, node->value);
    }
}

void field_list_node_to_xml(struct field_list_node * node, void * parent, FILE * file) {
    struct field_node * current = node->first;
    fprintf(file, "<field_list_node>");
    while (current != NULL) {
        field_node_to_xml(current, file);
        // fprintf(file, "ID%p->ID%p\n", parent, current);
        current = current->next;
    }
    fprintf(file, "</field_list_node>");
}
