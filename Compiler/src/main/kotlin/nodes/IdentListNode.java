package nodes;

public class IdentListNode {
    IdentNode first;
    IdentNode last;

    public IdentListNode(String ID) {
        IdentNode newIndentNode = new IdentNode(ID);
        this.first = newIndentNode;
        this.last = newIndentNode;
    }

    public void addIdent(String ID) {
        IdentNode newIndentNode = new IdentNode(ID);
        this.last.next = newIndentNode;
        this.last = newIndentNode;
    }
}
