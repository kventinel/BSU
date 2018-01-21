import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

class AddElement extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private Product product;

    AddElement() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        pack();
    }

    private void onOK() {
        try {
            product = new Product(textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()));
        } catch(NumberFormatException exeption){
            product = null;
            JOptionPane.showMessageDialog(null, "Bad count");
        }
        dispose();
    }

    private void onCancel() {
        product = null;
        dispose();
    }

    Product getData() {
        return product;
    }

    public static void main(String[] args) {
        AddElement dialog = new AddElement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
