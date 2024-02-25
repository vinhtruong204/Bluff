package display;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame();
        jFrame.setTitle("Bluff");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);

        jFrame.add(gamePanel);
        jFrame.pack();

        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
