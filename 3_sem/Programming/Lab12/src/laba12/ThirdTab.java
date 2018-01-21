package laba12;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Rak Alexey on 12/13/16.
 */
class ThirdTab extends JPanel {
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JPanel mainPanel;
    private ImageIcon selectIcon =  new ImageIcon("src/laba12/general/Redo16.gif");
    private ImageIcon unselectIcon =  new ImageIcon("src/laba12/general/Stop16.gif");
    private ImageIcon enteredIcon =  new ImageIcon("src/laba12/general/About16.gif");
    private ImageIcon pressedIcon =  new ImageIcon("src/laba12/general/Add16.gif");

    ThirdTab() {
        super();
        add(mainPanel);

        radioButton1.setIcon(unselectIcon);
        radioButton2.setIcon(unselectIcon);
        radioButton3.setIcon(unselectIcon);
        radioButton4.setIcon(unselectIcon);
        radioButton5.setIcon(unselectIcon);
        radioButton6.setIcon(unselectIcon);

        MyChangeListener changeListener = new MyChangeListener();
        radioButton1.addChangeListener(changeListener);
        radioButton2.addChangeListener(changeListener);
        radioButton3.addChangeListener(changeListener);
        radioButton4.addChangeListener(changeListener);
        radioButton5.addChangeListener(changeListener);
        radioButton6.addChangeListener(changeListener);

        MyMouseListener mouseListener = new MyMouseListener();
        radioButton1.addMouseListener(mouseListener);
        radioButton2.addMouseListener(mouseListener);
        radioButton3.addMouseListener(mouseListener);
        radioButton4.addMouseListener(mouseListener);
        radioButton5.addMouseListener(mouseListener);
        radioButton6.addMouseListener(mouseListener);
    }

    private class MyChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            if (radioButton.isSelected()) {
                radioButton.setIcon(selectIcon);
            } else {
                radioButton.setIcon(unselectIcon);
            }
        }
    }

    private class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            radioButton.setIcon(pressedIcon);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            if (radioButton.isSelected()) {
                radioButton.setIcon(selectIcon);
            } else {
                radioButton.setIcon(unselectIcon);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            radioButton.setIcon(enteredIcon);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            if (radioButton.isSelected()) {
                radioButton.setIcon(selectIcon);
            } else {
                radioButton.setIcon(unselectIcon);
            }
        }
    }
}
