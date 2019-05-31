package Model;

public class Tile {
    private boolean walkable;
    private boolean empty;
    private boolean begin;
    private Type background;
    private Type foreground;

    public Tile(boolean walkable, boolean empty, boolean begin, Type background, Type foreground) {
        this.walkable = walkable;
        this.empty = empty;
        this.begin = begin;
        this.background = background;
        this.foreground = foreground;
    }

    public boolean isBegin() {
        return begin;
    }

    public void setBegin(boolean begin) {
        this.begin = begin;
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