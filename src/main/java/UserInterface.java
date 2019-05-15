import javax.swing.*;
import java.awt.*;

public class UserInterface {
    public static void main(String[] args) {
        ImageIcon center = new ImageIcon(UserInterface.class.getResource("foregroundObject/center.png"));
        ImageIcon house = new ImageIcon(UserInterface.class.getResource("foregroundObject/house.png"));
        ImageIcon sea = new ImageIcon(UserInterface.class.getResource("backgroundTile/sea.png"));
        ImageIcon grass = new ImageIcon(UserInterface.class.getResource("backgroundTile/grass.png"));
        ImageIcon hero = new ImageIcon(UserInterface.class.getResource("npc/hero.png"));

        JFrame frame = new JFrame("MyRPGMaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);

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
        toolbar.add(new JButton("button1"));
        toolbar.add(new JButton("button2"));

        tiles.add(new JButton(center));
        tiles.add(new JButton(house));
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

        frame.setJMenuBar(menubar);
        frame.getContentPane().add(BorderLayout.NORTH, toolbar);
        frame.getContentPane().add(BorderLayout.WEST, tiles);
        frame.getContentPane().add(maps);
        frame.setVisible(true);
    }
}
