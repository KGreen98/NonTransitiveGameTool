package PennysTree;

public class PTreeMain {
    //TODO Make a GUI
    public static void main(String[] args) {
        String A = "011";
        String B = "010";
        int alphabetSize = 2;
        PTreeNode tree = new PTreeNode("", null);
        PTreeManip manip = new PTreeManip(alphabetSize);

        //List<PTreeNode> leaves = manip.getLeaves(tree);
        manip.run(A, B, tree);
    }
}