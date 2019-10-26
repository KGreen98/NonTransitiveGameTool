package PennysTree;

public class TreeMarking {
    private int nodeType;
    private PTreeNode equivalentNode;

    public TreeMarking(int i, PTreeNode node){
        nodeType = i;
        equivalentNode = node;
    }

    public int getNodeType() {
        return nodeType;
    }

    public PTreeNode getEquivalentNode() {
        return equivalentNode;
    }
}
