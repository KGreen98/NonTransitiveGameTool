package AdjMatrix.model.Probabilities;

public class MatrixAddress {
    int index;
    double odds;
    public MatrixAddress(int i, double weight){
        this.index = i;
        this.odds = weight;
    }
}
