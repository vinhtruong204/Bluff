package helpmethods;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class LoadSave {
    public static BufferedImage loadImage(String filePath)
    {
        // Try load image from file path
        try {
            return ImageIO.read(new File(filePath));
        } catch (Exception e) {
            System.err.println("Could not load file from: " + filePath);
            System.err.println(e.getMessage());
        }
        return null;
    }
}
