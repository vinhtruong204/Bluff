package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import helpmethods.CheckKeyPress;
import helpmethods.PlayerAnimationType;
import playing.entity.Player;
import playing.tile.TileManager;
import gamestate.StateMethods;

public class Playing implements StateMethods {
    private final float speed = 5f;
    Player player;
    private TileManager tileManager;

    public Playing() {
        tileManager = new TileManager();
        player = new Player(tileManager.getTile(), tileManager.getMapTileNum());
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(Graphics g) {
        tileManager.render(g);
        player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
