package Model;

import java.awt.*;

public class Map {
    private String name;
    private int width;
    private int height;
    private Tile[][] tiles;

    public Map(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        initTiles();
    }

    public void initTiles() {
        for(int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                tiles[i][j] = new Tile(true, true, false, null, null);
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

    public void addForeground(Type type, int x, int y) {
        int w = type.getWidth();
        int h = type.getHeight();
        if (width < x + w || height < y + h || isNotEmpty(x, y, w, h))
            return;
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                tiles[x + i][y + j].setForeground(type);
                tiles[x + i][y + j].setWalkable(false);
                tiles[x + i][y + j].setEmpty(false);
                tiles[x + i][y + j].setBegin(false);
            }
        }
        tiles[x][y].setBegin(true);
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
            }
        }
        tiles[x][y].setBegin(false);

    }

    public void selectTiles(Point p1, Point p2, boolean bool) {
        for (int i = p1.x; i <= p2.x; ++i) {
            for (int j = p1.y; j <= p2.y; ++j) {
                tiles[i][j].setSelected(bool);
            }
        }
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
}