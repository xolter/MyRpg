package Model;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void select(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectTiles(p1, p2, true);
        }
        setChanged();
        notifyObservers();
    }

    public void resetSelectedTiles(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectTiles(p1, p2, false);
            map.selectObject(p1, false);
        }
        setChanged();
        notifyObservers();
    }

    public void setCurentTile(Type type, boolean isBackground) {
        this.curentTile = type;
        this.curTileIsBackground = isBackground;
    }

    public void replaceAllSelectedTiles(Type type, Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null && p1.x > -1) {
            map.replaceAllSelectedTiles(type, p1, p2);
        }
        setChanged();
        notifyObservers();
    }
    public void removeAllSelectedTiles(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            map.removeAllSelectedTiles(p1, p2);
        }
        setChanged();
        notifyObservers();
    }

    public void selectObject(Point point) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectObject(point, true);
        }
        setChanged();
        notifyObservers();
    }

    public void ToWorldFile(String filename) throws IOException {
        FileWriter worldfile = new FileWriter(filename + ".wrld");
        PrintWriter printer = new PrintWriter(worldfile);
        for (Map map : maps)
        {
            Tile[][] tiles = map.getTiles();
            printer.println(map.getName() + " " + map.getWidth() + " " + map.getHeight());
            printer.println("{");
            for (int i = 0; i < map.getWidth(); i++)
            {
                for (int j = 0; j < map.getHeight(); j++)
                {
                    Type backtile = tiles[i][j].getBackground();
                    if (backtile != null)
                    {
                        printer.println("   " + "background x=" + j + " y=" + i);
                        printer.println("   " + backtile.getName() + " " + backtile.getWidth() + " " + backtile.getHeight());
                        printer.println();
                    }

                    Type foretile = tiles[i][j].getForeground();
                    if (foretile != null)
                    {
                        printer.println("   " + "foreground x=" + j + " y=" + i);
                        printer.println("   " + foretile.getName() + " " + foretile.getWidth() + " " + foretile.getHeight());
                        printer.println();
                    }
                }
            }
            printer.println("}");
            printer.println();
        }
        printer.close();
    }
}