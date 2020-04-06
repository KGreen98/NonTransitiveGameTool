package Project.controller;

import Project.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Project.view.GameBoard;
import Project.view.View;

public class SimulateListener implements ActionListener {
    private Model model;
    private View view;
    private GameBoard gb;
    public SimulateListener(Model m, View v) {
        model = m;
        view = v;
        gb = view.getGameBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputA = gb.getaInput();
        String inputB = gb.getbInput();
        ArrayList<String> aInput =  model.splitUserInput(inputA);
        ArrayList<String> bInput = model.splitUserInput(inputB);
        int simCount = (int) gb.getSimulationCount().getValue();
        if (!validSimCount(simCount)){
            simCount = 100;
            gb.setSimulationCount(100);
        }

        ArrayList<Integer> results = model.multiSimresults(aInput, bInput, simCount);

        int aWon = results.get(0);
        int bWon = results.get(1);
        System.out.println("awin" + aWon + bWon);
        System.out.println(simCount);
        double aPercent = ((double)aWon / (double)simCount) * 100;
        double bPercent = ((double)bWon / (double)simCount) * 100;
        aPercent = Math.floor(aPercent * 100)/100;
        bPercent = Math.floor(bPercent * 100)/100;

        gb.getGamesWonA().setText(aWon + "(" + aPercent + "%)");
        gb.getGamesWonB().setText(bWon + "(" + bPercent + "%)");
    }

    public boolean validSimCount(int wc){
        if (wc < 1 || wc > 1000000) {
            return false;
        }
        return true;
    }
}