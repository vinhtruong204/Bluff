package game;

import javax.swing.JPanel;

import input.KeyboardInputs;
import input.MouseInputs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GamePanel extends JPanel implements Runnable {
    // Update interval for each frame
    private final int TICKS_PER_SECOND = 60;
    private final int SKIP_TICKS = 1000000000 / TICKS_PER_SECOND;
    private final int MAX_FRAMESKIP = 5;

    // Variable time to help render motion in game
    public static float interpolation;

    // ups: update per second
    private long next_start_time;
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
        boolean running = true;
        long next_game_tick = System.nanoTime();
        int loops = 0;
        next_start_time = System.nanoTime() + 1000000000;

        while (running) {
            loops = 0;
            
            while (System.nanoTime() > next_game_tick && loops < MAX_FRAMESKIP) {
                update();
                next_game_tick += SKIP_TICKS;
                loops++;
            }

            interpolation = (float) (System.nanoTime() + (long) SKIP_TICKS - next_game_tick)
                    / (float) (SKIP_TICKS);
            repaint();

            printStats();
        }
    }

    private void printStats() {
        // Print stats every second
        if (System.nanoTime() > next_start_time) {
            System.out.printf("FPS: %d, UPS: %d\n", fps, ups);
            fps = 0;
            ups = 0;
            next_start_time = System.nanoTime() + 1000000000;
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

        // Free memory manual
        g.dispose();
        fps++;
    }
}
