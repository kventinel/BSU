import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.SynchronousQueue;

public class ColorChooser extends JDialog {
    private JScrollBar red, green, blue;
    private ColorPane pane = new ColorPane();

    private ColorChooser(Window owner, Color original) {
        super(owner,"Choose color",ModalityType.APPLICATION_MODAL);
        red = new JScrollBar(JScrollBar.HORIZONTAL, original.getRed(), 1, 0, 256);
        green = new JScrollBar(JScrollBar.HORIZONTAL, original.getGreen(), 1, 0, 256);
        blue = new JScrollBar(JScrollBar.HORIZONTAL, original.getBlue(), 1, 0, 256);

        AdjustmentListener listener = (e) -> pane.repaint();
        red.addAdjustmentListener(listener);
        green.addAdjustmentListener(listener);
        blue.addAdjustmentListener(listener);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(2, 0, 0, 0);
        constraints.gridwidth = 2;
        panel.add(red, constraints);

        constraints.gridy = 1;
        panel.add(green, constraints);

        constraints.gridy = 2;
        panel.add(blue, constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 1;
        JButton okButton = new JButton("OK");
        okButton.addActionListener((e) -> {
            dispose();
        });
        panel.add(okButton, constraints);

        constraints.gridx = 1;
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener((e) -> {
            red.setValue(original.getRed());
            green.setValue(original.getGreen());
            blue.setValue(original.getBlue());
            repaint();
        });
        panel.add(resetButton, constraints);

        add(pane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                red.setValue(original.getRed());
                green.setValue(original.getGreen());
                blue.setValue(original.getBlue());
            }
        });

        setSize(170, 260);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static Color showDialog(Window owner, Color original) {
        ColorChooser chooser = new ColorChooser(owner, original);
        chooser.dispose();
        return new Color(chooser.red.getValue(),chooser.green.getValue(),chooser.blue.getValue());
    }

    private class ColorPane extends JPanel {
        private ColorPane() {
            setMinimumSize(new Dimension(100, 100));
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(new Color(red.getValue(), green.getValue(), blue.getValue()));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
