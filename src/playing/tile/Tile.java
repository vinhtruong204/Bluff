package playing.tile;

import java.awt.image.BufferedImage;

import core.Position;
import core.Size;

public class Tile {
    private BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
