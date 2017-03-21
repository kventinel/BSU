import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

class CircleImg extends JPanel {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int current = -1, time = 1000;
    private BufferedImage buffer;
    private int rad;
    private Image img = null;
    private Timer timer;

    class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            current = (current + 1) % 60;
            buffer = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffer.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.drawImage(img, (int) (rad + rad * Math.sin(Math.toRadians(current * 6))),
                    (int) (rad - rad * Math.cos(Math.toRadians(current * 6))), null);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 500, 500);
        g.drawImage(buffer, 0, 0, null);
    }

    public CircleImg() {
        rad = Math.min(400, 400) / 2;
        JSlider sl = new JSlider(JSlider.VERTICAL, 100, 4000, time);
        sl.setPaintTicks(true);
        sl.addChangeListener(e -> {
            time = sl.getValue();
            timer.stop();
            timer = new Timer(time, new Listener());
            System.out.println(time);
            timer.start();
        });
        setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        add(sl);
        try {
            img = ImageIO.read(new File("20150928_163840.jpg"));
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(null, "Bad file path");
        }
        timer = new Timer(time, new Listener());
        timer.start();
    }
}