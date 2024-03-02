package menu;

import java.awt.Graphics;

import core.Position;
import core.Size;
import helpmethods.LoadSave;
import playing.entity.GameObject;
import states.MenuState;

public class Help extends GameObject {

    public Help() {
        // Load help image and initialize position and size
        image = LoadSave.loadImage("img/Menu/Help.png");
        position = new Position(0.0f, 0.0f);
        size = new Size(image.getWidth(), image.getHeight());
    }

    @Override
    public void update() {

    }

    @Override
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