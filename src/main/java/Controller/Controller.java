package Controller;

import Model.Map;
import View.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controller extends AbstractAction {

    private Map map;
    private UserInterface userInterface;

    public Controller(UserInterface userInterface) {
        if (map == null) {
            this.map = new Map();
        }
        this.userInterface = userInterface;
            map.addObserver(userInterface);

    }

    public void actionPerformed(ActionEvent actionEvent) {
        map.add_foreground_object("foreground_object");
    }
}
