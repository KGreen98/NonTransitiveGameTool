import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeMain {

    public static void main(String[] args) {
        String A = "001";
        String B = "1001";


        MyTreeNode tree = new MyTreeNode(3, null);
        tree.addTreeLayer(tree);
        tree.addTreeLayer(tree);
        tree.addTreeLayer(tree);

        TreeManip manip = new TreeManip();
        List<MyTreeNode> leaves = tree.getLeaves(tree);
        System.out.println(leaves.size());
        for (MyTreeNode a : leaves){
            manip.bestProgress(A,a);
        }
    }
}