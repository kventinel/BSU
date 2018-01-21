import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Window extends JFrame {
    private Series series;
    private File path;

    private JPanel rootPanel;
    private JRadioButton linerRadioButton;
    private JRadioButton exponentialRadioButton;
    private JButton saveButton;
    private JList<String> seriesList;
    private JSpinner firstSpinner;
    private JSpinner stepSpinner;
    private JSpinner lengthSpinner;

    public Window() {
        super("Series");
        add(rootPanel);

        path = new File("");
        update();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 300));
        setSize(600, 500);
        setVisible(true);

        linerRadioButton.addActionListener(e -> radioButtonGroup("liner"));
        exponentialRadioButton.addActionListener(e -> radioButtonGroup("exponential"));
        firstSpinner.addChangeListener(e -> update());
        stepSpinner.addChangeListener(e -> update());
        lengthSpinner.addChangeListener(e -> update());
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(path);
            if (fileChooser.showSaveDialog(Window.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    series.save(path = fileChooser.getSelectedFile());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Bad File");
                }
            }
        });
    }

    private void radioButtonGroup(String change) {
        if(change.compareTo("liner") == 0) {
            exponentialRadioButton.setSelected(false);
        } else {
            linerRadioButton.setSelected(false);
        }
        update();
    }

    private void update() {
        if (linerRadioButton.isSelected()) {
            series = new Liner();
        }
        else if (exponentialRadioButton.isSelected()) {
            series = new Exponential();
        }
        if(series != null) {
            series.set((Double) firstSpinner.getValue(), (Double) stepSpinner.getValue());
            int length = (Integer) lengthSpinner.getValue();
            seriesList.setListData(new String[]{"sum=" + series.sum(3) + " elements:" + series.toString()});
        }
    }

    private void createUIComponents() {
        firstSpinner = new JSpinner(new SpinnerDoubleModel());
        stepSpinner = new JSpinner(new SpinnerDoubleModel());
        lengthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    }

    private class SpinnerDoubleModel extends SpinnerNumberModel {

        public SpinnerDoubleModel() {
            setValue((Double) 0.0);
            setStepSize((Double) 0.1);
        }

        @Override
        public Object getNextValue() {
            return ((Double)getValue() + (Double)getStepSize());
        }

        @Override
        public Object getPreviousValue() {
            return ((Double)getValue() - (Double)getStepSize());
        }
    }
}
