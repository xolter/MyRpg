package Editor.Controller;

import Editor.View.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MapOptionPane extends AbstractAction {
    UserInterface userInterface;
    Controller controller;

    public MapOptionPane(UserInterface userInterface, Controller controller) {
        this.userInterface = userInterface;
        this.controller = controller;
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
        else if (evt.equals("Selection mode"))
            controller.switchSelectionMode();
        else if (evt.equals("Rubber mode"))
            controller.switchRubberMode();
        else if (evt.equals("Walk mode"))
            controller.switchWalkableMode();
        else if (evt.equals("Teleport"))
            userInterface.addPortal();
        else if (evt.equals("Undo"))
            controller.unstackUndo();
        else if (evt.equals("Redo"))
            controller.unstackRedo();
        else if (evt.equals("New"))
            userInterface.NewProject();
        else if (evt.equals("Play"))
            controller.startGame();
        else if (evt.equals("Load Tile")) {
            try {
                userInterface.LoadTile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                userInterface.OpenWorldFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}