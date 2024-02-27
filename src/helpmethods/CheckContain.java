package helpmethods;

import java.awt.event.MouseEvent;
import menu.MenuButton;

public class CheckContain {

    // Check mouse over a button
    public static boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
