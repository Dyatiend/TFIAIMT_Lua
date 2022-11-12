import nodes.Utils
import java.io.File

fun main(args: Array<String>) {
    val rootNode = Utils.fromXML("../LuaCompiler/cmake-build-debug/xml.xml")!!

    fillTables(rootNode)

    constantsTable.toCSV()
    localVarsTable.toCSV()

    val file = File("tree.dot")
    file.writeText("")
    Utils.printProgram(rootNode, file)
    file.writeText(file.readText().replace("@", "").replace("nodes.", ""))
}