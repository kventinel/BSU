/**
 * Created by Rak Alexey on 11/18/16.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

abstract public class Series {
    public abstract double getElement(int j);

    public abstract void set(double a, double b);

    public double sum(int n) {
        double sum = 0;
        for (int i = 1; i <= n; ++i) {
            sum += getElement(i);
        }
        return sum;
    }

    public String toString() {
        int n = 3;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; ++i) {
            sb.append(getElement(i));
            sb.append(" ");
        }
        sb.append("...");
        return sb.toString();
    }

    public void save(File path) throws IOException{
        FileWriter writer = new FileWriter(path);
        String text = toString();
        writer.write(text);
        writer.flush();
    }
}
