package sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import helpmethods.LoadSave;

public class SoundObject implements SoundStatus {
    // used to perform and control an audio clip
    Clip clip;

    //Stores the clip's current position
    long clipPosition;

    //constructor
    public SoundObject(String filePath) {
        try {
            AudioInputStream audioInputStream = LoadSave.loadSound(filePath);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    //start
    @Override
    public void start(){
        //Start playing sound
        clip.start();
    }

    //loop
    @Override
    public void loop(int i){
        //loop
        clip.loop(i);
    }

    //stop
    @Override
    public void stop(){
        //Stores the clip's current position before stopping it
        clipPosition = clip.getMicrosecondPosition();
        //stop 
        clip.stop();
    }

    //pause
    @Override
    public void resume(){
        // Reposition the clip
        clip.setMicrosecondPosition(clipPosition); 
        // playback from the saved position
        clip.start(); 
    }
}
