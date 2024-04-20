import java.io.*;

public class WriteMap {
    public static void main(String[] args) {
        FileWriter writer = null;
        int row = 30, col = 80;

        try {
            writer = new FileWriter("res/map/Map06.txt");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (i == 0 || j == 0 || i == row - 1 || j == col - 1)
                    writer.write("1 ");
                    else 
                    writer.write("0 ");
                }
                writer.write("\n");
            }
            
            writer.close();
        } catch (Exception e) {

        }
        
    }
}
