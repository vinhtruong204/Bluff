package helpmethods;

public class HeartConstants {
    public static final int RED_HEART = 0;
    public static final int WHITE_HEART= 1;

    // Total frame and type
    public static final int TOTAL_TYPE = 2;
    public static final int TOTAL_FRAME = 22;

    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case RED_HEART:
                return 22;
            case WHITE_HEART:
                return 4;
            default:
                return 0;
        }
    }
}
