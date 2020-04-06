package AdjMatrix.model.Rules;

import java.util.ArrayList;

public class GameRuleset {

    private int base;
    private int denominator;
    private ArrayList<Rolls> rolls = new ArrayList<>();
    //Set of letters available and ratio of occuring
    //TODO change for varied ratios

    public GameRuleset(ArrayList<Rolls> probs) {
        rolls.addAll(probs);
        base = rolls.size();

        int total = 0;
        for (Rolls r: rolls) {
            total += r.getOdds();
        }
        setDenominator(total);
    }

    public ArrayList<Rolls> getRolls() {
        return rolls;
    }
    
    public int getOddsFromChar(char c){
        for (Rolls r: rolls) {
            if (r.getCharacter() == c){
                return r.getOdds();
            }
        }
        return 1;
    }

    public char[] getCharSet(){
        char[] set = new char[base];
        for (int index = 0; index < rolls.size(); index++) {
            set[index] = rolls.get(index).getCharacter();
        }
        return set;
    }

    public int getRolledIndex(int face){
        int current = 0;
        for (int i = 0; i < rolls.size(); i++) {
            current += rolls.get(i).getOdds();
            if (face < current){
                return i;
            }
        }
        return 0;
    }

    public int getBase() {
        return rolls.size();
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public String toString(){
        String statement = "<html>";//""Alphabet size:" + base + System.lineSeparator();
        for (Rolls r: rolls) {
            //System.out.println("Hello");
            statement += r.toString() + "/" + denominator + " chance \n";
            statement += "<br>";
        }
        statement += "</html>";
        return statement;
    }



}
