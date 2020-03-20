package AdjMatrix.model.Probabilities;

public class Edge{
    int startVertex;
    int endVertex;
    int odds;
    public Edge(int start, int end, int weight){
        this.startVertex = start;
        this.endVertex = end;
        this.odds = weight;
    }
}