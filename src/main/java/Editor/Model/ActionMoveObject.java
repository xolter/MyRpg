package Editor.Model;

import java.awt.*;

public class ActionMoveObject extends Action {

    private Type type;
    private Point begin;
    private Point end;
    private boolean isBackground;
    private int mapIndex;

    ActionMoveObject(Model model, Type type, Point begin, Point end, int mapIndex) {
        super(model);
        this.type = type;
        this.begin = new Point(begin.x, begin.y);
        this.end = new Point(end.x, end.y);
        this.isBackground = type.isBackground();
        this.mapIndex = mapIndex;
    }

    @Override
    public void undo() {
        Map map = model.getMaps().get(mapIndex);
        if (isBackground) {
            map.deleteBackground(end.x, end.y);
            map.addBackground(type, begin.x, begin.y);
        }
        else {
            Tile[][] tiles = map.getTiles();
            Point beginObj = tiles[end.x][end.y].getBegin();
            map.deleteForeground(beginObj.x, beginObj.y);
            map.addForeground(type, begin.x, begin.y);
        }
    }

    @Override
    public void redo() {
        Map map = model.getMaps().get(mapIndex);
        if (isBackground) {
            map.deleteBackground(begin.x, begin.y);
            map.addBackground(type, end.x, end.y);
        }
        else {
            Tile[][] tiles = map.getTiles();
            Point beginObj = tiles[begin.x][begin.y].getBegin();
            map.deleteForeground(beginObj.x, beginObj.y);
            map.addForeground(type, end.x, end.y);
        }
    }
}
