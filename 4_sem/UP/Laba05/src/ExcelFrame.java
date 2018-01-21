/**
 * Created by Rak Alexey on 3/21/17.
 */

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelFrame extends JFrame {
    private JTable table;
    private final int ROWS = 10;
    private final int COLUMNS = 6;

    public ExcelFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 800, 400);
        setTitle("Mini Date Excel");

        table = new JTable(ROWS, COLUMNS) {
            public Class getColumnClass(int column) {
                return String.class;
            }
        };
        table.setModel(new MyTableModel());
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setRowHeight(48);

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setContentPane(contentPane);

        setVisible(true);
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = new String[COLUMNS];
        private String[][] values = new String[ROWS][COLUMNS];
        private String[][] strings = new String[ROWS][COLUMNS];
        private Boolean round[][] = new Boolean[ROWS][COLUMNS];
        private Pattern datePattern = Pattern.compile("(\\d+)[.](\\d+)[.](\\d+)");
        private Pattern longDatePattern = Pattern.compile("[=](\\d+)[.](\\d+)[.](\\d+)[+-](\\d+)");
        private Pattern letterDatePattern = Pattern.compile("^[=]([A-Z](\\d+))[+-](\\d+)$");
        private Pattern funcPattern = Pattern.compile("^[=]((MIN)|(MAX))[(](((\\d+)[.](\\d+)[.](\\d+)[,])|([A-Z](\\d+)[,]))*(((\\d+)[.](\\d+)[.](\\d+))|([A-Z](\\d+)))[)]");
        private Matcher m;
        private int in_matcher = 0;
        private int edit_col = -1;
        private int edit_row = -1;

        MyTableModel() {
            for (int i = 0; i < ROWS; ++i) {
                for (int j = 0; j < COLUMNS; ++j) {
                    round[i][j] = false;
                    strings[i][j] = "";
                    values[i][j] = "";
                }
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return values.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            if (edit_col == col && edit_row == row)
                return values[row][col];
            else
                return strings[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            if (in_matcher == 0) {
                edit_col = col;
                edit_row = row;
            }
            boolean change = false;
            if (value != null) {
                String temp = (String) value;
                temp = temp.replaceAll(" ", "");
                values[row][col] = temp;
                String str = match(temp, row, col);
                if (!str.equals(strings[row][col])) {
                    strings[row][col] = match(temp, row, col);
                    change = true;
                }
            }

            while (change) {
                change = false;
                for (int i = 0; i < ROWS; ++i) {
                    for (int j = 0; j < COLUMNS; ++j) {
                        String temp = match(values[i][j], i, j);
                        if (!temp.equals(strings[i][j])) {
                            strings[i][j] = temp;
                            change = true;
                        }
                    }
                }
            }
            if (in_matcher == 0) {
                edit_row = -1;
                edit_col = -1;
            }
            fireTableCellUpdated(row, col);
        }

        private String match(String str, int row, int column) {
            if (round[row][column]) {
                return "Illegal link!";
            }
            Matcher dateMatcher = datePattern.matcher(str);
            Matcher longDateMatcher = longDatePattern.matcher(str);
            Matcher linkMatcher = letterDatePattern.matcher(str);
            Matcher funcMatcher = funcPattern.matcher(str);
            if (dateMatcher.matches()) {
                m = Pattern.compile("(\\d+)").matcher(str);
                m.find();
                int day = Integer.parseInt(m.group());
                m.find();
                int month = Integer.parseInt(m.group());
                month--;
                m.find();
                int year = Integer.parseInt(m.group());

                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);
            } else if (longDateMatcher.matches()) {
                m = datePattern.matcher(str);
                m.find();
                String dat = m.group();

                m = Pattern.compile("(\\d+)").matcher(dat);
                m.find();
                int day = Integer.parseInt(m.group());
                m.find();
                int month = Integer.parseInt(m.group());
                month--;
                m.find();
                int year = Integer.parseInt(m.group());

                m = Pattern.compile("[+-](\\d+)").matcher(str);
                m.find();
                int change = Integer.parseInt(m.group());

                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                calendar.add(GregorianCalendar.DAY_OF_MONTH, change);
                return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);

            } else if (linkMatcher.matches()) {
                try {
                    int indexX = str.charAt(1) - 'A';
                    int indexY = str.charAt(2) - '0' - 1;
                    m = Pattern.compile("[+-](\\d+)").matcher(str);
                    m.find();
                    int change = Integer.parseInt(m.group());

                    char checker = new Character((char) ('A' + column));
                    round[row][column] = true;
                    ++in_matcher;
                    table.setValueAt(values[indexY][indexX], indexY, indexX);
                    --in_matcher;
                    round[row][column] = false;
                    String temp1 = strings[indexY][indexX];
                    m = Pattern.compile("(\\d+)").matcher(temp1);
                    m.find();
                    int day = Integer.parseInt(m.group());
                    m.find();
                    int month = Integer.parseInt(m.group());
                    month--;
                    m.find();
                    int year = Integer.parseInt(m.group());

                    GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                    calendar.add(GregorianCalendar.DAY_OF_MONTH, change);
                    return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);

                } catch (IllegalStateException | ArrayIndexOutOfBoundsException e) {
                    return "Illegal link!";
                } catch (NullPointerException e) {
                    return "No data in linked item!";
                }
            } else if (funcMatcher.matches()) {
                try {
                    SortedSet<GregorianCalendar> calendars = new TreeSet<>();

                    Matcher m1 = datePattern.matcher(str);
                    while (m1.find()) {
                        String dat = m1.group();
                        Matcher m2 = Pattern.compile("(\\d+)").matcher(dat);
                        m2.find();
                        int day = Integer.parseInt(m2.group());
                        m2.find();
                        int month = Integer.parseInt(m2.group());
                        month--;
                        m2.find();
                        int year = Integer.parseInt(m2.group());

                        GregorianCalendar tmpCalendar = new GregorianCalendar(year, month, day);
                        calendars.add(tmpCalendar);
                    }
                    m1 = Pattern.compile("[A-Z](\\d+)").matcher(str);
                    while (m1.find()) {
                        String dat = m1.group();
                        int indexX = dat.charAt(0) - 'A';
                        int indexY = dat.charAt(1) - '0' - 1;

                        round[row][column] = true;
                        ++in_matcher;
                        table.setValueAt(values[indexY][indexX], indexY, indexX);
                        --in_matcher;
                        round[row][column] = false;
                        String temp1 = strings[indexY][indexX];
                        Matcher m2 = Pattern.compile("(\\d+)").matcher(temp1);
                        m2.find();
                        int day = Integer.parseInt(m2.group());
                        m2.find();
                        int month = Integer.parseInt(m2.group());
                        month--;
                        m2.find();
                        int year = Integer.parseInt(m2.group());

                        GregorianCalendar tmpCalendar = new GregorianCalendar(year, month, day);
                        calendars.add(tmpCalendar);
                    }
                    GregorianCalendar calendar;
                    if (str.contains("MAX"))
                        calendar = calendars.last();
                    else
                        calendar = calendars.first();

                    return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);

                } catch (NullPointerException e) {
                    return "No data in linked item!";
                } catch (IllegalStateException e) {
                    return "Incorrect data in linked item!";
                }
            } else {
                if (str.equals(""))
                    return "";
                else
                    return "Illegal data!";
            }
        }
    }
}