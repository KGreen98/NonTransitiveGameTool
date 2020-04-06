package AdjMatrix.model.GameSimulation;

import AdjMatrix.model.Rules.GameRuleset;

import java.util.ArrayList;

public class GameSimulator {
    private ArrayList<String> inputA;
    private ArrayList<String> inputB;
    private GameRuleset gr;
    private int maxLength;

    public GameSimulator(ArrayList<String> a, ArrayList<String> b, GameRuleset gameRuleset) {
        inputA = a;
        inputB = b;
        gr = gameRuleset;
        maxLength = maxLength(combineLists());
    }

    private ArrayList<String> combineLists(){
        ArrayList<String> allInputs = new ArrayList<String>();
        allInputs.addAll(inputA);
        allInputs.addAll(inputB);
        return allInputs;
    }

    private int maxLength(ArrayList<String> allInputs){
        int max = 0;
        for (String input: allInputs) {
            if (input.length()>max){
                max = input.length();
            }
        }
        return max;
    }

    public GameResultData runGameSim() {
        String sequence = "";
        int count = 0;
        ArrayList<String> inputs = combineLists();
        while (count < 1000000){
            if (!checkFinished(sequence, inputs)){
                //increment sequence
                sequence = incrementList(sequence);
                count++;
            } else {

                GameResultData finalState = new GameResultData(getWinner(sequence, inputs), count, sequence);
                return finalState;
            }

        }
        GameResultData finalState = new GameResultData(-1, 0, "");
        return finalState;
    }

    private boolean checkFinished(String sequence, ArrayList<String> inputs){
        int sequenceLength = sequence.length();
        for (String input: inputs){
            int inputLength = input.length();
            if (sequenceLength >= inputLength) {
                String subsequence = sequence.substring(sequence.length() - inputLength);
                if (input.equals(subsequence)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getWinner(String sequence, ArrayList<String> inputs){
        int sequenceLength = sequence.length();
        for (String input: inputs){
            int inputLength = input.length();
            if (sequenceLength >= inputLength) {
                String subsequence = sequence.substring(sequence.length() - inputLength);
                if (input.equals(subsequence)) {
                    if (inputA.contains(input)){
                        return 0;
                    } else return 1;
                }
            }
        }
        return -1;
    }

    private char genRandom() {
        int randomInt = (int)(gr.getDenominator() * Math.random());
        int face = gr.getRolledIndex(randomInt);
        char[] chars = gr.getCharSet();
        return chars[face];
    }

    private String incrementList(String sequence) {
        String nextSequence = sequence;
        if (sequence.length() >= maxLength) {
            nextSequence = sequence.substring(1);
        }
        char bin = genRandom();
        nextSequence = nextSequence.concat(String.valueOf(bin));
        return nextSequence;
    }
}
