package PennysTree;

import java.util.ArrayList;

public class PTreeNode {
    private String data;
    private PTreeNode parent = null;
    private ArrayList<PTreeNode> children = null;
    private TreeMarking terminalMark;
    private boolean deadend;

    public PTreeNode(String data, PTreeNode p) {
        this.data = data;
        children = new ArrayList<PTreeNode>();
        parent = p;
        deadend = false;
        terminalMark = null;
    }

    public String getValue(){
        return data;
    }

    public PTreeNode getParent() {
        return parent;
    }

    public ArrayList<PTreeNode> getChildren(){
        return children;
    }

    public PTreeNode getChild(int i){
        return getChildren().get(i);
    }
//
//    public PTreeNode getRoot(){
//        PTreeNode node = this;
//        while (node.parent != null){
//            node = node.parent;
//        }
//        return node;
//    }

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

    public void setChild(PTreeNode node) {
        children.add(node);
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
        if (str.length() > 0) {
            str = new StringBuilder(str).reverse().toString();
        }
        return str;
    }

    public String writeAsStringWithRoot(){
        //returns branch as string up to root
        String str = writeAsString();
        if (str.equals("")){
            str = "root";
        }
        return str;
    }

    public TreeMarking getTerminalMark() {
        return terminalMark;
    }

    public void setTerminalMark(TreeMarking terminalMark) {
        this.terminalMark = terminalMark;
    }

    public String nodeValue(){
        if (!isDeadend()){
            return "Not a deadend";
        }
        if (terminalMark.equals(null)){
            return "Not marked as a terminal node";
        }
        if (terminalMark.getNodeType() == 0){
            return "Node == A";
        }
        else if (terminalMark.getNodeType() == 1){
            return "Node == B";
        }
        else if (terminalMark.getNodeType() == 3){
            return "Loop here:" + terminalMark.getEquivalentNode().writeAsString();
        }
        else{
            return "ERROR";
        }
    }

    public boolean isDeadend() {
        return deadend;
    }

    public void setDeadend(boolean deadend) {
        this.deadend = deadend;
    }
}