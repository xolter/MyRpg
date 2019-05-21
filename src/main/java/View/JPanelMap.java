package View;

import Controller.Controller;
import Model.Map;
import Model.Tile;
import Model.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JPanelMap extends JPanel{

    private final int width;
    private final int height;
    private final static int TILE_SIZE = 16;
    private BufferedImage backgroundImage;
    private BufferedImage foregroundImage;
    private Controller controller;


    public JPanelMap(BorderLayout layout, int width, int height, Controller controller) {
        super(layout);
        this.width = width;
        this.height = height;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.controller = controller;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }
        if (foregroundImage != null) {
            g.drawImage(foregroundImage, 0, 0, null);
        }

        printGrid(width, height, g);
    }

    public void addBackgroundTile(ImageIcon im, int x, int y) {
        Graphics g = backgroundImage.getGraphics();
        g.drawImage(im.getImage(), x, y, null);
        g.dispose();

        JLabel imglabel = new JLabel(im);
        imglabel.addMouseListener(this.controller);
        imglabel.addMouseMotionListener(this.controller);
        this.add(imglabel);
    }

    public void addForegroundTile(ImageIcon im, int x, int y) {
        Graphics g = backgroundImage.getGraphics();
        g.drawImage(im.getImage(), x, y, null);
        g.dispose();

        JLabel imglabel = new JLabel(im);
        imglabel.addMouseListener(this.controller);
        imglabel.addMouseMotionListener(this.controller);
        this.add(imglabel);
    }

    public void updateMapView(Map map) {
        Tile[][] mapTiles = map.getTiles();
        for (int i = 0; i < map.getWidth(); ++i) {
            for (int j = 0; j < map.getHeight(); ++j) {
                Type backType = mapTiles[i][j].getBackground();
                Type foreType = mapTiles[i][j].getForeground();
                if (backType != null) {
                    addBackgroundTile(UserInterface.getTiles()
                                      .get(backType.getName()), i * TILE_SIZE, j * TILE_SIZE);
                }
                if (foreType != null && mapTiles[i][j].isBegin()) {
                    addForegroundTile(UserInterface.getTiles()
                                      .get(foreType.getName()), i * TILE_SIZE, j * TILE_SIZE);
                }
            }
        }
    }

    public void printGrid(int width, int height, Graphics g)
    {
        for (int i = 0; i * TILE_SIZE < height; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(0, i * TILE_SIZE, width, i * TILE_SIZE);
        }

        for (int i = 0; i * TILE_SIZE < width; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, height);
        }
        g.dispose();
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }
}
