import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuffer;

public class MyTreeNode{
    private int data;
    private MyTreeNode parent = null;
    private MyTreeNode left;
    private MyTreeNode right;
    public List<MyTreeNode> pubLeafList = new ArrayList<>();
    boolean deadend;
    boolean matchP2;


    public MyTreeNode(int data, MyTreeNode p) {
        this.data = data;
        left = null;
        right = null;
        parent = p;
        deadend = false;
        matchP2 = false;
    }

    public int getValue(){
        return data;
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

    public void getLeaves(MyTreeNode parent, List<MyTreeNode> b){
        if (parent.left!=null){
                getLeaves(parent.left, b);
        }
        if (parent.right!=null){
                getLeaves(parent.right, b);
        }
        if ((parent.left==null) && (parent.right==null)){
            b.add(parent);
        }
    }

    public void addTreeLayer(MyTreeNode parent){
        System.out.println("    _____");
        System.out.println("    Increasing Tree Depth");
        pubLeafList.clear();
        getLeaves(parent, pubLeafList);
        for (MyTreeNode a : pubLeafList){
            addChildren(a);
        }
    }

    public void add5Layers(MyTreeNode parent){
        for (int i=0; i<5; i++){
            System.out.println("_____");
            System.out.println(i);
            pubLeafList.clear();
            getLeaves(parent, pubLeafList);
            for (MyTreeNode a : pubLeafList){
                addChildren(a);
            }
        }
    }

    public void writeStrings(MyTreeNode parent, int n){
        System.out.println("_____");
        System.out.println("Write Strings");
        pubLeafList.clear();
        getLeaves(parent, pubLeafList);
        for (MyTreeNode a : pubLeafList){

            String str = "" + a.getValue();
            for (int i=0; i<(n-1); i++) {
                a = a.parent;
                str = str + a.getValue();
            }
            StringBuffer sbf = new StringBuffer(str);
            sbf = sbf.reverse();
            str = sbf.toString();
            System.out.println(str);
        }
    }

}

