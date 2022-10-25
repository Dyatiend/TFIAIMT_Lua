package nodes;

public class Utils {
    private static int LAST_ID = 0;

    static public int getLastId() {
        return LAST_ID++;
    }

}
