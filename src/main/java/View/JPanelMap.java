package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JPanelMap extends JPanel{

    private final static int AREA_SIZE = 400;
    private BufferedImage panelImage;

    public JPanelMap(BorderLayout borderLayout) {
        super(borderLayout);
        panelImage = new BufferedImage(AREA_SIZE, AREA_SIZE, BufferedImage.TYPE_INT_ARGB);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (panelImage != null) {
            g.drawImage(panelImage, 0, 0, null);
        }
        printGrid(this.getWidth(), this.getHeight(), g);
    }

    public void addTile(Image im) {
        Graphics g = panelImage.getGraphics();
        g.drawImage(im, 0, 0, null);
        g.drawImage(im, 16, 16, null);
        g.dispose();
    }


    public void printGrid(int width, int height, Graphics g)
    {
        //Graphics g = panelImage.getGraphics();
        //Graphics g = this.getGraphics();
        for (int i = 0; i * 16 < height; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(0, i * 16, width, i * 16);
        }

        for (int i = 0; i * 16 < width; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(i * 16, 0, i * 16, height);
        }
        g.dispose();
    }
}
