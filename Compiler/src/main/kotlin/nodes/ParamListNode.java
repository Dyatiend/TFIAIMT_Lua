package nodes;

public class ParamListNode {
    public boolean hasVarArg;
    public IdentListNode list;

    public ParamListNode(IdentListNode ident_list, boolean var_arg) {
        this.hasVarArg = var_arg;
        this.list = ident_list;
    }
}
