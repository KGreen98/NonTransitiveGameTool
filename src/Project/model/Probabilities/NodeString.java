package Project.model.Probabilities;

public class NodeString {
    String nodeName;
    int odds;

    public NodeString(String node, int weight){
        this.nodeName = node;
        this.odds = weight;
    }
}
