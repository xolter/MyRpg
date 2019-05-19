package Model;

public class Tile {
    private boolean walkable;
    private boolean empty;
    private Type background;
    private Type foreground;

    public Tile(boolean walkable, boolean empty, Type background, Type foreground) {
        this.walkable = walkable;
        this.empty = empty;
        this.background = background;
        this.foreground = foreground;
    }

    public Type getBackground() { return background; }

    public void setBackground(Type background) {
        this.background = background;
    }

    public Type getForeground() {
        return foreground;
    }

    public void setForeground(Type foreground) {
        this.foreground = foreground;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}