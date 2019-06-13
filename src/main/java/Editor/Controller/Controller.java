package Editor.Controller;

import javax.swing.event.MouseInputAdapter;

import Editor.Model.Map;
import Editor.Model.Model;
import Editor.View.JPanelMap;
import Editor.Model.Type;
import GameEngine.Game;
import GameEngine.View.GameView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Controller extends MouseInputAdapter implements ActionListener, ChangeListener{

    private Model model;
    private boolean selectionMode;
    private boolean rubberMode;
    private boolean walkableMode;
    private boolean objectMoving;
    private Point pressed;
    private Point released;

    public Controller(Model model) {
        this.model = model;
        this.selectionMode = false;
        this.rubberMode = false;
        this.walkableMode = false;
        this.objectMoving = false;
        this.pressed = new Point();
        this.released = new Point();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        objectMoving = false;
        if (pressed.x > -1 && pressed.y > -1 && released.x > -1 && released.y > -1) {
            model.resetSelectedTiles(pressed, released);
        }
        if (!setPosition(mouseEvent, pressed))
            return;
        if (walkableMode) {
            setWalkableTiles(mouseEvent);
        }
        else if (!selectionMode)
            addAndRemove();
        else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            objectMoving = true;
            model.selectObject(pressed);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        if (!walkableMode && selectionMode)
            return;
        else {
            if (!setPosition(mouseEvent, pressed))
                return;
            if (walkableMode)
                setWalkableTiles(mouseEvent);
            else
                addAndRemove();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (!walkableMode && selectionMode && pressed.x > -1 && pressed.y > -1) {
            model.resetSelectedObject(pressed);
            if (setPosition(mouseEvent, released)) {
                if (objectMoving) {
                    model.moveObject(pressed, released);
                    objectMoving = false;
                }
                else if (released.x >= pressed.x && released.y >= pressed.y){
                    model.select(pressed, released);
                    if (rubberMode)
                        model.removeAllSelectedTiles(pressed, released);
                }
            }
            else {
                pressed.setLocation(-1, -1);
                released.setLocation(-1, -1);
            }
        }
    }

    public boolean setPosition(MouseEvent mouseEvent, Point point) {
        int x = mouseEvent.getX() / JPanelMap.getTileSize();
        int y = mouseEvent.getY() / JPanelMap.getTileSize();
        if (x < 0 || y < 0 || x >= model.getCurrentMap().getWidth() || y >= model.getCurrentMap().getHeight()) {
            point.setLocation(-1, -1);
            return false;
        }
        point.setLocation(x, y);
        return true;
    }

    public void setWalkableTiles(MouseEvent mouseEvent) {
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            model.setWalkable(pressed, false);
        }
        else {
            model.setWalkable(pressed, true);
        }
    }

    public void addAndRemove() {
        if (rubberMode) {
            model.removeTile(pressed.x, pressed.y);
        }
        else {
            model.placeTile(pressed.x, pressed.y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String evt = actionEvent.getActionCommand();
        Object obj = actionEvent.getSource();
        String tileName = "";
        if (obj instanceof JButton) {
            JButton button = (JButton) obj;
            tileName = button.getName();
        }
        if (evt.equals("tile") && !tileName.equals("")) {
            Type type = model.getType(tileName);
            if (!walkableMode && selectionMode && type.isBackground()) {
                model.replaceAllSelectedTiles(type, pressed, released);
            }
            else {
                model.setCurrentTile(type, type.isBackground());
            }
        }
        else if (evt.equals("Save")) {
            try {
                model.ToWorldFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            System.exit(0);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        if (selectionMode && pressed.x > -1 && pressed.y > -1 && released.x > -1 && released.y > -1)
            model.resetSelectedTiles(pressed, released);
        JTabbedPane mapTabs = (JTabbedPane) changeEvent.getSource();
        model.setCurrentMap(mapTabs.getSelectedIndex());
    }

    public void actionAddMap(String name, int width, int height) {
        model.addMap(name, width, height);
    }
    public void actionDelMap() {
        model.delMap();
    }
    public void actionResetMap() {
        model.resetMap();
    }

    public void switchSelectionMode() {
        selectionMode = !selectionMode;
        if (!selectionMode && pressed.x > -1 && pressed.y > -1 && released.x > -1 && released.y > -1)
            model.resetSelectedTiles(pressed, released);
    }

    public void switchRubberMode() {
        rubberMode = !rubberMode;
    }

    public void switchWalkableMode() {
        walkableMode = !walkableMode;
        model.switchWalkableMode();
    }

    public void addTile(String tilename, Boolean isback, int x, int y, int index)
    {
        Type tile = model.getType(tilename);
        if (tile == null)
            throw new Error("The " + tilename + " tile doesn't exist");
        model.placeTile(tile, isback, x, y, index);
    }

    public void addPortal(String mapName, Point portal1, Point portal2) {
        model.addPortal(mapName, portal1, portal2);
    }

    public void startGame(){
        if (!model.getMaps().isEmpty()) {
            Game game = new Game("MyRpgMaker", model.getMaps());
        }
    }

    public void unstackUndo() {
        model.unstackUndo();
    }
    public void unstackRedo() {
        model.unstackRedo();
    }

    public void putIntoTilesTable(String tileName, int width, int height, boolean isBackground, boolean isNPC) {
        model.putIntoTilesTable(tileName, width, height, isBackground, isNPC);
    }

    public void setWalkableTile(int x, int y, int currMap, boolean isWalkable) {
        Map map = model.getMaps().get(currMap);
        map.getTiles()[x][y].setWalkable(isWalkable);
    }
}