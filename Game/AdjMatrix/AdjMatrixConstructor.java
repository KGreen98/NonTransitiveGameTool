package AdjMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdjMatrixConstructor {

    public void build() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("X");
        lst.add("0");
        lst.add("00");
        lst.add("01");
        lst.add("001");
        lst.add("010");

        Map<String,Integer> map = new HashMap<String,Integer>();
        for (int i = 0; i < lst.size(); i++) {
            //X - 0
            map.put(lst.get(i), i);
        }

        int vertex = lst.size()-1;

        int[][] matrix = new int[vertex + 1][vertex + 1];
        ArrayList<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(map.get("X"), map.get("0")));
        edgeList.add(new Edge(map.get("X"), map.get("X")));
        edgeList.add(new Edge(map.get("0"), map.get("00")));
        edgeList.add(new Edge(map.get("0"), map.get("01")));
        edgeList.add(new Edge(map.get("00"), map.get("00")));
        edgeList.add(new Edge(map.get("00"), map.get("001")));
        edgeList.add(new Edge(map.get("01"), map.get("010")));
        edgeList.add(new Edge(map.get("01"), map.get("X")));

        for (int i = 0; i < edgeList.size(); i++) {
            Edge currentEdge = edgeList.get(i);
            int start = currentEdge.startVertex;
            int endVertex = currentEdge.endVertex;
            matrix[start][endVertex] = 1;
        }

        System.out.println(map);
        System.out.print(" \\   ");
        for (int i = 0; i <= vertex; i++) {
            System.out.print(lst.get(i) + "   ");
        }
        System.out.println();
        for (int i = 0; i <= vertex; i++) {
            System.out.print(lst.get(i) + "     ");
            for (int j = 0; j <= vertex; j++) {
                System.out.print(matrix[i][j] + "    ");
            }
            System.out.println();
        }
    }
}
