package Model;

import java.util.ArrayList;
import java.util.Observable;

public class Map extends Observable {
    private String name;
    private int width;
    private int height;
    private ArrayList<Tile> tiles;

    public void add_foreground_object(String type) {
        //Definitely not the final code
        this.name = type;
        setChanged();
        notifyObservers();
    }

    public String get_name() {
        return name;
    }
}