package nodes

class IdentNode(Id: String?) {
    var id: Int = Utils.lastId
    var ident: String? = Id
    var next: IdentNode? = null
}