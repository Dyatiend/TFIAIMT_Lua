import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

fun main(args: Array<String>) {
    // Создается билдер дерева
    val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    // Создается дерево DOM документа из файла
    val document = documentBuilder.parse("../LuaCompiler/cmake-build-debug/xml.xml")

    // Получаем корневой элемент
    val xml: Node = document.documentElement

    // Корень выражения
    var root: Node? = null

    // Ищем корень
    val childNodes = xml.childNodes
    for (i in 0 until childNodes.length) {
        val child = childNodes.item(i)
        if (child.nodeType == Node.ELEMENT_NODE) {
            root = if (root == null) {
                child
            } else {
                throw IllegalAccessException("Выражение должно иметь один корневой узел")
            }
        }
    }
    if (root == null) {
        throw IllegalAccessException("Не найден корневой узел выражения")
    }

    println(root.firstChild.textContent)
}