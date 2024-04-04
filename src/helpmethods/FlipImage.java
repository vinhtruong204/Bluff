package helpmethods;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FlipImage {

  public static BufferedImage horizontalflip(BufferedImage img) {
    // Get image dimension
    int width = img.getWidth();
    int height = img.getHeight();

    // Create flip image
    BufferedImage flippedImage = new BufferedImage(width, height, img.getType());

    // Create graphic from flipped image
    Graphics2D g = flippedImage.createGraphics();

    /*
     * img the specified image to be drawn. This method does nothing if img is null.
     * dx1 the x coordinate of the first corner of the destination rectangle.
     * dy1 the y coordinate of the first corner of the destination rectangle.
     * dx2 the x coordinate of the second corner of the destination rectangle.
     * dy2 the y coordinate of the second corner of the destination rectangle.
     * sx1 the x coordinate of the first corner of the source rectangle.
     * sy1 the y coordinate of the first corner of the source rectangle.
     * sx2 the x coordinate of the second corner of the source rectangle.
     * sy2 the y coordinate of the second corner of the source rectangle.
     * observer object to be notified as more of the image is scaled and converted.
     */
    g.drawImage(img, 0, 0, width, height, width, 0, 0, height, null);

    // Return image flipped
    return flippedImage;
  }

}
