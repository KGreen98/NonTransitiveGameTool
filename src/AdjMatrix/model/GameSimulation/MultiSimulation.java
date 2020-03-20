package AdjMatrix.model.GameSimulation;

import java.util.ArrayList;

public class MultiSimulation {
    public PenneysGameMulti simulator;

    public MultiSimulation(PenneysGameMulti gameSimulatior) {
        simulator = gameSimulatior;
    }

    public ArrayList<GameData> runNewSimulation(int count){
        ArrayList<GameData> simResults = new ArrayList<>();
        for (int index =0; index < count; index++){
            simResults.add(simulator.runGameSim());
        }
        return simResults;
    }
}
