package nodes;

public class FieldListNode {
    FieldNode first;
    FieldNode last;

    public FieldListNode(FieldNode field) {
        this.first = field;
        this.last = field;
    }

    public void addField(FieldNode field) {
        this.last.next = field;
        this.last = field;
    }
}
