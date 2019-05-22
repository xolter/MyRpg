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
        if (maps.isEmpty())
            return null;
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

    public void delMap(int index)
    {
        maps.get(currentMap).initTiles();
        //maybe try to updateMapView ?
        maps.remove(index);

        setChanged();
        notifyObservers();
    }

    public void addBackground(Type type) {
        if (getCurrentMap() != null) {
            getCurrentMap().addBackground(type);
            setChanged();
            notifyObservers();
        }
    }

    public void addForeground(Type type) {
        if (getCurrentMap() != null) {
            getCurrentMap().addForeground(type);
            setChanged();
            notifyObservers();
        }
    }
}