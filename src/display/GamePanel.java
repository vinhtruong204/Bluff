package display;

import javax.swing.JPanel;

import game.Game;

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

    // private Game game;

    public GamePanel() {
        // keyHandler = new KeyHandler();
        // game = new Game(this.keyHandler);
        this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.black);
        // Draws multiple images to the buffer before displaying them on the screen
        this.setDoubleBuffered(true);
        // this.addKeyListener(keyHandler);
        new GameWindow(this);
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStartTime = System.currentTimeMillis() + 1000;

        while (running) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000.0d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            if (accumulator >= UPDATE_INTERVAL) {
                while (accumulator >= UPDATE_INTERVAL) {
                    update();
                    accumulator -= UPDATE_INTERVAL;
                }

                repaint();
            }

            printStats();
        }

    }

    private void printStats() {
        if (System.currentTimeMillis() > nextStartTime) {
            System.out.printf("FPS: %d, UPS: %d\n", fps, ups);
            fps = 0;
            ups = 0;
            nextStartTime = System.currentTimeMillis() + 1000;
        }

    }

    private void update() {
        // game.update();
        ups++;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // game.render(g);

        g.dispose();
        fps++;
    }
}
