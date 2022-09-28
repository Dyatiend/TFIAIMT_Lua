// Reserved words
%token AND BREAK DO ELSE ELSEIF END FALSE FOR FUNCTION IF IN LOCAL NIL NOT OR RETURN REPEAT THEN TRUE UNTIL WHILE GOTO

// Literals
%token EQL NOT_EQL LE GE FLOOR_DIV BITWISE_RIGHT_SHIFT BITWISE_LEFT_SHIFT CONCAT VAR_ARG IDENT NUMBER STRING GOTO_TAG_MARK

// Operators
%left OR
%left AND
%left '>' '<' GE LE EQL NOT_EQL
%right CONCAT
%left BITWISE_RIGHT_SHIFT BITWISE_LEFT_SHIFT
%left '+' '-'
%left '*' '/' '%' FLOOR_DIV
%right UNARY
%right '^'
%nonassoc ')'

%%

// chunk ::= block
chunk: block

// block ::= {stmt} [ret_stmt]
block: block_tmp
        | block_tmp ret_stmt
        | block_tmp ret_stmt ';'

block_tmp: /* EMPTY */ 
        | block_tmp stmt 
        | block_tmp ';'

/*
stat ::=  ';' | 
		 var_list '=' explist | 
		 function_call | 
		 label | 
		 break | 
		 goto Name | 
		 do block end | 
		 while exp do block end | 
		 repeat block until exp | 
		 if exp then block {elsif exp then block} [else block] end | 
		 for Name '=' exp ',' exp [',' exp] do block end | 
		 for ident_list in explist do block end | 
		 function function_name function_body | 
		 local function Name function_body | 
		 local ident_list ['=' explist] 
*/
stmt: var_list '=' expr_seq 
        | prefix_expr // Если function_call то reduce/reduce конфликт (prefix_expr включает function_call)
        | label
        | BREAK
        | GOTO IDENT
        | DO block END 
        | WHILE expr DO block END  
        | REPEAT block UNTIL expr 
        | IF expr THEN block elseif_seq END 
        | IF expr THEN block elseif_seq ELSE block END 
        | FOR IDENT '=' expr ',' expr DO block END 
        | FOR IDENT '=' expr ',' expr ',' expr DO block END 
        | FOR ident_list IN expr_seq DO block END 
        | FUNCTION function_name function_body 
        | LOCAL FUNCTION IDENT function_body  
        | LOCAL ident_list 
        | LOCAL ident_list '=' expr_seq  

elseif_seq: /* EMPTY */
        | elseif_seq ELSEIF expr THEN block 

ret_stmt: RETURN  
        | RETURN expr_seq 

label: GOTO_TAG_MARK IDENT GOTO_TAG_MARK

function_name: function_name_tmp
        | function_name_tmp ':' IDENT 

function_name_tmp: IDENT 
        | function_name_tmp '.' IDENT 

var_list: var  
        | var_list ',' var 

var: IDENT  
        | prefix_expr '.' IDENT
        | prefix_expr '[' expr ']'

ident_list: IDENT
        | ident_list ',' IDENT 

expr_seq: expr 
        | expr_seq ',' expr

expr: NIL  
        | FALSE   
        | TRUE  
        | NUMBER 
        | VAR_ARG 
        | function  
        | prefix_expr  
        | STRING  
        | table_constructor
        | expr OR expr  
        | expr AND expr 
        | expr '>' expr  
        | expr '<' expr 
        | expr BITWISE_LEFT_SHIFT expr 
        | expr FLOOR_DIV expr 
        | expr BITWISE_RIGHT_SHIFT expr
        | expr GE expr 
        | expr LE expr  
        | expr EQL expr 
        | expr NOT_EQL expr
        | expr CONCAT expr 
        | expr '+' expr 
        | expr '-' expr 
        | expr '*' expr 
        | expr '/' expr  
        | expr '%' expr  
        | expr '^' expr 
        | '-' expr %prec UNARY  
        | NOT expr %prec UNARY  
        | '#' expr %prec UNARY
        | '~' expr %prec UNARY
        | expr '&' expr
        | expr '~' expr
        | expr '|' expr
        /*
        
                    
                    */

prefix_expr: var
        | function_call
        | '(' function_call ')'
        | '(' expr ')' 

function_call: prefix_expr args
        | prefix_expr ':' IDENT args 

args: '(' ')'
        | '(' expr_seq ')'
        | table_constructor
        | STRING 

function: FUNCTION function_body

function_body: '(' ')' block END 
        | '(' param_list ')' block END

param_list: VAR_ARG
        | ident_list
        | ident_list ',' VAR_ARG

table_constructor: '{' '}'
        | '{' field_list '}' 

field_list: field  
        | field_list fieldsep 
        | field_list fieldsep field

field: expr
        | IDENT '=' expr
        | '[' expr ']' '=' expr

fieldsep: ','
        | ';'

%%
