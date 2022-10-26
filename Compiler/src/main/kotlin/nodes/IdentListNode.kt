package nodes

class IdentListNode(ID: String?) {
    var first: IdentNode
    var last: IdentNode

    init {
        val newIndentNode = IdentNode(ID)
        first = newIndentNode
        last = newIndentNode
    }

    fun addIdent(ID: String?) {
        val newIndentNode = IdentNode(ID)
        last.next = newIndentNode
        last = newIndentNode
    }
}