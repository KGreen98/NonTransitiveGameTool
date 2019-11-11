package AdjMatrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphBuilder {
    public ArrayList<List<String>> edgeList = new ArrayList<>();
    public ArrayList<String> nodeList = new ArrayList<>();
    public String A = "001";
    public String B = "010";
    Queue<String> q = new LinkedList<>();

    public GraphBuilder(){
        String a = "";
        //nodeList.add(a);
        q.add(a);
        while (!q.isEmpty()){
            stringComp(q.poll());
        }
        System.out.println(nodeList);
        System.out.println(edgeList);
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
        if (cmpA == 0){
            //System.out.println("A" + a);
            return;
        }
        if (cmpB == 0){
            //System.out.println("B" + a);
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

        if (a.length() > A.length()) {
            nodeString = nodeString.substring(nodeString.length() - A.length());
        }

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