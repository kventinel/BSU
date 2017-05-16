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

class DemoPanel extends JPanel {
    private BufferedImage lea;
    private Lightning lightning;
    private Zeus zeus;
    private DuckObservable ducks;
    private boolean end = false;
    private Timer timer;
    private Font textFont;
    private int width;
    private int height;

    DemoPanel(int newWidth, int newHeight) {
        super();
        Dimension windowSize = new Dimension(newWidth, newHeight);
        setSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);

        width = newWidth;
        height = newHeight;
        textFont = new Font("name", Font.BOLD, newHeight / 20);

        try {
            lea = ImageIO.read(new File("data/lea.jpg"));
            lightning = new Lightning();
            Duck.leftDuck = ImageIO.read(new File("data/duck_life_left.bmp"));
            Duck.rightDuck = ImageIO.read(new File("data/duck_life_right.bmp"));
            Duck.dieLeftDuck = ImageIO.read(new File("data/duck_die_left.bmp"));
            Duck.dieRightDuck = ImageIO.read(new File("data/duck_die_right.bmp"));
            zeus = new Zeus(width, height);
        } catch (IOException ex) {
        }

        ducks = new DuckObservable(width, height);

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
            g.drawString("Your Statistic:", width / 3, height * 3 / 10);
            g.drawString("Level " + Integer.toString(ducks.getLevel()), width / 3, height * 4 / 10);
            g.drawString("Count hits " + Double.toString(ducks.getHits()), width / 3, height * 5 / 10);
            g.drawString("Count shoots " + Double.toString(ducks.getShoots()), width / 3, height * 6 / 10);
            g.drawString("Accuracy " + Double.toString(100 * ducks.getAccuracy()) + "%", width / 3, height * 7 / 10);
            g.drawString("Lost ducks " + Integer.toString(ducks.getLostDucks()), width / 3, height * 8 / 10);
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
