package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import helpmethods.CheckKeyPress;
import helpmethods.PlayerAnimationType;
import playing.entity.Player;
import playing.pause.PauseButton;
import playing.tile.TileManager;
import gamestate.StateMethods;

public class Playing implements StateMethods {
    private Player player;
    private TileManager tileManager;
    private PauseButton pauseButton;

    public Playing() {
        tileManager = new TileManager();
        pauseButton = new PauseButton(3);
        player = new Player(tileManager.getTile(), tileManager.getMapTileNum());
    }

    @Override
    public void update() {
        player.update();
        pauseButton.update();
    }

    @Override
    public void render(Graphics g) {
        tileManager.render(g);
        player.render(g);
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setKeyPress(CheckKeyPress.Left);
                player.setAniType(PlayerAnimationType.RUN);
                break;
            case KeyEvent.VK_W:
                player.setKeyPress(CheckKeyPress.Up);
                player.setAniType(PlayerAnimationType.JUMP);
                break;
            case KeyEvent.VK_S:
                player.setKeyPress(CheckKeyPress.Down);
                player.setAniType(PlayerAnimationType.FALL);
                break;
            case KeyEvent.VK_D:
                player.setKeyPress(CheckKeyPress.Right);
                player.setAniType(PlayerAnimationType.RUN);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setKeyPress(CheckKeyPress.keyDefault);
                player.setAniType(PlayerAnimationType.IDLE);
                break;
            case KeyEvent.VK_W:
                player.setKeyPress(CheckKeyPress.keyDefault);
                player.setAniType(PlayerAnimationType.IDLE);
                player.setKeyPress(CheckKeyPress.keyDefault);
                player.setAniType(PlayerAnimationType.IDLE);
                break;
            case KeyEvent.VK_S:
                player.setKeyPress(CheckKeyPress.keyDefault);
                player.setAniType(PlayerAnimationType.IDLE);
                break;
            case KeyEvent.VK_D:
                player.setKeyPress(CheckKeyPress.keyDefault);
                player.setAniType(PlayerAnimationType.IDLE);
                break;

            default:
                break;
        }
    }

}
