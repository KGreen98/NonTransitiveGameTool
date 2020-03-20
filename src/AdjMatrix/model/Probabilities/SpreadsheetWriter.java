package AdjMatrix.model.Probabilities;

import AdjMatrix.model.Rules.GameRuleset;
import AdjMatrix.model.Rules.PlayerRuleset;
import AdjMatrix.model.Rules.TotalAphabet;
import org.ojalgo.scalar.RationalNumber;

import java.util.ArrayList;
import java.util.Arrays;

public class SpreadsheetWriter {
    private ArrayList<String> allStringsA;
    private ArrayList<String> allStringsB;
    private GameRuleset gr;

    public SpreadsheetWriter(GameRuleset gameRuleset, PlayerRuleset prA, PlayerRuleset prB) {
        gr = gameRuleset;
        TotalAphabet ta = new TotalAphabet();
        allStringsA = ta.listAllPossibleStrings2(gameRuleset, prA);
        allStringsB = ta.listAllPossibleStrings2(gameRuleset, prB);
    }

    public ArrayList<String> getAllStringsA() {
        return allStringsA;
    }

    public ArrayList<String> getAllStringsB() {
        return allStringsB;
    }

    public RationalNumber[][] getList(){
        int aSize = allStringsA.size();
        int bSize = allStringsB.size();
        RationalNumber[][] table = new RationalNumber[aSize][bSize];
        for (int row = 0; row < aSize; row++) {
            for (int col = 0; col < bSize; col++) {
                ArrayList<String> inputA = new ArrayList<>();
                inputA.add(allStringsA.get(row));
                ArrayList<String> inputB = new ArrayList<>();
                inputB.add(allStringsB.get(col));
                ProbabilityCalculator amc = new ProbabilityCalculator(inputA, inputB,  gr);
                table[row][col] = amc.finalProb();
            }
        }
        return table;
    }
}
