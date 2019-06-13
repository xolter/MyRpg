package GameEngine.Model;

import java.awt.*;

public class Player {

    Point position;

    public Player(int x, int y) {
        this.position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }
}
