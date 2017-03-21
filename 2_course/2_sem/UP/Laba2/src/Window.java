import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by alex on 2/20/17.
 */
public class Window extends JFrame {
    private Countries panel1;

    public Window() {
        super("title");

        HashMap<String, Pair<String, ImageIcon>> map = new HashMap<>();
        try {
            FileReader reader = new FileReader("countries.txt");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext()) {
                String country = scanner.next();
                String capital = scanner.next();
                String file = "plain/flag_" + scanner.next() + ".png";
                ImageIcon flag = new ImageIcon(file);
                map.put(country, new Pair<String, ImageIcon>(capital, flag));
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Bad File");
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane);

        tabbedPane.add(new Countries(map), "First tab");
        Tours tours = new Tours(map);
        tabbedPane.add(tours, "Second tab");

        StudentsTab studentsTab = new StudentsTab();
        tabbedPane.add(studentsTab, "Students");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu tour = new JMenu("Trip");
        menuBar.add(tour);

        JMenuItem cost = new JMenuItem("Cost");
        cost.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, tours.getCost());
        });
        tour.add(cost);

        JMenuItem add = new JMenuItem("Add tour");
        add.addActionListener(e -> {
            tours.addNew();
        });
        tour.add(add);

        JMenu student = new JMenu("Students");
        menuBar.add(student);

        JMenuItem addStudent = new JMenuItem("Add");
        addStudent.addActionListener(e->{
            studentsTab.addStudent();
        });
        student.add(addStudent);

        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
