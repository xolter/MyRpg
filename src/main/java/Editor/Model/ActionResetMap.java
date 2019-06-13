package Editor.Model;

import java.awt.*;

public class ActionResetMap extends Action {

    private Tile[][] prevTiles;
    private Point begin;
    private Point end;
    private int mapIndex;

    ActionResetMap(Model model, int mapIndex) {
        super(model);
        this.begin = new Point(0, 0);
        this.mapIndex = mapIndex;
        Map map = model.getMaps().get(mapIndex);
        this.end = new Point(map.getWidth() - 1, map.getHeight() - 1);
        initPrevTiles();
    }

    public void initPrevTiles() {
        Map map = model.getMaps().get(mapIndex);
        prevTiles = map.getArea(begin, end);
    }

    @Override
    public void undo() {
        Map map = model.getMaps().get(mapIndex);
        map.setArea(begin, end, prevTiles);
    }

    @Override
    public void redo() {
        Map map = model.getMaps().get(mapIndex);
        map.initTiles();
    }
}
