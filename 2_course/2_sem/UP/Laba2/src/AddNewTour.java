import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AddNewTour extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField countryTextField;
    private JTextField costTextField;
    private JButton pictureButton;
    ImageIcon icon;
    boolean ok;

    public AddNewTour() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setMinimumSize(new Dimension(500, 100));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pictureButton.addActionListener(e -> {
            JFileChooser fileopen = new JFileChooser("/home/alex/IdeaProjects/Laba2/");
            int ret = fileopen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                icon = new ImageIcon(fileopen.getSelectedFile().getPath());
            }
        });
    }

    private void onOK() {
        ok = true;
        dispose();
    }

    private void onCancel() {
        ok = false;
        dispose();
    }

    public Object[] getData() throws NumberFormatException {
        return new Object[]{icon, countryTextField.getText(), Integer.parseInt(costTextField.getText()), false};
    }

    public boolean isOk() {
        return ok;
    }

    public static void main(String[] args) {
        AddNewTour dialog = new AddNewTour();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
