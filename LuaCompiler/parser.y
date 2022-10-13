%{
    #include <stdio.h>
    #include "tree_nodes.h"
    #include "print_tree.h"
    #include "dot.h"

    void yyerror(const char * message) {
        fprintf(stderr, message);
    }

    int yylex();
    int yyparse();
    extern FILE* yyin;

    struct chunk_node * chunk_node;
%}

%union {
    double number;
    char * ident;
    char * string;

    struct stmt_node * stmt_node;
    struct stmt_seq_node * stmt_seq_node;

    struct expr_node * expr_node;
    struct expr_seq_node * expr_seq_node;

    struct ident_list_node * ident_list_node;
    struct param_list_node * param_list_node;

    struct field_node * field_node;
    struct field_list_node * field_list_node;
    char * field_sep_node;
}

%type<stmt_seq_node> chunk;
%type<stmt_seq_node> block;
%type<stmt_seq_node> block_tmp;
%type<stmt_node> stmt;
%type<stmt_seq_node> elseif_seq;
%type<stmt_node> ret_stmt;
%type<expr_seq_node> var_list;
%type<expr_node> var;
%type<expr_node> function_call;
%type<ident_list_node> ident_list;
%type<expr_seq_node> expr_seq;
%type<expr_node> expr;
%type<expr_seq_node> args;
%type<param_list_node> param_list;
%type<expr_node> table_constructor;
%type<field_node> field;
%type<field_list_node> field_list;
%type<field_sep_node> field_sep;

// Reserved words
%token AND BREAK DO ELSE ELSEIF END FALSE FOR FUNCTION IF IN LOCAL NIL NOT OR RETURN REPEAT THEN TRUE UNTIL WHILE

// Literals
%token EQL NOT_EQL LE GE FLOOR_DIV CONCAT VAR_ARG IDENT NUMBER STRING

// Operators
%left OR
%left AND
%left '>' '<' GE LE EQL NOT_EQL
%left '|'
%left '~'
%left '&'
%right CONCAT
%left '+' '-'
%left '*' '/' '%' FLOOR_DIV
%right UNARY
%right '^'
%nonassoc ')'

%%

// chunk ::= block
chunk:                block
                    ;

// block ::= {stmt} [ret_stmt]
block:                block_tmp
                    | block_tmp ret_stmt
                    | block_tmp ret_stmt ';'
                    ;

block_tmp:            /* EMPTY */
                    | block_tmp stmt
                    | block_tmp ';'
                    ;

/*
stat ::=  ';' | 
		 varlist '=' explist | 
		 functioncall | 
		 label | 
		 break | 
		 goto Name | 
		 do block end | 
		 while exp do block end | 
		 repeat block until exp | 
		 if exp then block {elsif exp then block} [else block] end | 
		 for Name '=' exp ',' exp [',' exp] do block end | 
		 for namelist in explist do block end | 
		 function funcname funcbody | 
		 local function Name funcbody | 
		 local namelist ['=' explist]
		 ;
*/
stmt:                 var_list '=' expr_seq { $$ = create_assign_stmt_node($1, $3); }
                    | function_call { $$ = create_function_call_stmt_node($1); }
                    | BREAK { //TODO хз что тут должно быть }
                    | DO block END { $$ = create_do_stmt_node($2); }
                    | WHILE expr DO block END { $$ = create_cycle_stmt_node(WHILE_LOOP, $2, $4); }
                    | REPEAT block UNTIL expr { $$ = create_cycle_stmt_node(REPEAT_LOOP, $4, $2); }
                    | IF expr THEN block elseif_seq END { $$ = create_if_stmt_node($2, $4, $5, NULL); }
                    | IF expr THEN block elseif_seq ELSE block END { $$ = create_if_stmt_node($2, $4, $5, $7); }
                    | FOR IDENT '=' expr ',' expr DO block END { $$ = create_for_stmt_node($2, $4, $6, NULL, $8); }
                    | FOR IDENT '=' expr ',' expr ',' expr DO block END { $$ = create_for_stmt_node($2, $4, $6, $8, $9); }
                    | FOR ident_list IN expr_seq DO block END { $$ = create_foreach_stmt_node($2, $4, $6); }
                    | FUNCTION IDENT '(' param_list ')' block END { $$ = create_function_def_stmt_node($2, $4, $6, false); }
                    | LOCAL FUNCTION IDENT '(' param_list ')' block END { $$ = create_function_def_stmt_node($2, $4, $6, true); }
                    | LOCAL ident_list { $$ = create_local_var_stmt_node($2, NULL); }
                    | LOCAL ident_list '=' expr_seq { $$ = create_local_var_stmt_node($2, $4); }
                    ;

elseif_seq:           /* EMPTY */
                    | elseif_seq ELSEIF expr THEN block
                    ;

// retstat ::= return [explist] [';']
// ';' вызывает конфликт (';' есть в block)
ret_stmt:             RETURN 
                    | RETURN expr_seq
                    ;

// varlist ::= var {',' var}
var_list:             var
                    | var_list ',' var
                    ;

// var ::=  Name | prefixexp '[' exp ']' | prefixexp '.' Name 
var:                  IDENT
                    | var '[' expr ']'
                    | var '.' IDENT
                    | function_call '[' expr ']'
                    | function_call '.' IDENT
                    | '(' expr ')' '[' expr ']'
                    | '(' expr ')' '.' IDENT
                    ;

function_call:        IDENT args
                    ;

// namelist ::= Name {',' Name}
ident_list:           IDENT 
                    | ident_list ',' IDENT
                    ;

// explist ::= exp {',' exp}
expr_seq:             expr 
                    | expr_seq ',' expr
                    ;

/*
exp ::=  nil | false | true | Numeral | LiteralString | '...' | functiondef | 
	 prefixexp | tableconstructor | exp binop exp | unop exp 
*/
expr:                 NIL { $$ = create_expr_node($1); } // FIXME ХЗ
                    | FALSE { $$ = create_bool_expr_node(false); }
                    | TRUE { $$ = create_bool_expr_node(true); }
                    | NUMBER { $$ = create_number_expr_node($1); }
                    | STRING { $$ = create_string_expr_node($1); }
                    | VAR_ARG { $$ = create_var_arg_expr_node(); }
                    | var //FIXME ХЗ
                    | function_call 
                    | '(' expr ')' { $$ = create_adjusting_expr($1); }
                    | table_constructor //FIXME ХЗ
                    | expr '-' expr { $$ = create_bin_expr_node(MINUS, $1, $3); }
                    | expr '*' expr { $$ = create_bin_expr_node(MUL, $1, $3); }
                    | expr '+' expr { $$ = create_bin_expr_node(PLUS, $1, $3); }
                    | expr '/' expr { $$ = create_bin_expr_node(DIV, $1, $3); }
                    | expr FLOOR_DIV expr { $$ = create_bin_expr_node(FLOOR_DIV, $1, $3); }
                    | expr '^' expr { $$ = create_bin_expr_node(POW, $1, $3); }
                    | expr '%' expr { $$ = create_bin_expr_node(MOD, $1, $3); }
                    | expr '&' expr { $$ = create_bin_expr_node(BIT_AND, $1, $3); }
                    | expr '~' expr { $$ = create_bin_expr_node(XOR, $1, $3); }
                    | expr '|' expr { $$ = create_bin_expr_node(BIT_OR, $1, $3); }
                    | expr CONCAT expr { $$ = create_bin_expr_node(CONCAT, $1, $3); }
                    | expr '<' expr { $$ = create_bin_expr_node(LESS, $1, $3); }
                    | expr LE expr { $$ = create_bin_expr_node(LE, $1, $3); }
                    | expr '>' expr { $$ = create_bin_expr_node(GREATER, $1, $3); }
                    | expr GE expr { $$ = create_bin_expr_node(GE, $1, $3); }
                    | expr EQL expr { $$ = create_bin_expr_node(EQUAL, $1, $3); }
                    | expr NOT_EQL expr { $$ = create_bin_expr_node(NOT_EQUAL, $1, $3); }
                    | expr AND expr { $$ = create_bin_expr_node(AND, $1, $3); }
                    | expr OR expr { $$ = create_bin_expr_node(OR, $1, $3); }
                    | '-' expr %prec UNARY { $$ = create_unary_expr_Node(UNARY_MINUS, $2); }
                    | NOT expr %prec UNARY { $$ = create_unary_expr_Node(NOT, $2); }
                    | '#' expr %prec UNARY { $$ = create_unary_expr_Node(LEN, $2); }
                    | '~' expr %prec UNARY { $$ = create_unary_expr_Node(BIT_NOT, $2); }
                    ;

// args ::=  '(' [explist] ')' | tableconstructor | LiteralString 
args:                 '(' ')'
                    | '(' expr_seq ')'
                    | table_constructor
                    | STRING
                    ;

// parlist ::= namelist [',' '...'] | '...'
param_list:           /* EMPTY */
                    | ident_list
                    | ident_list ',' VAR_ARG
                    | VAR_ARG
                    ;

// tableconstructor ::= '{' [fieldlist] '}'
table_constructor:    '{' '}'
                    | '{' field_list '}'
                    | '{' field_list field_sep '}'
                    ;

// fieldlist ::= field {fieldsep field} [fieldsep]
field_list:           field
                    | field_list field_sep field
                    ;

// field ::= '[' exp ']' '=' exp | Name '=' exp | exp
field:                IDENT '=' expr
                    | '[' expr ']' '=' expr
                    | expr
                    ;

// fieldsep ::= ',' | ';'
field_sep:            ','
                    | ';'
                    ;

%%
