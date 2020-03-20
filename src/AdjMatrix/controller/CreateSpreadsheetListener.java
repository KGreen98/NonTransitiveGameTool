package AdjMatrix.controller;

import AdjMatrix.model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateSpreadsheetListener implements ActionListener {
    private Model model;
    public CreateSpreadsheetListener(Model m) {
        model = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(model.getRulesGame());
        System.out.println(model.getRulesA());
        System.out.println(model.getRulesB());

        try {
            model.saveSpreadsheetRational();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
