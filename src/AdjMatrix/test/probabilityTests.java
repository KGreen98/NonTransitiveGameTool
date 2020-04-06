package AdjMatrix.test;

import AdjMatrix.model.Model;
import AdjMatrix.model.Rules.GameRuleset;
import AdjMatrix.model.Rules.PlayerRuleset;
import AdjMatrix.model.Rules.Rolls;
import org.ojalgo.scalar.RationalNumber;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Collections;

import static org.junit.Assert.*;
public class probabilityTests {
    Model theModel = new Model();

    @org.junit.Test
    public void penneysGameTest(){
        //theModel.setRulesGame(new GameRuleset());
        ArrayList<Rolls> gr = new ArrayList<>();
        gr.add(new Rolls('H', 1));
        gr.add(new Rolls('T',1));
        theModel.setRulesGame(new GameRuleset(gr));
        theModel.setRulesA(new PlayerRuleset(3,1));
        theModel.setRulesB(new PlayerRuleset(3,1));

        ArrayList<String> hhh = new ArrayList<>(Collections.singletonList("HHH"));
        ArrayList<String> hht = new ArrayList<>(Collections.singletonList("HHT"));
        ArrayList<String> hth = new ArrayList<>(Collections.singletonList("HTH"));
        ArrayList<String> htt = new ArrayList<>(Collections.singletonList("HTT"));
        ArrayList<String> thh = new ArrayList<>(Collections.singletonList("THH"));
        ArrayList<String> tht = new ArrayList<>(Collections.singletonList("THT"));
        ArrayList<String> tth = new ArrayList<>(Collections.singletonList("TTH"));
        ArrayList<String> ttt = new ArrayList<>(Collections.singletonList("TTT"));

        assertEquals(theModel.findProb(hhh,hht), RationalNumber.of(1,2));
        assertEquals(theModel.findProb(hhh,hth), RationalNumber.of(2,5));
        assertEquals(theModel.findProb(hhh,htt), RationalNumber.of(2,5));
        assertEquals(theModel.findProb(hhh,thh), RationalNumber.of(1,8));
        assertEquals(theModel.findProb(hhh,tht), RationalNumber.of(5,12));
        assertEquals(theModel.findProb(hhh,tth), RationalNumber.of(3,10));
        assertEquals(theModel.findProb(hhh,ttt), RationalNumber.of(1,2));

        assertEquals(theModel.findProb(hth,tth), RationalNumber.of(3,8));
        assertEquals(theModel.findProb(thh,ttt), RationalNumber.of(3,5));
        assertEquals(theModel.findProb(tth,htt), RationalNumber.of(1,4));
        assertEquals(theModel.findProb(thh,hht), RationalNumber.of(3,4));
    }

    @org.junit.Test
    public void penneysLength(){
        //theModel.setRulesGame(new GameRuleset());
        ArrayList<Rolls> gr = new ArrayList<>();
        gr.add(new Rolls('H', 1));
        gr.add(new Rolls('T',1));
        theModel.setRulesGame(new GameRuleset(gr));
        theModel.setRulesA(new PlayerRuleset(5,1));
        theModel.setRulesB(new PlayerRuleset(5,1));
        ArrayList<String> h5 = new ArrayList<>(Collections.singletonList("HHHHH"));
        ArrayList<String> th5 = new ArrayList<>(Collections.singletonList("THHHH"));
        assertEquals(theModel.findProb(h5,th5), RationalNumber.of(1,32));

        theModel.setRulesA(new PlayerRuleset(8,1));
        theModel.setRulesB(new PlayerRuleset(8,1));
        ArrayList<String> h8 = new ArrayList<>(Collections.singletonList("HHHHHHHH"));
        ArrayList<String> th8 = new ArrayList<>(Collections.singletonList("THHHHHHH"));
        assertEquals(theModel.findProb(h8,th8), RationalNumber.of(1,256));

        theModel.setRulesA(new PlayerRuleset(5,1));
        theModel.setRulesB(new PlayerRuleset(4,1));
        ArrayList<String> t5 = new ArrayList<>(Collections.singletonList("TTTTT"));
        ArrayList<String> h4 = new ArrayList<>(Collections.singletonList("HHHH"));
        assertEquals(theModel.findProb(t5,h4), RationalNumber.of(15,46));
    }

    @org.junit.Test
    public void complementTest() {
        //theModel.setRulesGame(new GameRuleset());
        ArrayList<Rolls> gr = new ArrayList<>();
        gr.add(new Rolls('H', 1));
        gr.add(new Rolls('T', 1));
        theModel.setRulesGame(new GameRuleset(gr));
        theModel.setRulesA(new PlayerRuleset(3, 1));
        theModel.setRulesB(new PlayerRuleset(3, 1));
        ArrayList<String> hhh = new ArrayList<>(Collections.singletonList("HHH"));
        ArrayList<String> ttt = new ArrayList<>(Collections.singletonList("TTT"));
        assertEquals(theModel.findProb(hhh, ttt).add(theModel.findProb(ttt,hhh)).toBigDecimal(), BigDecimal.ONE);
    }
}
