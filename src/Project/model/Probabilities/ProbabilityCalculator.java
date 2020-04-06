package Project.model.Probabilities;
import Project.model.Rules.GameRuleset;
import org.apache.commons.collections4.CollectionUtils;
import org.ojalgo.scalar.RationalNumber;

import java.util.*;

public class ProbabilityCalculator {
    private ArrayList<String> A;
    private ArrayList<String> B;

    private ArrayList<WeightedEdge> wEdges;
    private ArrayList<String> allNodes;
    private double[][] matrix;

    private ArrayList<Integer> eqnBuilder = new ArrayList<>();
    private ArrayList<Integer> nodesList = new ArrayList<>();

    private RationalNumber[][] coefficientsRational;
    private RationalNumber[] constantsRational;
    private GameRuleset gr;
    private int base;

    public ProbabilityCalculator(ArrayList<String> InputA, ArrayList<String> InputB, GameRuleset gameRuleset){
        gr = gameRuleset;
        A = InputA;
        B = InputB;
        base = gr.getBase();
        GraphBuilder gb = new GraphBuilder(A, B, gr);
        wEdges = gb.getWeightedEdgeList();
        allNodes = gb.getNodeList();
    }

    public RationalNumber finalProb(){
        if (A.equals(B) || CollectionUtils.containsAny(A, B)){
            return RationalNumber.valueOf(-1);
        } else {
            int vertex = allNodes.size();
            matrix = new double[vertex][vertex];
            build();
            //printMatrix();
            //printEdges();
            matrixTraversal();
            return eqnBuilderAnalyser();
        }
    }

    private void build() {
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (WeightedEdge e: wEdges){
            String a = e.getStartVertex();
            String b = e.getEndVertex();
            edgeList.add(new Edge(findNodePosition(a), findNodePosition(b), e.getOdds()));
        }

        for (int i = 0; i < edgeList.size(); i++) {
            Edge currentEdge = edgeList.get(i);
            int start = currentEdge.getStartVertex();
            int endVertex = currentEdge.getEndVertex();
            matrix[start][endVertex] = currentEdge.getOdds();
        }
    }


    private void matrixTraversal(){
        ArrayList<Integer>ListOfVisitedVariables = new ArrayList<Integer>();
        Queue<MatrixAddress> VariablesList = new LinkedList<>();
        VariablesList.add(new MatrixAddress(0,0.0));
        while (!VariablesList.isEmpty()){
            MatrixAddress node = VariablesList.peek();
            ArrayList<MatrixAddress> links = nextNodes(node.getIndex());
            ArrayList<MatrixAddress> nodeLinks = new ArrayList<>();
            nodeLinks.add(node);
            nodeLinks.addAll(links);
            for (MatrixAddress newNodes: nodeLinks) {
                eqnBuilder.add(newNodes.getIndex());
            }
            for (MatrixAddress i: links) {
                if (!VariablesList.contains(i) && !ListOfVisitedVariables.contains(i.getIndex())){
                    VariablesList.add(i);
                }
            }
            ListOfVisitedVariables.add(node.getIndex());
            VariablesList.remove();
        }
        nodesList.addAll(ListOfVisitedVariables);
    }

    private ArrayList<MatrixAddress> nextNodes(int index){
        ArrayList<MatrixAddress> inNodes = new ArrayList<>();
        for (int i = 0; i < matrix[index].length; i++){
            if (matrix[index][i] >= 1){
                inNodes.add(new MatrixAddress(i, matrix[index][i]));
            }
        }
        return inNodes;
    }

    private RationalNumber eqnBuilderAnalyser(){
        boolean possible = false;
        //set coefficients
        coefficientsBuilder(allNodes.size());
        for (WeightedEdge e:wEdges) {
            int indexStart = findNodePosition(e.getStartVertex());
            int indexEnd = findNodePosition(e.getEndVertex());
            RationalNumber nF = RationalNumber.of(e.getOdds(), gr.getDenominator());
            coefficientsRational[indexStart][indexEnd] = coefficientsRational[indexStart][indexEnd].subtract(nF);
        }
        //set constants
        constantsBuilder(allNodes.size());
        for (String goal: A) {
            for (int i = 0; i < allNodes.size(); i++) {
                if (allNodes.get(i).equals(goal)){
                    constantSetGoal(i);
                    possible = true;
                }
            }

        }
        if (possible) {
            LinearEquationSolver gs = new LinearEquationSolver(coefficientsRational, constantsRational);
            coefficientsPrinter();
            System.out.println("//");
            System.out.println(constantsRational.toString());
            return gs.rationalMatrix();
        } else {
            return RationalNumber.ZERO;
        }

    }

    private void coefficientsBuilder(int size){
        coefficientsRational = new RationalNumber[size][size];
        for (int i = 0; i < size; i++){
            Arrays.fill(coefficientsRational[i], RationalNumber.ZERO);
            coefficientsRational[i][i] = RationalNumber.valueOf(1);
        }
    }

    private void coefficientsPrinter(){
        System.out.println("Coefficients printer");
        int size = coefficientsRational.length;
        for (int i = 0; i < size; i++){
            System.out.println(Arrays.toString(coefficientsRational[i]));
        }
    }

    private void constantsBuilder(int size){
        constantsRational = new RationalNumber[size];
        Arrays.fill(constantsRational, RationalNumber.ZERO);
    }

    private void constantSetGoal(int index){
        constantsRational[index] = RationalNumber.ONE;
    }

    //______________________________

    private int findNodePosition(String a){
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).equals(a)){
                return i;
            }
        }
        System.out.println("ERROR, node not found");
        return -1;
    }

    //PRINT METHODS
    private void printMatrix(){
        System.out.println();
        System.out.println("PRINTING MATRIX");
        int vertex = allNodes.size()-1;
        System.out.print("/////");
        for (int i = 0; i <= vertex; i++) {
            int m = 3;
            int a = allNodes.get(i).length();
            System.out.print("-" + allNodes.get(i) + "  ");
            int b= m-a;
            for (int j=0; j < b; j++){
                System.out.print(" ");
            }
        }
        System.out.println();
        for (int i = 0; i <= vertex; i++) {
            System.out.print("-" + allNodes.get(i));
            int m = 5;
            int a = allNodes.get(i).length();
            int b= m-a;
            for (int j=0; j < b; j++){
                System.out.print(" ");
            }
            for (int j = 0; j <= vertex; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
        System.out.println("");
    }

    private void printEdges() {
        for (int i = 0; i < allNodes.size(); i++) {
            ArrayList<MatrixAddress> outNodes = nextNodes(i);
            if (outNodes.size() > 0){
                System.out.print("Node '" + allNodes.get(i) + "' is connected to: ");
                for (MatrixAddress n: outNodes) {
                    System.out.print(allNodes.get(n.getIndex()) + ", ");
                }
                System.out.println();
            }
        }
        System.out.println("");
    }
}
