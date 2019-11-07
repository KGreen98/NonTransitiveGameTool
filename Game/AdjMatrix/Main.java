package AdjMatrix;

public class Main {
    public static void main(String[] args) {
        GraphBuilder gb = new GraphBuilder();
        AdjMatrixConstructor a = new AdjMatrixConstructor();
        a.printMatrix();
        a.recurse(0);
        a.depthFirst(0, 3);
    }
}

