package PennysTree;

import java.util.List;

public class PTreeMain {
    //TODO Make a GUI
    public static void main(String[] args) {
        String A = "010";
        String B = "110";

        PTreeNode tree = new PTreeNode(3, null);
        PTreeManip manip = new PTreeManip();

        List<PTreeNode> leaves = manip.getLeaves(tree);
        manip.run(A, B, tree);
    }
}