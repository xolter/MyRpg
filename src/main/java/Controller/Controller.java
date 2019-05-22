package Controller;

import Model.Map;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import Model.Model;
import View.JPanelMap;
import View.UserInterface;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import static Model.Type.*;

public class Controller extends MouseInputAdapter implements ActionListener, ChangeListener{

    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    //private Point location;
    //private MouseEvent pressed;

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        //pressed = mouseEvent;
        //JLabel jLabel = (JLabel)mouseEvent.getSource();
        int x = mouseEvent.getX() / JPanelMap.getTileSize();
        int y = mouseEvent.getY() / JPanelMap.getTileSize();
        model.placeTile(x, y);
        System.out.println("pressed x : " + x + " y : " + y);
    }

    /*@Override
    public void mouseReleased(MouseEvent mouseEvent) {
        super.mouseReleased(mouseEvent);
        Component component = mouseEvent.getComponent();
        location = component.getLocation(location);
        int x = location.x + pressed.getX();
        int y = location.y + pressed.getY();

        System.out.println("released x : " + x + " y : " + y);
    }*/

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
    public void actionDelMap(int index) {
        model.delMap(index);
    }
}