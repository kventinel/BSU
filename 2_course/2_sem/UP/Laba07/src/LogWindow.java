import observer.*;
import javax.swing.*;
import java.awt.*;

public class LogWindow extends JFrame implements Observer<String> {
    JTextArea area = new JTextArea("");

    LogWindow(String caption) {
        super(caption);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        area.setLineWrap(true);
        area.setEditable(false);
        add(area, BorderLayout.CENTER);
    }

    @Override
    public void update(String object) {
        area.setText(area.getText() + object + " ");
    }
}