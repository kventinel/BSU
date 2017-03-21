import javax.swing.*;
import java.awt.*;

/**
 * Created by alex on 3/6/17.
 */
public class Window extends JFrame {
    public Window() {
        super("Timer");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        final JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tabbedPane.add("Clock", new Clock());
        tabbedPane.add("CircleImg", new CircleImg());
        tabbedPane.add("Diagram", new Diagram());
    }
}
