package AlexGame;

import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rak Alexey on 5/12/17.
 */

public class Panel extends JPanel {
    private BufferedImage lea;
    private BufferedImage duck200;
    private ArrayList<Duck> ducks = new ArrayList<Duck>();
    private boolean create = true;

    Panel() {
        super();
        Dimension windowSize = new Dimension(1862, 1025);
        setSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);

        try {
            lea = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/lea.jpg"));
            duck200 = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/duck.png"));
        } catch (IOException ex) {
        }

        updateDucks();
        new Timer(10, e->{
            updateDucks();
        }).start();

        setVisible(true);
    }

    private void updateDucks() {
        for (Duck duck : ducks) {
            duck.move();
        }

        while (ducks.size() < 5) {
            ducks.add(new Duck());
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (create) {
            super.paintComponent(g);
            g.drawImage(lea, 0, 0, this);
            create = false;
        }
        for (Duck duck : ducks) {
            g.drawImage(duck200, duck.getX(), duck.getY(), this);
        }
    }
}
