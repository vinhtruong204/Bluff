package playing.entity.bomb;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpmethods.LoadSave;

public class BombManager {

    private ArrayList<Bomb> bombs;

    private int maxBomb;
    private int numberOfBombsExploded;

    private BufferedImage bombItem;

    public BombManager(int maxBomb,int numberOfBombsExploded)
    {
        bombs = new ArrayList<>();
        this.maxBomb = maxBomb;
        this.numberOfBombsExploded = numberOfBombsExploded;
        initBombItem();
    }

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
