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

        ArrayList<String> background_list = get_resources("backgroundTile");
        ArrayList<String> foreground_list = get_resources("foregroundObject");
        ArrayList<String> npc_list = get_resources("npc");

        ImageIcon center = new ImageIcon(UserInterface.class.getResource("../foregroundObject/center.png"));
        ImageIcon house = new ImageIcon(UserInterface.class.getResource("../foregroundObject/house.png"));
        ImageIcon sea = new ImageIcon(UserInterface.class.getResource("../backgroundTile/sea.png"));
        ImageIcon grass = new ImageIcon(UserInterface.class.getResource("../backgroundTile/grass.png"));
        ImageIcon hero = new ImageIcon(UserInterface.class.getResource("../npc/hero.png"));

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
        
        JButton center_button = new JButton(center);
        center_button.setBackground(Color.ORANGE);
        center_button.addActionListener(new Controller(this)); //le bouton maison ecoute
        tiles.add(center_button);
        JButton house_button = new JButton(house);
        house_button.setBackground(Color.ORANGE);
        house_button.addActionListener(new Controller(this)); //le bouton maison ecoute
        tiles.add(house_button);
        JButton sea_button = new JButton(sea);
        sea_button.setBackground(Color.ORANGE);
        sea_button.addActionListener(new Controller(this));
        tiles.add(sea_button);
        JButton grass_button = new JButton(grass);
        grass_button.setBackground(Color.ORANGE);
        grass_button.addActionListener(new Controller(this));
        tiles.add(grass_button);
        JButton hero_button = new JButton(hero);
        hero_button.setBackground(Color.ORANGE);
        hero_button.addActionListener(new Controller(this));
        tiles.add(hero_button);

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
