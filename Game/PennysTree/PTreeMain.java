package PennysTree;

import java.util.List;

public class PTreeMain {
    //TODO Make a GUI
    public static void main(String[] args) {
        String A = "01000";
        String B = "00100";

        PTreeNode tree = new PTreeNode(3, null);
        PTreeManip manip = new PTreeManip();

        List<PTreeNode> leaves = manip.getLeaves(tree);
        manip.run(A, B, tree);
    }
}