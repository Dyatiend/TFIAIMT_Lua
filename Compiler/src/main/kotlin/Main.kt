import nodes.Utils
import java.io.File

fun main(args: Array<String>) {

    val process = ProcessBuilder("../LuaCompiler/cmake-build-debug/LuaCompiler","../LuaCompiler/tests/test10.lua").start()

    val rootNode = Utils.fromXML("xml.xml")!!

    fillTables(rootNode)

    classesTable.toCSV()

    val file = File("tree.dot")
    file.writeText("")
    Utils.printProgram(rootNode, file)
    file.writeText(file.readText().replace("@", "").replace("nodes.", ""))
}