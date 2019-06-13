package Editor.Model;

import java.awt.*;

public class Portal {
    private Point src;
    private Point dst;
    private String mapName;

    Portal(String mapName, Point src, Point dst) {
        this.src = src;
        this.dst = dst;
        this.mapName = mapName;
    }

    public Point getSrc() {
        return src;
    }

    public Point getDst() {
        return dst;
    }

    public String getMapName() {
        return mapName;
    }
}
