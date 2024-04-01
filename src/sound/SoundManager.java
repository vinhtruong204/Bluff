package sound;


public class SoundManager {
    SoundBackground sBackground;
    SoundItem bomb;
    SoundItem heart;

    public SoundManager() {
        sBackground = new SoundBackground("sound/drive-to-triumph-188794.wav");
    }

    public void start(){
        sBackground.start();
        sBackground.loop();
    }

    
}
