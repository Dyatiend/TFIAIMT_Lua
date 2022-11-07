import nodes.ChunkNode
import nodes.FieldNode
import nodes.Utils
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main(args: Array<String>) {
    val rootNode = Utils.fromXML("../LuaCompiler/cmake-build-debug/xml.xml")
    val file = File("tree.dot")
    Utils.printProgram(rootNode, file)
}