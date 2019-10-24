package PennysTree;

public class Item {
    private int valA;
    private int valB;
    private PTreeNode code;

    public Item(int compA, int compB, PTreeNode node) {
        this.valA = compA;
        this.valB = compB;
        this.code = node;
    }

    public int getValA() {
        return valA;
    }

    public int getValB() {
        return valB;
    }

    public PTreeNode getNode() {
        return code;
    }
    public void printItem(){
        System.out.println("CompareA: " + valA + ", CompareB: " + valB + ", Nodestr:" + getNode().writeAsString());
    }
}
