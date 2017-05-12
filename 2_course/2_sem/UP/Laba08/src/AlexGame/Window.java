package AlexGame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class Window extends JFrame {
    Window() {
        super("KriaProduction");
        Dimension windowSize = new Dimension(1862, 1025);
        setSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);
        setDefaultCloseOperation(Window.EXIT_ON_CLOSE);

        add(new Panel());

        setVisible(true);
    }
}
