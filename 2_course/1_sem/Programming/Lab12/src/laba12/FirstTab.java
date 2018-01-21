package laba12;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rak Alexey on 12/13/16.
 */
public class FirstTab extends JPanel {
    private JPanel panel1;
    private JList list1;
    private JList list2;
    private JButton LeftToRight;
    private JButton RightToLeft;
    private DefaultListModel<String> data1;
    private DefaultListModel<String> data2;


    public FirstTab() {
        super();
        setLayout(new BorderLayout());
        list1.setPreferredSize(new Dimension(500, 1000));
        data1 = new DefaultListModel<>();
        data2 = new DefaultListModel<>();
        data1.addElement("tretr");
        data1.addElement("tretr");
        data1.addElement("fufu");
        data1.addElement("kekos");
        data2.addElement("odddd");
        data2.addElement("kneff");
        data2.addElement("mail.ru");
        list1.setModel(data1);
        list2.setModel(data2);

        add(panel1);
        LeftToRight.addActionListener(e -> {
            int[] choiceData = list1.getSelectedIndices();
            for (int i = choiceData.length - 1; i >= 0; --i) {
                data2.addElement(data1.remove(choiceData[i]));
            }
        });

        RightToLeft.addActionListener(e -> {
            int[] choiceData = list2.getSelectedIndices();
            for (int i = choiceData.length - 1; i >= 0; --i) {
                data1.addElement(data2.remove(choiceData[i]));
            }
        });
    }
}
