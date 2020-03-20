package AdjMatrix.controller;

import AdjMatrix.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarListener implements ActionListener {

    private Model model;

    public MenuBarListener(Model m) {
        model = m;
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load Rules":
                model.loadRules();
                break;
            case "Reset Rules":
                model.saveRules(model.getRulesGame(), model.getRulesA(), model.getRulesB());
                break;
            case "Quit":
                System.exit(0);
                break;
        }
    }

}
