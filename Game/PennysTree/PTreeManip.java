package PennysTree;

import java.util.ArrayList;
import java.util.List;

public class PTreeManip {
    //TODO FIX THIS
    //Fix for any length of tree as should work regardless of tree length
    public void run(String A, String B, PTreeNode root) {
        PTreeManip manip = new PTreeManip();
        for (int n=0; n<((A.length() * 2) +2); n++){
            List<PTreeNode> list = getLeaves(root);
            //TODO change this
            //if (n >= A.length()) {
                for (PTreeNode a : list) {
                    System.out.println(a.writeAsString());
                    if (n > 0 && n < A.length()){
                        treeCutEarly(A,B,a);
                    }
                    if (n >= A.length()) {
                    treeCut(A, B, a);
                        if (a.writeAsString().length() > 1){
                            treeCutStingChar(A, B, a);
                        }
                    }
                }
                manip.addTreeLayer(root);
            }
//            else {
//                manip.addTreeLayer(root);
//            }

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
        System.out.println("            INCREASED TREE DEPTH:" + leaves.size());
    }

    public void treeCut(String A, String B, PTreeNode node){
        int bpA = bestProgress(A, node);
        int bpB = bestProgress(B, node);
        if (bpA == 0){
            node.setDeadend(true);
            System.out.println("This node matches A, cutting off");
        }
        //If node has 0 progress on A, consider reset history
        if (bpA == (A.length())){
            node.setDeadend(true);
            System.out.println("This node has nothing in common with A, cutting off");
        }
        //If node equals B
        if (bpB == 0){
            node.setDeadend(true);
            System.out.println("This node matches B, cutting off");
        }
    }

    public void treeCutStingChar(String A, String B, PTreeNode node){
        int bpA = bestProgress(A, node);
        int bpB = bestProgress(B, node);
        if (bpA == (A.length() - 1)){
            node.setDeadend(true);
            System.out.println("This node has matches an initial branch, 1char long towards A");
        }
        if (bpB == (A.length() - 1)){
            node.setDeadend(true);
            System.out.println("This node has matches an initial branch, 1char long towards B");
        }
    }

    public void treeCutEarly(String A, String B, PTreeNode node){
        int bpA = bestProgress2(A, node);
        int bpB = bestProgress2(B, node);
        System.out.println("TCE" + bpA + bpB + " vs " + node.findLengthFromLeaf());
        if ((bpA == node.findLengthFromLeaf()) && (bpB == node.findLengthFromLeaf())){
            node.setDeadend(true);
            System.out.println("Nah, no chance this node will lead to anything good.");
        }
    }

    //todo, fix for tree < string in length
    public int bestProgress(String tempA, PTreeNode node){
        //Returns distance from match, if returns 0, it matches.
        //If returns Length of A, 0 match.
        //If returns Length of A-1, 1 match.
        //Get total string from tree node up to root
        String a = node.writeAsString();
        //Most recent n in tree as String
        String subString = a.substring(a.length() - tempA.length());
        //System.out.println("Tree:" + subString + " :vs Input:" + A);
        String A = tempA;
        int maxCount = 0;
        while (A.length()>=0) {
            //System.out.println("    Tree:" + subString + " :vs Input:" + A);
            if (A.equals(subString)) {
                //System.out.println("BestP: " + maxCount);
                return maxCount;
            }
            subString = subString.substring(1);
            A = A.substring(0, A.length() - 1);
            maxCount++;
        }
        System.out.println("Compare ERROR");
        return 100000;
    }

    public int bestProgress2(String A, PTreeNode node){
        /**
         * Deals with tree when tree length < A length
         * If 0, A or B will be satisfied
         * If 1, Neither A or B are satisfied, cutoff seems appropriate
         */
        if (node.findLengthFromLeaf() < A.length()){
            A = A.substring(0, node.findLengthFromLeaf());
        }
        else{
            System.out.println("Somethings wrong here");
            return 99999999;
        }
        String a = node.writeAsString();
        //Most recent n in tree as String
        String subString = a.substring(a.length() - A.length());
        //System.out.println("Tree:" + subString + " :vs Input:" + A);
        int maxCount = 0;
        while (A.length()>=0) {
            //System.out.println("    Tree:" + subString + " :vs Input:" + A);
            if (A.equals(subString)) {
                return maxCount;
            }
            subString = subString.substring(1);
            A = A.substring(0, A.length() - 1);
            maxCount++;
        }
        System.out.println("Compare ERROR");
        return 100000;
    }
}