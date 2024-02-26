package playing.entity;

import java.awt.Graphics;

import helpmethods.LoadSave;

public class Player extends GameObject {

    public Player() {
        image = LoadSave.loadImage("img/Enemy-Cucumber.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

}
