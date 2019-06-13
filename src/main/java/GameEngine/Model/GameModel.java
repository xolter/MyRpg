package GameEngine.Model;

import Editor.Model.Map;
import Editor.Model.Portal;
import Editor.Model.Tile;
import GameEngine.View.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class GameModel extends Observable {

    private ArrayList<Map> maps;
    private int currMap;
    private Point beginScreen;
    private Player player;

    public GameModel(ArrayList<Map> maps) {
        this.maps = maps;
        this.currMap = 0;
        int width = maps.get(0).getWidth();
        int height = maps.get(0).getHeight();

        int x = width / 2;
        int y = height - 1;

        int screenX = findScreenX(width, x);
        int screenY = findScreenY(height, y);

        this.beginScreen = new Point(screenX, screenY);
        this.player = new Player(x, y);
    }

    private int findScreenX(int width, int x) {
        if (GameView.getWidthDimension() > width)
            return 0;
        return x - (GameView.getWidthDimension() / 2) < 0 ? 0 : x - (GameView.getWidthDimension() / 2);
    }

    private int findScreenY(int height, int y) {
        if (GameView.getHeightDimension() > height)
            return 0;
        int screenY = y - (GameView.getHeightDimension() / 2);
        if (screenY < 0)
            return 0;
        if (screenY + GameView.getHeightDimension() > height)
            return height - GameView.getHeightDimension();
        return screenY;
    }

    public void moveUp() {
        Map map = getCurrMap();
        Point playerPos = player.getPosition();
        Portal portal = map.getPortal(playerPos.x, playerPos.y - 1);
        if (portal != null)
            teleportation(portal);
        else if (playerPos.y - 1 >= 0 && map.getTiles()[playerPos.x][playerPos.y - 1].isWalkable()) {
            if (beginScreen.y > 0 && playerPos.y == beginScreen.y + (GameView.getHeightDimension() / 2)) {
                beginScreen.setLocation(beginScreen.x, beginScreen.y - 1);
            }

            if (playerPos.y > 0) {
                player.setPosition(playerPos.x, playerPos.y - 1);
            }
            setChanged();
            notifyObservers("going up");
        }
    }

    public void moveDown() {
        Map map = getCurrMap();
        Point playerPos = player.getPosition();
        Portal portal = map.getPortal(playerPos.x, playerPos.y + 1);
        if (portal != null)
            teleportation(portal);
        else if (playerPos.y + 1 < map.getHeight() && map.getTiles()[playerPos.x][playerPos.y + 1].isWalkable()) {
            int height = getCurrMap().getHeight();
            if (beginScreen.y + GameView.getHeightDimension() < height &&
                    playerPos.y == beginScreen.y + (GameView.getHeightDimension() / 2)) {
                beginScreen.setLocation(beginScreen.x, beginScreen.y + 1);
            }
            if (playerPos.y < height - 1) {
                player.setPosition(playerPos.x, playerPos.y + 1);
            }
            setChanged();
            notifyObservers("going down");
        }
    }

    public void moveLeft() {
        Map map = getCurrMap();
        Point playerPos = player.getPosition();
        Portal portal = map.getPortal(playerPos.x - 1, playerPos.y);
        if (portal != null)
            teleportation(portal);
        else if (playerPos.x - 1 >= 0 && map.getTiles()[playerPos.x - 1][playerPos.y].isWalkable()) {
            if (beginScreen.x > 0 && playerPos.x == beginScreen.x + (GameView.getWidthDimension() / 2)) {
                beginScreen.setLocation(beginScreen.x - 1, beginScreen.y);
            }
            if (playerPos.x > 0) {
                player.setPosition(playerPos.x - 1, playerPos.y);
            }
            setChanged();
            notifyObservers("go to the left");
        }
    }

    public void moveRight() {
        Map map = getCurrMap();
        Point playerPos = player.getPosition();
        Portal portal = map.getPortal(playerPos.x + 1, playerPos.y);
        if (portal != null)
            teleportation(portal);
        else if (playerPos.x + 1 < map.getWidth() && map.getTiles()[playerPos.x + 1][playerPos.y].isWalkable()) {
            int width = getCurrMap().getWidth();
            if (beginScreen.x + GameView.getWidthDimension() < width &&
                    playerPos.x == beginScreen.x + (GameView.getWidthDimension() / 2)) {
                beginScreen.setLocation(beginScreen.x + 1, beginScreen.y);
            }
            if (playerPos.x < width - 1) {
                player.setPosition(playerPos.x + 1, playerPos.y);
            }
            setChanged();
            notifyObservers("go to the right");
        }
    }

    public void teleportation(Portal portal) {
        for (int i =0; i < maps.size(); ++i) {
            if (maps.get(i).getName().equals(portal.getMapName())) {
                currMap = i;
                break;
            }
        }
        int width = maps.get(currMap).getWidth();
        int height = maps.get(currMap).getHeight();
        int screenX = findScreenX(width, portal.getDst().x);
        int screenY = findScreenY(height, portal.getDst().y);
        beginScreen.setLocation(screenX, screenY);
        player.setPosition(portal.getDst().x,  portal.getDst().y);

        setChanged();
        notifyObservers("TP");

    }

    public boolean playerCloseToNPC() {
        Point pos = player.getPosition();
        Map map = getCurrMap();
        Tile[][] tiles = map.getTiles();
        if (pos.y - 1 >= 0) {
            if (tiles[pos.x][pos.y - 1].getForeground() != null &&
                    tiles[pos.x][pos.y - 1].getForeground().isNPC())
                return true;
        }
        if (pos.y + 1 < map.getHeight()) {
            if (tiles[pos.x][pos.y + 1].getForeground() != null &&
                    tiles[pos.x][pos.y + 1].getForeground().isNPC())
                return true;
        }
        if (pos.x - 1 >= 0) {
            if (tiles[pos.x - 1][pos.y].getForeground() != null &&
                    tiles[pos.x - 1][pos.y].getForeground().isNPC())
                return true;
        }
        if (pos.x + 1 < map.getWidth()) {
            if (tiles[pos.x + 1][pos.y].getForeground() != null &&
                    tiles[pos.x + 1][pos.y].getForeground().isNPC())
                return true;
        }
        return false;
    }

    public Map getCurrMap() {
        return maps.get(currMap);
    }

    public Point getBeginScreen() {
        return beginScreen;
    }

    public Player getPlayer() {
        return player;
    }

}
