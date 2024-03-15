package helpmethods;

public class BombConstants {
    public static final int PLACINGBOMB = 0;
    public static final int ACTIVATINGBOMB = 1;
    public static final int EXPLODINGBOMB = 2;

    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case PLACINGBOMB:
                return 1;
            case ACTIVATINGBOMB:
                return 4;
            case EXPLODINGBOMB:
                return 6;
            default:
                return 0;
        }
    }
}
