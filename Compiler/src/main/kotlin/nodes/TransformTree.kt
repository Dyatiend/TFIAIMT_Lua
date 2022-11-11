package nodes

object TransformTree {

    fun transform(rootNode : ChunkNode?) {
        if (rootNode?.block != null) {
            transform(rootNode.block)
        }
    }

    private fun transform(node : StmtSeqNode?) {
        var current = node?.first
        var previous : StmtNode? = null
        current?.let {
            while (current != null) {
                transform(current, previous, node)
                previous = current
                current = current!!.next
            }
        }
    }

    private fun transform(node : StmtNode?, prevNode: StmtNode?, seqNode: StmtSeqNode?) {
        when(node?.type) {
            StmtType.UNINITIALIZED -> {
//                transform(node.conditionExpr)
                transform(node.ifBlock)
            }
            StmtType.ASSIGNMENT -> {
                //TODO Походу не надо делать)))))
//                transform(node.varList)
//                transform(node.values)
            }
            StmtType.FUNCTION_CALL -> {
//                transform(node.functionCall)
            }
            StmtType.BREAK -> {

            }
            StmtType.DO_LOOP -> {
                transform(node.actionBlock)
            }
            StmtType.WHILE_LOOP -> {
//                transform(node.conditionExpr)
                transform(node.actionBlock)
            }
            StmtType.REPEAT_LOOP -> {
                transform(node.actionBlock)
//                transform(node.conditionExpr)
            }
            StmtType.IF -> {
//                transform(node.conditionExpr)
                transform(node.ifBlock)
                if(node.elseifSeq != null && node.elseifSeq?.first != null) {
                    transform(node.elseifSeq)
                }
                if(node.elseBlock != null) {
                    transform(node.elseBlock)
                }
            }
            StmtType.FOR -> {
//                transform(node.initialValue)
//                transform(node.conditionExpr)
                val initialValue = StmtNode.createAssignStmtNode(
                    ExprSeqNode.createVarListNode(
                        VarNode.createIdVarNode(node.ident)
                    ),
                    ExprSeqNode(node.initialValue!!)
                )
                if(prevNode != null) {
                    initialValue.next = node
                    prevNode.next = initialValue
                } else {
                    initialValue.next = node
                    seqNode?.first = initialValue
                }
                if(node.stepExpr != null) {
                    val stepExpr = StmtNode.createAssignStmtNode(
                        ExprSeqNode.createVarListNode(
                            VarNode.createIdVarNode(node.ident)
                        ),
                        ExprSeqNode(
                            ExprNode.createBinExprNode(
                                ExprType.PLUS,
                                ExprNode.createVarExprNode(
                                    VarNode.createIdVarNode(node.ident)
                                ),
                                node.stepExpr
                            )
                        )
                    )

                    if(node.actionBlock?.last == null) {
                        node.actionBlock?.first = stepExpr
                        node.actionBlock?.last = stepExpr
                    } else {
                        node.actionBlock?.last?.next = stepExpr
                        node.actionBlock?.last = stepExpr
                    }
                    node.stepExpr = null
                } else {
                    val stepExpr = StmtNode.createAssignStmtNode(
                        ExprSeqNode.createVarListNode(
                            VarNode.createIdVarNode(node.ident)
                        ),
                        ExprSeqNode(
                            ExprNode.createBinExprNode(
                                ExprType.PLUS,
                                ExprNode.createVarExprNode(
                                    VarNode.createIdVarNode(node.ident)
                                ),
                                ExprNode.createIntNumberExprNode(1)
                            )
                        )
                    )

                    if(node.actionBlock?.last == null) {
                        node.actionBlock?.first = stepExpr
                        node.actionBlock?.last = stepExpr
                    } else {
                        node.actionBlock?.last?.next = stepExpr
                        node.actionBlock?.last = stepExpr
                    }
                }
                node.type = StmtType.WHILE_LOOP
                transform(node.actionBlock)
            }
            StmtType.FOREACH -> {
                //TODO хз как тут преобразовать к while_loop
//                transform(node.identList)
//                transform(node.values)
                transform(node.actionBlock)
            }
            StmtType.FUNCTION_DEF -> {
                //transform(node.params)
                transform(node.actionBlock)
            }
            StmtType.VAR_DEF -> {
                //transform(node.identList)

                if(node.values != null) {
//                    transform(node.values)
                }
            }
            StmtType.RETURN -> {
//                if(node.values != null) {
//                    transform(node.values)
//                }
            }
            null -> {

            }
        }
    }
}