package playing.entity;

import java.awt.Graphics;

import helpmethods.LoadSave;

public class Player extends GameObject {

    public Player() {
        // Load image player from file
        image = LoadSave.loadImage("img/Player-Bomb Guy.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        // Render player to screen
        g.drawImage(image, 0, 0, null);
    }

}
