package playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import playing.level.LevelManager;
import playing.pause.PauseButton;
import sound.pause_music_background.PauseMusicBackGroundState;
import sound.pause_music_background.PauseMusicBackground;
import sound.pause_sound.PauseSoundButtons;
import sound.pause_sound.PauseSoundState;
import gamestate.StateMethods;

public class Playing implements StateMethods {
    private LevelManager levelManager;
    private PauseButton pauseButton;
    private PauseSoundButtons pauseSoundButton;
    private PauseMusicBackground pauseMusicBackground;

    public Playing() {
        levelManager = new LevelManager(this);
        pauseButton = new PauseButton(3);
        pauseSoundButton = new PauseSoundButtons(1);
        pauseMusicBackground = new PauseMusicBackground(1);
    } 

    public void resetAll() {
        levelManager = new LevelManager(this);
        pauseButton = new PauseButton(3);
        pauseSoundButton = new PauseSoundButtons(1);
        PauseSoundState.pauseSoundState = PauseSoundState.ON;
        pauseMusicBackground = new PauseMusicBackground(1);
        PauseMusicBackGroundState.pauseMusicBackGroundState = PauseMusicBackGroundState.ON;
    }

    @Override
    public void update() {
        levelManager.update();
        pauseButton.update();
        pauseSoundButton.update();
        pauseMusicBackground.update();
    }

    @Override
    public void render(Graphics g) {
        levelManager.render(g);
        pauseButton.render(g);
        pauseSoundButton.render(g);
        pauseMusicBackground.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (pauseButton.isIn(e)) {
            pauseButton.setMousePressed(true);
        }
        
        if(pauseSoundButton.isIn(e) && PauseSoundState.pauseSoundState == PauseSoundState.ON){
            pauseSoundButton.setMousePressed(true);
        }else if(pauseSoundButton.isIn(e) && PauseSoundState.pauseSoundState == PauseSoundState.OFF){
            pauseSoundButton.setMousePressed(true);
        }

        if(pauseMusicBackground.isIn(e) && PauseMusicBackGroundState.pauseMusicBackGroundState == PauseMusicBackGroundState.ON){
            pauseMusicBackground.setMousePressed(true);
        }
        else if(pauseMusicBackground.isIn(e) && PauseMusicBackGroundState.pauseMusicBackGroundState == PauseMusicBackGroundState.OFF){
            pauseMusicBackground.setMousePressed(true);
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pauseButton.isIn(e) && pauseButton.isMousePressed()) {
            // Apply game state when mouse released on pause button
            pauseButton.applyGameState();
        }

        if(pauseSoundButton.isIn(e) && PauseSoundState.pauseSoundState == PauseSoundState.ON){
            pauseSoundButton = new PauseSoundButtons(0);
            PauseSoundState.pauseSoundState = PauseSoundState.OFF;
        }
        else if(pauseSoundButton.isIn(e) && PauseSoundState.pauseSoundState == PauseSoundState.OFF){
            pauseSoundButton = new PauseSoundButtons(1);
            PauseSoundState.pauseSoundState = PauseSoundState.ON;
        }

        if(pauseMusicBackground.isIn(e) && PauseMusicBackGroundState.pauseMusicBackGroundState == PauseMusicBackGroundState.ON){
            pauseMusicBackground = new PauseMusicBackground(0);
            PauseMusicBackGroundState.pauseMusicBackGroundState = PauseMusicBackGroundState.OFF;
        }
        
        else if(pauseMusicBackground.isIn(e) && PauseMusicBackGroundState.pauseMusicBackGroundState == PauseMusicBackGroundState.OFF){
            pauseMusicBackground = new PauseMusicBackground(1);
            PauseMusicBackGroundState.pauseMusicBackGroundState = PauseMusicBackGroundState.ON;
        }

        // Reset mouse over when mouse moved
        pauseButton.resetBoolean();
        pauseSoundButton.resetBoolean();
        pauseMusicBackground.resetBoolean();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        // Reset mouse over when mouse move
        pauseButton.setMouseOver(false);
        pauseSoundButton.setMouseOver(false);
        pauseMusicBackground.setMouseOver(false);

        // Check mouse over if mouse is in pause button
        if (pauseButton.isIn(e)) {
            pauseButton.setMouseOver(true);
        }

        if(pauseSoundButton.isIn(e)){
            pauseSoundButton.setMouseOver(true);
        }

        if(pauseMusicBackground.isIn(e)){
            pauseMusicBackground.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        levelManager.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        levelManager.keyReleased(e);
    }

}
