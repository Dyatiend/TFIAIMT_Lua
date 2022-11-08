package nodes

import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.File
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

    fun printProgram(rootNode : ChunkNode?, file : File) {
        file.appendText("digraph G {\n")
        file.appendText("ID"+rootNode+" [label=\"program\"]\n")

        if (rootNode?.block != null) {
            printStmtSeqNode(rootNode.block, rootNode, file)
        }
        file.appendText("}")
    }

    private fun printStmtSeqNode(node : StmtSeqNode?, parent : Any?, file : File) {
        var current = node?.first
        current?.let {
            while (current != null) {
                printStmtNode(current, file)
                file.appendText("ID"+parent+"->ID"+current+"\n")
                current = current!!.next
            }
        }
    }

    private fun printStmtNode(node : StmtNode?, file : File) {
        when(node?.type) {
            StmtType.UNINITIALIZED -> {
                // Типа elseif ))))
                file.appendText("ID"+node+" [label=\"ELSEIF id "+node.id+"\"]\n")
                printExprNode(node.conditionExpr, file)
                file.appendText("ID"+node+"->ID"+node.conditionExpr+" [label=\"CONDITION\"]\n")

                file.appendText("ID"+node.ifBlock+" [label=\"BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.ifBlock+"\n")
                printStmtSeqNode(node.ifBlock, node.ifBlock, file)
            }
            StmtType.ASSIGNMENT -> {
                file.appendText("ID"+node+" [label=\"ASSIGNMENT id "+node.id+"\"]\n")
                file.appendText("ID"+node.varList+" [label=\"VARS\"]\n")
                file.appendText("ID"+node.values+" [label=\"VALUES\"]\n")
                file.appendText("ID"+node+"->ID"+node.varList+"\n")
                file.appendText("ID"+node+"->ID"+node.values+"\n")
                printExprSeqNode(node.varList, node.varList, file)
                printExprSeqNode(node.values, node.values, file)
            }
            StmtType.FUNCTION_CALL -> {
                file.appendText("ID"+node+" [label=\"_FUNCTION_CALL id "+node.id+"\"]\n")
                printExprNode(node.functionCall, file)
                file.appendText("ID"+node+"->ID"+node.functionCall+"\n")
            }
            StmtType.BREAK -> {
                file.appendText("ID"+node+" [label=\"BREAK id "+node.id+"\"]\n")
            }
            StmtType.DO_LOOP -> {
                file.appendText("ID"+node+" [label=\"DO_LOOP id "+node.id+"\"]\n")
                file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                printStmtSeqNode(node.actionBlock, node.actionBlock, file)
            }
            StmtType.WHILE_LOOP -> {
                file.appendText("ID"+node+" [label=\"WHILE_LOOP id "+node.id+"\"]\n")
                printExprNode(node.conditionExpr, file)
                file.appendText("ID"+node+"->ID"+node.conditionExpr+" [label=\"CONDITION\"]\n")
                file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                printStmtSeqNode(node.actionBlock, node.actionBlock, file)
            }
            StmtType.REPEAT_LOOP -> {
                file.appendText("ID"+node+" [label=\"REPEAT_LOOP id "+node.id+"\"]\n")
                file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                printStmtSeqNode(node.actionBlock, node.actionBlock, file)
                printExprNode(node.conditionExpr, file)
                file.appendText("ID"+node+"->ID"+node.conditionExpr+" [label=\"CONDITION\"]\n")
            }
            StmtType.IF -> {
                file.appendText("ID"+node+" [label=\"IF id "+node.id+"\"]\n")
                printExprNode(node.conditionExpr, file)
                file.appendText("ID"+node+"->ID"+node.conditionExpr+" [label=\"CONDITION\"]\n")

                file.appendText("ID"+node.ifBlock+" [label=\"IF BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.ifBlock+"\n")
                printStmtSeqNode(node.ifBlock, node.ifBlock, file)

                if(node.elseifSeq != null && node.elseifSeq?.first != null) {
                    file.appendText("ID"+node.elseifSeq+" [label=\"ELSEIF SEQ\"]\n")
                    file.appendText("ID"+node.elseifSeq+" [label=\"ELSEIF SEQ\"]\n")
                    file.appendText("ID"+node+"->ID"+node.elseifSeq+"\n")
                    printStmtSeqNode(node.elseifSeq, node.elseifSeq, file)
                }

                if(node.elseBlock != null) {
                    file.appendText("ID"+node.elseBlock+" [label=\"ELSE BLOCK\"]\n")
                    file.appendText("ID"+node+"->ID"+node.elseBlock+"\n")
                    printStmtSeqNode(node.elseBlock, node.elseBlock, file)
                }
            }
            StmtType.FOR -> {
                file.appendText("ID"+node+" [label=\"FOR id "+node.id+"\"]\n")
                file.appendText("ID"+node.id+" [label=\"ident "+node.ident+"\"]\n")
                file.appendText("ID"+node+"->ID"+node.id+" [label=\"VAR\"]\n")

                printExprNode(node.initialValue, file)
                file.appendText("ID"+node+"->ID"+node.initialValue+" [label=\"INIT VAL\"]\n")

                printExprNode(node.conditionExpr, file)
                file.appendText("ID"+node+"->ID"+node.conditionExpr+" [label=\"CONDITION\"]\n")

                if(node.stepExpr != null) {
                    printExprNode(node.stepExpr, file)
                    file.appendText("ID"+node+"->ID"+node.stepExpr+" [label=\"STEP EXPR\"]\n")
                }

                file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                printStmtSeqNode(node.actionBlock, node.actionBlock, file)
            }
            StmtType.FOREACH -> {
                file.appendText("ID"+node+" [label=\"FOREACH id "+node.id+"\"]\n")

                file.appendText("ID"+node.identList+" [label=\"IDENT LIST\"]\n")
                file.appendText("ID"+node+"->ID"+node.identList+"\n")
                printIdentListNode(node.identList, node.identList, file)

                file.appendText("ID"+node.values+" [label=\"VALUES\"]\n")
                file.appendText("ID"+node+"->ID"+node.values+"\n")
                printExprSeqNode(node.values, node.values, file)

                file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                printStmtSeqNode(node.actionBlock, node.actionBlock, file)
            }
            StmtType.FUNCTION_DEF -> {
                if(node.isLocal) {
                    file.appendText("ID"+node+" [label=\"LOCAL FUNC id "+node.id+"\"]\n")

                    file.appendText("ID"+node.id+" [label=\"name "+node.ident+"\"]\n")
                    file.appendText("ID"+node+"->ID"+node.id+" [label=\"name\"]\n")

                    file.appendText("ID"+node.params+" [label=\"PARAMS\"]\n")
                    file.appendText("ID"+node+"->ID"+node.params+"\n")
                    printParamListNode(node.params, node.params, file)

                    file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                    file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                    printStmtSeqNode(node.actionBlock, node.actionBlock, file)
                } else {
                    file.appendText("ID"+node+" [label=\"FUNC id "+node.id+"\"]\n")

                    file.appendText("ID"+node.id+" [label=\"name "+node.ident+"\"]\n")
                    file.appendText("ID"+node+"->ID"+node.id+" [label=\"name\"]\n")

                    file.appendText("ID"+node.params+" [label=\"PARAMS\"]\n")
                    file.appendText("ID"+node+"->ID"+node.params+"\n")
                    printParamListNode(node.params, node.params, file)

                    file.appendText("ID"+node.actionBlock+" [label=\"BLOCK\"]\n")
                    file.appendText("ID"+node+"->ID"+node.actionBlock+"\n")
                    printStmtSeqNode(node.actionBlock, node.actionBlock, file)
                }

            }
            StmtType.VAR_DEF -> {
                file.appendText("ID"+node+" [label=\"VAR DEF id "+node.id+"\"]\n")

                file.appendText("ID"+node.identList+" [label=\"IDENT LIST\"]\n")
                file.appendText("ID"+node+"->ID"+node.identList+"\n")
                printIdentListNode(node.identList, node.identList, file)

                if(node.values != null) {
                    file.appendText("ID"+node.values+" [label=\"VALUES\"]\n")
                    file.appendText("ID"+node+"->ID"+node.values+"\n")
                    printExprSeqNode(node.values, node.values, file)
                }
            }
            StmtType.RETURN -> {
                file.appendText("ID"+node+" [label=\"RETURN id "+node.id+"\"]\n")

                if(node.values != null) {
                    file.appendText("ID"+node.values+" [label=\"VALUES\"]\n")
                    file.appendText("ID"+node+"->ID"+node.values+"\n")
                    printExprSeqNode(node.values, node.values, file)
                }
            }

            else -> {}
        }
    }

    private fun printExprSeqNode(node : ExprSeqNode?, parent : Any?, file : File) {
        var current = node?.first
        current.let {
            while (current != null) {
                printExprNode(current, file)
                file.appendText("ID"+parent+"->ID"+current+"\n")
                current = current!!.next
            }
        }
    }

    private fun printExprNode(node : ExprNode?, file : File) {
        when(node?.type) {
            ExprType.UNINITIALIZED -> {

            }
            ExprType.NIL -> {
                file.appendText("ID"+node+" [label=\"NIL id "+node.id+"\"]\n")
            }
            ExprType.BOOLEAN -> {
                if(node.boolValue) {
                    file.appendText("ID"+node+" [label=\"BOOL true id "+node.id+"\"]\n")
                } else {
                    file.appendText("ID"+node+" [label=\"BOOL false id "+node.id+"\"]\n")
                }
            }
            ExprType.NUMBER -> {
                file.appendText("ID"+node+" [label=\"NUMBER "+node.numberValue+" id "+node.id+"\"]\n")
            }
            ExprType.STRING -> {
                file.appendText("ID"+node+" [label=\"STRING "+node.stringValue+" id "+node.id+"\"]\n")
            }
            ExprType.VAR_ARG -> {
                file.appendText("ID"+node+" [label=\"VAR_ARG id "+node.id+"\"]\n")
            }
            ExprType.VAR -> {
                file.appendText("ID"+node+" [label=\"VAR id "+node.id+"\"]\n")

                printVarNode(node.varNode, node, file)
            }
            ExprType.FUNCTION_CALL -> {
                file.appendText("ID"+node+" [label=\"FUNCTION CALL id "+node.id+"\"]\n")

                file.appendText("ID"+node.id+" [label=\"name "+node.ident+"\"]\n")
                file.appendText("ID"+node+"->ID"+node.id+" [label=\"name\"]\n")

                if(node.args != null) {
                    file.appendText("ID"+node.args+" [label=\"ARGS\"]\n")
                    file.appendText("ID"+node+"->ID"+node.args+"\n")
                    printExprSeqNode(node.args, node.args, file)
                }
            }
            ExprType.ADJUST -> {
                file.appendText("ID"+node+" [label=\"ADJUST id "+node.id+"\"]\n")

                printExprNode(node?.adjustedExpr, file)
                file.appendText("ID"+node+"->ID"+node.adjustedExpr+"\n")
            }
            ExprType.TABLE_CONSTRUCTOR -> {
                file.appendText("ID"+node+" [label=\"TABLE id "+node.id+"\"]\n")

                if(node.tableConstructor != null) {
                    printFieldListNode(node.tableConstructor, node, file)
                }
            }
            ExprType.PLUS -> {
                file.appendText("ID"+node+" [label=\"PLUS id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.MINUS -> {
                file.appendText("ID"+node+" [label=\"MINUS id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.MUL -> {
                file.appendText("ID"+node+" [label=\"MUL id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.DIV -> {
                file.appendText("ID"+node+" [label=\"DIV id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.FLOOR_DIV -> {
                file.appendText("ID"+node+" [label=\"FLOOR_DIV id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.POW -> {
                file.appendText("ID"+node+" [label=\"POW id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.XOR -> {
                file.appendText("ID"+node+" [label=\"XOR id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.MOD -> {
                file.appendText("ID"+node+" [label=\"MOD id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.BIT_AND -> {
                file.appendText("ID"+node+" [label=\"BIT_AND id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.BIT_OR -> {
                file.appendText("ID"+node+" [label=\"BIT_OR id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.CONCAT -> {
                file.appendText("ID"+node+" [label=\"CONCAT id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.LESS -> {
                file.appendText("ID"+node+" [label=\"LESS id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.LE -> {
                file.appendText("ID"+node+" [label=\"LE id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.GREATER -> {
                file.appendText("ID"+node+" [label=\"GREATER id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.GE -> {
                file.appendText("ID"+node+" [label=\"GE id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.EQUAL -> {
                file.appendText("ID"+node+" [label=\"EQUAL id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.NOT_EQUAL -> {
                file.appendText("ID"+node+" [label=\"NOT_EQUAL id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.LOG_AND -> {
                file.appendText("ID"+node+" [label=\"LOG_AND id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.LOG_OR -> {
                file.appendText("ID"+node+" [label=\"LOG_OR id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")

                printExprNode(node.secondOperand, file)
                file.appendText("ID"+node+"->ID"+node.secondOperand+" [label=\"SECOND\"]\n")
            }
            ExprType.UNARY_MINUS -> {
                file.appendText("ID"+node+" [label=\"UNARY_MINUS id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")
            }
            ExprType.NOT -> {
                file.appendText("ID"+node+" [label=\"NOT id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")
            }
            ExprType.LEN -> {
                file.appendText("ID"+node+" [label=\"LEN id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")
            }
            ExprType.BIT_NOT -> {
                file.appendText("ID"+node+" [label=\"BIT_NOT id "+node.id+"\"]\n")

                printExprNode(node.firstOperand, file)
                file.appendText("ID"+node+"->ID"+node.firstOperand+" [label=\"FIRST\"]\n")
            }

            else -> {}
        }
    }

    private fun printVarItemNode(node : VarItemNode?, file : File) {
        when(node?.type) {
            VarType.UNINITIALIZED -> {

            }
            VarType.IDENT -> {
                file.appendText("ID"+node+" [label=\"var_item type IDENT id "+node.id+"\"]\n")
                file.appendText("ID"+node.id+" [label=\"ident "+node.ident+"\"]\n")
                file.appendText("ID"+node+"->ID"+node.id+"\n")
            }
            VarType.VAR -> {
                if(node.isMapKey) {
                    file.appendText("ID"+node+" [label=\"var_item type VAR KEY id "+node.id+"\"]\n")
                    file.appendText("ID"+node.id+" [label=\"ident key "+node.ident+"\"]\n")
                    file.appendText("ID"+node+"->ID"+node.id+"\n")
                } else {
                    file.appendText("ID"+node+" [label=\"var_item type VAR EXPR id "+node.id+"\"]\n")
                    printExprNode(node.secondExpr, file)
                    file.appendText("ID"+node+"->ID"+node.secondExpr+"\n")
                }
            }
            VarType.FUNCTION_CALL -> {
                if(node.isMapKey) {
                    file.appendText("ID"+node+" [label=\"var_item type FUNC CALL KEY id "+node.id+"\"]\n")
                    printExprNode(node.firstExpr, file)
                    file.appendText("ID"+node.id+" [label=\"ident "+node.ident+"\"]\n")
                    file.appendText("ID"+node+"->ID"+node.firstExpr+" [label=\"fun call\"]\n")
                    file.appendText("ID"+node+"->ID"+node.id+" [label=\"key ident\"]\n")
                } else {
                    file.appendText("ID"+node+" [label=\"var_item type FUNC CALL EXPR id "+node.id+"\"]\n")
                    printExprNode(node.firstExpr, file)
                    printExprNode(node.secondExpr, file)
                    file.appendText("ID"+node+"->ID"+node.firstExpr+" [label=\"fun call\"]\n")
                    file.appendText("ID"+node+"->ID"+node.secondExpr+" [label=\"key expr\"]\n")
                }
            }
            VarType.ADJUSTED_EXPR -> {
                if(node.isMapKey) {
                    file.appendText("ID"+node+" [label=\"var_item type ADJUSTED EXPR KEY id "+node.id+"\"]\n")
                    printExprNode(node.firstExpr, file)
                    file.appendText("ID"+node.id+" [label=\"ident "+node.ident+"\"]\n")
                    file.appendText("ID"+node+"->ID"+node.firstExpr+" [label=\"expr\"]\n")
                    file.appendText("ID"+node+"->ID"+node.id+" [label=\"key ident\"]\n")
                } else {
                    file.appendText("ID"+node+" [label=\"var_item type ADJUSTED EXPR EXPR id "+node.id+"\"]\n")
                    printExprNode(node.firstExpr, file)
                    printExprNode(node.secondExpr, file)
                    file.appendText("ID"+node+"->ID"+node.firstExpr+" [label=\"expr\"]\n")
                    file.appendText("ID"+node+"->ID"+node.secondExpr+" [label=\"key expr\"]\n")
                }
            }

            else -> {}
        }
    }

    private fun printVarNode(node : VarNode?, parent : Any?, file : File) {
        var current = node?.first
        current.let {
            while (current != null) {
                printVarItemNode(current, file)
                file.appendText("ID"+parent+"->ID"+current+"\n")
                current = current!!.next
            }
        }
    }

    private fun printIdentNode(node : IdentNode?, file : File) {
        file.appendText("ID"+node+" [label=\"ident "+node?.ident+" id "+node?.id+"\"]\n")
    }

    private fun printIdentListNode(node : IdentListNode?, parent : Any?, file : File) {
        var current = node?.first
        current.let {
            while (current != null) {
                printIdentNode(current, file)
                file.appendText("ID"+parent+"->ID"+current+"\n")
                current = current!!.next
            }
        }
    }

    private fun printParamListNode(node : ParamListNode?, parent : Any?, file : File) {
        printIdentListNode(node?.list, parent, file)
        if(node!!.hasVarArg) {
            file.appendText("ID"+node+" [label=\"var_arg\"]\n")
            file.appendText("ID"+parent+"->ID"+node+"\n")
        }
    }

    private fun printFieldNode(node : FieldNode?, file : File) {
        if(node?.ident != null) {
            file.appendText("ID"+node+" [label=\"field type IDENT id "+node.id+"\"]\n")
            file.appendText("ID"+node.id+" [label=\"ident "+node.ident+"\"]\n")
            printExprNode(node.value, file)
            file.appendText("ID"+node+"->ID"+node.id+" [label=\"ident\"]\n")
            file.appendText("ID"+node+"->ID"+node.id+" [label=\"val\"]\n")
        }
        else if(node?.key != null) {
            file.appendText("ID"+node+" [label=\"field type KEY id "+node.id+"\"]\n")
            printExprNode(node.key, file)
            printExprNode(node.value, file)
            file.appendText("ID"+node+"->ID"+node.key+" [label=\"key\"]\n")
            file.appendText("ID"+node+"->ID"+node.value+" [label=\"val\"]\n")
        }
        else {
            file.appendText("ID"+node+" [label=\"field type EXPR id "+node?.id+"\"]\n")
            printExprNode(node?.value, file)
            file.appendText("ID"+node+"->ID"+node?.value+"\n")
        }
    }

    private fun printFieldListNode(node : FieldListNode?, parent : Any?, file : File) {
        var current = node?.first
        current.let {
            while (current != null) {
                printFieldNode(current, file)
                file.appendText("ID"+parent+"->ID"+current+"\n")
                current = current!!.next
            }
        }
    }
}