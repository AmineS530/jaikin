
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class Jaikin {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Jaikin");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JaikinUtils.Loop();
            JLabel label = new JLabel("Welcome to the Jaikin!");

            panel.setPreferredSize(new Dimension(1000, 800));
            panel.setBackground(java.awt.Color.BLACK);
            frame.add(panel, BorderLayout.CENTER);
            panel.add(label);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}
