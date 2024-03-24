package helpmethods;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

public class Draw {
    public static void drawString(Graphics g,String text,int i,int j)
    {
        // Set color
        g.setColor(Color.YELLOW);
        // Set font
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/PressStart2P-Regular.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        g.setFont(customFont);
        //Draw String
        g.drawString(text,j,i);
    }

    public static void drawImage(Graphics g,BufferedImage image,int i,int j)
    {
        g.drawImage(image, j, i,48,48, null);
    }
}
