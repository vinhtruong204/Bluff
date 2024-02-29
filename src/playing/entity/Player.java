package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.sql.rowset.spi.TransactionalWriter;

import core.Position;
import helpmethods.LoadSave;

public class Player extends GameObject {

    private BufferedImage[][] anim;
    private final int TILE_PLAYER = 58;
    private int index, totalAnim;
    private int TOTAL_FRAME=0;
    private int animStick,animIndex;

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTotalAnim(int totalAnim) {
        this.totalAnim = totalAnim;
    }

    public Player() {
        // Load image player from file
        image = LoadSave.loadImage("img/Player-Bomb Guy.png");
        position = new Position(100f, 100f);
        anim = new BufferedImage[6][26];
        loadImage();
        index=0;
        totalAnim=26;

    }

    private void loadImage() {
        for (int i = 0; i < anim.length; i++) {
            for (int j = 0; j < anim[i].length; j++) {
                anim[i][j] = image.getSubimage(j * TILE_PLAYER, i * TILE_PLAYER, TILE_PLAYER, TILE_PLAYER);
            }
        }
    }

    @Override
    public void update() {
        animStick++;
        if(animStick>1)
        {
            animStick=0;
            animIndex++;
            if(animIndex>=totalAnim)
            {
                animIndex=0;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        // Render player to screen
            g.drawImage(anim[index][animIndex], (int)position.getX(), (int)position.getY(),TILE_PLAYER,TILE_PLAYER, null);
    }

}
