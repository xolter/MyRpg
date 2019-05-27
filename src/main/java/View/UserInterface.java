package View;

import Controller.Controller;
import Controller.MapOptionPane;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.*;

public class UserInterface extends JFrame implements Observer {

    private Controller controller;
    private JTabbedPane mapTabs;
    private static boolean displayGrid;
    private static Hashtable<String, ImageIcon> tiles;

    public UserInterface(String title, Controller controller) {
        super(title);

        this.controller = controller;
        displayGrid = false;
        this.tiles = new Hashtable<String, ImageIcon>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel maps = new JPanel(new BorderLayout());

        mapTabs = new JTabbedPane();
        mapTabs.addChangeListener(this.controller);
        maps.add(mapTabs);

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

    public JButton addButton(ImageIcon img, JPanel panel, String tileName)
    {
        //set hashtable
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

            ImageIcon img = cropImage(imgname);
            addButton(img, npc_tiles, imgname);
        }
        tabs.add(npc_tiles);

        tiles.add(tabs);
        return tiles;
    }

    public ImageIcon cropImage(String name) {
        if (name.equals("hero.png")) {
            Image crop_img = new ImageIcon(UserInterface.class.getResource("../npc/" + name)).getImage();
            crop_img = createImage(new FilteredImageSource(crop_img.getSource(), new CropImageFilter(16, 0, 32, 56)));
            Image scale_img = crop_img.getScaledInstance(16, 32, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(scale_img);
            return img;
        }
        else {
            Image crop_img = new ImageIcon(UserInterface.class.getResource("../npc/" + name)).getImage();
            crop_img = createImage(new FilteredImageSource(crop_img.getSource(), new CropImageFilter(10, 0, 100, 115)));
            Image scale_img = crop_img.getScaledInstance(22, 32, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(scale_img);
            return img;
        }
    }

    public JMenuBar addMenubar()
    {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem quit = new JMenuItem("Quit");

        quit.setActionCommand("Quit");
        quit.addActionListener(controller);

        file.add(open);
        file.add(save);
        file.add(quit);
        menubar.add(file);
        return menubar;
    }

    public JButton addToolbarButton(String imagePath, String tip, String command) {
        ImageIcon img = new ImageIcon(UserInterface.class.getResource(imagePath));
        JButton button = new JButton(img);
        button.setToolTipText(tip);
        button.setActionCommand(command);
        button.addActionListener(new MapOptionPane(this));
        return button;
    }

    public JToolBar addToolbar()
    {
        JToolBar toolbar = new JToolBar();
        JButton newMap = addToolbarButton("../icon/new_map_icon.png", "Create a new map...", "New map");
        JButton delMap = addToolbarButton("../icon/delete_map_icon.png", "Delete current map", "Delete map");
        JButton resetMap = addToolbarButton("../icon/reset_map_icon.png", "Reset current map's tiles", "Reset map");
        JButton displayGrid = addToolbarButton("../icon/grid_icon.png", "Display/hide the grid", "Display grid");
        toolbar.add(newMap);
        toolbar.add(delMap);
        toolbar.add(resetMap);
        toolbar.add(displayGrid);
        return toolbar;
    }

    public void addMapView()
    {
        Object[] map_options = get_mapoptions();

        if ((Integer) map_options[3] == 0)
            return;

        JPanelMap map = new JPanelMap(new BorderLayout(), (Integer)map_options[0] * JPanelMap.getTileSize(),
                                      (Integer)map_options[1] * JPanelMap.getTileSize(),
                                      this.controller, this);

        String mapName;
        if (map_options[2].equals(""))
            mapName = "map" + (mapTabs.getTabCount() + 1);
        else
            mapName = map_options[2].toString();
        map.setName(mapName);
        mapTabs.add(map);
        controller.actionAddMap(mapName, (Integer)map_options[0], (Integer)map_options[1]); //Create new map in database
    }

    public void delMapView()
    {
        if (mapTabs.getTabCount() > 1) {
            resetMapView();
            int index = mapTabs.getSelectedIndex();
            mapTabs.remove(index);
            controller.actionDelMap();
        }
    }

    public void resetMapView()
    {
        int curr = mapTabs.getSelectedIndex();
        JPanelMap mapView = (JPanelMap) mapTabs.getComponentAt(curr);
        mapView.clearMap();
        mapView.repaint();
        controller.actionResetMap();
    }

    public Object[] get_mapoptions()
    {
        JTextField width = new JTextField(3);
        JTextField height = new JTextField(3);
        JTextField name = new JTextField(10);
        JPanel mapsize_pan = new JPanel(new GridLayout(3,1));
        mapsize_pan.add(new JLabel("width:"));
        mapsize_pan.add(width);
        mapsize_pan.add(new JLabel("height:"));
        mapsize_pan.add(height);
        mapsize_pan.add(new JLabel("name:"));
        mapsize_pan.add(name);
        Object[] options = new Object[4];
        int result = JOptionPane.showConfirmDialog(null, mapsize_pan,
                "Map Size", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int w = 75;
            int h = 50;
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
            options[0] = w;
            options[1] = h;
            options[2] = name.getText();
            options[3] = 1;
        }
        else
            options[3] = 0;
        return options;
    }

    public void switchGrid() {
        displayGrid = !displayGrid;
        int curr = mapTabs.getSelectedIndex();
        JPanelMap mapView = (JPanelMap) mapTabs.getComponentAt(curr);
        mapView.repaint();
    }

    public void update(Observable observable, Object o) {
        Model model = (Model)observable;
        if (model.getCurrentMap() != null) {
            int currIndex = model.getCurrentIndex();
            JPanelMap mapView = (JPanelMap) mapTabs.getComponentAt(currIndex);
            mapView.updateMapView(model.getCurrentMap());
            mapView.repaint();
        }
    }

    public static Hashtable<String, ImageIcon> getTiles() {
        return tiles;
    }
    public boolean getDisplayGrid() {
        return displayGrid;
    }
}
