package AdjMatrix.model.Probabilities;

import org.ojalgo.matrix.RationalMatrix;
import org.ojalgo.scalar.RationalNumber;

public class LinearEquationSolver {
    private RationalNumber[][] coefficients;
    private RationalNumber[] constants;

    public LinearEquationSolver(RationalNumber[][] coefficientsRational, RationalNumber[] constantsRational) {
        coefficients = coefficientsRational;
        constants = constantsRational;

    }

    public RationalNumber rationalMatrix() {
        RationalMatrix.Factory rationalMatrixFactory = RationalMatrix.FACTORY;
        RationalMatrix rMatrix = rationalMatrixFactory.rows(coefficients);
        RationalMatrix rMatrixCon = rationalMatrixFactory.columns(constants);
        RationalMatrix ansRat = rMatrix.solve(rMatrixCon);
        return ansRat.get(0);
    }
}