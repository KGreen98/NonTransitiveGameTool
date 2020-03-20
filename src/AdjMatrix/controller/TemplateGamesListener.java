package AdjMatrix.controller;

import AdjMatrix.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import AdjMatrix.view.RulesBoard;

public class TemplateGamesListener implements ActionListener {

    private RulesBoard rulesBoard;

    public TemplateGamesListener(RulesBoard rb) {
        rulesBoard = rb;
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        //TODO fix for ratios
        switch (e.getActionCommand()) {
            case "Fair Coin":
                rulesBoard.getGameRulesTextField().setText("H=1,T=1");
                break;
            case "4-Sided Die":
                rulesBoard.getGameRulesTextField().setText("1=1,2=1,3=1,4=1");
                break;
            case "6-Sided Die":
                rulesBoard.getGameRulesTextField().setText("1=1,2=1,3=1,4=1,5=1,6=1");
                break;
            case "10-Sided Die":
                rulesBoard.getGameRulesTextField().setText("1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1,9=1,X=1");
                break;
            case "Roulette Wheel":
                rulesBoard.getGameRulesTextField().setText("R=18,B=18,G=1");
                break;
            case "Roulette Wheel (US)":
                rulesBoard.getGameRulesTextField().setText("R=18,B=18,G=2");
                break;
            case "Unfair 4-Sided Die":
                rulesBoard.getGameRulesTextField().setText("1=4,2=3,3=2,4=1");
                break;
            case "Unfair 6-Sided Die":
                rulesBoard.getGameRulesTextField().setText("1=6,2=5,3=4,4=3,5=2,6=1");
                break;
        }
    }

}
