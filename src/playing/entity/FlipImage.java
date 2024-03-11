package playing.entity;
import java.awt.Graphics2D;
import java  .awt.image.BufferedImage;

public class FlipImage {

  public static BufferedImage flipImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = flippedImage.createGraphics();

    // Translate the origin to the center of the image
    g.translate(width / 2.0, height / 2.0);

    // Rotate 180 degrees around the center
    //g.rotate(Math.PI, 0, 0);

    // Flip horizontally (optional)
    g.scale(-1, 1);

    // Draw the original image onto the flipped image
    g.drawImage(image, -width / 2, -height / 2, null);
    g.dispose();
    return flippedImage;
  }

}
