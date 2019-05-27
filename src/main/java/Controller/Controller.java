package Controller;

import javax.swing.event.MouseInputAdapter;
import Model.Model;
import View.JPanelMap;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import static Model.Type.*;

public class Controller extends MouseInputAdapter implements ActionListener, ChangeListener{

    private Model model;
    private boolean selectionMode;
    private Point location;
    private MouseEvent pressed;

    public Controller(Model model) {
        this.model = model;
        this.selectionMode = false;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if (!selectionMode)
            addAndRemove(mouseEvent);
        //System.out.println("pressed x : " + x + " y : " + y);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        if (!selectionMode)
            addAndRemove(mouseEvent);
        //System.out.println("dragged x : " + x + " y : " + y);
    }

    public void addAndRemove(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / JPanelMap.getTileSize();
        int y = mouseEvent.getY() / JPanelMap.getTileSize();
        if (x < 0 || y < 0 || x >= model.getCurrentMap().getWidth() || y >= model.getCurrentMap().getHeight())
            return;
        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
            model.placeTile(x, y);
        }
        else {
            model.removeTile(x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String evt = actionEvent.getActionCommand();
        if (evt.equals("grass.png"))
            model.setCurentTile(Grass, true);
        else if (evt.equals("sea.png"))
            model.setCurentTile(Sea, true);
        else if (evt.equals("center.png"))
            model.setCurentTile(Center, false);
        else if (evt.equals("house.png"))
            model.setCurentTile(House, false);
        else if (evt.equals("hero.png"))
            model.setCurentTile(Hero, false);
        else if (evt.equals("bluehair.png"))
            model.setCurentTile(Blue, false);
        else if (evt.equals("sand.png"))
            model.setCurentTile(Sand, true);
        else if (evt.equals("brick.png"))
            model.setCurentTile(Brick, true);
        else
            System.exit(0);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        JTabbedPane mapTabs = (JTabbedPane) changeEvent.getSource();
        model.setCurrentMap(mapTabs.getSelectedIndex());
    }

    public void actionAddMap(String name, int width, int height) {
        model.addMap(name, width, height);
    }
    public void actionDelMap() {
        model.delMap();
    }
    public void actionResetMap() {
        model.resetMap();
    }

    public void switchSelectionMode() {
        selectionMode = !selectionMode;
    }
}