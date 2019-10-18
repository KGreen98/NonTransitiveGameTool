import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuffer;

public class MyTreeNode{
    private int data;
    private MyTreeNode parent = null;
    private MyTreeNode left;
    private MyTreeNode right;
    boolean deadend;


    public MyTreeNode(int data, MyTreeNode p) {
        this.data = data;
        left = null;
        right = null;
        parent = p;
        deadend = false;
    }

    public int getValue(){
        return data;
    }

    public MyTreeNode getParent() {
        return parent;
    }

    public int findlength() {
        //length from leaf
        int length = 0;
        MyTreeNode node = this;
        while (node.parent != null){
            length++;
            node = node.parent;
        }
        return length;
    }

    public MyTreeNode getLeftChild() {
        return left;
    }
    public MyTreeNode getRightChild() {
        return right;
    }

    public void setLeftChild(MyTreeNode node) {
        left = node;
    }

    public void setRightChild(MyTreeNode node) {
        right = node;
    }

    public void addChildren(MyTreeNode parent) {
        MyTreeNode child0 = new MyTreeNode(0, parent);
        MyTreeNode child1 = new MyTreeNode(1, parent);
        parent.setLeftChild(child0);
        parent.setRightChild(child1);
    }

    public List<MyTreeNode> getLeaves(MyTreeNode node){
        List<MyTreeNode> list = new ArrayList<>();
        checkLeaves(node, list);
        return list;
    }

    public void checkLeaves(MyTreeNode node, List<MyTreeNode> b){
        if (node.left!=null){
            checkLeaves(node.left, b);
        }
        if (node.right!=null){
            checkLeaves(node.right, b);
        }
        if ((node.left==null) && (node.right==null)){
            b.add(node);
        }

    }

    public void addTreeLayer(MyTreeNode parent){
        List<MyTreeNode> leaves = getLeaves(parent);
        for (MyTreeNode a : leaves){
            addChildren(a);
        }
        System.out.println("    Increased Tree Depth:");
    }

    public void writeStrings(MyTreeNode parent, int n){
        System.out.println("Write Strings");
        List<MyTreeNode> leaves = getLeaves(parent);
        for (MyTreeNode a : leaves){
            String str = "" + a.getValue();
            for (int i=0; i<(n-1); i++) {
                a = a.parent;
                str = str + a.getValue();
            }
            System.out.println(str);
        }
    }
}