package AdjMatrix;

public class Main {
    public static String A = "000";
    public static String B = "101";
    public static void main(String[] args) {
        AdjMatrixConstructor a = new AdjMatrixConstructor(A, B);
        System.out.println();
        System.out.println("Linear Equation test");
        LinearEquations l = new LinearEquations(1, -0.5, -0.5, 1, 0, 0.5);
    }
}
