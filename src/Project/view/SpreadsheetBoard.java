package Project.view;

import Project.controller.CreateSpreadsheetListener;
import Project.controller.CreateSpreadsheetListenerReal;
import Project.model.Model;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class SpreadsheetBoard extends JPanel {
    private Model m;
    private JPanel panel1;
    private JButton saveProbabilityTableRationalButton;
    private JButton saveProbabilityTableRealButton;
    private JLabel possibleA;
    private JLabel possibleB;
    private JLabel possibleTotal;

    public SpreadsheetBoard(Model model) {
        this.add(panel1);
        saveProbabilityTableRationalButton.addActionListener(new CreateSpreadsheetListener(model, this));
        saveProbabilityTableRealButton.addActionListener(new CreateSpreadsheetListenerReal(model, this));
        m = model;
        update(m);
    }

    public void update(Model model){
        m = model;
        m.getRulesA();
        m.getRulesB();
        int base = m.getRulesGame().getBase();
        long lengthA = m.playerATotalCombinations();
        long lengthB = m.playerBTotalCombinations();
        //int a = (int) Math.pow(base, lengthA);
        //int b = (int) Math.pow(base, lengthB);
        possibleA.setText("Player A:" + lengthA + " possible inputs");
        possibleB.setText("Player B:" + lengthB + " possible inputs");
        possibleTotal.setText("Table will require " + NumberFormat.getNumberInstance(Locale.US).format(lengthA*lengthB) + " calculations");
    }
}
