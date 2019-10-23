package PennysTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PTreeManip {
    //Fix for any length of tree as should work regardless of tree length
    public ArrayList<String> patterns = new ArrayList<>();
    public ArrayList<Item> items = new ArrayList<Item>();
    public void run(String A, String B, PTreeNode root) {

        PTreeManip manip = new PTreeManip();
        for (int n=0; n<(A.length() * 2); n++){
        List<PTreeNode> list = getLeaves(root);
            for (PTreeNode a : list) {
                System.out.println(a.writeAsString());
                if (n > 0) {
                    if (n < A.length()){
                        addItem(A, B, a);
                        addPattern(a);
                    }
                    checkFullyResetHistory(A, B, a);
                    if (n >= A.length()) {

                        treeCut(A, B, a);
                        compareItem(A, B, a);
                        //checkLoop(A.length(), a);
                    }
                }
            }
            manip.addTreeLayer(root);
        }
        System.out.println("Analysing Tree");
        getEnds(root);

    }

    public void addPattern(PTreeNode node){
        String word = node.writeAsString();
        if (!patterns.contains(word)){
            patterns.add(word);
        }
    }

    //might be each or max length not sure
    public void checkLoop(int maxLength, PTreeNode node){
        String word = node.writeAsString();
        String word2 = word.substring(0, word.length() - maxLength);
        //System.out.println("str: " + word + "  substr: " + word2);
        if (word2.length() % 2 == 1){
            word2 = word2.substring(1);
        }
        if (word2.length() % 2 == 0 && word2.length()>0){
            String word3 = word2.substring(0, word2.length()/2);
            String word4 = word2.substring(word2.length()/2);
//            System.out.println("Word 1: " + word);
//            System.out.println("Word 2: " + word2);
//            System.out.println("Word 3: " + word3);
//            System.out.println("Word 4: " + word4);
            if (word3.equals(word4)){
                int revertDist = word2.length();
                PTreeNode nodePropper = node;
                System.out.println(revertDist);
                for (int i = 0; i < revertDist; i++){
                    nodePropper = nodePropper.getParent();
                }
                //todo look at XXXXAAA for any length X
                System.out.println(node.writeAsString() + " is actually " + nodePropper.writeAsString() + "  deleting their children");
                //nodePropper.setDeadend(true);
                //nodePropper.removeChildren();
                node.getParent().getLeftChild().setDeadend(true);
                node.getParent().getLeftChild().setTerminalMark(new TreeMarking(3, nodePropper));
                node.getParent().getRightChild().setDeadend(true);
                node.getParent().getRightChild().setTerminalMark(new TreeMarking(3, nodePropper));
                node.setDeadend(true);
                //node.setDeadend(true);
                //System.out.println("We've been here before");
            }
        }



    }

    public void addChildren(PTreeNode parent) {
        PTreeNode child0 = new PTreeNode(0, parent);
        PTreeNode child1 = new PTreeNode(1, parent);
        parent.setLeftChild(child0);
        parent.setRightChild(child1);
    }

    //Adds nodes with no children that are not marked as a deadend, to list of nodes
    public void checkLeaves(PTreeNode node, List<PTreeNode> list){
        if (!node.isDeadend()) {
            if (node.getLeftChild() != null) {
                checkLeaves(node.getLeftChild(), list);
            }
            if (node.getRightChild() != null) {
                checkLeaves(node.getRightChild(), list);
            }
            if ((node.getLeftChild() == null) && (node.getRightChild() == null)) {
                list.add(node);
            }
        }
    }

    public List<PTreeNode> getLeaves(PTreeNode node){
        List<PTreeNode> list = new ArrayList<>();
        checkLeaves(node, list);
        return list;
    }

    public void addTreeLayer(PTreeNode parent){
        List<PTreeNode> leaves = getLeaves(parent);
        for (PTreeNode a : leaves){
            addChildren(a);
        }
        System.out.println("            Extending tree, " + leaves.size() + " Branches");
        System.out.println("____________________________________________________________________________________________");
        System.out.println("____________________________________________________________________________________________");
        System.out.println("____________________________________________________________________________________________");
    }

    public void treeCut(String A, String B, PTreeNode node){
        int bpA = compare(A, node);
        int bpB = compare(B, node);
        if (bpA == 0){
            //checkLoop(A.length(), node);
            //to do something different if not a loop
            node.setDeadend(true);
            node.setTerminalMark(new TreeMarking(0));
            System.out.println("This node matches A, cutting off");
        }
        //If node has 0 progress on A, consider reset history
//        if (bpA == (A.length())){
//            node.setDeadend(true);
//            node.setTerminalMark(new TreeMarking(6));
//            System.out.println("This node has nothing in common with A, cutting off");
//        }
        //If node equals B
        if (bpB == 0){
            node.setDeadend(true);
            node.setTerminalMark(new TreeMarking(1));
            System.out.println("This node matches B, cutting off");
        }
    }

    public void treeCutStingChar(String A, String B, PTreeNode node){
        int bpA = compare(A, node);
        int bpB = compare(B, node);
        if (bpA == (A.length() - 1)){
            node.setDeadend(true);
            System.out.println("This node has matches an initial branch, 1char long towards A");
        }
        if (bpB == (A.length() - 1)){
            node.setDeadend(true);
            System.out.println("This node has matches an initial branch, 1char long towards B");
        }

    }

    public void checkFullyResetHistory(String A, String B, PTreeNode node){
        int bpA = compare(A, node);
        int bpB = compare(B, node);
        if ((bpA == node.findLengthFromLeaf()) && (bpB == node.findLengthFromLeaf())){
            node.setDeadend(true);
            node.setTerminalMark(new TreeMarking(2));
            System.out.println("No Progress made towards A or B, cutting off.");
        }
    }

    public int compare(String A, PTreeNode node){
        //Returns distance from match, if returns 0, it matches.
        //If returns Length of A, no match.
        //If returns Length of A-1, 1 match.
        //Get total string from tree node up to root

        //Closer to 0 it is, the closer the string is to being a match
        String nodeString = node.writeAsString();
        //Most recent n in tree as String
        String subA = A;

        //Set subA and NodeString to same length to compare.
        if (node.findLengthFromLeaf() < A.length()){
            subA = A.substring(0, node.findLengthFromLeaf());
        }
        if (node.findLengthFromLeaf() > A.length()) {
            nodeString = nodeString.substring(nodeString.length() - A.length());
        }

        //System.out.println("Tree:" + nodeString + " :vs Input:" + A);
        int maxCount = 0;
        while (subA.length()>=0) {
            if (subA.equals(nodeString)) {
                return maxCount;
            }
            nodeString = nodeString.substring(1);
            subA = subA.substring(0, subA.length() - 1);
            maxCount++;
        }
        System.out.println("Compare ERROR");
        return 100000;
    }

    public ArrayList<PTreeNode> getAllChildren(PTreeNode node){
        ArrayList<PTreeNode> list = new ArrayList<PTreeNode>();
        list.add(node);
        if (node.getLeftChild()!=null) {
            list.addAll(getAllChildren(node.getLeftChild()));
        }
        if (node.getRightChild()!=null) {
            list.addAll(getAllChildren(node.getRightChild()));
        }
        return list;
    }

    public void getEnds(PTreeNode root){
        for (PTreeNode a: getAllChildren(root)){
            if (!(a.getTerminalMark() == null)){
                System.out.println(a.writeAsString());
                a.getTerminalMark().PrintType();
            }
        }
    }

    public void addItem(String A, String B, PTreeNode node){
        Item a = createItem(A, B, node);
        items.add(a);
    }

    public Item createItem(String A, String B, PTreeNode node){
        int compA = compare(A, node);
        int compB = compare(B, node);
        Item a = new Item(compA, compB, node);
        return a;
    }

    public void compareItem(String A, String B, PTreeNode node){
        Item thisNode = createItem(A, B, node);
        for(Item it: items){
            String wStr = getPrecedingString(A, node);
            while (wStr.length()>0) {
                //System.out.println("    wStr:" + wStr + " it:" + it.getNode().writeAsString());
                if (it.getNode().writeAsString().equals(wStr)) {
                    //System.out.println("    wStr:" + wStr + " it:" + it.getNode().writeAsString());
                    if (it.getValA() == thisNode.getValA()) {
                        if (it.getValB() == thisNode.getValB()) {
                            System.out.println("Seems familiar:" + node.writeAsString() + " : " + it.getNode().writeAsString());
                            node.setDeadend(true);
                            node.setTerminalMark(new TreeMarking(9, it.getNode()));
                        }
                    }
                }
                wStr = wStr.substring(1);
            }
        }
    }

    //Returns the substring before last A length characters
    //A = 011
    //fullString = ABCDEFGH
    //returns: ABCDE
    public String getPrecedingString(String A, PTreeNode node){
        String fullString = node.writeAsString();
        return fullString.substring(0, fullString.length()-A.length()+1);
    }

    //compare items with item on list
    //iterate through items
    //if items match, that is a loop, cut tree etc


}