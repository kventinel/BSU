import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField courseField;
    private JTextField groupField;
    private JTextField nameField;
    private Student student;

    public StudentDialog(Student data) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(500, 150));
        if (data != null) {
            courseField.setText(data.getCourse());
            groupField.setText(data.getGroup());
            nameField.setText(data.getName());
        }

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
    }

    private void onOK() {
        if (courseField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Input course");
        } else if (groupField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Input group");
        } else if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Input name");
        } else {
            student = new Student(courseField.getText(), groupField.getText(), nameField.getText());
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Student getData() {
        return student;
    }

    public static void main(String[] args) {
        StudentDialog dialog = new StudentDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
