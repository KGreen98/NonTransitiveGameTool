package AdjMatrix.controller;

import AdjMatrix.model.Model;
import AdjMatrix.model.GameSimulation.GameSimulator;
import AdjMatrix.model.Rules.GameRuleset;
import AdjMatrix.view.GameBoard;
import AdjMatrix.view.RulesBoard;
import AdjMatrix.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {
    private View theView;
    private Model theModel;
    private GameBoard gb;
    private RulesBoard rb;

    public View getTheView() {
        return theView;
    }

    public Model getTheModel() {
        return theModel;
    }

    public Controller(Model m, View v){
        this.theModel = m;
        this.theView = v;
        gb = v.gameBoard;
        rb = v.rulesBoard;

        String GameR = theModel.getRulesGame().toString();
        String A = theModel.getRulesA().toString();
        String B = theModel.getRulesB().toString();

        gb.addGameRunListener(new GameRunListener());
        gb.addFindBestOptionListener(new FindBestOptionListener(theModel, theView));
        gb.addSimulateListener(new SimulateListener(theModel, theView));
        gb.addProbabilityListener(new ProbabilityListener(theModel, theView));
        rb.addApplyRulesListener(new ApplyRulesListener(theModel, theView));
        //theView.gameBoard.addGameRunListener(new GameRunListener());
        //this.theView.addCalulateListener(new SimulateListener());
        //this.theView.addBestBListener(new BestBListener());
    }


    public class GameRunListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            String inputA, inputB;
            ArrayList<String> inputsA, inputsB;
            GameRuleset ruleset = getTheModel().getRulesGame();
            try{
                inputA = gb.getaInput();
                inputB = gb.getbInput();
                inputsA = theModel.splitUserInput(inputA);
                inputsB = theModel.splitUserInput(inputB);

                GameSimulator a = new GameSimulator(inputsA, inputsB, ruleset);
                String result = a.runGameSim().toString();
                gb.setGameMessageBox(result);
            }
            catch (NumberFormatException ex){
//                theView.displayErrorMessage("You need 2 integers");
            }

        }
    }
}
