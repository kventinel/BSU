import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;;

/**
 * Created by alex on 3/14/17.
 */

public class FirstPanel extends JPanel {
    public FirstPanel() throws IOException {
        ImageIcon iconOk = new ImageIcon(ImageIO.read(new File("ok.png")));
        ImageIcon iconWrong = new ImageIcon(ImageIO.read(new File("wrong.png")));
        setLayout(new GridLayout(0, 3));
        String[] types = {"nat", "int", "float", "date", "time", "email"};
        String[] regex = {"[1-9][0-9]*",
                "([+-]?[1-9][0-9]*)|0",
                "(([-+]?([0-9]|[1-9][0-9]*)((\\.[0-9]*)|(\\.[0-9]*)?e([+-]?[1-9][0-9]*|0)))|([-+]?([0-9]?|[1-9][0-9]*)((\\.[0-9]+)|(\\.[0-9]*)?e([+-]?[1-9][0-9]*|0))))",
                "((([1-9]|1[0-9]|2[0-8])/2)|([1-9]|[1-2][0-9]|30)/(4|6|9|11)|([1-9]|[1-2][0-9]|30|31)/(1|3|5|7|10|12))/([1-9][0-9]*)",
                "([0-9]|[0-1][0-9]|2[0-3]):[0-5][0-9]",
                "\\w+(\\.\\w*)+@\\w+(\\.\\w*)+\\w+"
        };
        JComboBox comboBox = new JComboBox(types);
        JTextField textField = new JTextField();
        JLabel label = new JLabel();
        comboBox.addActionListener(actionEvent -> {
            String val = textField.getText();
            if (val.matches(regex[comboBox.getSelectedIndex()])) {
                textField.setBackground(Color.green.brighter());
                label.setIcon(iconOk);
            }
            else {
                textField.setBackground(Color.red.brighter());
                label.setIcon(iconWrong);
            }
        });
        add(comboBox);
        textField.addCaretListener(caretEvent -> {
            String val = textField.getText();
            if (val.matches(regex[comboBox.getSelectedIndex()])) {
                textField.setBackground(Color.green.brighter());
                label.setIcon(iconOk);
            }
            else {
                textField.setBackground(Color.red.brighter());
                label.setIcon(iconWrong);
            }
        });
        add(textField);
        add(label);
    }
}


