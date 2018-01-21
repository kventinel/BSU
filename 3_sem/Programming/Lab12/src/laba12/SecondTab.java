package laba12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Rak Alexey on 12/13/16.
 */
class SecondTab extends JPanel {

    SecondTab() {
        super();
        setLayout(new GridLayout(5, 5));
        ButtonListener listener = new ButtonListener();
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                JButton button = new JButton();
                button.setText("Some text");
                button.addMouseListener(listener);
                add(button);
            }
        }
    }

    private class ButtonListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setText("Clicked!");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setText("Some text");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(Color.BLUE);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(null);
        }
    }
}
