package AdjMatrix.model.Rules;

import java.util.ArrayList;
import java.util.Arrays;

public class TotalAlphabet {

    //returns a list of all legal inputs
    public ArrayList<ArrayList<String>> allPossibleInputs(GameRuleset gr, PlayerRuleset pr){
        ArrayList<String> allStrings = allPossibleSequences(gr, pr);
        int setSize = pr.getWordCount();
        ArrayList<ArrayList<String>> allPossibleInputs = allSubsetsLengthK(allStrings, setSize);
        return allPossibleInputs;
    }

    //returns a list of all legal inputs
    //returns all permutations of list of the required size
    private ArrayList<ArrayList<String>> allSubsetsLengthK(ArrayList<String> list, int size) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word: list) {
            ArrayList<String> a = new ArrayList<>(Arrays.asList(word));
            results.add(a);
        }
        for (int i=1; i < size; i++){
            ArrayList<ArrayList<String>> newList = new ArrayList<>();
            for (ArrayList<String> input: results) {
                ArrayList<ArrayList<String>> a = nextNodes(list, input);
                for (ArrayList<String> aa: a) {
                    if (checkDuplicate(aa, newList)) {
                        newList.add(aa);
                    }
                }
                newList.remove(input);
            }
            results.clear();
            results = newList;
        }
        return results;
    }

    private ArrayList<ArrayList<String>> nextNodes(ArrayList<String> list, ArrayList<String> a){
        ArrayList<ArrayList<String>> nextInputs = new ArrayList<>();
        for (String word: list) {
            if (!a.contains(word)){
                ArrayList<String> newList = new ArrayList<>();
                newList.addAll(a);
                newList.add(word);
                nextInputs.add(newList);
            }
        }
        return nextInputs;
    }

    private boolean checkDuplicate(ArrayList<String> a, ArrayList<ArrayList<String>> nextList){
        for (ArrayList<String> sequences: nextList) {
            if (sequences.containsAll(a)){
                return false;
            }
        }
        return true;
    }

    //Returns list of all legal sequences for a given alphabet and word length
    public ArrayList<String> allPossibleSequences(GameRuleset gr, PlayerRuleset pr){
        int wordLength = pr.getLength();
        char[] alphabet = gr.getCharSet();
        int base = alphabet.length;

        ArrayList<String> allStrings = new ArrayList<>();
        double total = Math.pow(base, wordLength);
        for (Integer i = 0; i < total; i++) {
            String word = i.toString();
            word = Integer.toString(Integer.parseInt(word, 10), base);
            if (word.length() < wordLength) {
                word = String.format("%0" + (wordLength - word.length()) + "d%s", 0, word);
            }
            word = convertToAlphabet(word, gr);
            allStrings.add(word);
        }
        return allStrings;
    }

    //Converts decimal numbers to equivalent faces
    private String convertToAlphabet(String number, GameRuleset gr){
        char[] lookup = gr.getCharSet();
        char[] inputChars = number.toCharArray();
        ArrayList<Character> newWord = new ArrayList<Character>();
        for (char num: inputChars) {
            if (Character.isDigit(num)){
                int y = Character.getNumericValue(num);
                newWord.add(lookup[y]);
            }
        }
        String output = "";
        for (Character c: newWord) {
            output += c;
        }
        return output;
    }
}
