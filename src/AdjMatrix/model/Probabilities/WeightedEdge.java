package AdjMatrix.model.Probabilities;

public class WeightedEdge{
    private String startVertex;
    private String endVertex;
    private int odds;
    public WeightedEdge(String startNode, String endNode, int weight){
        this.startVertex = startNode;
        this.endVertex = endNode;
        this.odds = weight;
    }

    public String getStartVertex() {
        return startVertex;
    }

    public String getEndVertex() {
        return endVertex;
    }

    public int getOdds() {
        return odds;
    }
}