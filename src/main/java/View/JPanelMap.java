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
    }

    public void addTile(Image im) {
        Graphics g = panelImage.getGraphics();
        g.drawImage(im, 0, 0, null);
        g.drawImage(im, 20, 20, null);
        g.dispose();
    }
}
