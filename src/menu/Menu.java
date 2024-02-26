package menu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestate.StateMethods;
import helpmethods.CheckContain;
import helpmethods.LoadSave;

public class Menu implements StateMethods {
    BufferedImage background;
    MenuButton[] buttons;

    public Menu() {
        background = LoadSave.loadImage("img/Background_Menu.png");
        buttons = new MenuButton[3];
        loadButtons();
    }

    private void loadButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new MenuButton(i);
        }
    }

    @Override
    public void update() {
        for (MenuButton button : buttons) {
            button.update();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null);
        for (MenuButton button : buttons) {
            button.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (CheckContain.isIn(e, button)) {
                button.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (CheckContain.isIn(e, button)) {
                if (button.isMousePressed()) {
                    // Apply game state
                    button.applyGameState();
                }
            }
        }

        // Reset boolean of all button
        for (MenuButton button : buttons) {
            button.resetBoolean();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (CheckContain.isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
