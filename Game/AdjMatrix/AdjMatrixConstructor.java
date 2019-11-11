package AdjMatrix;

import sun.invoke.empty.Empty;

import java.util.*;

public class AdjMatrixConstructor {
    public ArrayList<String> nodes;
    public ArrayList<List<String>> edges;
    public ArrayList<ArrayList<Integer>> completedPaths = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> completedLoops = new ArrayList<>();
    public int[][] matrix;
    public int A;

    public AdjMatrixConstructor(){
        GraphBuilder gb = new GraphBuilder();
        nodes = gb.getNodeList();
        edges = gb.getEdgeList();
        A = gb.getAIndex();
        int vertex = nodes.size();
        matrix = new int[vertex][vertex];
        build();
        search(0, 5);
        analyseLists(A, 0);
    }

    public void build() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        for (int i = 0; i < nodes.size(); i++) {
            map.put(nodes.get(i), i);
        }
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (List<String> e: edges){
            String a = e.get(0);
            String b = e.get(1);
            edgeList.add(new Edge(map.get(a), map.get(b)));
        }

        for (int i = 0; i < edgeList.size(); i++) {
            Edge currentEdge = edgeList.get(i);
            int start = currentEdge.startVertex;
            int endVertex = currentEdge.endVertex;
            matrix[start][endVertex] = 1;
        }
        System.out.println(map);
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
        System.out.println(start);
        completedPaths.clear();
        completedLoops.clear();
        ArrayList<ArrayList<Integer>> openPaths = new ArrayList<>();
        ArrayList<Integer> startPath = new ArrayList<>();
        startPath.add(start);
        openPaths.add(startPath);
        while (!openPaths.isEmpty()){
            ArrayList<Integer> currentPath = openPaths.get(0);
            //for (ArrayList<Integer> path: openPaths){
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
        System.out.println("Important Nodes:" + nodesOfInterest);

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
        ArrayList<Integer> importantLoops = new ArrayList<>();
        for (Integer n: nodesOfInterest) {
            if (directALoops.contains(n)){
                importantLoops.add(n);
            }
        }
        System.out.println("Important Looping nodes:" + importantLoops);


        for (Integer l: importantLoops) {
            ArrayList<ArrayList<Integer>> nodeLoops = new ArrayList<>();
            //int nodeVal = importantLoops.get(l);
            for (ArrayList<Integer> cL: completedLoops) {
                if (finalNode(cL).equals(l)){
                    nodeLoops.add(cL);
                }
            }
            analyseNodeLoops(l, nodeLoops);
            //analyseNodeLoop(nodeVal, nodeLoops);
        }
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

    public void analyseNodeLoop(Integer l, ArrayList<ArrayList<Integer>> nodeLoops){
        System.out.println("Analyse NodeLoop");
        System.out.println(l);
        double total = 0;
        for (ArrayList<Integer>loop: nodeLoops) {
            for (int i = 0; i < loop.size(); i++){
                search(loop.get(i), 0);
            }
        }
        System.out.println(nodeLoops);
        System.out.println(total);
    }


    public void printMatrix(){
        System.out.println();
        System.out.println("PRINTING MATRIX");
        int vertex = nodes.size()-1;
        System.out.print("//// ");
        for (int i = 0; i <= vertex; i++) {
            int m = 3;
            int a = nodes.get(i).length();
            System.out.print("-" + nodes.get(i));
            int b= m-a;
            for (int j=0; j < b; j++){
                System.out.print(" ");
            }
        }
        System.out.println();
        for (int i = 0; i <= vertex; i++) {
            System.out.print("-" + nodes.get(i));
            int m = 5;
            int a = nodes.get(i).length();
            int b= m-a;
            for (int j=0; j < b; j++){
                System.out.print(" ");
            }
            for (int j = 0; j <= vertex; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }
}
