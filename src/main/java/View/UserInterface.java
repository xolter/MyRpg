package View;

import Controller.Controller;
import Model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.*;

public class UserInterface extends JFrame implements Observer {

    private Controller controller;
    private JPanelMap mapView;
    private Hashtable<String, ImageIcon> tiles;

    public UserInterface(String title, Controller controller) {
        super(title);

        this.controller = controller;

        this.tiles = new Hashtable<String, ImageIcon>();

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
        mapView.setTiles(tiles);
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

    public JButton addButton(ImageIcon img, JPanel panel, String tileName)
    {
        //set hashtable
        //Image image = imageToBufferedImage(img.getImage());
        tiles.put(tileName, img);

        JButton res = new JButton(img);

        res.setPreferredSize(new Dimension(64, 64));
        if (panel.getName().equals("NPC")) {
            res.setBackground(Color.ORANGE);
        }
        else if (panel.getName().equals("Background")) {
            res.setBackground(Color.LIGHT_GRAY);
        }
        else {
            res.setBackground(Color.pink);
        }

        res.setActionCommand(tileName);
        res.addActionListener(controller);
        panel.add(res);
        return res;
    }

    public JPanel addTilesButton()
    {
        JPanel tiles = new JPanel(new BorderLayout());
        tiles.setPreferredSize(new Dimension(getBounds().width / 3, tiles.getBounds().height));
        JTabbedPane tabs = new JTabbedPane();
        ArrayList<String> background_list = get_resources("backgroundTile");
        ArrayList<String> foreground_list = get_resources("foregroundObject");
        ArrayList<String> npc_list = get_resources("npc");

        JPanel back_tiles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        back_tiles.setName("Background");
        for (String imgname : background_list)
        {
            ImageIcon img = new ImageIcon(UserInterface.class.getResource("../backgroundTile/" + imgname));
            addButton(img, back_tiles, imgname);
        }
        tabs.add(back_tiles);

        JPanel fore_tiles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fore_tiles.setName("Foreground");
        for (String imgname : foreground_list)
        {
            ImageIcon img = new ImageIcon(UserInterface.class.getResource("../foregroundObject/" + imgname));
            addButton(img, fore_tiles, imgname);
        }
        tabs.add(fore_tiles);

        JPanel npc_tiles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        npc_tiles.setName("NPC");
        for (String imgname : npc_list)
        {
            Image scale_img = new ImageIcon(UserInterface.class.getResource("../npc/" + imgname)).getImage();
            scale_img = createImage(new FilteredImageSource(scale_img.getSource(), new CropImageFilter(16, 0, 32, 64)));
            ImageIcon img = new ImageIcon(scale_img);
            addButton(img, npc_tiles, imgname);

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

        int[] map_size = get_mapsize();
        JPanelMap map = new JPanelMap(new BorderLayout());
        map.setName("map" + (tabs.getTabCount() + 1));
        tabs.add(map);
        return map;
    }

    public int[] get_mapsize()
    {
        JTextField width = new JTextField(3);
        JTextField height = new JTextField(3);
        JPanel mapsize_pan = new JPanel(new GridLayout(2,1));
        mapsize_pan.add(new JLabel("width:"));
        mapsize_pan.add(width);
        mapsize_pan.add(new JLabel("height:"));
        mapsize_pan.add(height);
        int[] size = new int[]{100, 100};
        int result = JOptionPane.showConfirmDialog(null, mapsize_pan,
                "Map Size", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int w = 100;
            int h = 100;
            if (!width.getText().equals("") && width.getText().matches("\\d+")) {
                w = Integer.parseInt(width.getText());
                if (w > 800)
                    w = 800;
                if (w < 1)
                    w = 1;
            }
            if (!height.getText().equals("") && height.getText().matches("\\d+"))
            {
                h = Integer.parseInt(height.getText());
                if (h > 800)
                    h = 800;
                if (h < 1)
                    h = 1;
            }
            size[0] = w;
            size[1] = h;
        }
        return size;
    }

    public void update(Observable observable, Object o) {
        System.out.println("damn");

        /*mapView.addBackgroundTile(tiles.get("grass.png"), 0, 0);
        mapView.addBackgroundTile(tiles.get("grass.png"), 0, 16);
        mapView.addBackgroundTile(tiles.get("grass.png"), 0, 32);
        mapView.addBackgroundTile(tiles.get("grass.png"), 16, 0);
        mapView.addBackgroundTile(tiles.get("grass.png"), 16, 16);
        mapView.addBackgroundTile(tiles.get("grass.png"), 16, 32);
        mapView.addBackgroundTile(tiles.get("grass.png"), 32, 0);
        mapView.addBackgroundTile(tiles.get("grass.png"), 32, 16);
        mapView.addBackgroundTile(tiles.get("grass.png"), 32, 32);
        mapView.addForegroundTile(tiles.get("center.png"), 0, 16);*/

        Map map = (Model.Map)observable;
        mapView.updateMapView(map);

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
