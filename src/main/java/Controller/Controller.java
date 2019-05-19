package Controller;

import Model.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Model.Type.*;

public class Controller implements ActionListener{

    static Map map;

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