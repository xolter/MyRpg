package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class UserInterface extends JFrame implements Observer {

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

    public UserInterface(String title) {
        super(title);

        ArrayList<String> background_list = get_resources("backgroundTile");
        ArrayList<String> foreground_list = get_resources("foregroundObject");
        ArrayList<String> npc_list = get_resources("npc");

        ImageIcon center = addImage("../foregroundObject/center.png");
        ImageIcon house = addImage("../foregroundObject/house.png");
        ImageIcon sea = addImage("../backgroundTile/sea.png");
        ImageIcon grass = addImage("../backgroundTile/grass.png");
        ImageIcon hero = addImage("../npc/hero.png");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel tiles = new JPanel(new GridLayout(3,3)); //maybe use GridBagLayout
        tiles.setPreferredSize(new Dimension(getBounds().width / 3, tiles.getBounds().height));

        JPanel maps = new JPanel(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        file.add(open);
        file.add(save);
        menubar.add(file);

        JToolBar toolbar = new JToolBar();
        toolbar.add(new JButton("Tool1"));
        toolbar.add(new JButton("Tool2"));
        
        JButton center_button = addButton(center, tiles);
        JButton house_button = addButton(house, tiles);
        JButton sea_button = addButton(sea, tiles);
        JButton grass_button = addButton(grass, tiles);
        JButton hero_button = addButton(hero, tiles);

        JPanel first = new JPanel(new BorderLayout());  //Draw the map on a BufferedImage by extending Jpanel...
        first.setName("map1_name");
        JPanel second = new JPanel(new BorderLayout());
        second.setName("map2_name");
        tabs.add(first);
        tabs.add(second);
        maps.add(tabs);

        setJMenuBar(menubar);
        getContentPane().add(BorderLayout.NORTH, toolbar);
        getContentPane().add(BorderLayout.WEST, tiles);
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

    public void update(Observable observable, Object o) {
        System.out.println("damn");
    }
}
