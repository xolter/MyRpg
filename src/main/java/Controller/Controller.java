package Controller;

import Model.Map;
import View.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{

    static Map map;

    public Controller(Map map) {
        this.map = map;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        map.add_foreground_object("chef");
    }
}