package AdjMatrix.model.GameSimulation;

import AdjMatrix.model.Rules.GameRuleset;

import java.util.ArrayList;

public class PenneysGameMulti {
    private ArrayList<String> inputA;
    private ArrayList<String> inputB;
    private GameRuleset gr;

    private int base;
    private int maxLength;

    public PenneysGameMulti(ArrayList<String> a, ArrayList<String> b, GameRuleset gameRuleset) {
        inputA = a;
        inputB = b;
        gr = gameRuleset;
        maxLength = maxLength(combineLists());

        //TODO fix for multiratios
        base = gr.getBase();
    }

    public ArrayList<String> combineLists(){
        ArrayList<String> allInputs = new ArrayList<String>();
        allInputs.addAll(inputA);
        allInputs.addAll(inputB);
        return allInputs;
    }

    public int maxLength(ArrayList<String> allInputs){
        int max = 0;
        for (String input: allInputs) {
            if (input.length()>max){
                max = input.length();
            }
        }
        return max;
    }

    public GameData runGameSim() {
        String sequence = "";
        int count = 0;
        ArrayList<String> inputs = combineLists();
        while (count < 1000000){
            if (!checkFinished(sequence, inputs)){
                //increment sequence
                sequence = incrementList(sequence);
                count++;
            } else {

                GameData finalState = new GameData(getWinner(sequence, inputs), count, sequence);
                return finalState;
            }

        }
        GameData finalState = new GameData(-1, 0, "");
        return finalState;
    }

    public boolean checkFinished(String sequence, ArrayList<String> inputs){
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

    public int getWinner(String sequence, ArrayList<String> inputs){
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

    //TODO fix for ratios
    public char genRandom(int base) {
        int randomInt = (int)(gr.getBase() * Math.random());
        char[] chars = gr.getCharSet();
        return chars[randomInt];
    }

    //TODO fix for ratios
    public String incrementList(String sequence) {
        String nextSequence = sequence;
        if (sequence.length() >= maxLength) {
            nextSequence = sequence.substring(1);
        }
        char bin = genRandom(base);
        nextSequence = nextSequence.concat(String.valueOf(bin));
        return nextSequence;
    }
}
