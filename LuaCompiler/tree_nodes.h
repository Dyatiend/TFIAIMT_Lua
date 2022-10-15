#pragma once

#include <stddef.h>
#include <stdbool.h>

enum expr_type {
    UNINITIALIZED,
    _NIL,
    BOOLEAN,
    _NUMBER,
    _STRING,
    _VAR_ARG,
    VAR,
    FUNCTION_CALL,
    ADJUST,
    TABLE_CONSTRUCTOR,
    PLUS,
    MINUS,
    MUL,
    DIV,
    _FLOOR_DIV,
    POW,
    XOR,
    MOD,
    BIT_AND,
    BIT_OR,
    _CONCAT,
    LESS,
    _LE,
    GREATER,
    _GE,
    EQUAL,
    NOT_EQUAL,
    LOG_AND,
    LOG_OR,
    UNARY_MINUS,
    _NOT,
    LEN,
    BIT_NOT
};

enum stmt_type {
    _UNINITIALIZED,
	ASSIGNMENT,
    _FUNCTION_CALL,
    _BREAK,
    DO_LOOP,
    WHILE_LOOP,
    REPEAT_LOOP,
    _IF,
    _FOR,
    FOR_IN,
    FUNCTION_DEF,
    VAR_DEF,
    _RETURN,
};

enum var_type {
    __UNINITIALIZED,
    _IDENT,
    __VAR, //TODO __VAR заменил на EXPR. Для чего VAR был нужен??
    __FUNCTION_CALL,
    ADJUSTED_EXPR
};


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
};

struct stmt_seq_node {
    struct stmt_node * first;
    struct stmt_node * last;
};

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
};

struct var_item_node {
    enum var_type type; // Тип переменной

    unsigned int id;

    bool is_map_key; // Получена ли переменная по ключу (через точку)

    char * ident; // Переменная либо имя ключа
    
    struct expr_node * first_expr; // Function call или expr в скобках
    struct expr_node * second_expr; // expr в квадратных скобках

    struct var_item_node * next;
};

struct var_node {
    struct var_item_node * first;
    struct var_item_node * last;
};

struct expr_seq_node {
    struct expr_node * first;
    struct expr_node * last;
};

struct ident_node {
    unsigned int id;

    char * ident;

    struct ident_node * next;
};

struct ident_list_node {
    struct ident_node * first;
    struct ident_node * last;
};

struct param_list_node {
    bool has_var_arg;
    struct ident_list_node * list;
};

struct field_node {
    unsigned int id;

    char * ident;
    struct expr_node * key;
    struct expr_node * value;

    struct field_node * next;
};

struct field_list_node {
    struct field_node * first;
    struct field_node * last;
};

struct chunk_node {
    struct stmt_seq_node* block;
};
