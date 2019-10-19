package PennysTree;

import java.util.List;

public class PTreeMain {

    public static void main(String[] args) {
        String A = "010";
        String B = "001";
        //A = new StringBuilder(A).reverse().toString();
        //B = new StringBuilder(B).reverse().toString();

        PTreeNode tree = new PTreeNode(3, null);
        PTreeManip manip = new PTreeManip();

        List<PTreeNode> leaves = manip.getLeaves(tree);
        manip.run(A, B, tree);
    }
}