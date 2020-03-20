package AdjMatrix.model.Rules;

import java.util.ArrayList;

public class PlayerRuleset {
    private int length;
    private int base;
    private int wordCount;

    public PlayerRuleset(int len, int wordCount) {
        setLength(len);
        setWordCount(wordCount);
    }
    
    public String toString(){
        String statement = "" + wordCount;
        if (wordCount == 1) {
            statement += " word";
        }
        else{
            statement += " words";
        }
        statement += " of length " + length;
        return statement;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    //TODO validate user input methods
    public boolean validateInput(ArrayList<String> playerInput) {
        return true;
    }

    public boolean validateLength(ArrayList<String> playerInput) {
        for (String input: playerInput) {
            if (input.length() != getLength()){
                return false;
            }
        }
        return true;
    }

    public boolean validateWordCount(ArrayList<String> playerInput) {
        if (playerInput.size() == getWordCount()) {
            return true;
        }
        else {
            return false;
        }
    }



}
