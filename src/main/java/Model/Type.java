package Model;

public enum Type {
    Grass(1, 1),
    Sea(1, 1),
    Center(4, 4),
    House(3, 4),
    Hero(1, 2);

    int width;
    int height;

    Type(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}