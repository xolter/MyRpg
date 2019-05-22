package Model;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {
    private int currentMap;
    private boolean curTileIsBackground;
    private Type curentTile;
    private ArrayList<Map> maps;

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

    public void placeTile(int x, int y) {
        if (getCurrentMap() != null && curentTile != null) {
            if (curTileIsBackground) {
                getCurrentMap().addBackground(curentTile, x, y);
            }
            else {
                getCurrentMap().addForeground(curentTile, x, y);
            }
            setChanged();
            notifyObservers();
        }
    }

    public void setCurentTile(Type type, boolean isBackground) {
        this.curentTile = type;
        this.curTileIsBackground = isBackground;
    }
}