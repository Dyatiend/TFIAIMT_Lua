package nodes;

public class VarItemNode {
    public VarType type = VarType.UNINITIALIZED; // Тип переменной

    public int id = 0;

    public boolean is_map_key = false; // Получена ли переменная по ключу (через точку)

    public String ident = ""; // Переменная либо имя ключа

    public ExprNode first_expr = null; // Function call или expr в скобках
    public ExprNode second_expr = null; // expr в квадратных скобках

    public VarItemNode next = null;
}
