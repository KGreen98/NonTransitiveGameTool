package AdjMatrix.view;

import AdjMatrix.model.Model;

import javax.swing.*;
import java.awt.*;

public class RulesDisplayBoard extends JPanel {
    private Model m;
    private JPanel displayPanel;
    private JLabel AlphabetString;
    private JLabel PlayerAString;
    private JLabel PlayerBString;
    private JPanel alphabatPanel;
    private JPanel aPanel;
    private JPanel bPanel;

    public RulesDisplayBoard(Model model) {
        this.add(displayPanel);
        //this.setSize(60,500);
        m = model;
        update(m);
    }

    public void update(Model model){
        m = model;
        AlphabetString.setText(m.getRulesGame().toString());
        PlayerAString.setText(m.getRulesA().toString());
        PlayerBString.setText(m.getRulesB().toString());
    }
}
