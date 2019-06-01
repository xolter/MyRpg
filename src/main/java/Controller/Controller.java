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
import java.io.IOException;

import static Model.Type.*;

public class Controller extends MouseInputAdapter implements ActionListener, ChangeListener{

    private Model model;
    private boolean selectionMode;
    private boolean rubberMode;
    private Point pressed;
    private Point released;

    public Controller(Model model) {
        this.model = model;
        this.selectionMode = false;
        this.rubberMode = false;
        this.pressed = new Point();
        this.released = new Point();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if (pressed.x > -1 && pressed.y > -1 && released.x > -1 && released.y > -1)
            model.resetSelectedTiles(pressed, released);
        if (!setPosition(mouseEvent, pressed))
            return;
        if (!selectionMode)
            addAndRemove();
        //System.out.println("pressed x : " + pressed.x + " y : " + pressed.y);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        if (selectionMode)
            return;
        else {
            if (!setPosition(mouseEvent, pressed))
                return;
            addAndRemove();
        }
        //System.out.println("dragged x : " + x + " y : " + y);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (selectionMode && pressed.x > -1 && pressed.y > -1) {
            if (setPosition(mouseEvent, released) && released.x >= pressed.x && released.y >= pressed.y) {
                model.select(pressed, released);
                if (rubberMode)
                    model.resetGroupTile(pressed, released);
            }
            else {
                pressed.setLocation(-1, -1);
                released.setLocation(-1, -1);
            }
        }
        //System.out.println("released x : " + released.x + " y : " + released.y);
    }

    public boolean setPosition(MouseEvent mouseEvent, Point point) {
        int x = mouseEvent.getX() / JPanelMap.getTileSize();
        int y = mouseEvent.getY() / JPanelMap.getTileSize();
        if (x < 0 || y < 0 || x >= model.getCurrentMap().getWidth() || y >= model.getCurrentMap().getHeight()) {
            point.setLocation(-1, -1);
            return false;
        }
        point.setLocation(x, y);
        return true;
    }

    public void addAndRemove() {
        if (rubberMode) {
            model.removeTile(pressed.x, pressed.y);
        }
        else {
            model.placeTile(pressed.x, pressed.y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String evt = actionEvent.getActionCommand();
        if (evt.equals("grass.png")) {
            if (selectionMode)
                model.setGroupTile(Grass, pressed, released);
            else
                model.setCurentTile(Grass, true);
        }
        else if (evt.equals("sea.png")) {
            if (selectionMode)
                model.setGroupTile(Sea, pressed, released);
            else
                model.setCurentTile(Sea, true);
        }
        else if (evt.equals("sand.png")) {
            if (selectionMode)
                model.setGroupTile(Sand, pressed, released);
            model.setCurentTile(Sand, true);
        }
        else if (evt.equals("brick.png")) {
            if (selectionMode)
                model.setGroupTile(Brick, pressed, released);
            else
                model.setCurentTile(Brick, true);
        }
        else if (evt.equals("center.png"))
            model.setCurentTile(Center, false);
        else if (evt.equals("house.png"))
            model.setCurentTile(House, false);
        else if (evt.equals("hero.png"))
            model.setCurentTile(Hero, false);
        else if (evt.equals("bluehair.png"))
            model.setCurentTile(Blue, false);
        else if (evt.equals("Save")) {
            try {
                model.ToWorldFile("test");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        if (!selectionMode && pressed.x > -1 && pressed.y > -1 && released.x > -1 && released.y > -1)
            model.resetSelectedTiles(pressed, released);
    }

    public void switchRubberMode() {
        rubberMode = !rubberMode;
    }
}