package helpmethods;

// File path to all image in resources
public class FilePath {
    // Path to player image
    public class Player {
        public static final String PLAYER_IMAGE_PATH = "res/img/Player/Player-Bomb Guy.png";
    }

    // All path to enemies image
    public class Enemy {
        public static final String CUCUMBER = "res/img/Enemy/Enemy-Cucumber.png";
        public static final String CAPTAIN = "res/img/Enemy/Enemy-Captain.png";
        public static final String WHALE = "res/img/Enemy/Enemy-Whale.png";
        public static final String BOLD_PIRATE = "res/img/Enemy/Enemy-Bold Pirate.png";
        public static final String BIG_GUY = "res/img/Enemy/Enemy-Big Guy.png";
    }

    public class Object {
        public static final String HEART = "res/img/Object/Object-Heart.png";
        public static final String DOOR = "res/img/Door/Door.png";
        public static final String BOMB = "res/img/Player/Bomb.png";
        public static final String BOMB_ITEM = "res/img/Object/BombItem.png";
    }

    public class Tile {
        public static final String BLUE = "res/img/Tile/Blue.png";
        public static final String ELEVATION = "res/img/Tile/Tilemap_Elevation.png";
        public static final String BOX = "res/img/Tile/Box.png";
    }

    public class Font {
        public static final String PRESS_START2P = "res/font/PressStart2P-Regular.ttf";
    }

    public class Map {
        public static final String MAP_1 = "res/map/Map01.txt";
        public static final String MAP_2 = "res/map/Map02.txt";
        public static final String MAP_3 = "res/map/Map03.txt";
        public static final String MAP_4 = "res/map/Map04.txt";
        public static final String MAP_5 = "res/map/Map05.txt";
        public static final String MAP_6 = "res/map/Map06.txt";
        public static final String MAP_7 = "res/map/Map07.txt";
    }

    public class Sound {
        // Button
        public static final String PAUSE_SOUND_BUTTON = "res/img/Pause/Pause_Button_Sound.png";
        public static final String PAUSE_SOUND_BACKGROUND_BUTTON = "res/img/Pause/Pause_Music_Button.png";

        // Audio
        public static final String MUSIC_BACKGROUND = "res/sound/SoundBackground.wav";

        // Sound effect
        public static final String BOMB_SOUND = "res/sound/SoundBomb.wav";
        public static final String COLLECTED_HEART_SOUND = "res/sound/collect.wav";
        public static final String GAMEOVER_SOUND = "res/sound/GameOver.wav";
        public static final String ENEMY_ATTACK = "res/sound/EnemyAttack.wav";
        public static final String SOUND_NEW_MAP = "res/sound/SoundNewMap.wav";
        public static final String SOUND_JUMP_PLAYER = "res/sound/Jump.wav";
    }

    public class GameOver{
        //game over
        public static final String GAME_OVER = "res/img/Win_Lose/You_Lose.png";
    }

    public class Menu {
        // Background
        public static final String HELP_MENU = "res/img/Menu/Help.png";
        public static final String BACKGROUND = "res/img/Menu/Background_Menu.png";

        // Button
        public static final String MENU_BUTTONS = "res/img/Menu/Menu_Buttons.png";
    }

    public class Pause {
        // Pause button in playing
        public static final String PAUSE_BUTTON = "res/img/Pause/Pause_Buttons.png";

        // Background
        public static final String PAUSE_BACKGROUND = "res/img/Pause/Pause_Background.png";

        // Pause button
        public static final String PAUSE_OPTIONS = "res/img/Pause/Pause_Buttons.png";

        // Popup exit background
        public static final String POPUP_BACKGROUND = "res/img/Pause/Exit_Popup_Background.png";

        // Popup exit button
        public static final String POPUP_BUTTONS = "res/img/Pause/Popup_Button.png";

    }

}