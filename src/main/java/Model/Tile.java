package Model;

public class Tile {
    private boolean walkable;
    private boolean empty;
    private Type type;

    public boolean is_walkable() {
        return walkable;
    }

    public boolean is_empty() {
        return empty;
    }
}
