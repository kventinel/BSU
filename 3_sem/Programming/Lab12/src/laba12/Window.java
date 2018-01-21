package laba12;

import javax.swing.*;

/**
 * Created by Rak Alexey on 12/12/16.
 */

public class Window extends JFrame{
    public Window() {
        super("Window");
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane);

        FirstTab firstTab = new FirstTab();
        tabbedPane.add(firstTab, "First tab");

        SecondTab secondTab = new SecondTab();
        tabbedPane.add(secondTab, "Second tab");

        ThirdTab thirdTab = new ThirdTab();
        tabbedPane.add(thirdTab, "Third tab");

        setSize(1000, 800);
        setLocation(450, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main (String[] args) {
        new Window();
    }
}
