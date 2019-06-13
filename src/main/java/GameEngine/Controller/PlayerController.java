package GameEngine.Controller;

import Editor.Model.Map;
import GameEngine.Model.GameModel;
import GameEngine.View.GameView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener{

    private GameModel gameModel;
    private GameView gameView;

    public PlayerController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            gameModel.moveUp();
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
            gameModel.moveDown();
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
            gameModel.moveLeft();
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            gameModel.moveRight();
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) {
            gameView.pauseMenu();
        }
        else if (keyCode == KeyEvent.VK_ENTER) {
            if (gameModel.playerCloseToNPC())
                gameView.interact();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public Map getCurrMap() {
        return gameModel.getCurrMap();
    }

    public Point getBeginScreen() {
        return gameModel.getBeginScreen();
    }

    public Point getPlayerPosition() {
        return gameModel.getPlayer().getPosition();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
