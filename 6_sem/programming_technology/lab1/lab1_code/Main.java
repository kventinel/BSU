import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Main(){
        super();
        add(new DrawingPanel(this));

        setMinimumSize(new Dimension(500,500));
        setPreferredSize(new Dimension(1000,1000));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args){
        new Main();
    }
}
