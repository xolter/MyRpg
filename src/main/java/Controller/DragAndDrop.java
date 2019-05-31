package Controller;

import View.JPanelMap;
import View.UserInterface;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DragAndDrop extends MouseInputAdapter {
    private Point location;
    private MouseEvent pressed;

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        pressed = mouseEvent;
        //JLabel jLabel = (JLabel)mouseEvent.getSource();
        System.out.println("pressed x : " + pressed.getX() + " y : " + pressed.getY());
    }

    /*@Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        Component component = mouseEvent.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + mouseEvent.getX();
        int y = location.y - pressed.getY() + mouseEvent.getY();
        component.setLocation(x, y);
        component.repaint();
    }*/

    /*@Override
    public void mouseReleased(MouseEvent mouseEvent) {
        super.mouseReleased(mouseEvent);
        Component component = mouseEvent.getComponent();
        location = component.getLocation(location);
        int x = location.x + pressed.getX();
        int y = location.y + pressed.getY();

        System.out.println("released x : " + x + " y : " + y);
    }*/
}
