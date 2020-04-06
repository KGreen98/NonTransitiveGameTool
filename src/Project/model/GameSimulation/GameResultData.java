package Project.model.GameSimulation;

public class GameResultData {
    private int winner;
    private int rolls;
    private String winningString;

    public GameResultData(int winningPLayer, int rollCount, String sequence) {
        winner = winningPLayer;
        rolls = rollCount;
        winningString = sequence;
    }

    public int getWinner() {
        return winner;
    }

    public int getRollCount() {
        return rolls;
    }

    public String getWinningString() {
        return winningString;
    }

    public String toString(){
        String text = "Player ";
        if (getWinner() == 0){
            text += "A ";
        } else if(getWinner() == 1){
            text += "B ";
        } else if(getWinner() == -1){
            return "ERROR, took too long";
        }
            text += "wins! With sequence " + getWinningString() + ". After " + getRollCount() + " rolls.";
        return text;
    }
}
