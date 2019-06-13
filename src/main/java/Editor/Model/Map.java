package Editor.Model;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private String name;
    private int width;
    private int height;
    private Tile[][] tiles;
    private ArrayList<Action> undoStack;
    private ArrayList<Action> redoStack;
    private ArrayList<Portal> portals;

    public Map(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.undoStack = new ArrayList<Action>();
        this.redoStack = new ArrayList<Action>();
        this.portals = new ArrayList<Portal>();
        this.tiles = new Tile[width][height];
        initTiles();
    }

    public void initTiles() {
        for(int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                tiles[i][j] = new Tile(true, true, null, null);
            }
        }
    }

    public void addBackground(Type type, int x, int y) {
        tiles[x][y].setBackground(type);
    }

    public boolean isNotEmpty(int x, int y, int w, int h) {
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                if (!tiles[x + i][y + j].isEmpty())
                    return true;
            }
        }
        return false;
    }

    public void emptyArea(int x, int y, int w, int h, boolean bool) {
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                tiles[x + i][y + j].setEmpty(bool);
            }
        }
    }

    public void addForeground(Type type, int x, int y) {
        int w = type.getWidth();
        int h = type.getHeight();
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                tiles[x + i][y + j].setForeground(type);
                tiles[x + i][y + j].setWalkable(false);
                tiles[x + i][y + j].setEmpty(false);
                tiles[x + i][y + j].setBegin(x, y);
            }
        }
    }

    public void deleteForeground(int x, int y) {
        Type type = tiles[x][y].getForeground();
        int w = type.getWidth();
        int h = type.getHeight();
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                tiles[x + i][y + j].setForeground(null);
                tiles[x + i][y + j].setWalkable(true);
                tiles[x + i][y + j].setEmpty(true);
                tiles[x + i][y + j].setBegin(-1, -1);
            }
        }
    }

    public void selectTiles(Point p1, Point p2, boolean bool) {
        for (int i = p1.x; i <= p2.x; ++i) {
            for (int j = p1.y; j <= p2.y; ++j) {
                tiles[i][j].setSelected(bool);
            }
        }
    }

    public void replaceAllSelectedTiles(Type type, Point p1, Point p2) {
        for (int i = p1.x; i <= p2.x; ++i) {
            for (int j = p1.y; j <= p2.y; ++j) {
                tiles[i][j].setBackground(type);
            }
        }
    }

    public void removeAllSelectedTiles(Point p1, Point p2) {
        for (int i = p1.x; i <= p2.x; ++i) {
            for (int j = p1.y; j <= p2.y; ++j) {
                tiles[i][j].setBackground(null);
                tiles[i][j].setForeground(null);
                tiles[i][j].setWalkable(true);
                tiles[i][j].setEmpty(true);
                tiles[i][j].setBegin(-1, -1);
            }
        }
    }

    public void selectObject(Point point, boolean bool) {
        Tile tile = tiles[point.x][point.y];
        if (tile.getForeground() != null) {
            Point begin = tile.getBegin();
            Type type = tiles[begin.x][begin.y].getForeground();
            int w = type.getWidth();
            int h = type.getHeight();
            for (int i = 0; i < w; ++i) {
                for (int j = 0; j < h; ++j) {
                    tiles[begin.x + i][begin.y + j].setObjectSelected(bool);
                }
            }
        }
        else if (tile.getBackground() != null) {
            tile.setObjectSelected(bool);
        }
    }

    public Tile[][] getArea(Point begin, Point end) {
        int w = end.x - begin.x;
        int h = end.y - begin.y;
        Tile[][] res = new Tile[w + 1][h + 1];

        for (int i = 0; i <= w; ++i) {
            for (int j = 0; j <= h; ++j) {
                Tile tile = tiles[begin.x + i][begin.y + j];
                Point start = tile.getBegin();
                res[i][j] = new Tile(tile.isWalkable(), tile.isEmpty(), tile.getBackground(), tile.getForeground());
                res[i][j].setBegin(start.x, start.y);
            }
        }
        return res;
    }

    public void setArea(Point begin, Point end, Tile[][] tilesArea) {
        int w = end.x - begin.x;
        int h = end.y - begin.y;
        for (int i = 0; i <= w; ++i) {
            for (int j = 0; j <= h; ++j) {
                Tile tile = tilesArea[i][j];
                Point point = tile.getBegin();
                tiles[begin.x + i][begin.y + j].setWalkable(tile.isWalkable());
                tiles[begin.x + i][begin.y + j].setEmpty(tile.isEmpty());
                tiles[begin.x + i][begin.y + j].setBackground(tile.getBackground());
                tiles[begin.x + i][begin.y + j].setForeground(tile.getForeground());
                tiles[begin.x + i][begin.y + j].setBegin(point.x, point.y);
            }
        }
    }

    public Type getPrevBackType(int x, int y) {
        return tiles[x][y].getBackground();
    }

    public void deleteBackground(int x, int y) {
        tiles[x][y].setBackground(null);
    }

    public String getName() {
        return name;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<Action> getUndoStack() {
        return undoStack;
    }

    public ArrayList<Action> getRedoStack() {
        return redoStack;
    }

    public void pushIntoUndoStack(Action action) {
        undoStack.add(action);
    }

    public void pushIntoRedoStack(Action action) {
        redoStack.add(action);
    }

    public void resetRedoStack() {
        redoStack.clear();
    }

    public Action popUndoStack() {
        return undoStack.remove(undoStack.size() - 1);
    }

    public Action popRedoStack() {
        return redoStack.remove(redoStack.size() - 1);
    }

    public void addPortal(String mapName, Point src, Point dst) {
        Portal portal = new Portal(mapName, src, dst);
        portals.add(portal);
    }

    public ArrayList<Portal> getPortals() {
        return portals;
    }

    public Portal getPortal(int x, int y) {
        for (Portal portal : portals) {
            Point src = portal.getSrc();
            if (src.x == x && src.y == y)
                return portal;
        }
        return null;
    }

    public void removePortal(Portal portal) {
        portals.remove(portal);
    }
}