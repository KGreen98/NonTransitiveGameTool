package AdjMatrix.main;

import AdjMatrix.controller.Controller;
import AdjMatrix.model.Model;
import AdjMatrix.view.View;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        view.setVisible(true);
    }
}