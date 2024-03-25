package game;

import javax.swing.JPanel;

import input.KeyboardInputs;
import input.MouseInputs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GamePanel extends JPanel implements Runnable {
    // Update interval for each frame
    private final double UPDATE_INTERVAL = 1.0d / 60.0d;

    private boolean running;

    // ups: update per second
    private long nextStartTime;
    private int fps, ups;

    private Game game;

    public GamePanel() {
        // Create new game
        game = new Game();

        // Create and add keyboard and mouse input
        this.addKeyListener(new KeyboardInputs(game));
        this.addMouseListener(new MouseInputs(game));

        // Set size of panel, focus and background color
        this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.black);

        // Draws multiple images to the buffer before displaying them on the screen
        this.setDoubleBuffered(true);

        // Create game window depend on game panel
        new GameWindow(this);
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStartTime = System.currentTimeMillis() + 1000;

        // While game is running
        while (running) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000.0d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            if (accumulator >= UPDATE_INTERVAL) {
                while (accumulator >= UPDATE_INTERVAL) {
                    // Update game
                    update();
                    accumulator -= UPDATE_INTERVAL;
                }

                // Call paintComponent function render to screen
                repaint();
            }

            // Print fps and ups
            printStats();
        }

    }

    private void printStats() {
        // Print stats every second
        if (System.currentTimeMillis() > nextStartTime) {
            System.out.printf("FPS: %d, UPS: %d\n", fps, ups);
            fps = 0;
            ups = 0;
            nextStartTime = System.currentTimeMillis() + 1000;
        }

    }

    private void update() {
        // Update game
        game.update();
        ups++;
    }

    public void paintComponent(Graphics g) {
        // Call paintComponent of JPanel class
        super.paintComponent(g);

        // Render game
        game.render(g);
        // game.inPos();

        // Free memory manual
        g.dispose();
        fps++;
    }
}
