package Controller;

import View.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewMapOptionPane extends AbstractAction {
    UserInterface userInterface;

    public NewMapOptionPane(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        userInterface.addMapView();
    }
}