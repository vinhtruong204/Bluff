package playing.level;

import java.awt.image.BufferedImage;

import helpmethods.LoadSave;

public class Tile {
    // Size of tile
    public static final int TILE_SIZE = 48;
    
    // Total tile type 
    public static final int TOTAL_TILE_TYPE = 2;

    // All type of tile
    public static final int BLUE = 0; // background
    public static final int ELEVATION = 1; // brick
    

    // Actual image of tile
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
