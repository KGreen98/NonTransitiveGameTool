package AdjMatrix.model.Probabilities;

public class Edge{
    private int startVertex;
    private int endVertex;
    private int odds;
    public Edge(int start, int end, int weight){
        this.startVertex = start;
        this.endVertex = end;
        this.odds = weight;
    }

    public int getStartVertex() {
        return startVertex;
    }

    public int getEndVertex() {
        return endVertex;
    }

    public int getOdds() {
        return odds;
    }
}