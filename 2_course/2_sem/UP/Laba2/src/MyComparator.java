/**
 * Created by alex on 2/27/17.
 */

import java.util.Comparator;

public class MyComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        int x = o1.getCourse().compareTo(o2.getCourse());
        if (x != 0) {
            return -x;
        }
        x = o1.getGroup().compareTo(o2.getGroup());
        if (x != 0) {
            return x;
        }
        return o1.getName().compareTo(o2.getName());
    }
}
