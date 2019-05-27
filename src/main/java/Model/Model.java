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

    public void resetMap()
    {
        maps.get(currentMap).initTiles();
        setChanged();
        notifyObservers();
    }

    public void delMap()
    {
        resetMap();
        maps.remove(currentMap);
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

    public void removeTile(int x, int y) {
        Map map = getCurrentMap();
        if (map != null) {
            Tile[][] tiles = map.getTiles();
            if (tiles[x][y].getForeground() != null) {
                map.deleteForeground(x, y);
            }
            else if(tiles[x][y].getBackground() != null) {
                map.deleteBackground(x, y);
            }
        }
        setChanged();
        notifyObservers();
    }

    public void setCurentTile(Type type, boolean isBackground) {
        this.curentTile = type;
        this.curTileIsBackground = isBackground;
    }
}