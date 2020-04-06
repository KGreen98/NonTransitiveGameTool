package Project.controller;

import Project.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Project.view.GameBoard;
import Project.view.View;

import javax.swing.*;

public class FindBestOptionListener implements ActionListener {
    private Model model;
    private View view;
    private GameBoard gb;

    public FindBestOptionListener(Model m, View v) {
        model = m;
        view = v;
        gb = view.getGameBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model.playerBTotalCombinations()>75000) {
            JOptionPane.showMessageDialog(gb,
                    "Scenario too complex to determine the best move for player B.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            String inputA = gb.getaInput();
            ArrayList<String> aInput = model.splitUserInput(inputA);
            ArrayList<String> bestInputB = model.findBestMoveB(aInput);
            gb.setbInput(model.unsplitUserInput(bestInputB));
        }
    }
}