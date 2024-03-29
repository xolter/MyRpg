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
    private UserInterface userInterface;

    /*@Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }
        if (foregroundImage != null) {
            g.drawImage(foregroundImage, 0, 0, null);
        }
        printGrid(width, height, g);

    }*/

    public JPanelMap(BorderLayout layout, int width, int height, Controller controller, UserInterface userInterface) {
        super(layout);
        this.width = width;
        this.height = height;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.controller = controller;
        this.userInterface = userInterface;
        /*if (height >= 63)
        {
            JPanel newpanel = new JPanel();
            JScrollPane scrollPane = new JScrollPane(newpanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            //scrollPane.setPreferredSize(new Dimension(300, 400));
            this.add(scrollPane);
            //scrollPane.setBounds(50, 30, 300, 50);
            //JPanel panel = new JPanel(null);
            //panel.setPreferredSize(new Dimension(500, 400));
            //this.add(scrollPane);
        }*/

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
