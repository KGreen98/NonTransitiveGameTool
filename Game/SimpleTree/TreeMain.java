package SimpleTree;

import java.util.List;

public class TreeMain {

    public static void main(String[] args) {
        String A = "001";
        String B = "100";

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