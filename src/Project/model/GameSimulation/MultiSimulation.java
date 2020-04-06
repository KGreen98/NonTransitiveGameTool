package Project.model.GameSimulation;

import java.util.ArrayList;

public class MultiSimulation {
    private GameSimulator simulator;

    public MultiSimulation(GameSimulator gameSimulatior) {
        simulator = gameSimulatior;
    }

    public ArrayList<GameResultData> runNewSimulation(int count){
        ArrayList<GameResultData> simResults = new ArrayList<>();
        for (int index =0; index < count; index++){
            simResults.add(simulator.runGameSim());
        }
        return simResults;
    }
}
