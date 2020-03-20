package AdjMatrix.controller;

import AdjMatrix.model.Model;
import AdjMatrix.view.GameBoard;
import AdjMatrix.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProbabilityListener implements ActionListener {
    private Model model;
    private View view;
    private GameBoard gb;
    public ProbabilityListener(Model m, View v) {
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

        String probA = model.findProb(aInput, bInput).toString();
        String probB = model.findProb(bInput, aInput).toString();

        gb.getaProb().setText(probA);
        gb.getbProb().setText(probB);
    }
}