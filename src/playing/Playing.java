package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import helpmethods.CheckKeyPress;
import playing.camera.camera;
import playing.entity.EnemyManager;
import playing.entity.Player;
import playing.pause.PauseButton;
import playing.tile.TileManager;
import gamestate.StateMethods;

public class Playing implements StateMethods {
    private Player player;
    private TileManager tileManager;
    private PauseButton pauseButton;
    private camera screen;

    private EnemyManager enemyManager;

    public Playing() {
        tileManager = new TileManager();
        player = new Player(tileManager.getTile(), tileManager.getMapTileNum());
        screen = new camera(tileManager.getMapTileNum(), tileManager.getTile(), player);
        pauseButton = new PauseButton(3);
        enemyManager = new EnemyManager();
    }

    @Override
    public void update() {
        tileManager.update();
        player.update();
        enemyManager.update();
        pauseButton.update();
        player.update();
        player.setScreen(screen.getMapStartX(),screen.getMapStartY());
        screen.update();
    }

    @Override
    public void render(Graphics g) {
        screen.render(g);
        tileManager.render(g);
        player.render(g);
        pauseButton.update();
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setKeyPress(CheckKeyPress.Left);
                break;
            case KeyEvent.VK_W:
                player.setKeyPress(CheckKeyPress.Up);
                break;
            case KeyEvent.VK_D:
                player.setKeyPress(CheckKeyPress.Right);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setKeyPress(CheckKeyPress.Down);
                break;
            case KeyEvent.VK_W:
                player.setKeyPress(CheckKeyPress.Down);
                break;
            case KeyEvent.VK_D:
                player.setKeyPress(CheckKeyPress.Down);
                break;

            default:
                break;
        }
    }

}
