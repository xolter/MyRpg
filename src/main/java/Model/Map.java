package Model;

import java.util.Observable;

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
                tiles[i][j] = new Tile(false, true, false, null, null);
            }
        }
    }

    public void addBackground(Type type) {
        tiles[0][0].setBackground(type);
        tiles[0][0].setWalkable(true);
        tiles[0][0].setEmpty(true);
        tiles[0][0].setBegin(false);
    }

    public void addForeground(Type type) {
        int w = type.getWidth();
        int h = type.getHeight();
        if (width < w || height < h)
            return;
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                tiles[i][j].setForeground(type);
                tiles[i][j].setWalkable(false);
                tiles[i][j].setEmpty(false);
                tiles[i][j].setBegin(false);
            }
        }
        tiles[0][0].setBegin(true);
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