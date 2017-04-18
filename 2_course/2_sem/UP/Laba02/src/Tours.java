import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by alex on 2/21/17.
 */
public class Tours extends JPanel {
    private HashMap<String, Pair<String, ImageIcon>> map;
    private JTable table;
    private DefaultTableModel data;

    public Tours(HashMap<String, Pair<String, ImageIcon>> newMap) {
        super();
        map = newMap;
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setRowHeight(40);
        add(new JScrollPane(table));
        data = new DefaultTableModel(new String[]{"Flag", "Country", "Cost", "Trip"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                } else if (columnIndex == 1) {
                    return String.class;
                } else if (columnIndex == 2) {
                    return Integer.class;
                } else {
                    return Boolean.class;
                }
            }
        };
        table.setModel(data);
        Random rand = new Random();
        for (Map.Entry<String, Pair<String, ImageIcon>> element : map.entrySet()) {
            data.addRow(new Object[]{map.get(element.getKey()).getValue(), element.getKey(), Math.abs(rand.nextInt()) % 1000, false});
        }
        setPreferredSize(new Dimension(700, 500));
        setMinimumSize(new Dimension(700, 500));
        setVisible(true);
    }

    public String getCost() {
        int cost = 0;
        for (int row = 0; row < data.getRowCount(); ++row) {
            if ((Boolean) data.getValueAt(row, 3)) {
                cost += (Integer) data.getValueAt(row, 2);
            }
        }
        return "Cost ypur trip: " + cost;
    }

    public void addNew() {
        AddNewTour dialog = new AddNewTour();
        dialog.setVisible(true);
        if (dialog.isOk()) {
            try {
                data.addRow(dialog.getData());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Bad");
            }
        }
    }
}
