package Editor.Model;

import java.awt.*;

public class Tile {
    private boolean walkable;
    private boolean empty;
    private Point begin;
    private boolean selected;
    private boolean objectSelected;
    private Type background;
    private Type foreground;

    public Tile(boolean walkable, boolean empty, Type background, Type foreground) {
        this.walkable = walkable;
        this.empty = empty;
        begin = new Point(-1, -1);
        this.background = background;
        this.foreground = foreground;
        this.selected = false;
        this.objectSelected = false;
    }

    public boolean isBegin(int x, int y) {
        return (begin.x == x && begin.y == y);
    }

    public void setBegin(int x, int y) {
        begin.setLocation(x, y);
    }

    public Point getBegin() {
        return begin;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isObjectSelected() {
        return objectSelected;
    }

    public void setObjectSelected(boolean objectSelected) {
        this.objectSelected = objectSelected;
    }
}