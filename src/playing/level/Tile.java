package playing.level;

import java.awt.image.BufferedImage;

import helpmethods.LoadSave;

public class Tile {
    public static final int TILE_SIZE = 48;

    private BufferedImage image;

    public Tile(String filePath) {
        image = LoadSave.loadImage(filePath);

    }

    // getter anf setter to take image and set image
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

}
