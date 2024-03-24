package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import playing.pause.PauseButton;
import playing.tile.LevelManager;
import gamestate.StateMethods;

public class Playing implements StateMethods {
    private LevelManager levelManager;
    private PauseButton pauseButton;

    public Playing() {
        levelManager = new LevelManager(this);
        pauseButton = new PauseButton(3);
    }

    public void resetAll() {
        levelManager = new LevelManager(this);
        pauseButton = new PauseButton(3);

    }

    @Override
    public void update() {
        levelManager.update();
        pauseButton.update();
    }

    @Override
    public void render(Graphics g) {
        levelManager.render(g);
        pauseButton.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (pauseButton.isIn(e)) {
            pauseButton.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pauseButton.isIn(e) && pauseButton.isMousePressed()) {
            // Apply game state when mouse released on pause button
            pauseButton.applyGameState();
        }

        // Reset mouse over when mouse moved
        pauseButton.resetBoolean();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Reset mouse over when mouse move
        pauseButton.setMouseOver(false);

        // Check mouse over if mouse is in pause button
        if (pauseButton.isIn(e)) {
            pauseButton.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        levelManager.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        levelManager.keyReleased(e);
    }

}
