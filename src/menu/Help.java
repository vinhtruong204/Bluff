package menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import helpmethods.LoadSave;

public class Help {
    private Position position;
    private Size size;
    private BufferedImage image;

    public Help() {
        // Load help image and initialize position and size
        image = LoadSave.loadImage("img/Menu/Help.png");
        position = new Position(0.0f, 0.0f);
        size = new Size(image.getWidth(), image.getHeight());
    }

    public void update() {

    }

    public void render(Graphics g) {
        g.drawImage(image,
                (int) position.getX(),
                (int) position.getY(),
                size.getWidth(),
                size.getHeight(),
                null);
    }

    public void applyGameState() {
        // Change game state to menu
        MenuState.menuState = MenuState.MAIN;
    }

}