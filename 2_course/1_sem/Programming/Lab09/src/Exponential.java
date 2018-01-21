/**
 * Created by Rak Alexey on 11/18/16.
 */

public class Exponential extends Series {
    private double b;
    private double q;

    public Exponential() {
        b = 0;
        q = 0;
    }

    public Exponential(double b_, double q_) {
        b = b_;
        q = q_;
    }

    final public double getElement(int j) {
        return b * Math.pow(q, j-1);
    }

    final public void set(double b_, double q_) {
        b = b_;
        q = q_;
    }
}
