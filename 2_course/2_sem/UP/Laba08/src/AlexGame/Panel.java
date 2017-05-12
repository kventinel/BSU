package AlexGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class Panel extends JPanel {
    private BufferedImage lea;
    private Lightning lightning;
    private Zeus zeus;
    private DuckObservable ducks = new DuckObservable();
    private boolean end = false;
    private Timer timer;
    private Font textFont = new Font("name", Font.BOLD, 50);

    Panel() {
        super();
        Dimension windowSize = new Dimension(1862, 1025);
        setSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);

        try {
            lea = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/lea.jpg"));
            lightning = new Lightning();
            Duck.leftDuck = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/duck_life_left.bmp"));
            Duck.rightDuck = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/duck_life_right.bmp"));
            Duck.dieLeftDuck = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/duck_die_left.bmp"));
            Duck.dieRightDuck = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/duck_die_right.bmp"));
            zeus = new Zeus();
        } catch (IOException ex) {
        }

        updateDucks();
        timer = new Timer(20, e -> {
            updateDucks();
            repaint();
        });
        timer.start();

        addMouseMotionListener(new PanelMouseMotionListener());
        addMouseListener(new PanelMouseListener());
        addKeyListener(new PanelKeyListener());
        setFocusable(true);

        setVisible(true);
    }

    private void updateDucks() {
        ducks.move();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(lea, 0, 0, this);
        g.setFont(textFont);
        if (!end) {
            g.drawImage(zeus.getImage(), zeus.getX(), zeus.getY(), this);
            ducks.getImages(g, this);
            if (lightning.is()) {
                g.drawImage(lightning.getImage(), lightning.getX(), 0, this);
            }
            g.drawString("Level:" + Integer.toString(ducks.getLevel()), 0, 50);
        } else {
            g.drawString("Your Statistic:", 700, 300);
            g.drawString("Level " + Integer.toString(ducks.getLevel()), 700, 400);
            g.drawString("Count hits " + Double.toString(ducks.getHits()), 700, 500);
            g.drawString("Count shoots " + Double.toString(ducks.getShoots()), 700, 600);
            g.drawString("Accuracy " + Double.toString(100 * ducks.getAccuracy()) + "%", 700, 700);
            g.drawString("Lost ducks " + Integer.toString(ducks.getLostDucks()), 700, 800);
            timer.stop();
        }
    }

    class PanelMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            zeus.setX(e.getX() - 200);
        }
    }

    class PanelMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            lightning.resize(e.getX(), e.getY());
            ducks.kill(e.getX(), e.getY());
        }
    }

    class PanelKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            end = true;
        }
    }
}
