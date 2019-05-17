import Controller.Controller;
import Model.Map;
import View.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        Map map = new Map();
        Controller controller = new Controller(map);
        UserInterface userInterface = new UserInterface("MyRpgMaker", controller);
        map.addObserver(userInterface);
    }
}