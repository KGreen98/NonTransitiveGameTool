package AdjMatrix.model.Probabilities;

public class WeightedEdge{
    String startVertex;
    String endVertex;
    int odds;
    public WeightedEdge(String startNode, String endNode, int weight){
        this.startVertex = startNode;
        this.endVertex = endNode;
        this.odds = weight;
    }
}