import sun.plugin.dom.css.RGBColor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

class Clock extends JPanel {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int current = -1;
    private BufferedImage buffer;

    public Clock() {
        setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        Timer timer = new Timer(1000, e -> {
            current = (current + 1) % 60;
            buffer = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
            paint();
        });
        timer.start();
    }

    private void paint(){
        Graphics2D g2d = buffer.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.drawLine(250, 250, (int) (250 + 250 * Math.sin(Math.toRadians(current * 6))),
                (int) (250 - 250 * Math.cos(Math.toRadians(current * 6))));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Random random = new Random();
        g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        g.fillOval(0, 0, 500, 500);
        g.drawImage(buffer, 0, 0, null);
    }
}