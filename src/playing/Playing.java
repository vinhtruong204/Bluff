package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamestate.StateMethods;
import playing.entity.Player;
import playing.tile.TileManager;

public class Playing implements StateMethods {
    Player player;
    private TileManager tileManager;

    public Playing() {
        player = new Player();
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
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
                System.out.println("It's A!");
                break;
            case KeyEvent.VK_W:
                System.out.println("It's W!");

                break;
            case KeyEvent.VK_S:
                System.out.println("It's S!");

                break;
            case KeyEvent.VK_D:
                System.out.println("It's D!");

                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:

                break;
            case KeyEvent.VK_W:

                break;
            case KeyEvent.VK_S:

                break;
            case KeyEvent.VK_D:

                break;

            default:
                break;
        }
    }

}
