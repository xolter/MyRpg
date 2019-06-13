package Editor.Model;

public class ActionAddTile extends Action {

    private Type prevType;
    private Type newType;
    private int x;
    private int y;
    private int mapIndex;
    private boolean isBackground;

    ActionAddTile(Model model, Type prevType, Type newType, int x, int y, int mapIndex) {
        super(model);
        this.prevType = prevType;
        this.newType = newType;
        this.x = x;
        this.y = y;
        this.mapIndex = mapIndex;
        this.isBackground = newType.isBackground();
    }

    @Override
    public void undo() {
        Map map = model.getMaps().get(mapIndex);
        if (prevType == null) {
            if (isBackground)
                map.deleteBackground(x, y);
            else
                map.deleteForeground(x, y);
        }
        else {
            if (isBackground)
                map.addBackground(prevType, x, y);
            else
                map.addForeground(prevType, x, y);
        }
    }

    @Override
    public void redo() {
        Map map = model.getMaps().get(mapIndex);
        if (isBackground)
            map.addBackground(newType, x, y);
        else
            map.addForeground(newType, x, y);
    }
}