package Laba11;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import Laba11.editor.MySaxParser;
import Laba11.editor.MyTableModel;

public class MainWindow extends JFrame {
    private boolean BINARY_MODE = false;
    private String fileName = "list.xml";
    private String binFileName = "list.bin";
    private DocumentBuilder builder;
    private JTable table = new JTable();
    private JTextField textField = new JTextField();
    private List<Laba11.data.Student> list;
    private List<MyTableModel> modelList;
    private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    private JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);

    private MainWindow() {
        super("Laba11");
        try {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    try {
                        if (BINARY_MODE) saveBinFile();
                        else saveFile();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        System.exit(1);
                    }
                }
            });
            setSize(640, 480);
            putAtMid();
            initXML();

            MenuBar menuBar = new MenuBar();
            setMenuBar(menuBar);
            Menu menu = new Menu("File");
            menuBar.add(menu);
            MenuItem readDom = new MenuItem("Read DOM File");
            MenuItem addExam = new MenuItem("Add Exam");
            MenuItem addTest = new MenuItem("Add Test");
            MenuItem remExam = new MenuItem("Remove Selected");
            MenuItem calcButton = new MenuItem("Calculations");
            menu.add(readDom);
            menu.add(addExam);
            menu.add(addTest);
            menu.add(remExam);
            menu.add(calcButton);

            JButton addStudent = new JButton("Add");
            JButton remStudent = new JButton("Remove");
            JPanel topPanel = new JPanel();
            Dimension di = textField.getPreferredSize();
            di.setSize(300, di.getHeight());
            textField.setPreferredSize(di);
            topPanel.add(textField, BorderLayout.WEST);
            topPanel.add(comboBox);
            topPanel.add(addStudent, BorderLayout.EAST);
            topPanel.add(remStudent);


            add(topPanel, BorderLayout.NORTH);
            add(table);

            comboBox.addActionListener(e -> {
                int idx = comboBox.getSelectedIndex();
                if (idx < 0) table.setModel(new DefaultTableModel());
                else table.setModel(modelList.get(idx));
                textField.setText((String) comboBox.getSelectedItem());
            });
            readDom.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser(".");
                if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        readFile();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Bad File");
                    }
                }
            });
            addExam.addActionListener(e -> {
                list.get(comboBox.getSelectedIndex()).getList().add(new Laba11.data.ExamPass("SomeName", "SomeExaminator", 1));
                updateVeryVery();
            });
            addTest.addActionListener(e -> {
                list.get(comboBox.getSelectedIndex()).getList().add(new Laba11.data.TestPass("SomeName", "SomeExaminator", false));
                updateVeryVery();
            });
            remExam.addActionListener(e -> {
                int idx = table.getSelectedRow();
                List<Laba11.data.AbstractPass> l = list.get(comboBox.getSelectedIndex()).getList();
                if (0 <= idx && idx < l.size()) l.remove(idx);
                updateVeryVery();
            });
            addStudent.addActionListener(e -> {
                Laba11.data.Student st = new Laba11.data.Student(textField.getText());
                list.add(st);
                modelList.add(new MyTableModel(st, table));
                comboBoxModel.addElement(textField.getText());
            });
            remStudent.addActionListener(e -> {
                if (comboBoxModel.getSize() == 0) return;
                int idx = comboBox.getSelectedIndex();
                comboBoxModel.removeElementAt(idx);
                list.remove(idx);
            });
            textField.addActionListener(e -> {
                String name = textField.getText();
                int idx = comboBox.getSelectedIndex();
                comboBoxModel.removeElementAt(idx);
                comboBoxModel.insertElementAt(name, idx);
                comboBox.setSelectedIndex(idx);
                list.get(idx).setName(name);
            });
            calcButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser(".");
                if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    String str = saxCalc(fileName);
                    JOptionPane.showMessageDialog(MainWindow.this, str);
                }
            });
            if (BINARY_MODE) readBinFile();
            else readFile();
        } catch (IOException ex) {
            list = new ArrayList<>();
            updateInfo();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage());
            System.exit(1);
        }
    }

    private String saxCalc(String fileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            MySaxParser myParser = new MySaxParser();
            parser.parse(new File(fileName), myParser);
            return "Amount of students = " + Integer.toString(myParser.getAmountOfStudents()) + '\n' +
                    "Amount of bad students = " + Integer.toString(myParser.getAmountOfBadStudents()) + '\n' +
                    "OS Average mark = " + Double.toString(myParser.getAvgMark()) + '\n';
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private void saveBinFile() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(binFileName)));
        out.writeObject(list);
        out.close();
    }

    private void readBinFile() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(binFileName)));
        list = (List<Laba11.data.Student>) in.readObject();
        updateInfo();
    }

    private void updateInfo() {
        this.modelList = new ArrayList<>(list.size());
        comboBoxModel.removeAllElements();
        for (Laba11.data.Student x : list) {
            modelList.add(new MyTableModel(x, table));
            comboBoxModel.addElement(x.getName());
        }
        comboBox.actionPerformed(null);
    }

    private void updateVeryVery() {
        setSize(getWidth() + 1, getHeight());
        setSize(getWidth() - 1, getHeight());
        table.repaint();
        repaint();
    }

    private void initXML() throws ParserConfigurationException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        builder = f.newDocumentBuilder();
    }

    private void readFile() throws IOException, SAXException {
        Document doc = builder.parse(new File(fileName));
        NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
        int size = nodes.getLength();
        List<Laba11.data.Student> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Node node = nodes.item(i);
            String typeOfSt = node.getNodeName();
            if (typeOfSt == null || typeOfSt.compareTo("Student") != 0) continue;
            String name = node.getAttributes().getNamedItem("name").getTextContent();
            NodeList exams = node.getChildNodes();
            int esise = exams.getLength();
            List<Laba11.data.AbstractPass> passes = new ArrayList<>(esise);
            for (int j = 0; j < esise; j++) {
                Node exam = exams.item(j);
                String type = exam.getNodeName();
                if (type.compareTo("TestPass") == 0) {
                    String examinator = exam.getAttributes().getNamedItem("examinator").getTextContent();
                    String exname = exam.getTextContent();
                    boolean passed = Boolean.parseBoolean(exam.getAttributes().getNamedItem("passed").getTextContent());
                    passes.add(new Laba11.data.TestPass(exname, examinator, passed));
                } else if (type.compareTo("ExamPass") == 0) {
                    String examinator = exam.getAttributes().getNamedItem("examinator").getTextContent();
                    String exname = exam.getTextContent();
                    int mark = Integer.parseInt(exam.getAttributes().getNamedItem("mark").getTextContent());
                    passes.add(new Laba11.data.ExamPass(exname, examinator, mark));
                }
            }
            list.add(new Laba11.data.Student(name, passes));
        }
        this.list = list;
        updateInfo();
    }

    private void saveFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("<StudentList>\n");
        for (Laba11.data.Student x : list) {
            sb.append(x.toXML());
            sb.append("\n");
        }
        sb.append("</StudentList>");
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage());
            System.exit(1);
        }
    }


    public static void main(String args[]) {
        new SplashDemo().dispose();
        new MainWindow().setVisible(true);
    }

    private void putAtMid() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - getWidth();
        int height = (int) screenSize.getHeight() - getHeight();
        setLocation(width / 2, height / 2);
    }
}
