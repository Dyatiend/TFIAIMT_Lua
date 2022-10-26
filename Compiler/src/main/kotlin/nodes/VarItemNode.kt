package nodes

class VarItemNode {
    var type = VarType.UNINITIALIZED // Тип переменной
    var id = 0
    var isMapKey = false // Получена ли переменная по ключу (через точку)
    var ident = "" // Переменная либо имя ключа
    var firstExpr: ExprNode? = null // Function call или expr в скобках
    var secondExpr: ExprNode? = null // expr в квадратных скобках
    var next: VarItemNode? = null
}