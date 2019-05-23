package Model;

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

    public void addBackground(Type type, int x, int y) {
        tiles[x][y].setBackground(type);
        tiles[x][y].setWalkable(true);
        tiles[x][y].setEmpty(true);
        tiles[x][y].setBegin(false);
    }

    public void addForeground(Type type, int x, int y) {
        int w = type.getWidth();
        int h = type.getHeight();
        if (width < x + w || height < y + h)
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