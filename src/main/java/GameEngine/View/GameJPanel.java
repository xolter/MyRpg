package GameEngine.View;

import Editor.Model.Map;
import Editor.Model.Tile;
import Editor.Model.Type;
import Editor.View.UserInterface;
import GameEngine.Controller.PlayerController;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameJPanel extends JPanel {

    private int width;
    private int height;
    private BufferedImage backgroundImage;
    private BufferedImage foregroundImage;
    private PlayerController controller;
    GameView gameView;

    public GameJPanel(BorderLayout layout, PlayerController controller, GameView gameView) {
        super(layout);
        Map map = controller.getCurrMap();
        this.width = map.getWidth() * GameView.getGameTileSize();
        this.height = map.getHeight() * GameView.getGameTileSize();
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.controller = controller;
        this.gameView = gameView;
        this.setPreferredSize(new Dimension(width, height));
        this.addKeyListener(this.controller);
        controller.setGameView(gameView);
        updateGameJPanel(map);
    }

    public void addTile(ImageIcon im, int x, int y, boolean isBackground) {
        Graphics g;
        if (isBackground)
            g = backgroundImage.getGraphics();
        else
            g = foregroundImage.getGraphics();
        g.drawImage(im.getImage(), x, y, null);
        g.dispose();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }
        if (foregroundImage != null) {
            g.drawImage(foregroundImage, 0, 0, null);
        }

        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void updateGameJPanel(Map map) {
        final int tileSize = GameView.getGameTileSize();
        Point beginScreen = controller.getBeginScreen();
        Tile[][] mapTiles = map.getTiles();
        int w = map.getWidth();
        int h = map.getHeight();

        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                Type backType = mapTiles[i][j].getBackground();
                Type foreType = mapTiles[i][j].getForeground();
                if (backType != null) {
                    addTile(UserInterface.getTiles()
                            .get(backType.getName()), i * tileSize, j * tileSize, true);
                }
                if (foreType != null && mapTiles[i][j].isBegin(i, j)) {
                    addTile(UserInterface.getTiles()
                            .get(foreType.getName()), i * tileSize, j * tileSize, false);
                }
            }
        }
        Point playerPos = controller.getPlayerPosition();
        addTile(UserInterface.getTiles().get("hero.png"), playerPos.x * tileSize, (playerPos.y - 1) * tileSize, false);


        int viewWidth = w < GameView.getWidthDimension() ? w : GameView.getWidthDimension();
        int viewHeight = h < GameView.getHeightDimension() ? h : GameView.getHeightDimension();
        backgroundImage = backgroundImage.getSubimage(beginScreen.x * tileSize, beginScreen.y * tileSize,
                viewWidth * tileSize,
                viewHeight * tileSize);

        foregroundImage = foregroundImage.getSubimage(beginScreen.x * tileSize, beginScreen.y * tileSize,
                viewWidth * tileSize,
                viewHeight * tileSize);
    }

    public void clearMap()
    {
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
}