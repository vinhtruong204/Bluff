package helpmethods;

public class HeartConstants {
    public static final int redHeart = 0;
    public static final int whiteHeart = 1;

    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case redHeart:
                return 22;
            case whiteHeart:
                return 4;
            default:
                return 0;
        }
    }
}
