import java.awt.*;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.image.ConvolveOp;
public class BlurredImage extends JPanel {

    private static final float[][] blurKernel = {
            { 0.0f, 0.1f, 0.0f },
            { 0.1f, 0.4f, 0.1f },
            { 0.0f, 0.1f, 0.0f }
    };

    private BufferedImage image;

    public BlurredImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Create a ConvolveOp with the blur kernel
        ConvolveOp blurOp = new ConvolveOp(null);

        // Filter the image with the blur operation
        BufferedImage blurredImage = blurOp.filter(image, null);

        // Draw the blurred image
        g2d.drawImage(blurredImage, 0, 0, null);
    }

    public static void main(String[] args) throws Exception {
        // Load your image
        BufferedImage image = ImageIO.read(new File("img/Player/Player-Bomb Guy.png"));

        // Create a panel with the blurred image
        BlurredImage panel = new BlurredImage(image);

        // Display the panel in a frame
        JFrame frame = new JFrame("Blurred Image");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
