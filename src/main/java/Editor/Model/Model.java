package Editor.Model;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;

public class Model extends Observable {
    private Hashtable<String, Type> tilesTable;
    private int currentMap;
    private boolean curTileIsBackground;
    private Type currentTile;
    private ArrayList<Map> maps;
    private boolean walkableMode;

    public Model() {
        maps = new ArrayList<Map>();
        tilesTable = new Hashtable<String, Type>();
        walkableMode = false;
    }

    public Map getCurrentMap() {
        if (maps.isEmpty() || currentMap < 0)
            return null;
        return maps.get(currentMap);
    }

    public int getCurrentIndex() {
        return currentMap;
    }

    public void setCurrentMap(int index) {
        currentMap = index;

        setChanged();
        notifyObservers("set current index");
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public Type getType(String tileName) {
        return tilesTable.get(tileName);
    }

    public boolean isWalkableMode() {
        return walkableMode;
    }

    public void switchWalkableMode() {
        walkableMode = !walkableMode;
        setChanged();
        notifyObservers("switch walkable mode");
    }

    public void unstackUndo() {
        Map map = getCurrentMap();
        if (map != null && map.getUndoStack().size() > 0) {
            Action action = map.popUndoStack();
            if (action != null) {
                action.undo();
                map.pushIntoRedoStack(action);
                setChanged();
                notifyObservers("unstack undo");
            }
        }
    }

    public void unstackRedo() {

        Map map = getCurrentMap();
        if (map != null && map.getRedoStack().size() > 0) {
            Action action = map.popRedoStack();
            if (action != null) {
                action.redo();
                map.pushIntoUndoStack(action);
                setChanged();
                notifyObservers("unstack redo");
            }
        }
    }

    public void addMap(String name, int width, int height) {
        Map map = new Map(name, width, height);
        maps.add(map);

        setChanged();
        notifyObservers("add map");
    }

    public void resetMap()
    {
        Map map = getCurrentMap();
        if (map != null) {
            ActionResetMap resetMap = new ActionResetMap(this, currentMap);
            map.pushIntoUndoStack(resetMap);
            map.resetRedoStack();
            map.initTiles();
            setChanged();
            notifyObservers("reset map");
        }
    }

    public void delMap()
    {
        maps.remove(currentMap);
        if (currentMap + 1 > maps.size())
            currentMap--;
        setChanged();
        notifyObservers("delete map");
    }

    private boolean notValidPlace(Map map, int x, int y, Type tile) {
        int width = map.getWidth();
        int height = map.getHeight();
        int w = tile.getWidth();
        int h = tile.getHeight();
        return (width < x + w || height < y + h || map.isNotEmpty(x, y, w, h));
    }

    private boolean notValidPlace(Map map, int x, int y, Type tile, Point selfBegin) {
        int width = map.getWidth();
        int height = map.getHeight();
        int w = tile.getWidth();
        int h = tile.getHeight();
        map.emptyArea(selfBegin.x, selfBegin.y, w, h, true);
        boolean res = (width < x + w || height < y + h || map.isNotEmpty(x, y, w, h));
        map.emptyArea(selfBegin.x, selfBegin.y, w, h, false);
        return res;
    }

    public void placeTile(int x, int y) {
        Map map = getCurrentMap();
        if (map != null && currentTile != null && currentTile != map.getPrevBackType(x, y)) {
            Type prevTile = map.getPrevBackType(x, y);
            if (curTileIsBackground) {
                ActionAddTile addTile = new ActionAddTile(this, prevTile, currentTile, x, y, currentMap);
                map.pushIntoUndoStack(addTile);
                map.resetRedoStack();
                map.addBackground(currentTile, x, y);
            }
            else {
                if (notValidPlace(map, x, y, currentTile))
                    return;
                Tile[][] tiles = map.getTiles();
                Point begin = tiles[x][y].getBegin();
                if (begin.x > -1 && currentTile != tiles[begin.x][begin.y].getForeground()) {
                    Type prevObj = map.getPrevBackType(begin.x, begin.y);
                    ActionAddTile addTile = new ActionAddTile(this, prevObj, currentTile, x, y, currentMap);
                    map.pushIntoUndoStack(addTile);
                    map.resetRedoStack();
                    getCurrentMap().addForeground(currentTile, x, y);
                }
                else {
                    ActionAddTile addTile = new ActionAddTile(this, null, currentTile, x, y, currentMap);
                    map.pushIntoUndoStack(addTile);
                    map.resetRedoStack();
                    getCurrentMap().addForeground(currentTile, x, y);
                }
            }
            setChanged();
            notifyObservers("place tile 1");
        }
    }

    public void placeTile(Type type, Boolean isback, int x, int y, int index)
    {
        if (getCurrentMap() != null && type != null) {
            if (isback)
                maps.get(index).addBackground(type, x, y);
            else
                maps.get(index).addForeground(type, x, y);
            setChanged();
            notifyObservers("place tile 2");
        }
    }

    public void removeTile(int x, int y) {
        Map map = getCurrentMap();
        if (map != null) {
            Tile[][] tiles = map.getTiles();
            Portal portal = map.getPortal(x, y);
            if (portal != null) {
                map.removePortal(portal);
                setChanged();
                notifyObservers("remove tile");
                return;
            }
            else if (tiles[x][y].getForeground() != null) {
                ActionDeleteTile delTile = new ActionDeleteTile(this, tiles[x][y].getForeground(), x, y, currentMap);
                map.pushIntoUndoStack(delTile);
                map.resetRedoStack();
                Point begin = tiles[x][y].getBegin();
                map.deleteForeground(begin.x, begin.y);
            }
            else if(tiles[x][y].getBackground() != null) {

                ActionDeleteTile delTile = new ActionDeleteTile(this, tiles[x][y].getBackground(), x, y, currentMap);
                map.pushIntoUndoStack(delTile);
                map.resetRedoStack();
                map.deleteBackground(x, y);
            }
            setChanged();
            notifyObservers("remove tile");
        }

    }

    public void select(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectTiles(p1, p2, true);
            setChanged();
            notifyObservers("select");
        }
    }

    public void resetSelectedTiles(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectTiles(p1, p2, false);
            setChanged();
            notifyObservers("reset selected tiles");
        }
    }

    public void resetSelectedObject(Point p1) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectObject(p1, false);
            setChanged();
            notifyObservers("reset selected object");
        }
    }

    public void setCurrentTile(Type type, boolean isBackground) {
        this.currentTile = type;
        this.curTileIsBackground = isBackground;
    }

    public void replaceAllSelectedTiles(Type type, Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null && p1.x > -1 && p2.x >= p1.x && p2.y >= p1.y) {
            ActionOnSelectedTiles selectedTiles = new ActionOnSelectedTiles(this, p1, p2, type, currentMap);
            map.pushIntoUndoStack(selectedTiles);
            map.resetRedoStack();
            map.replaceAllSelectedTiles(type, p1, p2);
            setChanged();
            notifyObservers("replace selected tiles");
        }
    }
    public void removeAllSelectedTiles(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            ActionOnSelectedTiles selectedTiles = new ActionOnSelectedTiles(this, p1, p2, null, currentMap);
            map.pushIntoUndoStack(selectedTiles);
            map.resetRedoStack();
            map.removeAllSelectedTiles(p1, p2);
            setChanged();
            notifyObservers("remove selected tiles");
        }
    }

    public void selectObject(Point point) {
        Map map = getCurrentMap();
        if (map != null) {
            map.selectObject(point, true);
            setChanged();
            notifyObservers("select object");
        }
    }

    public void moveObject(Point p1, Point p2) {
        Map map = getCurrentMap();
        if (map != null) {
            Type foreground = map.getTiles()[p1.x][p1.y].getForeground();
            Type background = map.getTiles()[p1.x][p1.y].getBackground();
            Point begin = map.getTiles()[p1.x][p1.y].getBegin();
            if (foreground != null && !notValidPlace(map, p2.x, p2.y, foreground, begin)) {

                ActionMoveObject moveObj = new ActionMoveObject(this, foreground, begin, p2, currentMap);
                map.pushIntoUndoStack(moveObj);
                map.resetRedoStack();
                map.deleteForeground(begin.x, begin.y);
                map.addForeground(foreground, p2.x, p2.y);
            }
            else if (background != null){
                ActionMoveObject moveObj = new ActionMoveObject(this, background, p1, p2, currentMap);
                map.pushIntoUndoStack(moveObj);
                map.resetRedoStack();
                map.deleteBackground(p1.x, p1.y);
                map.addBackground(background, p2.x, p2.y);
            }
            setChanged();
            notifyObservers("move object");
        }
    }

    public void putIntoTilesTable(String tileName, int width, int height, boolean isBackground, boolean isNPC) {
        Type type = new Type(tileName, width, height, isBackground, isNPC);
        tilesTable.put(tileName, type);
    }

    public void setWalkable(Point point, boolean walkable) {
        Map map = getCurrentMap();
        if (map != null) {
            map.getTiles()[point.x][point.y].setWalkable(walkable);
            setChanged();
            notifyObservers("set walkable");
        }
    }

    public void addPortal(String mapName, Point src, Point dst) {
        Map map = getCurrentMap();
        if (map != null) {
            if (src.x < 0 || src.x >= map.getWidth() || src.y < 0 || src.y >= map.getHeight())
                return;
            int i = 0;
            while (i < maps.size()) {
                if (maps.get(i).getName().equals(mapName))
                    break;
                ++i;
            }
            if (i < maps.size()) {
                Map destMap = maps.get(i);
                if (dst.x < 0 || dst.x >= destMap.getWidth() || dst.y < 0 || dst.y >= destMap.getHeight())
                    return;
                map.addPortal(mapName, src, dst);
                setChanged();
                notifyObservers("portal added");
            }
        }
    }

    private String SetFilename()
    {
        JTextField file = new JTextField(16);
        JPanel filesaver_pan = new JPanel();
        filesaver_pan.add(new JLabel("Filename:"));
        filesaver_pan.add(file);
        int result = JOptionPane.showConfirmDialog(null, filesaver_pan,
                "Save to World file", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
            return file.getText();
        else
            return "";
    }

    public void ToWorldFile() throws IOException {
        String filename = SetFilename();
        if (filename.equals(""))
            return;
        FileWriter worldfile = new FileWriter(filename + ".wrld");
        PrintWriter printer = new PrintWriter(worldfile);
        for (Map map : maps)
        {
            Tile[][] tiles = map.getTiles();
            printer.println(map.getName() + " " + map.getWidth() + " " + map.getHeight());
            printer.println("{");
            for (int i = 0; i < map.getWidth(); i++)
            {
                for (int j = 0; j < map.getHeight(); j++)
                {
                    int walk = tiles[i][j].isWalkable() ? 1 : 0;
                    Type backtile = tiles[i][j].getBackground();
                    if (backtile != null) {
                        printer.println("   " + "background " + j + " " + i + " " + backtile.getName() + " " + walk);
                    }
                    Type foretile = tiles[i][j].getForeground();
                    if (foretile != null && tiles[i][j].isBegin(i, j)) {
                        printer.println("   " + "foreground " + j + " " + i + " " + foretile.getName() + " " + walk);
                    }
                }
            }
            printer.println("portals");
            for (Portal portal : map.getPortals()) {
                Point src = portal.getSrc();
                Point dst = portal.getDst();
                printer.println(src.x + " " + src.y + " " + dst.x + " " + dst.y + " " + portal.getMapName());
            }
            printer.println("}");
            printer.println();
        }
        printer.close();
    }
}