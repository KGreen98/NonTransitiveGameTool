import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeMain {

    public static void main(String[] args) {
	    String A = "001";
	    String B = "100";

        MyTreeNode tree = new MyTreeNode(3, null);

        tree.addTreeLayer(tree);
        tree.writeStrings(tree, 1);

        tree.addTreeLayer(tree);
        tree.writeStrings(tree, 2);

        tree.addTreeLayer(tree);
        tree.writeStrings(tree, 3);
    }
}
