package gamestate.menu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestate.StateMethods;
import helpmethods.FilePath;
import helpmethods.LoadSave;

public class Menu implements StateMethods {
    // Background image of the menu
    BufferedImage background;

    // Array button of the menu
    MenuButton[] buttons;

    // Help state of menu
    Help help;

    public Menu() {
        // Load background from file
        background = LoadSave.loadImage(FilePath.Menu.BACKGROUND);

        // Allocate memory
        buttons = new MenuButton[3];

        // Initialize help
        help = new Help();

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

        // If game state is main menu
        if (MenuState.menuState == MenuState.MAIN) {
            // Render background to screen
            g.drawImage(background, 0, 0, null);

            // Render all buttons to screen
            for (MenuButton button : buttons) {
                button.render(g);
            }
        }

        // If game state is help menu
        else if (MenuState.menuState == MenuState.HELP) {
            help.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (MenuState.menuState == MenuState.MAIN) {
            for (MenuButton button : buttons) {
                // If mouse press over button
                if (button.isIn(e)) {
                    button.setMousePressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (MenuState.menuState == MenuState.MAIN) {
            for (MenuButton button : buttons) {
                if (button.isIn(e)) {
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
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (MenuState.menuState == MenuState.MAIN) {
            // Reset mouse over when mouse moved
            for (MenuButton button : buttons)
                button.setMouseOver(false);

            // Check and set mouse over
            for (MenuButton button : buttons)
                if (button.isIn(e)) {
                    button.setMouseOver(true);
                    break;
                }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If user pressed enter key in help window
        if (MenuState.menuState == MenuState.HELP && e.getKeyCode() == KeyEvent.VK_ENTER)
            help.applyGameState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
