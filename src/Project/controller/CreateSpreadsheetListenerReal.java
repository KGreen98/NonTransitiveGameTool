package Project.controller;

import Project.model.Model;
import Project.view.SpreadsheetBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateSpreadsheetListenerReal implements ActionListener {
    private Model model;
    private SpreadsheetBoard sb;
    public CreateSpreadsheetListenerReal(Model m, SpreadsheetBoard s) {
        model = m;
        sb = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model.tableSize()>1000000) {
            JOptionPane.showMessageDialog(sb,
                    "The table you are trying to create is too large.",
                    "Table Too Large",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                model.saveSpreadsheetReal();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
