package Controller;

import Model.Map;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import static Model.Type.*;

public class Controller extends MouseInputAdapter implements ActionListener{

    static Map map;
    Point location;
    MouseEvent pressed;

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        pressed = mouseEvent;
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

    public Controller(Map map) {
        this.map = map;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String evt = actionEvent.getActionCommand();
        if (evt.equals("grass.png"))
            map.addBackground(Grass);
        else if (evt.equals("sea.png"))
            map.addBackground(Sea);
        else if (evt.equals("center.png"))
            map.addForeground(Center);
        else if (evt.equals("house.png"))
            map.addForeground(House);
        else
            map.addForeground(Hero);

    }
}