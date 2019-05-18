package View;

import Controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserInterface extends JFrame implements Observer {

    private Controller controller;
    private JPanelMap mapView;
    private Hashtable<String, Image> backgroundTiles;

    public UserInterface(String title, Controller controller) {
        super(title);

        this.controller = controller;
        this.backgroundTiles = new Hashtable<String, Image>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel maps = new JPanel(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        maps.add(tabs);
        mapView = addMap(tabs);
        setJMenuBar(addMenubar());
        getContentPane().add(BorderLayout.NORTH, addToolbar());
        getContentPane().add(BorderLayout.WEST, addTilesButton());
        getContentPane().add(maps);
    }

    public ArrayList<String> get_resources(String type)
    {
        ArrayList<String> res = new ArrayList<String>();
        String path = "src/main/resources/" + type;
        if (type.equals("npc") || type.equals("foregroundObject") || type.equals("backgroundTile"))
        {
            File[] images = new File(path).listFiles();
            if (images == null)
                System.out.println("Wrong pathname : " + path);
            for (File img : images)
            {
                if (img.isFile())
                    res.add(img.getName());
            }
        }
        return res;
    }

    public JButton addButton(ImageIcon img, JPanel panel)
    {
        JButton res = new JButton(img);
        if (panel.getName().equals("NPC"))
            res.setBackground(Color.ORANGE);
        else if (panel.getName().equals("Background"))
            res.setBackground(Color.LIGHT_GRAY);
        else
            res.setBackground(Color.pink);
        res.addActionListener(controller);
        panel.add(res);
        return res;
    }

    public JPanel addTilesButton()
    {
        JPanel tiles = new JPanel();
        tiles.setPreferredSize(new Dimension(getBounds().width / 3, tiles.getBounds().height));
        JTabbedPane tabs = new JTabbedPane();
        ArrayList<String> background_list = get_resources("backgroundTile");
        ArrayList<String> foreground_list = get_resources("foregroundObject");
        ArrayList<String> npc_list = get_resources("npc");

        JPanel back_tiles = new JPanel(new GridLayout(3,3));
        back_tiles.setName("Background");
        for (String imgname : background_list)
        {
            ImageIcon img = new ImageIcon(UserInterface.class.getResource("../backgroundTile/" + imgname));
            addButton(img, back_tiles);

            //setting the hashtable using the image icon
            Image image = imageToBufferedImage(img.getImage());
            backgroundTiles.put(imgname, image);
        }
        tabs.add(back_tiles);

        JPanel fore_tiles = new JPanel(new GridLayout(3,3));
        fore_tiles.setName("Foreground");
        for (String imgname : foreground_list)
        {
            ImageIcon img = new ImageIcon(UserInterface.class.getResource("../foregroundObject/" + imgname));
            addButton(img, fore_tiles);
        }
        tabs.add(fore_tiles);

        JPanel npc_tiles = new JPanel(new GridLayout(3,3));
        npc_tiles.setName("NPC");
        for (String imgname : npc_list)
        {
            ImageIcon img = new ImageIcon(UserInterface.class.getResource("../npc/" + imgname));
            addButton(img, npc_tiles);
        }
        tabs.add(npc_tiles);

        tiles.add(tabs);
        return tiles;
    }

    public static BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bufferedImage = new BufferedImage
                (im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bufferedImage.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bufferedImage;
    }

    public JMenuBar addMenubar()
    {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        file.add(open);
        file.add(save);
        menubar.add(file);
        return menubar;
    }

    public JToolBar addToolbar()
    {
        JToolBar toolbar = new JToolBar();
        toolbar.add(new JButton("Tool1"));
        toolbar.add(new JButton("Tool2"));
        return toolbar;
    }

    public JPanelMap addMap(JTabbedPane tabs)
    {
        /*JPanel map = new JPanel(new BorderLayout());
        map.setName("map" + (tabs.getTabCount() + 1));
        tabs.add(map);
        return map;*/

        JPanelMap map = new JPanelMap(new BorderLayout());
        map.setName("map" + (tabs.getTabCount() + 1));
        tabs.add(map);
        return map;
    }

    public void update(Observable observable, Object o) {
        System.out.println("damn");

        mapView.addTile(backgroundTiles.get("grass.png"));
        mapView.repaint();


        //This code works, just need to precise which panel we want to draw and what size
        /*JLabel lab = new JLabel(new ImageIcon(UserInterface.class.getResource("../npc/hero.png")));
        this.add(lab);
        this.repaint();*/

        /*BufferedImage image = null;
        try {
            image = ImageIO.read(new File("../npc/hero.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final BufferedImage finalImage = image;
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(finalImage, 0, 0, null);
            }
        };
        this.add(panel);*/
    }
}
