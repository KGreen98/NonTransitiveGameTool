package AdjMatrix.view;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private JPanel allGamePanel;
    private JButton playGameButton;
    private JTextField aInput;
    private JLabel bRulesLabel;
    private JLabel aRulesLabel;
    private JPanel PlayerDetails;
    private JTextPane gameRulesPane2;
    private JLabel gameRulesLabel;
    private JFormattedTextField gameMessageBox;
    private JTextField bInput;
    private JButton findBestOptionButton;
    private JTabbedPane tabbedPane1;
    private JSpinner simulationCount;
    private JButton simulationRun;
    private JLabel gameMessageLabel;
    private JLabel aProb;
    private JLabel bProb;
    private JButton findProbabilitiesButton;
    private JLabel GamesWonA;
    private JLabel GamesWonB;
    private JTextArea gameRulesArea;

    public GameBoard() {
        this.add(allGamePanel);
        setSimulationCount(100);
        allGamePanel.setPreferredSize(new Dimension(480,350));
    }

    public String getaInput() {
        return aInput.getText();
    }

    public String getbInput() {
        return bInput.getText();
    }

    public void setbInput(String input) {bInput.setText(input);}

    public void setGameMessageBox(String message){
        gameMessageLabel.setText(message);
    }

    public void addGameRunListener(AdjMatrix.controller.Controller.GameRunListener gameRunListener) {
        playGameButton.addActionListener(gameRunListener);
    }

    public void addFindBestOptionListener(AdjMatrix.controller.FindBestOptionListener findBestOptionListener) {
        findBestOptionButton.addActionListener(findBestOptionListener);
    }

    public void addSimulateListener(AdjMatrix.controller.SimulateListener simulateListener) {
        simulationRun.addActionListener(simulateListener);
    }

    public void addProbabilityListener(AdjMatrix.controller.ProbabilityListener probabilityListener) {
        findProbabilitiesButton.addActionListener(probabilityListener);
    }

    public JSpinner getSimulationCount() {
        return simulationCount;
    }

    public void setSimulationCount(int i){
        simulationCount.setValue(i);
    }

    public JLabel getaProb() {
        return aProb;
    }

    public JLabel getbProb() {
        return bProb;
    }

    public JLabel getGamesWonA() {
        return GamesWonA;
    }

    public JLabel getGamesWonB() {
        return GamesWonB;
    }
}
