import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Rak Alexey on 12/19/16.
 */

class Window extends JFrame {
    private MyPanel mainPanel;

    Window() {
        super("Window");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu(" File");
        menuBar.add(fileMenu);

        JMenuItem openFile = new JMenuItem("Open");
        fileMenu.add(openFile);
        openFile.addActionListener(new OpenFile());

        JMenuItem addElement = new JMenuItem("Add");
        fileMenu.add(addElement);
        addElement.addActionListener(new Add());
        menuBar.add(fileMenu);

        mainPanel = new MyPanel();
        add(mainPanel);

        setPreferredSize(new Dimension(1000, 800));
        setMinimumSize(new Dimension(1000, 800));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    class OpenFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileopen = new JFileChooser("/home/alex/Yandex.Disk/BSU/Programming/Laba13");
                int ret = fileopen.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    FileReader fileReader = new FileReader(file);
                    Scanner scanner = new Scanner(fileReader);
                    ArrayList<Product> data = new ArrayList<>();
                    while(scanner.hasNext()) {
                        String name;
                        String country;
                        int count;
                        if (scanner.hasNext()) {
                            name = scanner.next();
                        } else {
                            JOptionPane.showMessageDialog(null, "Bad file");
                            return;
                        }
                        if (scanner.hasNext()) {
                            country = scanner.next();
                        } else {
                            JOptionPane.showMessageDialog(null, "Bad file");
                            return;
                        }
                        if (scanner.hasNextInt()) {
                            count = scanner.nextInt();
                        } else {
                            JOptionPane.showMessageDialog(null, "Bad file");
                            return;
                        }
                        data.add(new Product(name, country, count));
                    }
                    mainPanel.setData(data);
                }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, exception.toString());
            }
        }
    }

    class Add implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddElement dialog = new AddElement();
            dialog.setVisible(true);
            mainPanel.addElement(dialog.getData());
        }
    }
}
