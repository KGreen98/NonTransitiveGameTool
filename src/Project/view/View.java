package Project.view;
import Project.controller.MenuBarListener;
import Project.model.Model;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Model model;
    public RulesBoard rulesBoard;
    public JPanel rules;
    public GameBoard gameBoard = new GameBoard();
    public JPanel game;
    public SpreadsheetBoard spreadsheetBoard;
    public HelpBoard helpBoard = new HelpBoard();
    public RulesDisplayBoard rulesDisplay;

    public View(Model model) {
        this.model = model;
        rulesBoard = new RulesBoard(model);
        rules = rulesBoard.getAllRulesPanels();
        rulesDisplay = new RulesDisplayBoard(model);
        rulesDisplay.setPreferredSize(new Dimension(150,300));
        game = gameBoard;

        spreadsheetBoard = new SpreadsheetBoard(model);
        JMenuBar menuBar = new JMenuBar();


        JMenu fileMenu = new JMenu("File");
        fileMenu.setFocusable(false);

        MenuBarListener menuListener = new MenuBarListener(model, this);

        JMenuItem loadRules = new JMenuItem("Reset Rules");
        JMenuItem quit = new JMenuItem("Quit");

        loadRules.addActionListener(menuListener);
        quit.addActionListener(menuListener);

        fileMenu.addSeparator();
        fileMenu.add(loadRules);
        fileMenu.addSeparator();
        fileMenu.add(quit);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        JPanel jf = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Penney's Game Probabilty Calculator", SwingConstants.CENTER);
        title.setFont(new Font("serif", Font.PLAIN, 24));

        JTabbedPane jt = new JTabbedPane();
        jt.add("Set Rules", rules);
        jt.add( "Play Game", game);
        jt.add( "Probability Tables", spreadsheetBoard);
        jt.add( "Help", helpBoard);

        jf.add(title, BorderLayout.NORTH);
        jf.add(rulesDisplay, BorderLayout.WEST);
        jf.add(jt, BorderLayout.CENTER);

        this.setContentPane(jf);
        this.pack();
        this.setTitle("Penney's Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public RulesBoard getRulesBoard() {
        return rulesBoard;
    }

    public RulesDisplayBoard getRulesDisplay() {
        return rulesDisplay;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public SpreadsheetBoard getSpreadsheetBoard() {
        return spreadsheetBoard;
    }
}