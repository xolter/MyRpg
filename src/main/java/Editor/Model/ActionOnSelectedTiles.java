package Editor.Model;

import java.awt.*;

public class ActionOnSelectedTiles extends Action {

    private Tile[][] prevTiles;
    private Type type;
    private Point begin;
    private Point end;
    private int mapIndex;

    ActionOnSelectedTiles(Model model, Point begin, Point end, Type type, int mapIndex) {
        super(model);
        this.begin = new Point(begin.x, begin.y);
        this.end = new Point(end.x, end.y);
        this.type = type;
        this.mapIndex = mapIndex;
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
        if (type != null)
            map.replaceAllSelectedTiles(type, begin, end);
        else
            map.removeAllSelectedTiles(begin, end);
    }
}
