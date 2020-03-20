package AdjMatrix.model.Probabilities;

import AdjMatrix.model.Rules.GameRuleset;

import java.util.*;

public class GraphBuilder {
    public ArrayList<List<String>> edgeList = new ArrayList<>();
    public ArrayList<WeightedEdge> wEdgeList = new ArrayList<>();
    public ArrayList<String> nodeList = new ArrayList<>();
    private ArrayList<String> A;
    private ArrayList<String> B;
    private ArrayList<String> allInputs = new ArrayList<>();
    private Queue<String> unvisitedNodeQueue = new LinkedList<>();
    private Queue<NodeString> wUnvisitedNodeQueue = new LinkedList<>();
    private GameRuleset gr;

    //Produces graph based on state transitions
    public GraphBuilder(ArrayList<String> InputA, ArrayList<String> InputB, GameRuleset gameRuleset){
        A = InputA;
        B = InputB;
        gr = gameRuleset;

        //all Inputs combines both user inputs
        allInputs.addAll(InputA);
        allInputs.addAll(InputB);
        System.out.println(allInputs);
//        String EmptyString = "";
//        unvisitedNodeQueue.add(EmptyString);
//        while (!unvisitedNodeQueue.isEmpty()){
//            stringCompareAll(unvisitedNodeQueue.poll());
//        }
        wUnvisitedNodeQueue.add(new NodeString("",0));
        while (!wUnvisitedNodeQueue.isEmpty()){
            stringCompareAll(wUnvisitedNodeQueue.poll());
        }
    }

    public ArrayList<List<String>> getEdgeList() {
        return edgeList;
    }
    public ArrayList<WeightedEdge> getWeightedEdgeList() {
        return wEdgeList;
    }
    public ArrayList<String> getNodeList() {
        return nodeList;
    }

    //String compare, if string is equivalent to existing node
    public void stringCompareAll(NodeString seq){
        //check all current nodes, if node is equivalent to existing node
        //add edge from previous node to equivalent node and return
        String sequence = seq.nodeName;
        if (sequence.length() > 0) {
            ArrayList<Integer> r = compareAll(allInputs, sequence);
            for (String node : nodeList) {
                //System.out.println(sequence + "||" +  node);
                ArrayList<Integer> r2 = compareAll(allInputs, node);
                if (r.equals(r2)) {
                    //node is duplicate
                    //construct edge
                    List<String> l = new ArrayList<String>();
                    String sub = sequence.substring(0, sequence.length() - 1);
                    l.add(sub);
                    l.add(node);
                    //new edge is previous node -> node it is a duplicate of
                    edgeList.add(l);
                    WeightedEdge edgeDetails = new WeightedEdge(sub, node, seq.odds);
                    wEdgeList.add(edgeDetails);
                    return;
                }
            }
            //if node is not a duplicate, add new edge
            List<String> l = new ArrayList<String>();
            String sub = sequence.substring(0, sequence.length() - 1);
            l.add(sub);
            l.add(sequence);
            edgeList.add(l);

            WeightedEdge edgeDetails = new WeightedEdge(sub, sequence, seq.odds);
            wEdgeList.add(edgeDetails);
        }
        nodeList.add(sequence);

        //add children of sequence iff is not a duplicate and not a goal node
        //TODO fix for different ratios
        if (!allInputs.contains(sequence)){
            char[] base = gr.getCharSet();
            for (Character a: base) {
                String ay = sequence;
                String a0 = ay.concat(a.toString());
                wUnvisitedNodeQueue.add(new NodeString(a0, gr.getOddsFromChar(a)));
                //unvisitedNodeQueue.add(a0);
            }
         }
    }

    public ArrayList<Integer> compareAll(ArrayList<String> AllInputs, String sequence){
        String nodeString = sequence;
        ArrayList<Integer> list = new ArrayList<>();
        //Most recent n in tree as String
        for (String input: AllInputs) {
            list.add(compare(input, sequence));
        }
        return list;
    }

    public int compare(String A, String sequence){
        String nodeString = sequence;
        //Most recent n in tree as String
        String subA = A;

        //Set subA and NodeString to same length to compare.
        if (sequence.length() < A.length()){
            subA = A.substring(0, A.length()-1);
            return 1 + compare(subA, sequence);
        }

        if (sequence.length() > A.length()) {
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