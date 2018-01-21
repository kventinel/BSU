import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

        JMenuItem saveElement = new JMenuItem("Save");
        fileMenu.add(saveElement);
        saveElement.addActionListener(new Save());
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
                JFileChooser fileopen = new JFileChooser("/home/alex/Yandex.Disk/BSU/Programming/Laba14/src");
                int ret = fileopen.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    SaxParser saxp = new SaxParser();

                    parser.parse(file, saxp);
                    ArrayList<Product> data = saxp.getResult();
                    mainPanel.setData(data);
                }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, exception.toString());
            } catch (SAXException e1) {
                e1.printStackTrace();
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
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

    class Save implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String filePath;
                JFileChooser fileOpen = new JFileChooser("/home/alex/Yandex.Disk/BSU/Programming/Laba14/out");
                int ret = fileOpen.showDialog(null, "Save file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    FileWriter fileWriter = new FileWriter(file + ".xml");
                    fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
                    fileWriter.write("<Root>\n");
                    ArrayList<Country> data = mainPanel.getRequestData();
                    Iterator<Country> iterator = data.iterator();
                    while (iterator.hasNext()) {
                        Country element = iterator.next();
                        fileWriter.write("<country name=\"" + element.getCountry() + "\" count=\"" +
                                element.getCount() + "\"/>\n");
                    }
                    fileWriter.write("</Root>");
                    fileWriter.close();
                }
                mainPanel.repaint();
            } catch (IOException arg) {
                JOptionPane.showMessageDialog(null, arg.toString());
            }
        }
    }
}
