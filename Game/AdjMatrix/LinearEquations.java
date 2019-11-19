package AdjMatrix;

public class LinearEquations {

    private double x;
    private double y;
    //CRAMER'S RULE
    public LinearEquations(double a, double b, double c, double d, double e, double f) {
        double det = ((a * d) - (b * c));
        if (det != 0){
            x = (((e * d) - (b * f)) / det);
            y = (((a * f) - (e * c)) / det);
            System.out.println("x = " + x);
            System.out.println("y = " + y);
        }
        else{
            System.out.println("ERROR DIVIDE BY 0, FOR LINEAR EQUATIONS, NO SOLUTIONS");
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
