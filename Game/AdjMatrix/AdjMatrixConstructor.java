package AdjMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjMatrixConstructor {
    public ArrayList<String> nodes;
    public ArrayList<List<String>> edges;
    public int[][] matrix;

    public AdjMatrixConstructor(){
        GraphBuilder gb = new GraphBuilder();
        nodes = gb.getNodeList();
        edges = gb.getEdgeList();
        int vertex = nodes.size();
        matrix = new int[vertex][vertex];
        build();
    }

    public void build() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        for (int i = 0; i < nodes.size(); i++) {
            //X - 0
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

    public void search(int start, int end){
        ArrayList<Integer> visitedNodes;
        ArrayList<Integer> loopNodes;

        //list of visited nodes
        //if node is visited again, node becomes a loop node, cut off here.
        //Identify correct node A and B, no out nodes anyway...
        //should result in shortest(only) path, and a list of loop nodes for further traversal

    }

    //public loop traverser
    //each loop is traversed in order
    //along the path, mark if the path looks at already travesed node
    //correct path to A


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
