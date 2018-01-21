/**
 * Created by Rak Alexey on 11/18/16.
 */

public class Liner extends Series{
    private double a;
    private double b;

    public Liner() {
        a = 0;
        b = 0;
    }

    public Liner(double a_, double b_) {
        a = a_;
        b = b_;
    }

    final public double getElement(int j) {
        return a + b * (j-1);
    }

    final public void set(double a_, double b_) {
        a = a_;
        b = b_;
    }
}