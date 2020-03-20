package AdjMatrix.model.Rules;

public class Rolls {
    private char character;
    private int odds;

    public Rolls(char input, int numerator) {
        character = input;
        odds = numerator;
    }

    public char getCharacter() {
        return character;
    }

    public int getOdds() {
        return odds;
    }

    public String toString() {
        String statement = getCharacter() + " with " + getOdds();
        return statement;
    }
}
