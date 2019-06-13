package Editor.View;

import Editor.Controller.Controller;
import Editor.Controller.MapOptionPane;
import Editor.Model.Model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*;
import java.util.*;

public class UserInterface extends JFrame implements Observer {

    private Controller controller;
    private JTabbedPane mapTabs;
    private boolean displayGrid;
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
        int width = (int)Math.round((double)img.getIconWidth() / JPanelMap.getTileSize());
        int height = (int)Math.round((double)img.getIconHeight() / JPanelMap.getTileSize());
        JButton res = new JButton(img);

        if (panel.getName().equals("NPC")) {
            res.setBackground(Color.ORANGE);
            controller.putIntoTilesTable(tileName, width, height, false, true);
        }
        else if (panel.getName().equals("Background")) {
            res.setBackground(Color.LIGHT_GRAY);
            controller.putIntoTilesTable(tileName, width, height, true, false);
        }
        else {
            res.setBackground(Color.pink);
            controller.putIntoTilesTable(tileName, width, height, false, false);
        }
        res.setName(tileName);
        res.setActionCommand("tile");
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
            ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/backgroundTile/" + imgname);
            addButton(img, back_tiles, imgname);
        }
        tabs.add(back_tiles);

        JPanel fore_tiles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fore_tiles.setName("Foreground");
        for (String imgname : foreground_list)
        {
            ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/foregroundObject/" + imgname);
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
            Image crop_img = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/npc/" + name).getImage();
            crop_img = createImage(new FilteredImageSource(crop_img.getSource(), new CropImageFilter(16, 0, 32, 56)));
            Image scale_img = crop_img.getScaledInstance(16, 32, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(scale_img);
            return img;
        }
        else if (name.equals("bluehair.png")) {
            Image crop_img = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/npc/" + name).getImage();
            crop_img = createImage(new FilteredImageSource(crop_img.getSource(), new CropImageFilter(10, 0, 100, 115)));
            Image scale_img = crop_img.getScaledInstance(22, 32, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(scale_img);
            return img;
        }
        return new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/npc/" + name);
    }

    public JMenuBar addMenubar()
    {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem new_proj = new JMenuItem("New");
        new_proj.setActionCommand("New");
        new_proj.addActionListener(new MapOptionPane(this, controller));

        JMenuItem open = new JMenuItem("Open");
        open.setActionCommand("Open");
        open.addActionListener(new MapOptionPane(this, controller));

        JMenuItem load = new JMenuItem("Load Tile");
        load.setActionCommand("Load Tile");
        load.addActionListener(new MapOptionPane(this, controller));

        JMenuItem save = new JMenuItem("Save");
        save.setActionCommand("Save");
        save.addActionListener(controller);

        JMenuItem quit = new JMenuItem("Quit");
        quit.setActionCommand("Quit");
        quit.addActionListener(controller);

        file.add(new_proj);
        file.add(open);
        file.add(load);
        file.add(save);
        file.add(quit);
        menubar.add(file);
        return menubar;
    }

    private JButton addToolbarButton(String imagePath, String tip, String command) {
        ImageIcon img = new ImageIcon(UserInterface.class.getResource(imagePath));
        JButton button = new JButton(img);
        button.setToolTipText(tip);
        button.setActionCommand(command);
        button.addActionListener(new MapOptionPane(this, controller));
        return button;
    }

    private JToggleButton addToolbarToggleButton(String imagePath, String tip, String command) {
        ImageIcon img = new ImageIcon(UserInterface.class.getResource(imagePath));
        JToggleButton button = new JToggleButton(img);
        button.setToolTipText(tip);
        button.setActionCommand(command);
        button.addActionListener(new MapOptionPane(this, controller));
        return button;
    }

    public JToolBar addToolbar()
    {
        JToolBar toolbar = new JToolBar();
        JButton newMap = addToolbarButton("../../icon/new_map_icon.png", "Create a new map...", "New map");
        JButton delMap = addToolbarButton("../../icon/delete_map_icon.png", "Delete current map", "Delete map");
        JButton resetMap = addToolbarButton("../../icon/reset_map_icon.png", "Reset current map's tiles", "Reset map");
        JToggleButton displayGrid = addToolbarToggleButton("../../icon/grid_icon.png", "Display/hide the grid", "Display grid");
        JToggleButton selectMode = addToolbarToggleButton("../../icon/select_mode_icon.png", "Activate/Desactivate selection mode", "Selection mode");
        JToggleButton rubberMode = addToolbarToggleButton("../../icon/eraser_icon.png", "Activate/Desactivate rubber", "Rubber mode");
        JToggleButton walkMode = addToolbarToggleButton("../../icon/walk_icon.png", "Set walkable tiles", "Walk mode");
        JButton tp = addToolbarButton("../../icon/tp.png", "Place a portal", "Teleport");
        JButton undo = addToolbarButton("../../icon/undo_icon.png", "Undo", "Undo");
        JButton redo = addToolbarButton("../../icon/redo_icon.png", "Redo", "Redo");
        JButton play = addToolbarButton("../../icon/play_icon.png", "Play", "Play");

        toolbar.add(newMap);
        toolbar.add(delMap);
        toolbar.add(resetMap);
        toolbar.add(displayGrid);
        toolbar.add(selectMode);
        toolbar.add(rubberMode);
        toolbar.add(walkMode);
        toolbar.add(tp);
        toolbar.add(undo);
        toolbar.add(redo);
        toolbar.add(play);
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

        JScrollPane scroll = new JScrollPane(map, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        String mapName;
        if (map_options[2].equals(""))
            mapName = "map" + (mapTabs.getTabCount() + 1);
        else
            mapName = map_options[2].toString();
        scroll.setName(mapName);
        mapTabs.add(scroll);
        controller.actionAddMap(mapName, (Integer)map_options[0], (Integer)map_options[1]); //Create new map in database
    }

    public void addPortal() {
        Object[] portalOptions = getPortalOptions();
        if ((Integer) portalOptions[5] == 0 || portalOptions[2].equals("") || (Integer)portalOptions[0] == -1 ||
                (Integer)portalOptions[1] == -1 || (Integer)portalOptions[3] == -1 || (Integer)portalOptions[4] == -1)
            return;
        Point p1 = new Point((Integer)portalOptions[0], (Integer)portalOptions[1]);
        Point p2 = new Point((Integer)portalOptions[3], (Integer)portalOptions[4]);
        controller.addPortal(portalOptions[2].toString(), p1, p2);
    }

    public void delMapView() {
        if (mapTabs.getTabCount() > 0) {
            int index = mapTabs.getSelectedIndex();
            controller.actionDelMap();
            mapTabs.remove(index);
        }
    }

    public void resetMapView()
    {
        int curr = mapTabs.getSelectedIndex();
        if (curr >= 0) {
            JScrollPane scroll = (JScrollPane) mapTabs.getComponentAt(curr);
            JViewport viewport = scroll.getViewport();
            JPanelMap mapView = (JPanelMap) viewport.getView();
            mapView.clearMap();
            mapView.repaint();
            controller.actionResetMap();
        }
    }

    public void NewProject()
    {
        while (mapTabs.getTabCount() > 0)
            delMapView();
    }

    private String getDestDir()
    {
        String destdir = new String();
        JToggleButton background = new JToggleButton("Background");
        JToggleButton foregound = new JToggleButton("Foreground");
        JToggleButton npc = new JToggleButton("NPC");
        JPanel destdir_pan = new JPanel(new GridLayout(3,1));
        destdir_pan.add(background);
        destdir_pan.add(foregound);
        destdir_pan.add(npc);
        int result = JOptionPane.showConfirmDialog(null, destdir_pan,
                "Destination Directory", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
        {
            if (foregound.isSelected() && !background.isSelected() && !npc.isSelected())
                destdir = "foregroundObject";
            else if (!foregound.isSelected() && background.isSelected() && !npc.isSelected())
                destdir = "backgroundTile";
            else if (!foregound.isSelected() && !background.isSelected() && npc.isSelected())
                destdir = "npc";
            else
                JOptionPane.showMessageDialog(destdir_pan, "Cannot select more than 1 directory", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return destdir;
    }

    public void LoadTile() throws IOException {
        String destdir = getDestDir();
        if (destdir.equals(""))
            return;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
        String tilepath = new String();
        String tilename = new String();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            tilepath = chooser.getSelectedFile().getPath();
            tilename = chooser.getSelectedFile().getName();
        }
        File source = new File(tilepath);
        File dest = new File(System.getProperty("user.dir") + "/src/main/resources/" + destdir + "/" + tilename);
        System.out.println(source);
        System.out.println(dest);
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        in.close();
        out.close();
        getContentPane().remove(1);
        getContentPane().add(BorderLayout.WEST, addTilesButton());
    }

    public Object[] get_mapoptions()
    {
        JTextField width = new JTextField(3);
        JTextField height = new JTextField(3);
        JTextField name = new JTextField(10);
        JPanel mapsize_pan = new JPanel(new GridLayout(3,1));
        mapsize_pan.add(new JLabel("name:"));
        mapsize_pan.add(name);
        mapsize_pan.add(new JLabel("width:"));
        mapsize_pan.add(width);
        mapsize_pan.add(new JLabel("height:"));
        mapsize_pan.add(height);
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

    public Object[] getPortalOptions() {
        JTextField width = new JTextField(3);
        JTextField height = new JTextField(3);
        JTextField width2 = new JTextField(3);
        JTextField height2 = new JTextField(3);
        JTextField name = new JTextField(10);
        JPanel mapsize_pan = new JPanel(new GridLayout(0, 5));
        mapsize_pan.add(new JLabel("x source:"));
        mapsize_pan.add(new JLabel("y source:"));
        mapsize_pan.add(new JLabel("x destination:"));
        mapsize_pan.add(new JLabel("y destination:"));
        mapsize_pan.add(new JLabel("Destination map name:"));
        mapsize_pan.add(width);
        mapsize_pan.add(height);
        mapsize_pan.add(width2);
        mapsize_pan.add(height2);
        mapsize_pan.add(name);
        Object[] options = new Object[6];
        int result = JOptionPane.showConfirmDialog(null, mapsize_pan,
                "Portal", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int x1 = -1;
            int y1 = -1;
            int x2 = -1;
            int y2 = -1;
            if (!width.getText().equals("") && width.getText().matches("\\d+"))
            {
                x1 = Integer.parseInt(width.getText());
            }
            if (!height.getText().equals("") && height.getText().matches("\\d+"))
            {
                y1 = Integer.parseInt(height.getText());
            }
            if (!width2.getText().equals("") && width2.getText().matches("\\d+"))
            {
                x2 = Integer.parseInt(width2.getText());
            }
            if (!height2.getText().equals("") && height2.getText().matches("\\d+"))
            {
                y2 = Integer.parseInt(height2.getText());
            }
            options[0] = x1;
            options[1] = y1;
            options[2] = name.getText();
            options[3] = x2;
            options[4] = y2;
            options[5] = 1;
        }
        else
            options[5] = 0;
        return options;
    }

    public void switchGrid() {
        displayGrid = !displayGrid;
        if (mapTabs.getTabCount() > 0) {
            int curr = mapTabs.getSelectedIndex();
            JScrollPane scroll = (JScrollPane) mapTabs.getComponentAt(curr);
            JViewport viewport = scroll.getViewport();
            JPanelMap mapView = (JPanelMap) viewport.getView();
            mapView.repaint();
        }
    }

    private String GetFilename()
    {
        JTextField file = new JTextField(16);
        JPanel fileopener_pan = new JPanel();
        fileopener_pan.add(new JLabel("Filename:"));
        fileopener_pan.add(file);
        int result = JOptionPane.showConfirmDialog(null, fileopener_pan,
                "Open a World file", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
            return file.getText();
        else
            return "";
    }

    public void OpenWorldFile() throws IOException {
        String filename = GetFilename();
        if (filename.equals(""))
            return;
        NewProject();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();

        int currentMap = 0;
        while (line != null)
        {
            String[] map = line.split(" ");
            String mapname = map[0];
            int mapwidth = Integer.parseInt(map[1]);
            int mapheight = Integer.parseInt(map[2]);
            JPanelMap curmap = new JPanelMap(new BorderLayout(), mapwidth * JPanelMap.getTileSize(),
                    mapheight * JPanelMap.getTileSize(),
                    this.controller, this);
            JScrollPane scroll = new JScrollPane(curmap, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setName(mapname);
            mapTabs.add(scroll);
            controller.actionAddMap(mapname, mapwidth, mapheight);

            line = br.readLine();
            line = br.readLine();
            while (!line.equals("portals"))
            {
                String[] tileinfo = line.split("\\s+");
                System.out.println();
                int y = Integer.parseInt(tileinfo[2]);
                int x = Integer.parseInt(tileinfo[3]);
                String tilename = tileinfo[4];
                int walkable = Integer.parseInt(tileinfo[5]);
                boolean isWalkable = walkable == 1;
                if (tileinfo[1].equals("background")) {
                    controller.addTile(tilename, true, x, y, currentMap);
                    controller.setWalkableTile(x, y, currentMap, isWalkable);

                }
                else {
                    controller.addTile(tilename, false, x, y, currentMap);
                    controller.setWalkableTile(x, y, currentMap, isWalkable);
                }

                line = br.readLine();
            }
            line = br.readLine();
            while (!line.equals("}")) {
                String[] portalInfo = line.split("\\s+");
                System.out.println();
                int x1 = Integer.parseInt(portalInfo[0]);
                int y1 = Integer.parseInt(portalInfo[1]);
                int x2 = Integer.parseInt(portalInfo[2]);
                int y2 = Integer.parseInt(portalInfo[3]);
                String name = portalInfo[4];
                controller.addPortal(name, new Point(x1, y1), new Point(x2, y2));
                line = br.readLine();
            }
            line = br.readLine();
            line = br.readLine();
            currentMap++;
        }
        br.close();
    }

    public void update(Observable observable, Object o) {
        Model model = (Model)observable;
        if (model.getCurrentMap() != null) {
            int currIndex = model.getCurrentIndex();
            JScrollPane scroll = (JScrollPane) mapTabs.getComponentAt(currIndex);
            JViewport viewport = scroll.getViewport();
            JPanelMap mapView = (JPanelMap) viewport.getView();
            mapView.clearMap();
            mapView.updateMapView(model.getCurrentMap(), model.isWalkableMode());
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
