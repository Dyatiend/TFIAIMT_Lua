// Reserved words
%token AND BREAK DO ELSE ELSEIF END FALSE FOR FUNCTION IF IN LOCAL NIL NOT OR RETURN REPEAT THEN TRUE UNTIL WHILE GOTO

// Literals
%token EQL NOT_EQL LE GE FLOOR_DIV BITWISE_RIGHT_SHIFT BITWISE_LEFT_SHIFT CONCAT VAR_ARG IDENT NUMBER STRING GOTO_TAG_MARK

// Operators
%left OR
%left AND
%left '>' '<' GE LE EQL NOT_EQL
%left '|'
%left '~'
%left '&'
%left BITWISE_RIGHT_SHIFT BITWISE_LEFT_SHIFT
%right CONCAT
%left '+' '-'
%left '*' '/' '%' FLOOR_DIV
%right UNARY
%right '^'
%nonassoc ')'

%%

// chunk ::= block
chunk:                block

// block ::= {stmt} [ret_stmt]
block:                block_tmp
                    | block_tmp ret_stmt
                    | block_tmp ret_stmt ';'

block_tmp:            /* EMPTY */
                    | block_tmp stmt
                    | block_tmp ';'

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
*/
stmt:                 var_list '=' expr_seq
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

elseif_seq:           /* EMPTY */
                    | elseif_seq ELSEIF expr THEN block

// retstat ::= return [explist] [';']
// ';' вызывает конфликт (';' есть в block)
ret_stmt:             RETURN 
                    | RETURN expr_seq

// label ::= '::' Name '::'
label:                GOTO_TAG_MARK IDENT GOTO_TAG_MARK

// funcname ::= Name {'.' Name} [':' Name]
function_name:        function_name_tmp
                    | function_name_tmp ':' IDENT

function_name_tmp:    IDENT
                    | function_name_tmp '.' IDENT

// varlist ::= var {',' var}
var_list:             var
                    | var_list ',' var

// var ::=  Name | prefixexp '[' exp ']' | prefixexp '.' Name 
var:                  IDENT
                    | prefix_expr '[' expr ']'
                    | prefix_expr '.' IDENT

// namelist ::= Name {',' Name}
ident_list:           IDENT 
                    | ident_list ',' IDENT 

// explist ::= exp {',' exp}
expr_seq:             expr 
                    | expr_seq ',' expr 

/*
exp ::=  nil | false | true | Numeral | LiteralString | '...' | functiondef | 
	 prefixexp | tableconstructor | exp binop exp | unop exp 
*/
expr:                 NIL
                    | FALSE 
                    | TRUE 
                    | NUMBER 
                    | STRING
                    | VAR_ARG
                    | function_def
                    | prefix_expr
                    | table_constructor
                    | expr '-' expr
                    | expr '*' expr
                    | expr '+' expr
                    | expr '/' expr
                    | expr FLOOR_DIV expr
                    | expr '^' expr
                    | expr '%' expr
                    | expr '&' expr
                    | expr '~' expr
                    | expr '|' expr
                    | expr BITWISE_RIGHT_SHIFT expr
                    | expr BITWISE_LEFT_SHIFT expr
                    | expr CONCAT expr
                    | expr '<' expr
                    | expr LE expr
                    | expr '>' expr
                    | expr GE expr
                    | expr EQL expr
                    | expr NOT_EQL expr
                    | expr AND expr
                    | expr OR expr
                    | '-' expr %prec UNARY 
                    | NOT expr %prec UNARY
                    | '#' expr %prec UNARY
                    | '~' expr %prec UNARY

// prefixexp ::= var | functioncall | '(' exp ')'
prefix_expr:          var
                    | function_call
                    | '(' expr ')'

// functioncall ::=  prefixexp args | prefixexp ':' Name args 
function_call:        prefix_expr args
                    | prefix_expr ':' IDENT args

// args ::=  '(' [explist] ')' | tableconstructor | LiteralString 
args:                 '(' ')'
                    | '(' expr_seq ')'
                    | table_constructor
                    | STRING

// functiondef ::= function funcbody
function_def:         FUNCTION function_body

// funcbody ::= '(' [parlist] ')' block end
function_body:        '(' ')' block END
                    | '(' param_list ')' block END

// parlist ::= namelist [',' '...'] | '...'
param_list:           ident_list
                    | ident_list ',' VAR_ARG
                    | VAR_ARG

// tableconstructor ::= '{' [fieldlist] '}'
table_constructor:    '{' '}'
                    | '{' field_list '}'

// fieldlist ::= field {fieldsep field} [fieldsep]
field_list:           field 
                    | field_list field_sep
                    | field_list field_sep field

// field ::= '[' exp ']' '=' exp | Name '=' exp | exp
field:                IDENT '=' expr
                    | '[' expr ']' '=' expr
                    | expr

// fieldsep ::= ',' | ';'
field_sep:             ','
                    | ';'

%%
