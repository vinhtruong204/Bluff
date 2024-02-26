package helpmethods;

import java.awt.event.MouseEvent;
import menu.MenuButton;

public class CheckContain {
    public static boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
