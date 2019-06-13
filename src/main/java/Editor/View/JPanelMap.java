package Editor.View;

import Editor.Controller.Controller;
import Editor.Model.Map;
import Editor.Model.Portal;
import Editor.Model.Tile;
import Editor.Model.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class JPanelMap extends JPanel{

    private final int width;
    private final int height;
    private final Color highlight;
    private final Color objectHighlight;
    private final Color walkableHighlight;
    private final static int TILE_SIZE = 16;
    private BufferedImage backgroundImage;
    private BufferedImage foregroundImage;
    private Controller controller;
    private UserInterface userInterface;

    public JPanelMap(BorderLayout layout, int width, int height, Controller controller, UserInterface userInterface) {
        super(layout);
        this.width = width;
        this.height = height;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.controller = controller;
        this.userInterface = userInterface;
        this.highlight = new Color(0, 255, 0, 50);
        this.objectHighlight = new Color(255, 0, 0, 100);
        this.walkableHighlight = new Color(238, 130, 238, 100);
        this.setPreferredSize(new Dimension(width, height));

        this.addMouseListener(this.controller);
        this.addMouseMotionListener(this.controller);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }
        if (foregroundImage != null) {
            g.drawImage(foregroundImage, 0, 0, null);
        }
        if (userInterface.getDisplayGrid())
            printGrid(width, height, g);

        g.drawLine(width, 0, width, height);
        g.drawLine(0, height, width, height);

    }

    public void addBackgroundTile(ImageIcon im, int x, int y) {
        Graphics g = backgroundImage.getGraphics();
        g.drawImage(im.getImage(), x, y, null);
        g.dispose();
    }

    public void addForegroundTile(ImageIcon im, int x, int y) {
        Graphics g = foregroundImage.getGraphics();
        g.drawImage(im.getImage(), x, y, null);
        g.dispose();
    }

    public void updateMapView(Map map, boolean walkableMode) {
        Tile[][] mapTiles = map.getTiles();
        for (int i = 0; i < map.getWidth(); ++i) {
            for (int j = 0; j < map.getHeight(); ++j) {
                Type backType = mapTiles[i][j].getBackground();
                Type foreType = mapTiles[i][j].getForeground();
                if (backType != null) {
                    addBackgroundTile(UserInterface.getTiles()
                                      .get(backType.getName()), i * TILE_SIZE, j * TILE_SIZE);
                }
                if (foreType != null && mapTiles[i][j].isBegin(i, j)) {
                    addForegroundTile(UserInterface.getTiles()
                                      .get(foreType.getName()), i * TILE_SIZE, j * TILE_SIZE);
                }
                if (walkableMode && mapTiles[i][j].isWalkable()) {
                    Graphics g = foregroundImage.getGraphics();
                    g.setColor(walkableHighlight);
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                if (mapTiles[i][j].isSelected()) {
                    Graphics g = foregroundImage.getGraphics();
                    g.setColor(highlight);
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                else if (mapTiles[i][j].isObjectSelected()) {
                    Graphics g = foregroundImage.getGraphics();
                    g.setColor(objectHighlight);
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        ArrayList<Portal> portals = map.getPortals();
        if (portals.size() > 0) {
            for (int i = 0; i < portals.size(); ++i) {
                Point src = portals.get(i).getSrc();
                addForegroundTile(UserInterface.getTiles()
                        .get("tp.png"), src.x * TILE_SIZE, src.y * TILE_SIZE);
            }
        }
    }

    public void printGrid(int width, int height, Graphics g)
    {
        int i;
        for (i = 0; i * TILE_SIZE < height; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(0, i * TILE_SIZE, width, i * TILE_SIZE);
        }

        g.drawLine(0, i * TILE_SIZE, width, i * TILE_SIZE);

        for ( i = 0; i * TILE_SIZE < width; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, height);
        }

        g.drawLine(i * TILE_SIZE,0, i * TILE_SIZE, height);
        g.dispose();
    }

    public void clearMap()
    {
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }
}
