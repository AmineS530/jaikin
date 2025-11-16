import javax.swing.JFrame;
import javax.swing.JLabel;

import  java.awt.*;

public class Window {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Swing Window");
        frame.getContentPane().setBackground(Color.BLACK);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Loop panel = new Loop();
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(1000, 800));
        JLabel label = new JLabel("Welcome to the Jaikin!");
        panel.add(label);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}