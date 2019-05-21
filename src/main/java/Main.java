import Controller.Controller;
import Model.Map;
import Model.Model;
import View.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        //Map map = new Map("world", 100, 100);
        Model model = new Model();
        Controller controller = new Controller(model);
        UserInterface userInterface = new UserInterface("MyRpgMaker", controller);
        model.addObserver(userInterface);
    }
}