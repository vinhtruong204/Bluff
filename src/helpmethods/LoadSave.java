package helpmethods;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class LoadSave {
    public static BufferedImage loadImage(String filePath) {
        // Try load image from file path
        try {
            return ImageIO.read(new File(filePath));
        } catch (Exception e) {
            System.err.println("Could not load file from: " + filePath);
            System.err.println(e.getMessage());
        }
        return null;
    }


    public static AudioInputStream loadSound(String filePath)
    {
        // Try load Sound from file path
        try{
            return AudioSystem.getAudioInputStream(new File(filePath));
        } catch(Exception e){
            System.err.println("Could not load file from: " + filePath);
            System.err.println(e.getMessage());
        }
        return null;
    }

    

    public static int[][] loadMap(String filePath,int maxWorldCol,int maxWorldRow) {
        int map[][] = new int[maxWorldRow][maxWorldCol];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            int col = 0, row = 0;
            while (col < maxWorldCol && row < maxWorldRow) {
                String Line = br.readLine();
                while (col < maxWorldCol) {
                    String Number[] = Line.split("\\s+");
                    int Num = Integer.parseInt(Number[col]);
                    map[row][col] = Num;
                    col++;
                }
                if (col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
