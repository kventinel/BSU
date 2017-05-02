import observer.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        PreviewWindow win1 = new PreviewWindow("Preview");
        win1.setSize(700, 500);
        win1.setLocationRelativeTo(null);
        win1.setVisible(true);
        int x=win1.getX();
        int y=win1.getY();
        win1.setLocation(x-300,y);

        LogWindow win2 = new LogWindow("Log");
        win2.setSize(500, 500);
        win2.setLocationRelativeTo(null);
        win2.setVisible(true);
        win2.setLocation(x+450,y);


        MyKeyEventDispatcher dispatcher = new MyKeyEventDispatcher();
        dispatcher.registerObserver(win1);
        dispatcher.registerObserver(win2);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
    }

}