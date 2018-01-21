package AlexGame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class Window extends JFrame {
    Window(int width, int height) {
        super("KriaProduction");
        Dimension windowSize = new Dimension(width, height);
        setSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);
        setDefaultCloseOperation(Window.EXIT_ON_CLOSE);

        add(new DemoPanel(width, height));

        setVisible(true);
    }
}
