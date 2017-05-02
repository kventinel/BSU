import observer.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class MyKeyEventDispatcher extends AbstractObservable<String> implements KeyEventDispatcher {
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getKeyCode()==0) return false;
        if(e.paramString().charAt(4)!='P') return false;
        String text=KeyEvent.getKeyText(e.getKeyCode());
        notifyObservers(text);
        return false;
    }
}