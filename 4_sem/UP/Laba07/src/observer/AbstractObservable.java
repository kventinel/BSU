package observer;

import java.util.LinkedList;
import java.util.List;

public class AbstractObservable<E> implements Observable<E> {
    List<Observer> observers= new LinkedList<>();
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers(E object) {
        for (Observer observer : observers)
            observer.update(object);
    }
}