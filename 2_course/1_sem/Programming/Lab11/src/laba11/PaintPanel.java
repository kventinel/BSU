package laba11;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rak Alexey on 12/21/16.
 */
class PaintPanel extends JPanel {
    private int lastX, lastY;
    private static BufferedImage buffer;
    private static Color color = Color.BLUE;

    PaintPanel(int w, int h) {
        this.setBackground(Color.white);
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);
        g2d.setStroke(new BasicStroke(2.0f));

        addMouseListener(new addMousePressed());
        addMouseMotionListener(new addMouseMotion());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    private class addMousePressed extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            lastX = e.getX();
            lastY = e.getY();
            Graphics2D g2d = buffer.createGraphics();
            g2d.setColor(color);
            g2d.drawLine(lastX, lastY, lastX, lastY);
            repaint();
        }
    }

    private class addMouseMotion extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            Graphics2D g2d = buffer.createGraphics();
            g2d.setColor(color);
            g2d.drawLine(lastX, lastY, x, y);
            lastX = x;
            lastY = y;
            repaint();
        }
    }

    void setColor(String newColor) {
        if (newColor.equals("Blue")) {
            color = Color.BLUE;
        } else if (newColor.equals("Green")) {
            color = Color.GREEN;
        } else if (newColor.equals("Red")) {
            color = Color.RED;
        }
    }

    BufferedImage getBuffer() {
        return buffer;
    }

    void setBuffer(BufferedImage newBuffer) {
        buffer = newBuffer;
    }
}
