package EntertainmentLogger;

import javax.swing.*;
import java.awt.*;

public class Main_logger extends JFrame {

    public Main_logger() {
        setTitle("Entertainment Logger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLayout(new GridLayout(12, 1));

        addButton("Anime", Anime::new);
        addButton("Drama", Drama::new);
        addButton("Foreign Firms", Foreign_Firms::new);
        addButton("Hollywood Firms", Hollywood_firms::new);
        addButton("Japanese Songs", J_Song::new);
        addButton("Japanese Movies", JapaneseMovie::new);
        addButton("Korean Songs", K_Song::new);
        addButton("Musical", Musical::new);
        addButton("Opera", Opera::new);
        addButton("Western Firms", Western_Firms::new);
        addButton("Western Songs", Western_Song::new);
    }

    private void addButton(String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> SwingUtilities.invokeLater(action));
        add(button);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main_logger().setVisible(true));
    }
}
