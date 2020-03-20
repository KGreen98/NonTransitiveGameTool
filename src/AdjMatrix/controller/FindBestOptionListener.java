package AdjMatrix.controller;

        import AdjMatrix.model.Model;

        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.util.ArrayList;

        import AdjMatrix.view.GameBoard;
        import AdjMatrix.view.View;

public class FindBestOptionListener implements ActionListener {
    private Model model;
    private View view;
    private GameBoard gb;

    public FindBestOptionListener(Model m, View v) {
        model = m;
        view = v;
        gb = view.getGameBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputA = gb.getaInput();
        ArrayList<String> aInput =  model.splitUserInput(inputA);
        ArrayList<String> bestInputB = model.findBestMoveB(aInput);
        gb.setbInput(model.unsplitUserInput(bestInputB));
    }
}