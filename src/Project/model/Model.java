package Project.model;

import Project.model.GameSimulation.GameResultData;
import Project.model.GameSimulation.MultiSimulation;
import Project.model.GameSimulation.GameSimulator;

import Project.model.Probabilities.FindBestMove;
import Project.model.Probabilities.SpreadsheetWriter;
import Project.model.Rules.GameRuleset;
import Project.model.Rules.PlayerRuleset;
import Project.model.Probabilities.ProbabilityCalculator;
import Project.model.Rules.Rolls;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import org.ojalgo.scalar.RationalNumber;

import org.apache.commons.math3.util.CombinatoricsUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    private GameRuleset rulesGame;
    private PlayerRuleset rulesA;
    private PlayerRuleset rulesB;

    public Model(){
        ArrayList<Rolls> gr = new ArrayList<>();
        gr.add(new Rolls('H', 1));
        gr.add(new Rolls('T',1));
        setRulesGame(new GameRuleset(gr));
        setRulesA(new PlayerRuleset(3,1));
        setRulesB(new PlayerRuleset(3,1));
    }

    public GameRuleset getRulesGame() { return rulesGame; }
    public void setRulesGame(GameRuleset rulesGame) { this.rulesGame = rulesGame; }
    public PlayerRuleset getRulesA() {
        return rulesA;
    }
    public void setRulesA(PlayerRuleset rulesA) { this.rulesA = rulesA; }
    public PlayerRuleset getRulesB() { return rulesB; }
    public void setRulesB(PlayerRuleset rulesB) {
        this.rulesB = rulesB;
    }

    public long playerATotalCombinations(){
        int alphabetSize = getRulesGame().getBase();
        int wordLength = getRulesA().getLength();
        double allWordsTotal = Math.pow(alphabetSize, ((double) wordLength));
        int count = getRulesA().getWordCount();
        return CombinatoricsUtils.binomialCoefficient((int) allWordsTotal, count);
    }

    public long playerBTotalCombinations(){
        int alphabetSize = getRulesGame().getBase();
        int wordLength = getRulesB().getLength();
        double allWordsTotal = Math.pow(alphabetSize, ((double) wordLength));
        int count = getRulesB().getWordCount();
        return CombinatoricsUtils.binomialCoefficient((int) allWordsTotal, count);
    }

    public long tableSize(){
        return playerBTotalCombinations()*playerBTotalCombinations();
    }

    //add player ruleset parameters
    //add game ruleset parameters
    public RationalNumber findProb(ArrayList<String> A, ArrayList<String> B){
        ProbabilityCalculator pc = new ProbabilityCalculator(A, B, getRulesGame());
        return pc.finalProb();
    }


    public ArrayList<String> splitUserInput(String input){
        String[] items = input.split(",");
        ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(items));
        return wordList;
    }

    public String unsplitUserInput(ArrayList<String> input){
        String textInput = "";
        for (String word: input) {
            textInput += word;
            textInput += ",";
        }
        return textInput.substring(0, textInput.length()-1);
    }

    public String getGameRulesAsUserInput(){
        ArrayList<Rolls> allRolls = getRulesGame().getRolls();
        String textInput = "";
        for (Rolls r: allRolls) {
            textInput += r.getCharacter() + "=" + r.getOdds() + ",";
        }
        return textInput.substring(0, textInput.length()-1);
    }

    public GameRuleset inputToGameRules(String input){
        ArrayList<String> inputs = splitUserInput(input);
        ArrayList<Rolls> rolls = new ArrayList<>();
        for (String entry: inputs) {
            String[] items = entry.split("=");
            ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(items));
            if (rolls.size() < 10) {
                rolls.add(new Rolls(items[0].charAt(0), Integer.parseInt(items[1])));
            }
        }
        return new GameRuleset(rolls);
    }

    public ArrayList<String> findBestMoveB(ArrayList<String> AInput){
        FindBestMove finder = new FindBestMove(getRulesGame(), getRulesB());
        return finder.findBestChoiceB(AInput);
    }

    public ArrayList<GameResultData> runNGames(ArrayList<String> A, ArrayList<String> B, int n){
        GameSimulator simulation = new GameSimulator(A, B, getRulesGame());
        MultiSimulation multiSim = new MultiSimulation(simulation);
        return multiSim.runNewSimulation(n);
    }

    public ArrayList<Integer> multiSimresults(ArrayList<String> A, ArrayList<String> B, int n){
        ArrayList<GameResultData> gamesData = runNGames(A,B, n);
        Integer wonA = 0;
        Integer wonB = 0;
        for (GameResultData gd: gamesData) {
            if (gd.getWinner() == 0){
                wonA++;
            } else wonB++;
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(wonA);
        result.add(wonB);
        result.add(n);
        return result;
    }

    public GameResultData runOneGame(ArrayList<String> A, ArrayList<String> B, GameRuleset gr){
        GameSimulator simulation = new GameSimulator(A, B, gr);
        return simulation.runGameSim();
    }

    public void loadRules() {
        ArrayList<Rolls> gr = new ArrayList<>();
        gr.add(new Rolls('H', 1));
        gr.add(new Rolls('T',1));
        setRulesGame(new GameRuleset(gr));
        setRulesA(new PlayerRuleset(3,1));
        setRulesB(new PlayerRuleset(3,1));
    }

    public void saveRules(GameRuleset gr, PlayerRuleset a, PlayerRuleset b) {
        setRulesGame(gr);
        setRulesA(a);
        setRulesB(b);
    }

    public void saveSpreadsheetRational() throws IOException {
        SpreadsheetWriter sw = new SpreadsheetWriter(rulesGame, rulesA, rulesB);
        RationalNumber[][] table = sw.getList();
        ArrayList<ArrayList<String>> inputsA = sw.getAllStringsA();
        ArrayList<ArrayList<String>> inputsB = sw.getAllStringsB();

        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "\\Documents");
        fileChooser.setSelectedFile(new File("RationalTable.xlsx"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fts = fileChooser.getSelectedFile();
            String fname = fts.getAbsolutePath();
            if(!fname.endsWith(".xlsx") ) {
                fileChooser.setSelectedFile(new File(fname + ".xlsx"));
                fts = fileChooser.getSelectedFile();
            }

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFCellStyle cs = wb.createCellStyle();
            XSSFFont font = wb.createFont();
            font.setBold(true);
            cs.setFont(font);

            XSSFCellStyle grey = wb.createCellStyle();
            grey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFSheet sheet = wb.createSheet("Probabilities");
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < inputsB.size(); i++) {
                XSSFCell cell = row.createCell(i+1);
                cell.setCellValue(inputsB.get(i).toString());
                cell.setCellStyle(cs);
            }
            for (int i = 0; i < inputsA.size(); i++) {
                XSSFRow row2 = sheet.createRow(i+1);
                XSSFCell cell = row2.createCell(0);
                cell.setCellValue(inputsA.get(i).toString());
                cell.setCellStyle(cs);
            }
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++){
                    XSSFCell cell = sheet.getRow(i + 1).createCell(j+1);
                    double result = (table[i][j].toBigDecimal().doubleValue());
                    if (result != -1){
                        cell.setCellValue(String.valueOf(table[i][j]));
                    } else{
                        cell.setCellStyle(grey);
                    }

                }
            }
            wb.write(new FileOutputStream(fts));
            wb.close();
        }
    }

    public void saveSpreadsheetReal() throws IOException {
        SpreadsheetWriter sw = new SpreadsheetWriter(rulesGame, rulesA, rulesB);
        RationalNumber[][] table = sw.getList();
        ArrayList<ArrayList<String>> inputsA = sw.getAllStringsA();
        ArrayList<ArrayList<String>> inputsB = sw.getAllStringsB();

        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "\\Documents");
        fileChooser.setSelectedFile(new File("RealTable.xlsx"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fts = fileChooser.getSelectedFile();
            String fname = fts.getAbsolutePath();
            if(!fname.endsWith(".xlsx") ) {
                fileChooser.setSelectedFile(new File(fname + ".xlsx"));
                fts = fileChooser.getSelectedFile();
            }

            XSSFWorkbook wb = new XSSFWorkbook();
            CellStyle cs = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            cs.setFont(font);

            CellStyle grey = wb.createCellStyle();
            grey.setFillPattern(FillPatternType.SOLID_FOREGROUND);


            Sheet sheet = wb.createSheet("Probabilities");
            Row row = sheet.createRow(0);
            for (int i = 0; i < inputsB.size(); i++) {
                Cell cell = row.createCell(i+1);
                cell.setCellValue(inputsB.get(i).toString());
                cell.setCellStyle(cs);
            }
            for (int i = 0; i < inputsA.size(); i++) {
                Row row2 = sheet.createRow(i+1);
                Cell cell = row2.createCell(0);
                cell.setCellValue(inputsA.get(i).toString());
                cell.setCellStyle(cs);
            }
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++){
                    Cell cell = sheet.getRow(i + 1).createCell(j+1);
                    double result = (table[i][j].toBigDecimal().doubleValue());
                    if (result != -1){
                        cell.setCellValue(result);
                    } else{
                        cell.setCellStyle(grey);
                    }
                }
            }
            wb.write(new FileOutputStream(fts));
            wb.close();
        }
    }
}
