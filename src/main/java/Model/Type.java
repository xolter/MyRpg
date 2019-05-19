package Model;

public enum Type {
    Grass("grass.png",1, 1),
    Sea("sea.png", 1, 1),
    Center("center.png",4, 4),
    House("house.png", 3, 4),
    Hero("hero.png", 1, 2);

    String name;
    int width;
    int height;

    Type(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}