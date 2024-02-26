package helpmethods;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class LoadSave {
    public static BufferedImage loadImage(String filePath)
    {
        try {
            return ImageIO.read(new File(filePath));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
}
