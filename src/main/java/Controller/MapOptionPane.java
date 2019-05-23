package Controller;

import View.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MapOptionPane extends AbstractAction {
    UserInterface userInterface;

    public MapOptionPane(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String evt = actionEvent.getActionCommand();
        if (evt.equals("Delete map"))
            userInterface.delMapView();
        else if (evt.equals("Reset map"))
            userInterface.resetMapView();
        else if (evt.equals("New map"))
            userInterface.addMapView();
        else if (evt.equals("Display grid"))
            userInterface.switchGrid();
    }
}