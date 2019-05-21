package Controller;

import Model.Map;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import Model.Model;
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
    Point location;
    MouseEvent pressed;

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        pressed = mouseEvent;
        System.out.println(pressed.getX());
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        Component component = mouseEvent.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + mouseEvent.getX();
        int y = location.y - pressed.getY() + mouseEvent.getY();
        component.setLocation(x, y);
        //component.repaint();
    }

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String evt = actionEvent.getActionCommand();
        if (evt.equals("grass.png"))
            model.addBackground(Grass);
        else if (evt.equals("sea.png"))
            model.addBackground(Sea);
        else if (evt.equals("center.png"))
            model.addForeground(Center);
        else if (evt.equals("house.png"))
            model.addForeground(House);
        else if (evt.equals("hero.png"))
            model.addForeground(Hero);
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
}