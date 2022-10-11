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
stmt:                 var_list '=' expr_seq
                    | function_call
                    | BREAK
                    | DO block END
                    | WHILE expr DO block END
                    | REPEAT block UNTIL expr 
                    | IF expr THEN block elseif_seq END 
                    | IF expr THEN block elseif_seq ELSE block END 
                    | FOR IDENT '=' expr ',' expr DO block END
                    | FOR IDENT '=' expr ',' expr ',' expr DO block END
                    | FOR ident_list IN expr_seq DO block END
                    | FUNCTION IDENT '(' param_list ')' block END
                    | LOCAL FUNCTION IDENT '(' param_list ')' block END
                    | LOCAL ident_list 
                    | LOCAL ident_list '=' expr_seq
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
expr:                 NIL
                    | FALSE 
                    | TRUE 
                    | NUMBER 
                    | STRING
                    | VAR_ARG
                    | var
                    | function_call
                    | '(' expr ')'
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
