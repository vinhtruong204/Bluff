package playing.tile;

import java.awt.image.BufferedImage;

import helpmethods.LoadSave;

public class Level {
    // create BufferedImage to Load image Tile
    private BufferedImage image;
    private boolean collition;
    private int map[][];
    private int maxWorldCol,maxWorldRow;

    public Level(String filePath) {
        map = LoadSave.loadMap(filePath, maxWorldCol, maxWorldRow);
    }

    

    // getter anf setter to take image and set image
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setCollition(boolean collition)
    {
        this.collition=collition;
    }

    public boolean getCollition()
    {
        return collition;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }



    public void setMaxWorldCol(int maxWorldCol) {
        this.maxWorldCol = maxWorldCol;
    }



    public int getMaxWorldRow() {
        return maxWorldRow;
    }



    public void setMaxWorldRow(int maxWorldRow) {
        this.maxWorldRow = maxWorldRow;
    }
}
