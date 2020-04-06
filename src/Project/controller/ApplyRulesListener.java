package Project.controller;

import Project.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Project.model.Rules.GameRuleset;
import Project.model.Rules.PlayerRuleset;
import Project.view.RulesBoard;
import Project.view.View;

public class ApplyRulesListener implements ActionListener {
    private Model model;
    private View view;
    private RulesBoard rb;
    public ApplyRulesListener(Model m, View v) {
        model = m;
        view = v;
        rb = view.getRulesBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ratios = rb.getGameRulesTextField().getText();
        GameRuleset gr = model.inputToGameRules(ratios);
        model.setRulesGame(gr);

        int wcA = (int) rb.getSpinnerWordsA().getValue();
        int lenA = (int) rb.getSpinnerLenA().getValue();
        int wcB = (int) rb.getSpinnerWordsB().getValue();
        int lenB = (int) rb.getSpinnerLenB().getValue();
        if (!wcValid(wcA)){
            wcA = 1;
            rb.setWordsA(1);
        }
        if (!wcValid(wcB)){
            wcB = 1;
            rb.setWordsB(1);
        }
        if (!lenValid(lenA)){
            lenA = 3;
            rb.setSpinnerLenA(3);
        }
        if (!lenValid(lenB)){
            lenB = 3;
            rb.setSpinnerLenB(3);
        }
        PlayerRuleset newRulesetA = new PlayerRuleset(lenA, wcA);
        PlayerRuleset newRulesetB = new PlayerRuleset(lenB, wcB);
        model.setRulesA(newRulesetA);
        model.setRulesB(newRulesetB);
        view.getRulesDisplay().update(model);
        view.getSpreadsheetBoard().update(model);
    }

    public boolean wcValid(int wc){
        if (wc < 1 || wc > 10) {
            return false;
        }
        return true;
    }

    public boolean lenValid(int len){
        if (len < 2 || len > 20) {
            return false;
        }
        return true;
    }
}