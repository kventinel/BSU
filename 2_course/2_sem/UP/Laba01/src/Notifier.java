import java.util.TreeSet;

/**
 * Created by alex on 2/9/17.
 */
public class Notifier <T extends Notifiable> {
    private TreeSet<T> students;

    public Notifier(TreeSet<T> newStudents) {
        students = new TreeSet<T>(newStudents);
    }

    public void doNotifyAll(String message) {
        for (T student : students) {
            student.notify(message);
        }
    }
}
