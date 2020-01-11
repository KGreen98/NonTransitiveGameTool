package AdjMatrix;
import org.apache.commons.math3.linear.*;

import java.util.*;

public class AdjMatrixConstructor {
    public String A;
    public String B;

    public ArrayList<List<String>> edges;
    public ArrayList<String> allNodes;
    public double[][] matrix;
    public int indexA;

    public ArrayList<ArrayList<Integer>> completedPaths = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> completedLoops = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> eqnBuilder = new ArrayList<ArrayList<Integer>>();
    public ArrayList<Integer> nodesList = new ArrayList<>();
    public double[][] coefficients;
    public double[] constants;

    public AdjMatrixConstructor(String InputA, String InputB){
        A = InputA;
        B = InputB;
        GraphBuilder gb = new GraphBuilder(A, B, 3, 2);

        edges = gb.getEdgeList();
        allNodes = gb.getAllNodeList();
        indexA = findNodePosition(A);

        int vertex = allNodes.size();
        matrix = new double[vertex][vertex];
        build();
        printMatrix();
        printEdges();

        System.out.println("Searching Time");
        search(0, indexA);
        System.out.println("/-/-/");
        analyseLists(indexA, 0);
        System.out.println("Matrix traversal");
        matrixTraversal();
    }

    public void matrixTraversal(){
        ArrayList<Integer>ListOfVisitedVariables = new ArrayList<Integer>();
        Queue<Integer> VariablesList = new LinkedList<>();

        VariablesList.add(0);
        while (!VariablesList.isEmpty()){
            Integer node = VariablesList.peek();
            ArrayList<Integer> links = nextNodes(node);
            System.out.println(allNodes.get(node) + "|" + node + ":" + links);
            ArrayList<Integer> nodeLinks = new ArrayList<>();
            nodeLinks.add(node);
            nodeLinks.addAll(links);
            eqnBuilder.add(nodeLinks);
            for (int i: links) {
                if (!VariablesList.contains(i) && !ListOfVisitedVariables.contains(i)){
                    VariablesList.add(i);
                }
            }
            VariablesList.remove();
            ListOfVisitedVariables.add(node);
        }

        System.out.println("VisitedVar: " + ListOfVisitedVariables);
        nodesList.addAll(ListOfVisitedVariables);
        System.out.println(nodesList);

        eqnBuilderAnalyser();
    }

    public int findIndex(int val){
        for (int i = 0; i < nodesList.size(); i++) {
            if (val == nodesList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void eqnBuilderAnalyser(){
        coefficientsBuilder(eqnBuilder.size());
        for (int i = 0; i < eqnBuilder.size(); i++) {
            ArrayList<Integer> links = eqnBuilder.get(i);
            int node = links.get(0);
            if (allNodes.get(node).equals(A)){
                constantsBuilder(eqnBuilder.size(), i);
            }
            System.out.println(links);
            for (int j = 1; j < links.size(); j++) {
                int index = findIndex(links.get(j));
                //TODO fix for more vals
                coefficients[i][index] += -0.5;
            }
        }
        coefficientsPrinter();
        Math3Linears(coefficients, constants);
    }

    public void coefficientsBuilder(int size){
        coefficients = new double[size][size];
        for (int i = 0; i < size; i++){
            Arrays.fill(coefficients[i], 0);
            coefficients[i][i] = 1;
        }
    }

    public void coefficientsPrinter(){
        System.out.println("Coefficients printer");
        int size = coefficients.length;
        for (int i = 0; i < size; i++){
            System.out.println(Arrays.toString(coefficients[i]));
        }
    }

    public void constantsBuilder(int size, int index){
        System.out.println("Constants builder");
        constants = new double[size];
        Arrays.fill(constants, 0);
        constants[index] = 1;
        System.out.println(Arrays.toString(constants));
    }

    public void Math3Linears(double[][] coeff, double[] consta){
        RealMatrix coefficients = new Array2DRowRealMatrix(coeff,false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(consta, false);
        RealVector solution = solver.solve(constants);

        System.out.println("P(A):" + solution.getEntry(0));
        System.out.println();
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

    public void build() {
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (List<String> e: edges){
            String a = e.get(0);
            String b = e.get(1);
            edgeList.add(new Edge(findNodePosition(a), findNodePosition(b)));
        }

        for (int i = 0; i < edgeList.size(); i++) {
            Edge currentEdge = edgeList.get(i);
            int start = currentEdge.startVertex;
            int endVertex = currentEdge.endVertex;
            matrix[start][endVertex] = 1;
        }
    }

    public Integer finalNode(ArrayList<Integer> list){
        return list.get(list.size()-1);
    }

    public ArrayList<Integer> nextNodes(int index){
        ArrayList<Integer> inNodes = new ArrayList<>();
        for (int i = 0; i < matrix[index].length; i++){
            if (matrix[index][i] == 1){
                inNodes.add(i);
            }
        }
        return inNodes;
    }

    public void search(int start, int end){
        ArrayList<ArrayList<Integer>> openPaths = new ArrayList<>();
        ArrayList<Integer> startPath = new ArrayList<>();
        startPath.add(start);
        openPaths.add(startPath);
        while (!openPaths.isEmpty()){
            ArrayList<Integer> currentPath = openPaths.get(0);
            int node = finalNode(currentPath);
            List<Integer> enextNodes = nextNodes(node);
            if (enextNodes.size() == 0){
                ArrayList<Integer> newPath = (ArrayList<Integer>) currentPath.clone();
                completedPaths.add(newPath);
            }
            if (enextNodes.size() > 0){
                for (Integer n : enextNodes) {
                    if (currentPath.contains(n)) {
                        ArrayList<Integer> newPath = (ArrayList<Integer>) currentPath.clone();
                        newPath.add(n);
                        completedLoops.add(newPath);
                    }
                    else {
                        ArrayList<Integer> newPath = (ArrayList<Integer>) currentPath.clone();
                        newPath.add(n);
                        openPaths.add(newPath);
                    }
                }
            }
            openPaths.remove(0);
        }
        System.out.println("Loops: " + completedLoops);
        System.out.println("Paths" + completedPaths);
    }

    //eval node from loop, each path will reach either another loop, itself, A or B
    public void evalFromLoop(int start, ArrayList<Integer> loopList){
        ArrayList<ArrayList<Integer>> loopsPaths = new ArrayList<>();
        ArrayList<ArrayList<Integer>> openPaths = new ArrayList<>();
        ArrayList<Integer> startPath = new ArrayList<>();
        startPath.add(start);
        openPaths.add(startPath);
        while (!openPaths.isEmpty()){
            ArrayList<Integer> currentPath = openPaths.get(0);
            int node = finalNode(currentPath);
            List<Integer> enextNodes = nextNodes(node);
            if (enextNodes.size() == 0){
                if (node == indexA) {
                    ArrayList<Integer> newPath = (ArrayList<Integer>) currentPath.clone();
                    loopsPaths.add(newPath);
                }
            }
            if (enextNodes.size() > 0){
                for (Integer n : enextNodes) {
                    if (loopList.contains(n)) {
                        ArrayList<Integer> newPath = (ArrayList<Integer>) currentPath.clone();
                        newPath.add(n);
                        loopsPaths.add(newPath);
                    }
                    else {
                        ArrayList<Integer> newPath = (ArrayList<Integer>) currentPath.clone();
                        newPath.add(n);
                        openPaths.add(newPath);
                    }
                }
            }
            openPaths.remove(0);
        }
        System.out.println("    Loop's Paths" + loopsPaths);
    }

    public int longestListLength(ArrayList<ArrayList<Integer>> listOfLists){
        int max = 0;
        for (ArrayList<Integer> list: listOfLists) {
            if (list.size()>max){
                max = list.size();
            }
        }
        return max;
    }

    public void analyseLists(int AIndex, int sNode){
        //path portion
        ArrayList<ArrayList<Integer>> paths = completedPaths;
        ArrayList<ArrayList<Integer>> directAPaths = new ArrayList<>();
        for (ArrayList<Integer> path: paths) {
            int n = finalNode(path);
            if (n == AIndex){
                directAPaths.add((ArrayList<Integer>) path.clone());
            }
        }
        System.out.println("Important Paths:" + directAPaths);

        ArrayList<Integer> nodesOfInterest = new ArrayList<>();
        int len = longestListLength(directAPaths);
        for (int i = 0; i < len; i++){
            for (ArrayList<Integer> path: directAPaths) {
                if (path.size() > i){
                    Integer node = path.get(i);
                    if (!nodesOfInterest.contains(node)){
                        nodesOfInterest.add(node);
                    }
                }
            }
        }
        //System.out.println("Important Nodes:" + nodesOfInterest);

        //loops portion
        ArrayList<ArrayList<Integer>> loops = completedLoops;
        ArrayList<Integer> directALoops = new ArrayList<>();
        for (ArrayList<Integer> loop: loops) {
            int n = finalNode(loop);
            if (!directALoops.contains(n)){
                directALoops.add(n);
            }
        }

        //combined portion
        ArrayList<Integer> importantLoopNodes = new ArrayList<>();
        for (Integer n: nodesOfInterest) {
            if (directALoops.contains(n)){
                importantLoopNodes.add(n);
            }
        }
        System.out.println("Important Looping nodes:" + importantLoopNodes);
        //simpleEval(directAPaths, importantLoopNodes);
//        for (ArrayList<Integer> loopPaths: completedLoops) {
//            Collections.reverse(loopPaths);
//        }
//        pathFN(directAPaths, importantLoops);
    }

    public void simpleEval(ArrayList<ArrayList<Integer>> directAPaths, ArrayList<Integer> importantLoopNodes){
        double total = 0;
        if (importantLoopNodes.size() == 0){
            for (ArrayList<Integer> path: directAPaths) {
                total += 1/(Math.pow(2, (path.size()-1)));
            }
            System.out.println("simpleEval:" + total);
            return;
        }
        Collections.reverse(importantLoopNodes);
        for (Integer node: importantLoopNodes) {
            System.out.println("Searching node:" + allNodes.get(node));
            evalFromLoop(node, importantLoopNodes);
        }
        //go through each loop node until it is given a value, evaluate the loop node based on these values
        //list of nodes that have been evaluated and what they evaluate to,list of maps

        System.out.println("Finally now searching node:" + allNodes.get(0));
        evalFromLoop(0, importantLoopNodes);

        System.out.println("LoopNodes: " + importantLoopNodes);
        System.out.println("simpleEval:" + total);


    }

    public int differenceToEnd(ArrayList<Integer> directAPath, Integer loop){
        for (int i = 0; i < directAPath.size(); i++) {
            if (directAPath.get(i).equals(loop)){
                int value = directAPath.size() - i - 1;
                System.out.println("    " + value);
                return value;
            }
        }
        return -1000;
    }

    public int differenceToStart(ArrayList<Integer> directAPath, Integer loop){
        for (int i = 0; i < directAPath.size(); i++) {
            if (directAPath.get(i).equals(loop)){
                int value = i;
                System.out.println("    " + value);
                return value;
            }
        }
        return -1000;
    }

    public void pathFN(ArrayList<ArrayList<Integer>> directAPaths, ArrayList<Integer> importantLoops){
        HashMap<Integer, Double> evalMap = new HashMap<Integer, Double>();
        int PathsNo = directAPaths.size();
        System.out.println(PathsNo);
        for (int i = 0; i<directAPaths.size(); i++) {
            ArrayList<Integer> path = directAPaths.get(i);
            System.out.println("Path: " + path);

            for (int j = path.size()-1; j >= 0; j--) {
                if (importantLoops.contains(path.get(j))){
                    System.out.println(":::" + path.get(j));
                    double loop = loopFN(completedLoops, directAPaths, path.get(j));
                    int start = differenceToStart(directAPaths.get(i), j);
                    int end = differenceToEnd(directAPaths.get(i), j);
                    double sFraction = 1/(Math.pow(2, start));
                    double eFraction = 1/(Math.pow(2, end));
                    System.out.println("LoopEval " + loop);
                    System.out.println("startFraction " + sFraction);
                    System.out.println("endFraction " + eFraction);

                    double totalEval = (eFraction)/(1 - loop);
                    System.out.println("TotalEval: " + totalEval);
                    evalMap.put(j, totalEval);
                }
            }
        }
    }

    public double loopFN(ArrayList<ArrayList<Integer>> completedLoops, ArrayList<ArrayList<Integer>> directAPaths, Integer loopNode){
        System.out.println("LoopFN: " + loopNode);
        double self = 0;
        for (ArrayList<Integer> loopPaths: completedLoops) {
            if (loopPaths.get(0).equals(loopNode)){
                System.out.print(loopPaths);
                for(int i = 1; i < loopPaths.size(); i++){
                    if (loopPaths.get(i).equals(loopNode)){
                        self += 1/Math.pow(2, i);
                    }
                }
            }
        }
        System.out.println("Self: " + self);
        return self;
    }

    public void analyseNodeLoops(Integer l, ArrayList<ArrayList<Integer>> nodeLoops){
        System.out.println("Analyse NodeLoops");
        double total = 0;
        System.out.println(l);
        for (ArrayList<Integer>loop: nodeLoops) {
            for (int i = 0; i < loop.size(); i++){
                if (loop.get(i).equals(l)){
                    int expo = (loop.size() - i - 1);
                    System.out.println("    " + (loop.size() - i - 1));
                    total += 1/Math.pow(2, expo);
                    break;
                }
            }
        }
        System.out.println(nodeLoops);
        System.out.println(total);
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
            System.out.print("-" + allNodes.get(i));
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
        Math3Linears l = new Math3Linears(matrix);
    }

    public void printEdges() {
        for (int i = 0; i < allNodes.size(); i++) {
            ArrayList<Integer> outNodes = nextNodes(i);
            if (outNodes.size() > 0){
                System.out.print("Node '" + allNodes.get(i) + "' is connected to: ");
                for (Integer n: outNodes) {
                    System.out.print(allNodes.get(n) + ", ");
                }
                System.out.println();
            }
        }
        System.out.println("");
    }
}
