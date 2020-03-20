package AdjMatrix.model.Probabilities;
import AdjMatrix.model.Rules.GameRuleset;
import org.ojalgo.scalar.RationalNumber;

import java.util.*;

public class ProbabilityCalculator {
    public ArrayList<String> A;
    public ArrayList<String> B;

    //public ArrayList<List<String>> edges;
    public ArrayList<WeightedEdge> wEdges;
    public ArrayList<String> allNodes;
    public double[][] matrix;

    public ArrayList<Integer> eqnBuilder = new ArrayList<>();
    public ArrayList<Integer> nodesList = new ArrayList<>();

    public RationalNumber[][] coefficientsRational;
    public RationalNumber[] constantsRational;
    public GameRuleset gr;
    public int base;

    public ProbabilityCalculator(ArrayList<String> InputA, ArrayList<String> InputB, GameRuleset gameRuleset){
        gr = gameRuleset;
        A = InputA;
        B = InputB;

        base = gr.getBase();
        GraphBuilder gb = new GraphBuilder(A, B, gr);
        //edges = gb.getEdgeList();
        wEdges = gb.getWeightedEdgeList();
        allNodes = gb.getNodeList();
    }

    public RationalNumber finalProb(){
        if (A.equals(B)){
            return RationalNumber.valueOf(-1);
        } else {
            int vertex = allNodes.size();
            matrix = new double[vertex][vertex];
            build();
            printMatrix();
            printEdges();
            matrixTraversal();
            return eqnBuilderAnalyser();
        }
    }

    public void build() {
        ArrayList<Edge> edgeList = new ArrayList<>();
//        for (List<String> e: edges){
//            String a = e.get(0);
//            String b = e.get(1);
//            edgeList.add(new Edge(findNodePosition(a), findNodePosition(b)));
//        }

        for (WeightedEdge e: wEdges){
            String a = e.startVertex;
            String b = e.endVertex;
            edgeList.add(new Edge(findNodePosition(a), findNodePosition(b), e.odds));
        }

        for (int i = 0; i < edgeList.size(); i++) {
            Edge currentEdge = edgeList.get(i);
            int start = currentEdge.startVertex;
            int endVertex = currentEdge.endVertex;
            matrix[start][endVertex] = currentEdge.odds;
        }
    }


    public void matrixTraversal(){
        ArrayList<Integer>ListOfVisitedVariables = new ArrayList<Integer>();
        Queue<MatrixAddress> VariablesList = new LinkedList<>();
        VariablesList.add(new MatrixAddress(0,0.0));
        while (!VariablesList.isEmpty()){
            MatrixAddress node = VariablesList.peek();
            ArrayList<MatrixAddress> links = nextNodes(node.index);
            ArrayList<MatrixAddress> nodeLinks = new ArrayList<>();
            nodeLinks.add(node);
            nodeLinks.addAll(links);
            for (MatrixAddress newNodes: nodeLinks) {
                eqnBuilder.add(newNodes.index);
            }
            for (MatrixAddress i: links) {
                if (!VariablesList.contains(i) && !ListOfVisitedVariables.contains(i.index)){
                    VariablesList.add(i);
                }
            }
            ListOfVisitedVariables.add(node.index);
            VariablesList.remove();
        }
        nodesList.addAll(ListOfVisitedVariables);
    }

    public ArrayList<MatrixAddress> nextNodes(int index){
        ArrayList<MatrixAddress> inNodes = new ArrayList<>();
        for (int i = 0; i < matrix[index].length; i++){
            if (matrix[index][i] >= 1){
                inNodes.add(new MatrixAddress(i, matrix[index][i]));
            }
        }
        return inNodes;
    }

    public int findIndex(int val){
        for (int i = 0; i < nodesList.size(); i++) {
            if (val == nodesList.get(i)) {
                return i;
            }
        }
        return -1;
    }


    public RationalNumber eqnBuilderAnalyser(){
        boolean possible = false;
        //set coefficients
        coefficientsBuilder(allNodes.size());
        for (WeightedEdge e:wEdges) {
            int indexStart = findNodePosition(e.startVertex);
            int indexEnd = findNodePosition(e.endVertex);
            RationalNumber nF = RationalNumber.of(e.odds, gr.getDenominator());
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
//        for (int i = 0; i < eqnBuilder.size(); i++) {
//            ArrayList<Integer> links = eqnBuilder.get(i);
//            int node = links.get(0);
//            //if node is the goal node, set constant to its index
//            for (String goal: A) {
//                if (allNodes.get(node).equals(goal)){
//                    constantSetGoal(i);
//                    possible = true;
//                }
//            }

//            for (int j = 1; j < links.size(); j++) {
//                int index = findIndex(links.get(j));
//                if (index == 0){
//                    double a = 1.0 + base - links.size() + 1.0;
//                    RationalNumber nF = RationalNumber.of((long) a, base);
//                    coefficientsRational[i][index] = coefficientsRational[i][index].subtract(nF);
//                } else{
//                    RationalNumber fraction = RationalNumber.of(1, base);
//                    coefficientsRational[i][index] = coefficientsRational[i][index].subtract(fraction);
//                }
//            }
        if (possible) {
            //System.out.println("Possible");
            //coefficientsPrinter();
            //System.out.println("Constants " + Arrays.toString(constantsRational));
            LinearEquationSolver gs = new LinearEquationSolver(coefficientsRational, constantsRational);
            return gs.rationalMatrix();
        } else {
            return RationalNumber.ZERO;
        }

    }

    public void coefficientsBuilder(int size){
        coefficientsRational = new RationalNumber[size][size];
        for (int i = 0; i < size; i++){
            Arrays.fill(coefficientsRational[i], RationalNumber.ZERO);
            //coefficientsRational[i][i] = RationalNumber.of(1, A.size());
            coefficientsRational[i][i] = RationalNumber.valueOf(1);
        }
    }

    public void coefficientsPrinter(){
        System.out.println("Coefficients printer");
        int size = coefficientsRational.length;
        for (int i = 0; i < size; i++){
            System.out.println(Arrays.toString(coefficientsRational[i]));
        }
    }

    //todo fix for multiple words
    public void constantsBuilder(int size){
        constantsRational = new RationalNumber[size];
        Arrays.fill(constantsRational, RationalNumber.ZERO);
    }

    public void constantSetGoal(int index){
        constantsRational[index] = RationalNumber.ONE;
    }

    //______________________________

    public int findNodePosition(String a){
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).equals(a)){
                return i;
            }
        }
        System.out.println("ERROR, node not found");
        return -1;
    }





    //PRINT METHODS
    public void printMatrix(){
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

    public void printEdges() {
        for (int i = 0; i < allNodes.size(); i++) {
            ArrayList<MatrixAddress> outNodes = nextNodes(i);
            if (outNodes.size() > 0){
                System.out.print("Node '" + allNodes.get(i) + "' is connected to: ");
                for (MatrixAddress n: outNodes) {
                    System.out.print(allNodes.get(n.index) + ", ");
                }
                System.out.println();
            }
        }
        System.out.println("");
    }
}
