package playing.entity.bomb;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpmethods.Draw;
import helpmethods.LoadSave;

public class BombManager {

    //List bomb
    private ArrayList<Bomb> bombs;

    //Max bomb
    private int maxBomb;
    //number of bombs used
    private int numberOfBombsExploded;

    //bomb item
    private BufferedImage bombItem;

    //contructor
    public BombManager(int maxBomb)
    {
        bombs = new ArrayList<Bomb>();
        this.maxBomb = maxBomb;
        this.numberOfBombsExploded = 0;
        initBombItem();
    }

    //render
    public void render(Graphics g)
    {
        Draw.drawString(g, "x" + Integer.toString(maxBomb - numberOfBombsExploded), 50, 32);
        Draw.drawImage(g,bombItem , -8, 15,48,48);
    }

    //getter and setter
    private void initBombItem() {
        bombItem = LoadSave.loadImage("img/Object/BombItem.png");
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }
    public int getNumberOfBombsExploded() {
        return numberOfBombsExploded;
    }

    public void setNumberOfBombsExploded(int numberOfBombsExploded) {
        this.numberOfBombsExploded = numberOfBombsExploded;
    }

    public BufferedImage getBombItem() {
        return bombItem;
    }

    public void setBombItem(BufferedImage bombItem) {
        this.bombItem = bombItem;
    }
}
