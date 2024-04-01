package sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import helpmethods.LoadSave;

public class SoundItem {
    Clip clip;

    public SoundItem(String filePath) {
        try {
            AudioInputStream audioInputStream = LoadSave.loadSound(filePath);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        clip.start();
    }

    public void loop(){
        clip.loop(1);
    }

    public void stop(){
        clip.stop();
    }
}
