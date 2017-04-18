import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by alex on 3/14/17.
 */

public class Window extends JFrame {
    public Window() throws IOException {
        super("Data");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationByPlatform(true);
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(new FirstPanel(), "First");
        tabbedPane.add(new SecondPanel(), "Second");
        add(tabbedPane);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
