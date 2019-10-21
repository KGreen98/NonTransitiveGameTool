package PennysTree;

public class PTreeNode {
    private int data;
    private PTreeNode parent = null;
    private PTreeNode left;
    private PTreeNode right;
    private boolean deadend;

    public PTreeNode(int data, PTreeNode p) {
        this.data = data;
        left = null;
        right = null;
        parent = p;
        deadend = false;
    }

    public int getValue(){
        return data;
    }

    public PTreeNode getParent() {
        return parent;
    }

    public int findLengthFromLeaf() {
        //length from leaf
        int length = 0;
        PTreeNode node = this;
        while (node.parent != null){
            length++;
            node = node.parent;
        }
        return length;
    }

    public PTreeNode getLeftChild() {
        return left;
    }
    public PTreeNode getRightChild() {
        return right;
    }
    public boolean isDeadend() { return deadend; }
    public void setDeadend(boolean deadend) { this.deadend = deadend; }
    public void setLeftChild(PTreeNode node) {
        left = node;
    }
    public void setRightChild(PTreeNode node) {
        right = node;
    }

    public String writeAsString(){
        //returns branch as string up to root
        String str = "";
        int n = this.findLengthFromLeaf();
        PTreeNode b = this;
        for (int i=0; i<n; i++) {
            str = str + b.getValue();
            b = b.getParent();
        }
        str = new StringBuilder(str).reverse().toString();
        return str;
    }
}