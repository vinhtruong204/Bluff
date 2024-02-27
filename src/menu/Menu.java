package menu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestate.StateMethods;
import helpmethods.CheckContain;
import helpmethods.LoadSave;

public class Menu implements StateMethods {
    // Background image of the menu
    BufferedImage background;

    // Array button of the menu
    MenuButton[] buttons;

    public Menu() {
        // Load background from file
        background = LoadSave.loadImage("img/Background_Menu.png");

        // Allocate memory 
        buttons = new MenuButton[3];

        // Initialize all buttons
        initButtons();
    }

    private void initButtons() {
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
        // Render background to screen
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
            // If mouse press over button
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
                    // Apply game state when mouse released on button
                    button.applyGameState();
                }
            }
        }

        // Reset boolean of all button if mouse released
        for (MenuButton button : buttons) {
            button.resetBoolean();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Reset mouse over when mouse moved
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        // Check and set mouse over
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
