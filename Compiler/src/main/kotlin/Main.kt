import nodes.ChunkNode
import nodes.FieldNode
import nodes.TransformTree
import nodes.Utils
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main(args: Array<String>) {
    val rootNode = Utils.fromXML("../LuaCompiler/cmake-build-debug/xml.xml")

    TransformTree.transform(rootNode)

    val file = File("tree.dot")
    file.writeText("")
    Utils.printProgram(rootNode, file)
    file.writeText(file.readText().replace("@", "").replace("nodes.", ""))
}