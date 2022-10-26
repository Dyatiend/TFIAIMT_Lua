package nodes

import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

object Utils {
    private var LAST_ID = 0
    val lastId: Int
        get() = LAST_ID++

    fun fromXML(path: String?): ChunkNode? {
        try {
            // Создается билдер дерева
            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            // Создается дерево DOM документа из файла
            val document = documentBuilder.parse(path)

            // Получаем корневой элемент
            val root: Node = document.documentElement

            // Строим дерево
            return buildProgram(root)
        } catch (ex: ParserConfigurationException) {
            ex.printStackTrace(System.out)
        } catch (ex: IOException) {
            ex.printStackTrace(System.out)
        } catch (ex: SAXException) {
            ex.printStackTrace(System.out)
        }
        return null
    }

    private fun buildProgram(node: Node): ChunkNode {
        val res = StmtSeqNode()
        val stmtSeq = node.firstChild

        for (i in 0 until stmtSeq.childNodes.length) {
            val child = stmtSeq.childNodes.item(i)
            val stmt = buildStmtNode(child)
            res.addStmt(stmt)
        }

        return ChunkNode(res)
    }

    private fun buildStmtNode(node: Node): StmtNode {
        val typeStr = node.attributes.getNamedItem("type").nodeValue

        return when (val type = StmtType.valueOf(typeStr)) {
            StmtType.ASSIGNMENT -> {
                StmtNode.createAssignStmtNode(
                    buildExprSeqNode(node.firstChild.firstChild),
                    buildExprSeqNode(node.lastChild.firstChild)
                )
            }
            StmtType.FUNCTION_CALL -> {
                StmtNode.createFunctionCallStmtNode(buildExprNode(node.firstChild))
            }
            StmtType.BREAK -> StmtNode.createBreakStmtNode()
            StmtType.DO_LOOP -> {
                StmtNode.createDoStmtNode(buildStmtSeqNode(node.firstChild.firstChild))
            }
            StmtType.WHILE_LOOP, StmtType.REPEAT_LOOP -> {
                StmtNode.createCycleStmtNode(
                    type,
                    buildExprNode(node.firstChild.firstChild),
                    buildStmtSeqNode(node.lastChild.firstChild)
                )
            }
            StmtType.IF -> {
                StmtNode.createIfStmtNode(
                    buildExprNode(node.firstChild.firstChild),
                    buildStmtSeqNode(node.childNodes.item(1).firstChild),
                    (if (node.childNodes.length > 2 && node.childNodes.item(2).nodeName == "elseif_seq")
                        buildStmtSeqNode(node.childNodes.item(2)) // здесь firstChild не нужен т.к. тут мега костыль
                    else
                        null),
                    if (node.childNodes.length == 3 && node.childNodes.item(2).nodeName == "else")
                        buildStmtSeqNode(node.childNodes.item(2).firstChild)
                    else if (node.childNodes.length == 4 && node.childNodes.item(3).nodeName == "else")
                        buildStmtSeqNode(node.childNodes.item(3).firstChild)
                    else
                        null
                )
            }
            StmtType.FOR -> {
                StmtNode.createForStmtNode(
                    node.firstChild.attributes.getNamedItem("ident").nodeValue,
                    buildExprNode(node.childNodes.item(1).firstChild),
                    buildExprNode(node.childNodes.item(2).firstChild),
                    (if (node.childNodes.length == 5)
                        buildExprNode(node.childNodes.item(3).firstChild)
                    else
                        null),
                    buildStmtSeqNode(node.lastChild.firstChild)
                )
            }
            StmtType.FOREACH -> {
                StmtNode.createForeachStmtNode(
                    buildIdentListNode(node.childNodes.item(0).firstChild),
                    buildExprSeqNode(node.childNodes.item(1).firstChild),
                    buildStmtSeqNode(node.childNodes.item(2).firstChild)
                )
            }
            StmtType.FUNCTION_DEF -> {
                val isLocal = node.attributes.getNamedItem("is_local").nodeValue == "true"
                StmtNode.createFunctionDefStmtNode(
                    node.firstChild.attributes.getNamedItem("ident").nodeValue,
                    buildParamListNode(node.childNodes.item(1).firstChild),
                    buildStmtSeqNode(node.lastChild.firstChild),
                    isLocal
                )
            }
            StmtType.VAR_DEF -> {
                StmtNode.createLocalVarStmtNode(
                    buildIdentListNode(node.firstChild.firstChild),
                    if (node.childNodes.length == 2)
                        buildExprSeqNode(node.lastChild.firstChild)
                    else
                        null
                )
            }
            StmtType.RETURN -> {
                StmtNode.createReturnStmtNode(
                    if (node.childNodes.length != 0)
                        buildExprSeqNode(node.firstChild.firstChild)
                    else
                        null
                )
            }
            else -> {
                // невозможная ситуация
                require(type != StmtType.UNINITIALIZED) { "Информативное сообщение об ошибке" }
                StmtNode()
            }
        }
    }

    private fun buildExprSeqNode(node: Node): ExprSeqNode {
        val res = ExprSeqNode(buildExprNode(node.firstChild))

        for (i in 1 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            res.addExpr(buildExprNode(child))
        }

        return res
    }

    private fun buildExprNode(node: Node): ExprNode {
        val typeStr = node.attributes.getNamedItem("type").nodeValue

        return when (val type = ExprType.valueOf(typeStr)) {
            ExprType.NIL -> {
                ExprNode.createNilExprNode()
            }
            ExprType.BOOLEAN -> {
                ExprNode.createBoolExprNode(node.attributes.getNamedItem("value").nodeValue == "true")
            }
            ExprType.NUMBER -> {
                ExprNode.createNumberExprNode(node.attributes.getNamedItem("value").nodeValue.toFloat())
            }
            ExprType.STRING -> {
                ExprNode.createStringExprNode(node.textContent)
            }
            ExprType.VAR_ARG -> {
                ExprNode.createVarArgExprNode()
            }
            ExprType.VAR -> {
                ExprNode.createVarExprNode(buildVarNode(node.firstChild))
            }
            ExprType.FUNCTION_CALL -> {
                ExprNode.createFunctionCallExprNode(
                    node.attributes.getNamedItem("ident").nodeValue,
                    if (node.childNodes.length == 1)
                        buildExprSeqNode(node.firstChild.firstChild)
                    else
                        null
                )
            }
            ExprType.ADJUST -> {
                ExprNode.createAdjustingExprNode(buildExprNode(node.firstChild))
            }
            ExprType.TABLE_CONSTRUCTOR -> {
                ExprNode.createTableConstructorExprNode(
                    if (node.childNodes.length == 1)
                        buildFieldListNode(node.firstChild)
                    else
                        null
                )
            }
            ExprType.PLUS, ExprType.MINUS, ExprType.MUL, ExprType.DIV, ExprType.FLOOR_DIV,
            ExprType.POW, ExprType.XOR, ExprType.MOD, ExprType.BIT_AND, ExprType.BIT_OR,
            ExprType.CONCAT, ExprType.LESS, ExprType.LE, ExprType.GREATER, ExprType.GE,
            ExprType.EQUAL, ExprType.NOT_EQUAL, ExprType.LOG_AND, ExprType.LOG_OR -> {
                ExprNode.createBinExprNode(type, buildExprNode(node.firstChild),buildExprNode(node.lastChild))
            }
            ExprType.UNARY_MINUS, ExprType.NOT, ExprType.LEN, ExprType.BIT_NOT -> {
                ExprNode.createUnaryExprNode(type, buildExprNode(node.firstChild))
            }
            else -> {
                // невозможная ситуация
                require(type != ExprType.UNINITIALIZED) { "Информативное сообщение об ошибке" }
                ExprNode()
            }
        }
    }

    private fun buildVarItemNode(node: Node): VarItemNode {
        val typeStr = node.attributes.getNamedItem("type").nodeValue

        return when (val type = VarType.valueOf(typeStr)) {
            VarType.IDENT -> {
                val item = VarItemNode()
                item.id = lastId
                item.type = VarType.IDENT
                item.ident = node.attributes.getNamedItem("ident").nodeValue
                item
            }
            VarType.VAR -> {
                val isMapKey = node.attributes.getNamedItem("is_map_key").nodeValue == "true"

                if (isMapKey) {
                    val item = VarItemNode()
                    item.id = lastId
                    item.type = VarType.VAR
                    item.ident = node.attributes.getNamedItem("key").nodeValue
                    item.isMapKey = true
                    item
                }
                else {
                    val item = VarItemNode()
                    item.id = lastId
                    item.type = VarType.VAR
                    item.secondExpr = buildExprNode(node.firstChild)
                    item.isMapKey = false
                    item
                }
            }
            VarType.FUNCTION_CALL -> {
                val isMapKey = node.attributes.getNamedItem("is_map_key").nodeValue == "true"

                if (isMapKey) {
                    val item = VarItemNode()
                    item.id = lastId
                    item.type = VarType.FUNCTION_CALL
                    item.ident = node.attributes.getNamedItem("key").nodeValue
                    item.firstExpr = buildExprNode(node.firstChild)
                    item.isMapKey = true
                    item
                }
                else {
                    val item = VarItemNode()
                    item.id = lastId
                    item.type = VarType.FUNCTION_CALL
                    item.firstExpr = buildExprNode(node.firstChild)
                    item.secondExpr = buildExprNode(node.lastChild)
                    item.isMapKey = false
                    item
                }
            }
            VarType.ADJUSTED_EXPR -> {
                val isMapKey = node.attributes.getNamedItem("is_map_key").nodeValue == "true"

                if (isMapKey) {
                    val item = VarItemNode()
                    item.id = lastId
                    item.type = VarType.ADJUSTED_EXPR
                    item.ident = node.attributes.getNamedItem("key").nodeValue
                    item.firstExpr = buildExprNode(node.firstChild)
                    item.isMapKey = true
                    item
                }
                else {
                    val item = VarItemNode()
                    item.id = lastId
                    item.type = VarType.ADJUSTED_EXPR
                    item.firstExpr = buildExprNode(node.firstChild)
                    item.secondExpr = buildExprNode(node.lastChild)
                    item.isMapKey = false
                    item
                }
            }
            else -> {
                // невозможная ситуация
                require(type != VarType.UNINITIALIZED) { "Информативное сообщение об ошибке" }
                VarItemNode()
            }
        }
    }

    private fun buildVarNode(node: Node): VarNode {
        val res = VarNode(buildVarItemNode(node.firstChild))

        for (i in 1 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            res.add(buildVarItemNode(child))
        }

        return res
    }

    private fun buildFieldNode(node: Node): FieldNode {
        return if (node.attributes.getNamedItem("ident") != null) {
            FieldNode(
                node.attributes.getNamedItem("ident").nodeValue,
                buildExprNode(node.firstChild),
                null
            )
        }
        else if (node.childNodes.length == 2) {
            FieldNode(
                null,
                buildExprNode(node.lastChild),
                buildExprNode(node.firstChild)
            )
        }
        else {
            FieldNode(
                null,
                buildExprNode(node.firstChild),
                null
            )
        }
    }

    private fun buildFieldListNode(node: Node): FieldListNode {
        val res = FieldListNode(buildFieldNode(node.firstChild))

        for (i in 1 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            res.addField(buildFieldNode(child))
        }

        return res
    }

    private fun buildIdentListNode(node: Node): IdentListNode {
        val res = IdentListNode(node.firstChild.attributes.getNamedItem("ident").nodeValue)

        for (i in 1 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            res.addIdent(child.attributes.getNamedItem("ident").nodeValue)
        }

        return res
    }

    private fun buildParamListNode(node: Node): ParamListNode {
        return ParamListNode(buildIdentListNode(node.firstChild), node.attributes.getNamedItem("has_var_arg").nodeValue == "true")
    }

    private fun buildStmtSeqNode(node: Node): StmtSeqNode {
        if(node.nodeName == "elseif_seq") { // Костыль очень крутой
            val res = StmtSeqNode()
            val stmtSeq = node.firstChild

            for (i in 0 until stmtSeq.childNodes.length) {
                val child = stmtSeq.childNodes.item(i)
                res.addElseifSeq(
                    buildExprNode(child.firstChild.firstChild),
                    buildStmtSeqNode(child.lastChild.firstChild)
                )
            }

            return res
        }
        else {
            val res = StmtSeqNode()

            for (i in 0 until node.childNodes.length) {
                val child = node.childNodes.item(i)
                res.addStmt(buildStmtNode(child))
            }

            return res
        }
    }
}