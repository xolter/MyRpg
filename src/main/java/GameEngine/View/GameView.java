package GameEngine.View;

import Editor.Model.Map;
import GameEngine.Controller.PlayerController;
import GameEngine.Model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameView extends JFrame implements Observer {

    private final static int WIDTH_DIMENSION = 40;
    private final static int HEIGHT_DIMENSION = 30;
    private static int GAME_TILE_SIZE = 16;
    private GameJPanel gameJPanel;
    private PlayerController playerController;

    public GameView(String title, PlayerController playerController) {
        super(title);
        this.playerController = playerController;

        setPreferredSize(new Dimension(WIDTH_DIMENSION * GAME_TILE_SIZE, HEIGHT_DIMENSION * GAME_TILE_SIZE));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameJPanel = new GameJPanel(new BorderLayout(), this.playerController, this);
        getContentPane().add(gameJPanel);
        pack();
        setVisible(true);
    }

    public void pauseMenu() {
        if (JOptionPane.showConfirmDialog(this, "Quit?", "Pause Menu",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    public void interact() {
        JOptionPane.showMessageDialog(this, "Hello!");
    }

    @Override
    public void update(Observable observable, Object o) {
        GameModel gameModel = (GameModel)observable;
        Map map = gameModel.getCurrMap();
        gameJPanel.clearMap();
        gameJPanel.updateGameJPanel(map);
        gameJPanel.repaint();
    }

    public static int getWidthDimension() {
        return WIDTH_DIMENSION;
    }

    public static int getHeightDimension() {
        return HEIGHT_DIMENSION;
    }

    public static int getGameTileSize() {
        return GAME_TILE_SIZE;
    }
}
