import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alex on 3/14/17.
 */

public class SecondPanel extends JPanel {
    public SecondPanel() throws IOException{
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        DefaultListModel<String> model = new DefaultListModel<>();
        JList list = new JList(model);
        list.setBounds(0, 0, screenSize.width / 4, screenSize.height / 4);
        FileReader fileReader = new FileReader("4.2");
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            Matcher m = Pattern.compile("((([1-9]|1[0-9]|2[0-8])/2)|([1-9]|[1-2][0-9]|30)/(4|6|9|11)|([1-9]|[1-2][0-9]|30|31)/(1|3|5|7|10|12))/([1-9][0-9]*)").matcher(text);
            while (m.find()){
                model.addElement(m.group(0));
            }
        }
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == e.VK_ENTER
                        ) {
                    model.clear();
                    String text = textArea.getText();
                    Matcher m = Pattern.compile("((([1-9]|1[0-9]|2[0-8])/2)|([1-9]|[1-2][0-9]|30)/(4|6|9|11)|([1-9]|[1-2][0-9]|30|31)/(1|3|5|7|10|12))/([1-9][0-9]*)").matcher(text);
                    while (m.find()){
                        model.addElement(m.group(0));
                    }
                }
            }
        });
        add(textArea, BorderLayout.CENTER);
        add(list, BorderLayout.EAST);
    }
}
