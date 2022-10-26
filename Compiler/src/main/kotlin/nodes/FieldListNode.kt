package nodes

class FieldListNode(var first: FieldNode) {
    var last: FieldNode = first

    fun addField(field: FieldNode) {
        last.next = field
        last = field
    }
}