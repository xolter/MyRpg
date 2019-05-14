import javax.swing.*;
import java.awt.*;

public class UserInterface {
    public static void main(String[] args) {
        ImageIcon center = new ImageIcon(UserInterface.class.getResource("foregroundObject/center.png"));
        ImageIcon house = new ImageIcon(UserInterface.class.getResource("foregroundObject/house.png"));
        ImageIcon sea = new ImageIcon(UserInterface.class.getResource("backgroundTile/sea.png"));
        ImageIcon grass = new ImageIcon(UserInterface.class.getResource("backgroundTile/grass.png"));
        ImageIcon hero = new ImageIcon(UserInterface.class.getResource("npc/hero.png"));

        JWindow window = new JWindow();
        window.setSize(500, 500);
        window.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));
        window.setContentPane(panel);

        panel.add(new JButton(center));
        panel.add(new JButton(house));
        panel.add(new JButton(sea));
        panel.add(new JButton(grass));
        panel.add(new JButton(hero));
    }
}
