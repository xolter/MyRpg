package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class UserInterface extends JFrame implements Observer {

    public UserInterface(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel maps = new JPanel(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        file.add(open);
        file.add(save);
        menubar.add(file);

        JPanel first = new JPanel(new BorderLayout());  //Draw the map on a BufferedImage by extending Jpanel...
        first.setName("map1_name");
        JPanel second = new JPanel(new BorderLayout());
        second.setName("map2_name");
        tabs.add(first);
        tabs.add(second);
        maps.add(tabs);

        setJMenuBar(menubar);
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

    private ImageIcon addImage(String img)
    {
        return new ImageIcon(UserInterface.class.getResource(img));
    }

    private JButton addButton(ImageIcon img, JPanel panel)
    {
        JButton res = new JButton(img);
        res.setBackground(Color.ORANGE);
        res.addActionListener(new Controller(this));
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

    public JToolBar addToolbar()
    {
        JToolBar toolbar = new JToolBar();
        toolbar.add(new JButton("Tool1"));
        toolbar.add(new JButton("Tool2"));
        return toolbar;
    }

    public void update(Observable observable, Object o) {
        System.out.println("damn");
    }
}
