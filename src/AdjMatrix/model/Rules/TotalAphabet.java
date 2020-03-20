package AdjMatrix.model.Rules;

import java.util.ArrayList;

public class TotalAphabet {
    public ArrayList<ArrayList<String>> getAll(GameRuleset gr, PlayerRuleset pr){
        ArrayList<String> allStrings = listAllPossibleStrings2(gr, pr);
        int setSize = pr.getWordCount();
        ArrayList<ArrayList<String>> allPossibleInputs = new ArrayList<>();

        ArrayList<ArrayList<String>> powerset = everyPossibleInput(allStrings);
        //get all inputs of correct size
        System.out.println(powerset);
        for (ArrayList<String> set: powerset) {
            if (set.size() == setSize){
                allPossibleInputs.add(set);
            }
        }
        System.out.println(allPossibleInputs);
        return allPossibleInputs;
    }

    public static ArrayList<ArrayList<String>> everyPossibleInput(ArrayList<String> list) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (String item : list) {
            ArrayList<ArrayList<String>> newPs = new ArrayList<>();
            for (ArrayList<String> subset : result) {
                newPs.add(subset);
                ArrayList<String> newSubset = new ArrayList<>(subset);
                newSubset.add(item);
                newPs.add(newSubset);
            }
            result = newPs;
        }
        return result;
    }

    public ArrayList<String> listAllPossibleStrings2(GameRuleset gr, PlayerRuleset pr){
        int wordLength = pr.getLength();
        char[] alphabet = gr.getCharSet();
        int base = alphabet.length;

        ArrayList<String> allStrings = new ArrayList<>();
        double total = Math.pow(base, ((double) wordLength));
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

    public String convertToAlphabet(String number, GameRuleset gr){
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
