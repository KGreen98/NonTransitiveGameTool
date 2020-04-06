package Project.model.Probabilities;

import Project.model.Rules.GameRuleset;
import Project.model.Rules.PlayerRuleset;
import Project.model.Rules.TotalAlphabet;
import org.ojalgo.scalar.RationalNumber;

import java.util.ArrayList;

public class SpreadsheetWriter {
    private ArrayList<ArrayList<String>> allStringsA;
    private ArrayList<ArrayList<String>> allStringsB;
    private GameRuleset gr;

    public SpreadsheetWriter(GameRuleset gameRuleset, PlayerRuleset prA, PlayerRuleset prB) {
        gr = gameRuleset;
        TotalAlphabet ta = new TotalAlphabet();
        allStringsA = ta.allPossibleInputs(gameRuleset, prA);
        allStringsB = ta.allPossibleInputs(gameRuleset, prB);
    }

    public ArrayList<ArrayList<String>> getAllStringsA() {
        return allStringsA;
    }

    public ArrayList<ArrayList<String>> getAllStringsB() {
        return allStringsB;
    }

    public RationalNumber[][] getList(){
        int aSize = allStringsA.size();
        int bSize = allStringsB.size();
        RationalNumber[][] table = new RationalNumber[aSize][bSize];
        for (int row = 0; row < aSize; row++) {
            for (int col = 0; col < bSize; col++) {
                ArrayList<String> inputA = new ArrayList<>();
                inputA.addAll(allStringsA.get(row));
                ArrayList<String> inputB = new ArrayList<>();
                inputB.addAll(allStringsB.get(col));
                ProbabilityCalculator amc = new ProbabilityCalculator(inputA, inputB,  gr);
                table[row][col] = amc.finalProb();
            }
        }
        return table;
    }
}
