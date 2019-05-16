package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class UserInterface extends JFrame implements Observer {

    public UserInterface(String title) {
        super(title);

        /*ImageIcon center = new ImageIcon(UserInterface.class.getResource("../foregroundObject/center.png"));
        ImageIcon house = new ImageIcon(UserInterface.class.getResource("../foregroundObject/house.png"));
        ImageIcon sea = new ImageIcon(UserInterface.class.getResource("../backgroundTile/sea.png"));
        ImageIcon grass = new ImageIcon(UserInterface.class.getResource("../backgroundTile/grass.png"));
        ImageIcon hero = new ImageIcon(UserInterface.class.getResource("../npc/hero.png"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        JPanel tiles = new JPanel(new GridLayout(3, 3)); //maybe use GridBagLayout

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
        toolbar.add(new JButton("button1"));
        toolbar.add(new JButton("button2"));

        tiles.add(new JButton(center));

        JButton house_button = new JButton(house);
        //le bouton maison ecoute
        house_button.addActionListener(new Controller(this));
        tiles.add(house_button);

        tiles.add(new JButton(sea));
        tiles.add(new JButton(grass));
        tiles.add(new JButton(hero));

        JPanel first = new JPanel(new BorderLayout());
        first.setName("map1_name");
        first.add(new JTextArea());
        JPanel second = new JPanel(new BorderLayout());
        second.setName("map2_name");
        second.add(new JTextArea());
        tabs.add(first);
        tabs.add(second);
        maps.add(tabs);

        setJMenuBar(menubar);
        getContentPane().add(BorderLayout.NORTH, toolbar);
        getContentPane().add(BorderLayout.WEST, tiles);
        getContentPane().add(maps);
        setVisible(true);*/


        ImageIcon center = new ImageIcon(UserInterface.class.getResource("../foregroundObject/center.png"));
        ImageIcon house = new ImageIcon(UserInterface.class.getResource("../foregroundObject/house.png"));
        ImageIcon sea = new ImageIcon(UserInterface.class.getResource("../backgroundTile/sea.png"));
        ImageIcon grass = new ImageIcon(UserInterface.class.getResource("../backgroundTile/grass.png"));
        ImageIcon hero = new ImageIcon(UserInterface.class.getResource("../npc/hero.png"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel tiles = new JPanel(new GridLayout(3,3)); //maybe use GridBagLayout

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

        tiles.add(new JButton(center));

        JButton house_button = new JButton(house);
        house_button.addActionListener(new Controller(this)); //le bouton maison ecoute
        tiles.add(house_button);
        tiles.add(new JButton(sea));
        tiles.add(new JButton(grass));
        tiles.add(new JButton(hero));

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

    public void update(Observable observable, Object o) {
        System.out.println("damn");
    }
}
