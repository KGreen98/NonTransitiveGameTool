package AdjMatrix.controller;

import AdjMatrix.model.Model;
import AdjMatrix.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarListener implements ActionListener {

    private Model model;
    private View view;

    public MenuBarListener(Model m, View v) {
        model = m;
        view = v;
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Reset Rules":
                model.loadRules();
                model.saveRules(model.getRulesGame(), model.getRulesA(), model.getRulesB());
                view.getRulesDisplay().update(model);
                view.getRulesBoard().update(model);
                view.getSpreadsheetBoard().update(model);
                break;
            case "Quit":
                System.exit(0);
                break;
        }
    }

}
