package nodes

enum class ExprType {
    UNINITIALIZED, NIL, BOOLEAN, FLOAT_NUMBER, INT_NUMBER, STRING, VAR_ARG, VAR, FUNCTION_CALL, ADJUST, TABLE_CONSTRUCTOR, PLUS, MINUS, MUL, DIV, FLOOR_DIV, POW, XOR, MOD, BIT_AND, BIT_OR, CONCAT, LESS, LE, GREATER, GE, EQUAL, NOT_EQUAL, LOG_AND, LOG_OR, UNARY_MINUS, NOT, LEN, BIT_NOT;
    fun getMethod(): String {
        return when(this) {
            PLUS -> "__add__"
            MINUS -> "__sub__"
            MUL -> "__mul__"
            DIV -> "__div__"
            FLOOR_DIV -> "__floor_div__"
            POW -> "__pow__"
            XOR -> "__xor__"
            MOD -> "__mod__"
            BIT_AND -> "__bit_and__"
            BIT_OR -> "__bit_or__"
            CONCAT -> "__concat__"
            LESS -> "__less__"
            LE -> "__less_or_eql__"
            GREATER -> "__greater__"
            GE -> "__greater_or_eql__"
            EQUAL -> "__eql__"
            NOT_EQUAL -> "__not_eql__"
            LOG_AND -> "__logic_and__"
            LOG_OR -> "__logic_or__"
            UNARY_MINUS -> "__unary_minus__"
            NOT -> "__not__"
            LEN -> "__len__"
            BIT_NOT -> "__bit_not__"
            ADJUST -> "__adjust__"
            else -> ""
        }
    }
}