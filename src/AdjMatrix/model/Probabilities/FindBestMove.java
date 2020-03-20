package AdjMatrix.model.Probabilities;

import AdjMatrix.model.Rules.GameRuleset;
import AdjMatrix.model.Rules.PlayerRuleset;
import AdjMatrix.model.Rules.TotalAphabet;
import org.ojalgo.scalar.RationalNumber;
import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;

public class FindBestMove {
    public ArrayList<ArrayList<String>> set;
    public GameRuleset gr;
    PlayerRuleset prB;
    public FindBestMove(GameRuleset gameRuleset, PlayerRuleset playerRuleset) {
        gr = gameRuleset;
        prB = playerRuleset;
    }

    public ArrayList<String> findBestChoiceB(ArrayList<String> InputA){
        TotalAphabet totalAphabet = new TotalAphabet();
        set = totalAphabet.getAll(gr,prB);
        RationalNumber min = RationalNumber.MIN_VALUE;
        ArrayList<String> bestWord = new ArrayList<>();
        for (ArrayList<String> InputB: set) {
            if (!CollectionUtils.containsAny(InputB, InputA)) {
                ProbabilityCalculator amc = new ProbabilityCalculator(InputB, InputA, gr);
                RationalNumber prob = amc.finalProb();
                if (prob.doubleValue() > min.doubleValue()) {
                    min = prob;
                    bestWord = InputB;
                }
            }
        }
        System.out.println("Best option for B:" + bestWord + " with prob " + min);
        return bestWord;
    }
}





