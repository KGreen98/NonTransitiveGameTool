package AdjMatrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphBuilder {
    public ArrayList<List<String>> edgeList = new ArrayList<>();
    public ArrayList<String> nodeList = new ArrayList<>();
    public ArrayList<String> allNodeList = new ArrayList<>();
    public String A;
    public String B;
    Queue<String> q = new LinkedList<>();

    public GraphBuilder(String InputA, String InputB, int sequenceLength, int characterSetSize){
        String a = "";
        A = InputA;
        B = InputB;
        System.out.println("Getting all nodes");
        allNodeList = getAllNodes(sequenceLength, characterSetSize);
        System.out.println("All Nodes: " + allNodeList);
        q.add(a);
        while (!q.isEmpty()){
            stringComp(q.poll());
        }
        System.out.println(nodeList);
        System.out.println("Edges : " + edgeList);
    }

    //get list of all possible nodes given character set and sequence length (["", "0", "1", "00", "01", "10"...])
    public ArrayList<String> getAllNodes(int sequenceLength, int characterSetSize){
        ArrayList<String> allNodesList = new ArrayList<>();
        Queue<String> q = new LinkedList<>();
        String s = "";
        q.add(s);
        while (q.size() > 0){
            String head = q.poll();
            if (head.length() < sequenceLength) {
                ArrayList<String> nextNodes = getNextNodes(head, sequenceLength, characterSetSize);
                for (String node: nextNodes) {
                    q.add(node);
                }
            }
            allNodesList.add(head);
        }
        return allNodesList;
    }

    //returns list of next nodes from a given node (00 -> [000, 001])
    public ArrayList<String> getNextNodes(String nodeString, int sequenceLength, int characterSetSize){
        ArrayList<String> nextNodes = new ArrayList<>();
        for (Integer i = 0; i < characterSetSize; i++){
            String newnode = nodeString.concat(i.toString());
            nextNodes.add(newnode);
        }
        return nextNodes;
    }

    public ArrayList<String> getAllNodeList() {
        return allNodeList;
    }

    public ArrayList<List<String>> getEdgeList() {
        return edgeList;
    }

    public ArrayList<String> getNodeList() {
        return nodeList;
    }

    public int getAIndex(){
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).equals(A)){
                return i;
            }
        }
        return -1;
    }

    //String compare, if string is equivalent to existing node
    public void stringComp(String a){
        Integer cmpA = compare(A, a);
        Integer cmpB = compare(B, a);
        //check all current nodes, if node is equivalent to existing node
        //add edge from previous node to equivalent node and return
        if (a.length() > 0) {
            for (String node : nodeList) {
                int nodecmpA = compare(A, node);
                int nodecmpB = compare(B, node);
                if (cmpA.equals(nodecmpA) && cmpB.equals(nodecmpB)) {
                    //System.out.println("cutting off " + a);
                    List<String> l = new ArrayList<String>();
                    String sub = a.substring(0, a.length() - 1);
                    l.add(sub);
                    l.add(node);
                    edgeList.add(l);
                    return;
                }
            }
            List<String> l = new ArrayList<String>();
            String sub = a.substring(0, a.length() - 1);
            l.add(sub);
            l.add(a);
            edgeList.add(l);
        }
        nodeList.add(a);
        if (cmpA == 0 || cmpB == 0){
            return;
        }
        String ay = a;
        String a0 = a.concat("0");
        String a1 = ay.concat("1");
        q.add(a0);
        q.add(a1);
    }

    public int compare(String A, String a){
        String nodeString = a;
        //Most recent n in tree as String
        String subA = A;

        //Set subA and NodeString to same length to compare.
        if (a.length() < A.length()){
            subA = A.substring(0, A.length()-1);
            return 1 + compare(subA, a);
        }

//        if (a.length() > A.length()) {
//            nodeString = nodeString.substring(nodeString.length() - A.length());
//        }

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
}