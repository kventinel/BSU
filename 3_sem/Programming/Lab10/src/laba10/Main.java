package laba10;
import javax.swing.*;

/**
 * Created by Rak Alexey on 12/11/16.
 */

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        new Window();
    }
}
