package Editor.Model;

import java.awt.*;

public class ActionDeleteTile extends Action{

    private Type prevType;
    private int x;
    private int y;
    private Point begin;
    private int mapIndex;
    private boolean isBackground;

    ActionDeleteTile(Model model, Type prevType, int x, int y, int mapIndex) {
        super(model);
        this.prevType = prevType;
        this.x = x;
        this.y = y;
        this.mapIndex = mapIndex;
        this.isBackground = prevType.isBackground();
        begin = new Point();
        setBegin();
    }

    private void setBegin() {
        Map map = model.getMaps().get(mapIndex);
        Tile[][] tiles = map.getTiles();
        Point tmp = tiles[x][y].getBegin();
        begin.setLocation(tmp.x, tmp.y);
    }

    @Override
    public void undo() {

        Map map = model.getMaps().get(mapIndex);
        if (isBackground) {

            map.addBackground(prevType, x, y);
        }
        else {
            map.addForeground(prevType, begin.x, begin.y);
        }
    }

    @Override
    public void redo() {
        Map map = model.getMaps().get(mapIndex);
        if (isBackground)
            map.deleteBackground(x, y);
        else
            map.deleteForeground(begin.x, begin.y);
    }
}