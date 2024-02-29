package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamestate.StateMethods;
import playing.entity.Player;
import playing.tile.TileManager;

public class Playing implements StateMethods {
    private final float speed = 7f;
    Player player;
    private TileManager tileManager;

    public Playing() {
        player = new Player();
        tileManager = new TileManager();
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
                player.setIndex(1);
                player.setTotalAnim(14);
                player.getPosition().setX(player.getPosition().getX() - speed);
                break;
            case KeyEvent.VK_W:
                player.setIndex(4);
                player.setTotalAnim(4);
                player.getPosition().setY(player.getPosition().getY() - speed);
                break;
            case KeyEvent.VK_D:
                player.setIndex(1);
                player.setTotalAnim(14);
                player.getPosition().setX(player.getPosition().getX() + speed);

                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setIndex(0);
                player.setTotalAnim(26);
                break;
            case KeyEvent.VK_W:
                player.setIndex(0);
                player.setTotalAnim(26);
                break;
            case KeyEvent.VK_D:
                player.setIndex(0);
                player.setTotalAnim(26);
                break;

            default:
                break;
        }
    }

}
