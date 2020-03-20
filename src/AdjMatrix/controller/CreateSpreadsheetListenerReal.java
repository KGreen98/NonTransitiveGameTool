package AdjMatrix.controller;

import AdjMatrix.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateSpreadsheetListenerReal implements ActionListener {
    private Model model;
    public CreateSpreadsheetListenerReal(Model m) {
        model = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            model.saveSpreadsheetReal();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
