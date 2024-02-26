package display;

import javax.swing.JFrame;

public class GameWindow {
    // Actual frame
    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel) {
        // Create window and set title
        jFrame = new JFrame();
        jFrame.setTitle("Bluff");

        // Set default close operation
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);

        // Add the game panel and make the window size appropriate
        jFrame.add(gamePanel);
        jFrame.pack();

        // Set window position to center and accept window display
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
