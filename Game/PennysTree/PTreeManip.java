package PennysTree;

import java.util.ArrayList;
import java.util.List;

public class PTreeManip {
    //Fix for any length of tree as should work regardless of tree length
    public void run(String A, String B, PTreeNode root) {
        PTreeManip manip = new PTreeManip();
        for (int n=0; n<((A.length() * 2) +2); n++){
        List<PTreeNode> list = getLeaves(root);
            for (PTreeNode a : list) {
                System.out.println(a.writeAsString());
                if (n > 0 && n < A.length()){
                    treeCutEarly(A, B, a);
                }
                if (n >= A.length()) {
                treeCut(A, B, a);
                treeCutEarly(A, B, a);
                    if (a.writeAsString().length() > 1){
                        treeCutStingChar(A,B,a);
                    }
                }
            }
            manip.addTreeLayer(root);
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
        int bpA = bestProgress(A, node);
        int bpB = bestProgress(B, node);
        if ((bpA == node.findLengthFromLeaf()) && (bpB == node.findLengthFromLeaf())){
            node.setDeadend(true);
            System.out.println("Nah, no chance this node will lead to anything good.");
        }
    }

    //todo, fix for tree < string in length
    public int bestProgress(String A, PTreeNode node){
        //Returns distance from match, if returns 0, it matches.
        //If returns Length of A, no match.
        //If returns Length of A-1, 1 match.
        //Get total string from tree node up to root

        //Closer to 0 it is, the closer the string is to being a match
        String nodeString = node.writeAsString();
        //Most recent n in tree as String

        if (node.findLengthFromLeaf() < A.length()){
            A = A.substring(0, node.findLengthFromLeaf());
        }
        if (node.findLengthFromLeaf() > A.length()) {
            nodeString = nodeString.substring(nodeString.length() - A.length());
        }

        //System.out.println("Tree:" + nodeString + " :vs Input:" + A);
        int maxCount = 0;
        while (A.length()>=0) {
            if (A.equals(nodeString)) {
                return maxCount;
            }
            nodeString = nodeString.substring(1);
            A = A.substring(0, A.length() - 1);
            maxCount++;
        }
        System.out.println("Compare ERROR");
        return 100000;
    }
}