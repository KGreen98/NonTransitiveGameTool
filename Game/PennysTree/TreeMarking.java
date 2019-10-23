package PennysTree;

public class TreeMarking {
    private int nodeType;
    private PTreeNode equivalentNode;

    public TreeMarking(int i){
        nodeType = i;
    }

    public TreeMarking(int i, PTreeNode node){
        nodeType = i;
        equivalentNode = node;
    }

    public void PrintType(){
        if (nodeType == 0){
            System.out.println("Node == A");
        }
        else if (nodeType == 1){
            System.out.println("Node == B");
        }
        else if (nodeType == 2){
            System.out.println("Complete Reset");
        }
        else if (nodeType == 3){
            System.out.println("Loop here:" + equivalentNode.writeAsString());
        }
        else if (nodeType == 9){
            System.out.println("Looooooooop here:" + equivalentNode.writeAsString());
        }
        else{
            System.out.println("Something's wrong here");
        }
    }
}
