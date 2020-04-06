package AdjMatrix.view;

import AdjMatrix.controller.TemplateGamesListener;
import AdjMatrix.model.Model;
import AdjMatrix.model.Rules.GameRuleset;
import AdjMatrix.model.Rules.PlayerRuleset;

import javax.swing.*;
import java.awt.*;

public class RulesBoard extends JPanel {
    private JPanel allRulesPanels;
    private JButton applyRulesButton;
    private JButton fairCoinButton;
    private JButton a4SidedDieButton;
    private JButton a6SidedDieButton;
    private JButton a10SidedDieButton;
    private JButton rouletteWheelButton;
    private JButton rouletteWheelUSButton;
    private JButton unfair6SidedDieButton;
    private JButton diceTotalsButton;
    private JSpinner spinnerLenA;
    private JSpinner spinnerLenB;
    private JSpinner spinnerWordsB;
    private JSpinner spinnerWordsA;
    private JTextField gameRulesTextField;

    public RulesBoard(Model model) {
        getAllRulesPanels().setPreferredSize(new Dimension(480,350));
        update(model);

        TemplateGamesListener tgl = new TemplateGamesListener(this);
        fairCoinButton.addActionListener(tgl);
        a4SidedDieButton.addActionListener(tgl);
        a6SidedDieButton.addActionListener(tgl);
        a10SidedDieButton.addActionListener(tgl);
        rouletteWheelButton.addActionListener(tgl);
        rouletteWheelUSButton.addActionListener(tgl);
        unfair6SidedDieButton.addActionListener(tgl);
        diceTotalsButton.addActionListener(tgl);
    }

    public void update(Model model){
        GameRuleset gRules = model.getRulesGame();
        PlayerRuleset aRules = model.getRulesA();
        PlayerRuleset bRules = model.getRulesB();
        gameRulesTextField.setText(model.getGameRulesAsUserInput());
        spinnerLenA.setValue(aRules.getLength());
        spinnerWordsA.setValue(aRules.getWordCount());
        spinnerLenB.setValue(bRules.getLength());
        spinnerWordsB.setValue(bRules.getWordCount());
    }

    public JPanel getAllRulesPanels() {
        return allRulesPanels;
    }

    public void addApplyRulesListener(AdjMatrix.controller.ApplyRulesListener applyRulesListener) {
        applyRulesButton.addActionListener(applyRulesListener);
    }

    public JTextField getGameRulesTextField() {
        return gameRulesTextField;
    }

    public JSpinner getSpinnerLenA() {
        return spinnerLenA;
    }

    public void setSpinnerLenA(int i){
        spinnerLenA.setValue(i);
    }

    public JSpinner getSpinnerWordsA() {
        return spinnerWordsA;
    }

    public void setWordsA(int i){
        spinnerWordsA.setValue(i);
    }

    public JSpinner getSpinnerLenB() {
        return spinnerLenB;
    }

    public void setSpinnerLenB(int i){
        spinnerLenB.setValue(i);
    }

    public JSpinner getSpinnerWordsB() {
        return spinnerWordsB;
    }

    public void setWordsB(int i){
        spinnerWordsB.setValue(i);
    }


}
