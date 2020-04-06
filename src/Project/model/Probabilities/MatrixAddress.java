package Project.model.Probabilities;

public class MatrixAddress {
    private int index;
    private double odds;

    public MatrixAddress(int i, double weight){
        index = i;
        odds = weight;
    }

    public int getIndex() {
        return index;
    }

    public double getOdds() {
        return odds;
    }
}
