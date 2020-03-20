package AdjMatrix.test;

import AdjMatrix.model.Model;
import AdjMatrix.model.Probabilities.GraphBuilder;
import AdjMatrix.model.Probabilities.ProbabilityCalculator;
import AdjMatrix.model.Probabilities.SpreadsheetWriter;
import AdjMatrix.model.Rules.GameRuleset;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class probabilityTests {
    Model theModel = new Model();


    @org.junit.Test
    public void spreadsheetTest(){
        ArrayList<String> a = new ArrayList<>();
        a.add("000");
        ArrayList<String> b = new ArrayList<>();
        a.add("111");

        System.out.println(theModel.findProb(a,b));

        //SpreadsheetWriter sw = new SpreadsheetWriter(5,2);
        //assertArrayEquals(sw.getList(), sw.quickGetList());

    }
}
