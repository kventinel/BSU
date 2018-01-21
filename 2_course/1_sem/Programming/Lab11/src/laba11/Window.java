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

class Window extends JFrame{
    private static PaintPanel paintPanel;
    private static int width = 1500;
    private static int height = 1000;

    Window() {
        super("Drawing mouse");
        paintPanel = new PaintPanel(width, height);
        paintPanel.setPreferredSize(new Dimension(width, height));
        JScrollPane app = new JScrollPane(paintPanel);
        MenuPanel menuPanel = new MenuPanel();
        add(menuPanel, BorderLayout.NORTH);
        add(app, BorderLayout.CENTER);

        setSize(1000, 800);
        setLocation(450, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static class MenuPanel extends JPanel {
        private JButton buttonRed;
        private JButton buttonBlue;
        private JButton buttonGreen;
        private Font font = new Font("Verdana", Font.BOLD, 14);

        MenuPanel() {
            setPreferredSize(new Dimension(1000, 50));
            setLayout(new GridLayout(1, 4));
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu(" File");
            fileMenu.setFont(font);
            JMenuItem newItem = new JMenuItem("New");
            newItem.setFont(font);
            fileMenu.add(newItem);
            newItem.addActionListener(new AddNew());
            JMenuItem openItem = new JMenuItem("Open");
            openItem.setFont(font);
            fileMenu.add(openItem);
            openItem.addActionListener(new AddOpen());
            JMenuItem saveItem = new JMenuItem("Save as");
            saveItem.setFont(font);
            fileMenu.add(saveItem);
            saveItem.addActionListener(new AddSave());
            menuBar.add(fileMenu);
            menuBar.setBackground(Color.LIGHT_GRAY);
            add(menuBar);
            buttonBlue = new JButton("blue");
            buttonGreen = new JButton("green");
            buttonRed = new JButton("red");
            buttonBlue.setBackground(Color.BLUE);
            add(buttonBlue);
            add(buttonGreen);
            add(buttonRed);
            buttonBlue.addActionListener(new AddBlueButton());
            buttonGreen.addActionListener(new AddGreenButton());
            buttonRed.addActionListener(new AddRedButton());
        }

        private class AddNew implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.setBuffer(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
                Graphics2D g2d = paintPanel.getBuffer().createGraphics();
                g2d.setStroke(new BasicStroke(2.0f));
                paintPanel.repaint();
            }
        }

        private class AddOpen implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Open file");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        Image img = ImageIO.read(file);
                        setSize(new Dimension(getWidth(), getHeight()));
                        Graphics2D g2d = (Graphics2D) paintPanel.getBuffer().getGraphics();
                        g2d.drawImage(img, 0, 0, null);
                        paintPanel.repaint();
                    }
                } catch (IOException arg) {
                    JOptionPane.showMessageDialog(null, arg.toString());
                }
            }
        }

        private class AddSave implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filePath;
                    JFileChooser fileOpen = new JFileChooser();
                    int ret = fileOpen.showDialog(null, "Save file");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileOpen.getSelectedFile();
                        filePath = file.getPath();
                        ImageIO.write(paintPanel.getBuffer(), "png", new File(filePath + ".png"));
                    }
                    paintPanel.repaint();
                } catch (IOException arg) {
                    JOptionPane.showMessageDialog(null, arg.toString());
                }
            }
        }

        private class AddBlueButton implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.setColor("Blue");
                buttonBlue.setBackground(Color.BLUE);
                buttonGreen.setBackground(null);
                buttonRed.setBackground(null);
            }
        }

        private class AddGreenButton implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.setColor("Green");
                buttonGreen.setBackground(Color.GREEN);
                buttonRed.setBackground(null);
                buttonBlue.setBackground(null);
            }
        }

        private class AddRedButton implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.setColor("Red");
                buttonRed.setBackground(Color.RED);
                buttonBlue.setBackground(null);
                buttonGreen.setBackground(null);
            }
        }
    }
}
