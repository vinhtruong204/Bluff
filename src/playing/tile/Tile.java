package playing.tile;

import java.awt.image.BufferedImage;

import core.Position;
import core.Size;

public class Tile {
    //create BufferedImage to Load image Tile
    private BufferedImage image;

    //getter anf setter to take image and set image
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
