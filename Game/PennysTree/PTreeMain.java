package PennysTree;

import sun.invoke.empty.Empty;

import java.util.List;

public class PTreeMain {
    //TODO Make a GUI
    public static void main(String[] args) {
        String A = "001";
        String B = "010";

        PTreeNode tree = new PTreeNode("", null);
        PTreeManip manip = new PTreeManip();

        List<PTreeNode> leaves = manip.getLeaves(tree);
        manip.run(A, B, tree);
    }
}