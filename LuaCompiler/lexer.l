%option noyywrap
%option never-interactive

%{
    #include <stdio.h>

    void remove_char_from_str(const char * str, char * dest, char deleted_char);
%}

DIGIT [0-9]
HEX_DIGIT [0-9A-Fa-f]

EXPONENT [eE][+-]?{DIGIT}*
HEX_EXPONENT [pP][+-]?{HEX_DIGIT}*

VAR_CHARS [a-zA-Z0-9_]

%x COMMENT
%x ML_COMMENT
%x STRING_SINGLE_QUOTES
%x STRING_DOUBLE_QUOTES
%x DELETE_WHITESPACE

%%

%{
    enum TypeString {
        DOUBLE_QUOTES,
        SINGLE_QUOTES,
        MULTISTRING,
        UNDEFINED
    };
    
    int int_number;
    float float_number;
    char tmp1[100];
    char tmp2[100];
    char comment[1000];
    char string[1000];
    enum TypeString typeString;

    int commentLevel;
%}

%{
    // ++++++++++++++++++++++++++ Ключевые слова ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

and             { printf("Found AND keyword.\n"); }
or              { printf("Found OR keyword.\n"); }
not             { printf("Found NOT keyword.\n"); }

if              { printf("Found IF keyword.\n"); }
then            { printf("Found THEN keyword.\n"); }
else            { printf("Found ELSE keyword.\n"); }
elseif          { printf("Found ELSEIF keyword.\n"); }

repeat          { printf("Found REPEAT keyword.\n"); }
for             { printf("Found FOR keyword.\n"); }
in              { printf("Found IN keyword.\n"); }
do              { printf("Found DO keyword.\n"); }
while           { printf("Found WHILE keyword.\n"); }
until           { printf("Found UNTIL keyword.\n"); }
break           { printf("Found BREAK keyword.\n"); }
end             { printf("Found END keyword.\n"); }

true            { printf("Found TRUE keyword.\n"); }
false           { printf("Found FALSE keyword.\n"); }
nil             { printf("Found NIL keyword.\n"); }

function        { printf("Found FUNCTION keyword.\n"); }
return          { printf("Found RETURN keyword.\n"); }

goto            { printf("Found GOTO keyword.\n"); }

local           { printf("Found LOCAL keyword.\n"); }

%{
    // +++++++++++++++++++++++++ Служебные символы ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

"+"             { printf("Found ADD operator.\n"); }
"-"             { printf("Found SUB operator.\n"); }
"*"             { printf("Found MUL operator.\n"); }
"/"             { printf("Found DIV operator.\n"); }
"//"            { printf("Found FLOOR_DIV operator.\n"); }
"%"             { printf("Found MOD operator.\n"); }
"^"             { printf("Found POW operator.\n"); }

"#"             { printf("Found LEN operator.\n"); }

"&"             { printf("Found BITWISE_AND operator.\n"); }
"~"             { printf("Found BITWISE_XOR or BITWISE_NOT operator.\n"); }
"|"             { printf("Found BITWISE_OR operator.\n"); }
"<<"            { printf("Found BITWISE_LEFT_SHIFT operator.\n"); }
">>"            { printf("Found BITWISE_RIGHT_SHIFT operator.\n"); }

"=="            { printf("Found EQL operator.\n"); }
"~="            { printf("Found NOT_EQL operator.\n"); }
"<="            { printf("Found LE operator.\n"); }
">="            { printf("Found GE operator.\n"); }
"<"             { printf("Found LESS operator.\n"); }
">"             { printf("Found GREATER operator.\n"); }

"="             { printf("Found ASSIGN operator.\n"); }

"("             { printf("Found OPEN_PARENTHESIS.\n"); }
")"             { printf("Found CLOSE_PARENTHESIS.\n"); }
"{"             { printf("Found OPEN_CURLY_BRACKET.\n"); }
"}"             { printf("Found CLOSE_CURLY_BRACKET.\n"); }
"["             { printf("Found OPEN_SQUARE_BRACKET.\n"); }
"]"             { printf("Found CLOSE_SQUARE_BRACKET.\n"); }

"::"             { printf("Found GOTO_TAG_MARK.\n"); }
";"             { printf("Found SEMICOLON_FIELDSEP.\n"); }
":"             { printf("Found COLON operator.\n"); }
","             { printf("Found COMMA_FIELDSEP operator.\n"); }
"."             { printf("Found DOT operator.\n"); }
".."            { printf("Found CONCAT operator.\n"); }
"..."           { printf("Found VARARG.\n"); }

%{
    // +++++++++++++++++++++++++ Служебные символы ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

%{
    // +++++++++++++++++++++++++ Служебные символы ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // TODO
%}

{DIGIT}+ {
    printf("Found INT_DECIMAL_NUMBER %d\n", atoi(yytext));
}

{DIGIT}+"."{DIGIT}* {
    printf("Found FLOAT_DECIMAL_NUMBER %f\n", atof(yytext));
}

{DIGIT}+("."{DIGIT}*)?[eE][+-]?{DIGIT}+ {
    //TODO мб можно объединить с регуляркой сверху но пока хз как
    //TODO мб неправильно что-то с размерностью после запятой
    printf("Found FLOAT_DECIMAL_NUMBER %f\n", atof(yytext));
}

0[xX]{HEX_DIGIT}+ {
    sscanf(yytext, "%x", &int_number);
    printf("Found INT_HEXADECIMAL_NUMBER %d\n", int_number);
}

0[xX]{HEX_DIGIT}+[pP][+-]?{DIGIT}+ {
    sscanf(yytext, "%a", &float_number);
    printf("Found FLOAT_HEXADECIMAL_NUMBER %f\n", float_number);
}




"({DIGIT})*\.(?(1){DIGIT}*|{DIGIT}+)(?:|[eE][+-]?{DIGIT}+)" {
    printf("Found FLOAT_DECIMAL_NUMBER %f\n", atof(yytext));
}

"0[xX]({HEX_DIGIT})*\.(?(1){HEX_DIGIT}*|{HEX_DIGIT}+)(?:|[pP][+-]?{DIGIT}+)" {
    sscanf(yytext, "%a", &float_number);
    printf("Found FLOAT_HEXADECIMAL_NUMBER %f\n", float_number);
}

%{
    // +++++++++++++++++++++++++ Комментарии ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

-- {
BEGIN(COMMENT);
}

<COMMENT>"["=*"["/.* {
comment[0] = 0;
commentLevel = strlen(yytext) - 2;
BEGIN(ML_COMMENT);
}

<COMMENT>.* {
printf("Нашел одиночный %s\n", yytext);
BEGIN(INITIAL);
}

<ML_COMMENT>.*"]"=*"]" {

int level = 0;
for(int i = strlen(yytext) - 2; i >= 0; --i) {

    if(yytext[i] == '=') {
    level += 1;
    }
    if(yytext[i] == ']') break;
}

if(commentLevel == level) {
strcat(comment, yytext);
comment[strlen(comment) - 2 - commentLevel] = 0;
printf("Нашел многострочный %s\n", comment);
commentLevel = 0;
BEGIN(INITIAL);
}
else {
strcat(comment, yytext);
}
}

<ML_COMMENT>.* {
strcat(comment, yytext);
}

<ML_COMMENT>\n {
strcat(comment, yytext);
}


%{
    // +++++++++++++++++++++++++ Строки ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

\' {
    string[0] = 0;
    typeString = SINGLE_QUOTES;
    BEGIN(STRING_SINGLE_QUOTES);
}

\" {
    string[0] = 0;
    typeString = DOUBLE_QUOTES;
    BEGIN(STRING_DOUBLE_QUOTES);
}


<STRING_SINGLE_QUOTES>[^\a\b\f\n\r\t\v\\\'\"]+ {
    //TODO Здесь поидее можно удалить \a\b\f\n\r\t\v и это \" (тогда можно удалить нижние правила)
    strcat(string, yytext); 
}

<STRING_DOUBLE_QUOTES>[^\a\b\f\n\r\t\v\\\'\"]+ {
    //TODO Здесь поидее можно удалить \a\b\f\n\r\t\v и это \' (тогда можно удалить нижние правила)
    strcat(string, yytext); 
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\u\{{HEX_DIGIT}{1,8}\} {
    //TODO Проверить
    remove_char_from_str(yytext,tmp1,'{');
    remove_char_from_str(tmp1,tmp2,'}');
    strcat(string, tmp2);
    
    // sscanf(yytext+2, "%x", &int_number);
    // tmp1[0] = int_number;
    // tmp1[1] = 0;
    // strcat(string, tmp1);
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>"\\u\{(|{HEX_DIGIT}{9,})\}" {
    //TODO Проверить
    printf("ERROR: Invalid hex escape \n");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\x{HEX_DIGIT}{2} {
    sscanf(yytext+2, "%x", &int_number);
    tmp1[0] = int_number;
    tmp1[1] = 0;
    strcat(string, tmp1);
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\x[G-Zg-z]{1,2} {
    printf("ERROR: Invalid hex escape \n");
    //TODO мб надо бегин инишал сделать
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\x{HEX_DIGIT}{0,1} {
    printf("ERROR: Invalid hex escape \n");
    //TODO мб надо бегин инишал сделать
    //TODO мб еще нужная проверка на то что там не хекс числа
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\{DIGIT}{1,3} {
    sscanf(yytext+1, "%d", &int_number);
    tmp1[0] = int_number;
    tmp1[1] = 0;
    strcat(string, tmp1);
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\" " {
    //TODO Надо наверн вместо пробела написать любые символы кроме букв, которые являются эскейп последовательностями и чисел
    printf("ERROR: Invalid hex escape \n");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\a {
    strcat(string, "\a");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\b {
    strcat(string, "\b");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\f {
    strcat(string, "\f");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\n {
    strcat(string, "\n");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\r {
    strcat(string, "\r");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\t {
    strcat(string, "\t");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\v {
    strcat(string, "\v");
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\z {
    BEGIN(DELETE_WHITESPACE);
}

<DELETE_WHITESPACE>(" "|\n)* {
    if(typeString == DOUBLE_QUOTES)
    {
        BEGIN(STRING_DOUBLE_QUOTES);
    }
    else if(typeString == SINGLE_QUOTES)
    {
        BEGIN(STRING_SINGLE_QUOTES);
    }
    //TODO Добавить для многострочного текста
}

<STRING_SINGLE_QUOTES,STRING_DOUBLE_QUOTES>\\\\ {
    strcat(string, "\\");
}

<STRING_DOUBLE_QUOTES>\\\" {
    strcat(string, "\"");
}

<STRING_SINGLE_QUOTES>\\\' {
    strcat(string, "'");
}

<STRING_SINGLE_QUOTES>\" {
    strcat(string, "\"");
}

<STRING_DOUBLE_QUOTES>\' {
    strcat(string, "'");
}

<STRING_SINGLE_QUOTES>\' {
    printf("Found STRING_SINGLE_QUOTES: %s\n", string);
    typeString = UNDEFINED;
    BEGIN(INITIAL);
}

<STRING_DOUBLE_QUOTES>\" {
    printf("Found STRING_DOUBLE_QUOTES: %s\n", string);
    typeString = UNDEFINED;
    BEGIN(INITIAL);
}

<STRING_DOUBLE_QUOTES><<EOF>> { 
    printf("ERROR: expected \" \n"); 
    BEGIN(INITIAL);
    }

<STRING_SINGLE_QUOTES><<EOF>> { 
    printf("ERROR: expected ' \n");
    BEGIN(INITIAL);
    }


%{
    // +++++++++++++++++++++++++ _____ ++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%}

[a-zA-Z_]{VAR_CHARS}* {
    printf("Found VARIABLE or FUNCTION_NAME %s\n", yytext);
}

\n { 
    //printf("Found NEW_LINE\n");
}

%%

void remove_char_from_str(const char * str, char * dest, char deleted_char) {
	int j = 0;

    for(int i = 0; str[i] != 0; i++) {
        if(str[i] != deleted_char) {
            dest[j++] = str[i];
        }
    }

    dest[j] = 0;
}

int main(int argc, char **argv ){
	yyin = fopen(argv[1], "r" );

    yylex();
    return 0;
}