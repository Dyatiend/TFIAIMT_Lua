package nodes;

public class IdentNode {
    public int id;

    public String ident;

    IdentNode next = null;

    public IdentNode(String Id) {
        this.id = Utils.getLastId();
        this.ident = Id;
    }
}
