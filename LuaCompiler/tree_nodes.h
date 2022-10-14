#include <stddef.h>
#include <stdbool.h>

enum expr_type {
    UNINITIALIZED,
    NIL,
    BOOLEAN,
    NUMBER,
    STRING,
    VAR_ARG,
    VAR,
    FUNCTION_CALL,
    ADJUST,
    TABLE_CONSTRUCTOR,
    PLUS,
    MINUS,
    MUL,
    DIV,
    FLOOR_DIV,
    POW,
    XOR,
    MOD,
    BIT_AND,
    BIT_OR,
    CONCAT,
    LESS,
    LE,
    GREATER,
    GE,
    EQUAL,
    NOT_EQUAL,
    LOG_AND,
    LOG_OR,
    UNARY_MINUS,
    NOT,
    LEN,
    BIT_NOT
};

enum stmt_type {
    _UNINITIALIZED,
	ASSIGNMENT,
    _FUNCTION_CALL,
    BREAK,
    DO_LOOP,
    WHILE_LOOP,
    REPEAT_LOOP,
    IF,
    FOR,
    FOR_IN,
    FUNCTION_DEF,
    VAR_DEF,
    RETURN,
};

enum var_type {
    __UNINITIALIZED,
    IDENT,
    __VAR,
    __FUNCTION_CALL,
    ADJUSTED_EXPR
};

unsigned int LAST_ID = 0;

struct stmt_node {
    enum stmt_type type;

    unsigned int id;

    bool is_local; // Является ли функция локальной
    char * ident; // Имя переменной цикла или имя функции

    struct expr_seq_node * var_list; // Список переменных при приравнивании
    struct ident_list_node * ident_list; // Список переменных цикла или список переменных при объявлении
    struct expr_seq_node * values; // Значения при приравнивании или в цикле for in 

    struct expr_node * condition_expr; // Выражение условия для циков do, while, repeat, if, центральный оператор for
    struct stmt_seq_node * action_block; // Действие циклов, функций и elseif блоков

    struct stmt_seq_node * if_block; // IF true блок
    struct stmt_seq_node * elseif_seq; // Список елзифов
    struct stmt_seq_node * else_block; // Else блок

    struct param_list_node * params; // Параметры функции

    struct expr_node * function_call; // Вызов функции
    
    struct expr_node * initial_value; // Начальное зачение переменной в цикле for
    struct expr_node * step_expr; // Выражение в конце цикла for

    struct stmt_node * next;
} stmt_node_default = {
    _UNINITIALIZED, 0, false, NULL, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
};

struct stmt_seq_node {
    struct stmt_node * first;
    struct stmt_node * last;
} stmt_seq_node_default = { NULL, NULL };

struct expr_node {
    enum expr_type type;

    unsigned int id;

    bool bool_value; // Значение бул
    double number_value; // Число
    char * string_value; // Строка

    // Вызов функции
    char * ident;
    struct expr_seq_node * args;

    struct expr_node * adjusted_expr; // Выражение в скобках
	
    struct field_list_node * table_constructor; // Таблица

    struct expr_node * first_operand; // Первый операнд и операнд для унарных
    struct expr_node * second_operand; // Второй операнд

    struct var_node * var; // Переменная

    struct expr_node * next;
} expr_node_default = {
    UNINITIALIZED, 0, false, 0, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
};

struct var_item_node {
    enum var_type type; // Тип переменной

    unsigned int id;

    bool is_map_key; // Получена ли переменная по ключу (через точку)

    char * ident; // Переменная либо имя ключа
    
    struct expr_node * first_expr; // Function call или expr в скобках
    struct expr_node * second_expr; // expr в квадратных скобках

    struct var_item_node * next;
} var_item_node_default = {
    __UNINITIALIZED, 0, false, NULL, NULL, NULL, NULL
};

struct var_node {
    struct var_item_node * first;
    struct var_item_node * last;
} var_node_default = { NULL, NULL };

struct expr_seq_node {
    struct expr_node * first;
    struct expr_node * last;
} expr_seq_node_default = { NULL, NULL };

struct ident_node {
    unsigned int id;

    char * ident;

    struct ident_node * next;
} ident_node_default = { 0, NULL, NULL };

struct ident_list_node {
    struct ident_node * first;
    struct ident_node * last;
} ident_list_node_default = { NULL, NULL };

struct param_list_node {
    bool has_var_arg;
    struct ident_list_node * list;
} param_list_node_default = { false, NULL };

struct field_node {
    unsigned int id;

    char * ident;
    struct expr_node * key;
    struct expr_node * value;

    struct field_node * next;
} field_node_default = { 0, NULL, NULL, NULL };

struct field_list_node {
    struct field_node * first;
    struct field_node * last;
} field_list_node_default = { NULL, NULL };
