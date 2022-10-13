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
}

enum stmt_type {
    UNINITIALIZED,
	ASSIGNMENT,
    FUNCTION_CALL,
    BREAK,
    DO_LOOP,
    WHILE_LOOP,
    REPEAT_LOOP,
    IF,
    FOR,
    FOR_IN,
    FUNCTION_DEF,
    VAR_DEF, //FIXME Возможно можно убрать нижний или объединить их???
    VAR_DEF_WITH_ASSIGNMENT
};

struct stmt_node {
    enum stmt_type type;

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
    UNINITIALIZED, false, NULL, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
};

struct stmt_seq_node {
    struct stmt_node * first;
    struct stmt_node * last;
} stmt_seq_node_default = { NULL, NULL };

struct expr_node {
    enum expr_type type;

    bool bool_value; // Значение бул
    double number_value; // Число
    char * string_value; // Строка

    struct expr_node * function_call; // Вызов функции FIXME здесь должен быть другой тип???

    struct expr_node * adjusted_expr; // Выражение в скобках
	
    struct expr_node * table_constructor; // Что бы это могло быть FIXME тут тожен мб другой тип???

    struct expr_node * first_operand; // Первый операнд и операнд для унарных
    struct expr_node * second_operand; // Второй операнд

    struct expr_node * next;
} expr_node_default = {
    UNINITIALIZED, false, 0, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
};

struct expr_seq_node {
    struct expr_node * first;
    struct expr_node * last;
} expr_seq_node_default = { NULL, NULL };

struct ident_node {
    char * ident;

    struct ident_node * next;
} ident_node_default = { NULL, NULL };

struct ident_list_node {
    struct ident_node * first;
    struct ident_node * last;
} ident_list_node_default = {};

struct param_node {
    char * param;

    struct param_node * next;
} param_node_default = { NULL, NULL };

struct param_list_node {
    bool has_var_arg;
    struct param_node * first;
    struct param_node * last;
} param_list_node_default = { false, NULL, NULL };

struct field_node {
    char * ident;
    struct expr_node * key;
    struct expr_node * value;
} field_node_default = { NULL, NULL, NULL };

struct field_list_node {
    struct field_node * first;
    struct field_node * last;
} field_list_node_default = { NULL, NULL };
