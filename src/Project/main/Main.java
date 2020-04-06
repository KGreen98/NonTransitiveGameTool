package Project.main;

import Project.controller.Controller;
import Project.model.Model;
import Project.view.View;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        view.setVisible(true);
    }
}