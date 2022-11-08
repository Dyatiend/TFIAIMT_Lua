%option noyywrap
%option never-interactive

%{
    #include <stdio.h>
    #include "parser.tab.h"

    int calc_long_bracket_level(const char * str);
%}

DIGIT [0-9]
HEX_DIGIT [0-9A-Fa-f]

EXPONENT [eE][+-]?{DIGIT}+
HEX_EXPONENT [pP][+-]?{DIGIT}+

VAR_CHARS [a-zA-Z0-9_]

%x COMMENT
%x ML_COMMENT

%x SQ_STRING
%x DQ_STRING
%x ML_STRING

%x REMOVING_WHITESPACES

%%

%{
    enum string_type {
        SQ,
        DQ,
        UNDEFINED
    };

    int int_number;
    double float_number;

    char tmp1[100];
    char tmp2[100];

    char comment[1000];

    char string[1000];
    enum string_type string_type;

    int long_bracket_level;
%}

%{
    // ++++++++++++++++++++++++++ Ключевые слова ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

and             { return AND; /*printf("Found AND keyword.\n");*/ }
or              { return OR; /*printf("Found OR keyword.\n");*/ }
not             { return NOT; /*printf("Found NOT keyword.\n");*/ }

if              { return IF; /*printf("Found IF keyword.\n");*/ }
then            { return THEN; /*printf("Found THEN keyword.\n");*/ }
else            { return ELSE; /*printf("Found ELSE keyword.\n");*/ }
elseif          { return ELSEIF; /*printf("Found ELSEIF keyword.\n");*/ }

repeat          { return REPEAT; /*printf("Found REPEAT keyword.\n");*/ }
for             { return FOR; /*printf("Found FOR keyword.\n");*/ }
in              { return IN; /*printf("Found IN keyword.\n");*/ }
do              { return DO; /*printf("Found DO keyword.\n");*/ }
while           { return WHILE; /*printf("Found WHILE keyword.\n");*/ }
until           { return UNTIL; /*printf("Found UNTIL keyword.\n");*/ }
break           { return BREAK; /*printf("Found BREAK keyword.\n");*/ }
end             { return END; /*printf("Found END keyword.\n");*/ }

true            { return TRUE; /*printf("Found TRUE keyword.\n");*/ }
false           { return FALSE; /*printf("Found FALSE keyword.\n");*/ }
nil             { return NIL; /*printf("Found NIL keyword.\n");*/ }

function        { return FUNCTION; /*printf("Found FUNCTION keyword.\n");*/ }
return          { return RETURN; /*printf("Found RETURN keyword.\n");*/ }

local           { return LOCAL; /*printf("Found LOCAL keyword.\n");*/ }

%{
    // +++++++++++++++++++++++++ Служебные символы ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

"+"             { return '+'; /*printf("Found ADD operator.\n");*/ }
"-"             { return '-'; /*printf("Found SUB operator.\n");*/ }
"*"             { return '*'; /*printf("Found MUL operator.\n");*/ }
"/"             { return '/'; /*printf("Found DIV operator.\n");*/ }
"//"            { return FLOOR_DIV; /*printf("Found FLOOR_DIV operator.\n");*/ }
"%"             { return '%'; /*printf("Found MOD operator.\n");*/ }
"^"             { return '^'; /*printf("Found POW operator.\n");*/ }

"#"             { return '#'; /*printf("Found LEN operator.\n");*/ }

"&"             { return '&'; /*printf("Found BITWISE_AND operator.\n");*/ }
"~"             { return '~'; /*printf("Found BITWISE_XOR or BITWISE_NOT operator.\n");*/ }
"|"             { return '|'; /*printf("Found BITWISE_OR operator.\n");*/ }

"=="            { return EQL; /*printf("Found EQL operator.\n");*/ }
"~="            { return NOT_EQL; /*printf("Found NOT_EQL operator.\n");*/ }
"<="            { return LE; /*printf("Found LE operator.\n");*/ }
">="            { return GE; /*printf("Found GE operator.\n");*/ }
"<"             { return '<'; /*printf("Found LESS operator.\n");*/ }
">"             { return '>'; /*printf("Found GREATER operator.\n");*/ }

"="             { return '='; /*printf("Found ASSIGN operator.\n");*/ }

"("             { return '('; /*printf("Found OPEN_PARENTHESIS.\n");*/ }
")"             { return ')'; /*printf("Found CLOSE_PARENTHESIS.\n");*/ }
"{"             { return '{'; /*printf("Found OPEN_CURLY_BRACKET.\n");*/ }
"}"             { return '}'; /*printf("Found CLOSE_CURLY_BRACKET.\n");*/ }
"["             { return '['; /*printf("Found OPEN_SQUARE_BRACKET.\n");*/ }
"]"             { return ']'; /*printf("Found CLOSE_SQUARE_BRACKET.\n");*/ }

";"             { return ';'; /*printf("Found SEMICOLON_FIELDSEP.\n");*/ }
","             { return ','; /*printf("Found COMMA_FIELDSEP.\n");*/ }
"."             { return '.'; /*printf("Found DOT operator.\n");*/ }
".."            { return CONCAT; /*printf("Found CONCAT operator.\n");*/ }
"..."           { return VAR_ARG; /*printf("Found VARARG.\n");*/ }

%{
    // +++++++++++++++++++++++++ Числовые константы +++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

{DIGIT}+ {
    // printf("Found INT_DECIMAL_NUMBER <%d>.\n", atoi(yytext));
    yylval.int_number = atoi(yytext);
    return INT_NUMBER;
}

{DIGIT}+("."{DIGIT}*)?{EXPONENT}? {
    // printf("Found FLOAT_DECIMAL_NUMBER <%f>.\n", atof(yytext));
    yylval.float_number = atof(yytext);
    return FLOAT_NUMBER;
}

{DIGIT}+("."{DIGIT}*)?[eE][+-]? {
    //TODO: объединить
    printf("Error: malformed number <%s>.\n", yytext);
}

{DIGIT}*"."{DIGIT}+{EXPONENT}? {
    // printf("Found FLOAT_DECIMAL_NUMBER <%f>.\n", atof(yytext));
    yylval.float_number = atof(yytext);
    return FLOAT_NUMBER;
}

{DIGIT}*"."{DIGIT}+[eE][+-]? {
    printf("Error: malformed number <%s>.\n", yytext);
}

0[xX]{HEX_DIGIT}+ {
    sscanf(yytext, "%x", &int_number);
    // printf("Found INT_HEXADECIMAL_NUMBER <%d>.\n", int_number);
    yylval.int_number = int_number;
    return INT_NUMBER;
}

0[xX]{HEX_DIGIT}+("."{HEX_DIGIT}*)?{HEX_EXPONENT}? {
    sscanf(yytext, "%a", &float_number);
    // printf("Found FLOAT_HEXADECIMAL_NUMBER <%f>.\n", float_number);
    yylval.float_number = float_number;
    return FLOAT_NUMBER;
}

0[xX]{HEX_DIGIT}+("."{HEX_DIGIT}*)?[pP][+-]? {
    printf("Error: malformed number <%s>.\n", yytext);
}

0[xX]{HEX_DIGIT}*"."{HEX_DIGIT}+{HEX_EXPONENT}? {
    sscanf(yytext, "%a", &float_number);
    // printf("Found FLOAT_HEXADECIMAL_NUMBER <%f>.\n", float_number);
    yylval.float_number = float_number;
    return FLOAT_NUMBER;
}

0[xX]{HEX_DIGIT}*"."{HEX_DIGIT}+[pP][+-]? {
    printf("Error: malformed number <%s>.\n", yytext);
}

%{
    // ++++++++++++++++++++++++++++ Комментарии +++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

--"["=*"["/.* {
    comment[0] = 0;
    long_bracket_level = strlen(yytext) - 4;
    BEGIN(ML_COMMENT);
}

--.* {
    printf("Found COMMENT: <%s>.\n", yytext + 2);
}

<ML_COMMENT>[^\]]* {
    strcat(comment, yytext);
}

<ML_COMMENT>"]" {
    strcat(comment, yytext);
}

<ML_COMMENT>"]"=*"]"/.* {
    int level = calc_long_bracket_level(yytext);
    if(long_bracket_level == level) {
        strcat(comment, yytext);
        comment[strlen(comment) - 2 - long_bracket_level] = 0;

        printf("Found ML_COMMENT: \n<<<<<<<<\n%s\n>>>>>>>>\n", comment);

        long_bracket_level = 0;
        BEGIN(INITIAL);
    }
    else {
        strcat(comment, yytext);
    }
}

<ML_COMMENT><<EOF>> {
    printf("Error: unclosed multiline comment.\n");
    BEGIN(INITIAL);
}

%{
    // ++++++++++++++++++++++++ Строковые константы +++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

"["=*"["\n? {
    string[0] = 0;

    if(yytext[strlen(yytext) - 1] == '\n')
        long_bracket_level = strlen(yytext) - 3;
    else {
        long_bracket_level = strlen(yytext) - 2;
    }

    BEGIN(ML_STRING);
}

<ML_STRING>[^\]]* {
    strcat(string, yytext);
}

<ML_STRING>"]" {
    strcat(string, yytext);
}

<ML_STRING>"]"=*"]"/.* {
    int level = calc_long_bracket_level(yytext);
    if(long_bracket_level == level) {
        strcat(string, yytext);
        string[strlen(string) - 2 - long_bracket_level] = 0;

        // printf("Found ML_STRING: \n<<<<<<<<\n%s\n>>>>>>>>\n", string);
        yylval.string=(char *)malloc(strlen(string)+1);
        strcpy(yylval.string, string);

        long_bracket_level = 0;
        BEGIN(INITIAL);
        return STRING;
    }
    else {
        strcat(string, yytext);
    }
}

<ML_STRING><<EOF>> {
    printf("Error: unclosed multiline string.\n");
    BEGIN(INITIAL);
}

\' {
    string[0] = 0;
    string_type = SQ;
    BEGIN(SQ_STRING);
}

\" {
    string[0] = 0;
    string_type = DQ;
    BEGIN(DQ_STRING);
}

<SQ_STRING>[^\\\'\n]+ {
    strcat(string, yytext);
}

<DQ_STRING>[^\\\"\n]+ {
    strcat(string, yytext);
}

<SQ_STRING,DQ_STRING>\\x{HEX_DIGIT}{2} {
    sscanf(yytext + 2, "%x", &int_number);
    tmp1[0] = int_number;
    tmp1[1] = 0;
    strcat(string, tmp1);
}

<SQ_STRING,DQ_STRING>\\x[^0-9A-Fa-f\"\'\\]{1,2} {
    // TODO: BEGIN(INITIAL)?
    printf("Error: invalid hex escape <%s>.\n", yytext);
}

<SQ_STRING,DQ_STRING>\\x{HEX_DIGIT}{0,1} {
    // TODO: BEGIN(INITIAL)?
    printf("Error: invalid hex escape <%s>.\n", yytext);
}

<SQ_STRING,DQ_STRING>\\(1[0-9][0-9]|2[0-4][0-9]|25[0-5]|[1-9][0-9]|[0-9]) {
    sscanf(yytext + 1, "%d", &int_number);
    tmp1[0] = int_number;
    tmp1[1] = 0;
    strcat(string, tmp1);
}

<SQ_STRING,DQ_STRING>\\([3-9][0-9][0-9]|2[6-9][0-9]|25[6-9]) {
    // TODO: BEGIN(INITIAL)?
    printf("Error: invalid hex escape <%s>.\n", yytext);
}

<SQ_STRING,DQ_STRING>\\[^abfnrtvzx\"\'\\0-9] {
    // TODO: BEGIN(INITIAL)?
    printf("Error: invalid hex escape <%s>.\n", yytext);
}

<SQ_STRING,DQ_STRING>\\a {
    strcat(string, "\a");
}

<SQ_STRING,DQ_STRING>\\b {
    strcat(string, "\b");
}

<SQ_STRING,DQ_STRING>\\f {
    strcat(string, "\f");
}

<SQ_STRING,DQ_STRING>\\n {
    strcat(string, "\n");
}

<SQ_STRING,DQ_STRING>\\r {
    strcat(string, "\r");
}

<SQ_STRING,DQ_STRING>\\t {
    strcat(string, "\t");
}

<SQ_STRING,DQ_STRING>\\v {
    strcat(string, "\v");
}

<SQ_STRING,DQ_STRING>\\z {
    BEGIN(REMOVING_WHITESPACES);
}

<REMOVING_WHITESPACES>[ \f\n\r\t\v]* {
    if(string_type == SQ) {
        BEGIN(SQ_STRING);
    }
    else if(string_type == DQ) {
        BEGIN(DQ_STRING);
    }
}

<SQ_STRING,DQ_STRING>\\\\ {
    strcat(string, "\\");
}

<SQ_STRING,DQ_STRING>\n {
    printf("Error: unfinished string.\n");
}

<SQ_STRING,DQ_STRING>\\\" {
    strcat(string, "\"");
}

<SQ_STRING,DQ_STRING>\\\' {
    strcat(string, "'");
}

<SQ_STRING>\' {
    // printf("Found SQ_STRING: <<%s>>\n", string);
    yylval.string=(char *)malloc(strlen(string)+1);
    strcpy(yylval.string, string);
    string_type = UNDEFINED;
    BEGIN(INITIAL);
    return STRING;
}

<DQ_STRING>\" {
    // printf("Found DQ_STRING: <<%s>>\n", string);
    yylval.string=(char *)malloc(strlen(string)+1);
    strcpy(yylval.string, string);
    string_type = UNDEFINED;
    BEGIN(INITIAL);
    return STRING;
}

<SQ_STRING><<EOF>> {
    printf("Error: expected '.\n");
    BEGIN(INITIAL);
}

<DQ_STRING><<EOF>> {
    printf("Error: expected \".\n");
    BEGIN(INITIAL);
}

%{
    // ++++++++++++++++++++++ Пользовательские имена ++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

[a-zA-Z_]{VAR_CHARS}* {
    // printf("Found VARIABLE or FUNCTION_NAME <%s>.\n", yytext);
    yylval.ident=(char *)malloc(strlen(yytext)+1);
    strcpy(yylval.ident, yytext);
    return IDENT;
}

%{
    // +++++++++++ Пробельные символы и символы, вызывающие ошибку ++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

[^ \f\n\r\t\v] {
    printf("Error: unexpected symbol <%s>.\n", yytext);
}

[ \f\n\r\t\v] { }

%%

int calc_long_bracket_level(const char * str) {
    int level = 0;
    for(int i = strlen(str) - 2; i >= 0; --i) {
        if(str[i] == '=') {
            level += 1;
        }
        if(str[i] == ']') {
            break;
        }
    }
    return level;
}
