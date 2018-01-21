import observer.*;
import javax.swing.*;
import java.awt.*;

public class PreviewWindow extends JFrame implements Observer<String> {
    JLabel label = new JLabel("");

    PreviewWindow(String caption) {
        super(caption);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Font font = label.getFont();
        label.setFont(new Font(font.getFontName(), font.getStyle(), 100));
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void update(String object) {
        label.setText(object.toString());
    }
}