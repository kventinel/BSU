package laba10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Rak Alexey on 12/11/16.
 */

public class Window extends JFrame {
    private JPanel rootPanel;

    private StringBuffer buttonName = new StringBuffer("");
    private int oldX;
    private int oldY;
    private char backSpace = KeyEvent.VK_BACK_SPACE;

    Window() {
        super("Window");
        add(rootPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 700));
        setSize(1000, 700);
        setVisible(true);
        rootPanel.setLayout(null);

        final JButton button = new JButton();
        button.setSize(300, 50);
        button.setLocation(100, 100);
        rootPanel.add(button);

        final JTextArea statusBar = new JTextArea();
        rootPanel.add(statusBar);
        statusBar.setEditable(false);
        statusBar.setSize(150, 20);
        statusBar.setLocation(800, 650);
        statusBar.setText("x: 0   y: 0");

        rootPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setLocation(e.getX(), e.getY());
            }
        });

        rootPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setText("x: " + e.getX() + "   y: " + e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                statusBar.setText("x: " + (button.getX() + e.getX()) + "   y: " + (button.getY() + e.getY()));
            }
        });

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == backSpace) {
                    if (buttonName.length() > 0) {
                        buttonName.deleteCharAt(buttonName.length() - 1);
                    }
                } else {
                    buttonName.append(e.getKeyChar());
                }
                button.setText(buttonName.toString());

            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        button.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setText("x: " + (button.getX() + e.getX()) + "   y: " + (button.getY() + e.getY()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.isControlDown()) {
                    button.setLocation(button.getX() + e.getX() - oldX, button.getY() + e.getY() - oldY);
                }
                statusBar.setText("x: " + (button.getX() + e.getX()) + "   y: " + (button.getY() + e.getY()));
            }
        });
    }
}
