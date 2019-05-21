package Model;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {
    int currentMap;
    ArrayList<Map> maps;

    public Model() {
        maps = new ArrayList<Map>();
    }

    public Map getCurrentMap() {
        return maps.get(currentMap);
    }

    public int getCurrentIndex() {
        return currentMap;
    }

    public void setCurrentMap(int index) {
        currentMap = index;

        setChanged();
        notifyObservers();
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public void addMap(String name, int width, int height) {
        Map map = new Map(name, width, height);
        maps.add(map);

        setChanged();
        notifyObservers();
    }

    public void addBackground(Type type) {
        System.out.println("Background curr  = " + getCurrentIndex());
        getCurrentMap().addBackground(type);

        setChanged();
        notifyObservers();
    }

    public void addForeground(Type type) {
        System.out.println("Foreground curr  = " + getCurrentIndex());
        getCurrentMap().addForeground(type);

        setChanged();
        notifyObservers();
    }
}