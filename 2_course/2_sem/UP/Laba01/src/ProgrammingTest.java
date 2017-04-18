import java.util.TreeSet;

/**
 * Created by alex on 2/9/17.
 */
public class ProgrammingTest {
    public static void main(String[] args) {
        Academic academic1 = new Academic("Alexey");
        Academic academic2 = new Academic("Tony");
        Undergraduate undergraduate1 = new Undergraduate("Den", "Den1987", "Den1987@gmail.com", academic1);
        Undergraduate undergraduate2 = new Undergraduate("Simon", "Simon777", "Simon77@yandex.ru", academic2);
        Postgraduate postgraduate1 = new Postgraduate("Ann", "Ann_Smith", "AnnSmith@yahoo.com", academic1);
        Postgraduate postgraduate2 = new Postgraduate("Antony", "AntonyTheBest", "BigAntony@gmail.com", academic2);
        Postgraduate postgraduate3 = new Postgraduate("John", "John", "YouKnowNothing@gmail.com", academic1);
        TreeSet<Student> students = new TreeSet<Student>();
        students.add(undergraduate1);
        students.add(undergraduate2);
        students.add(postgraduate1);
        students.add(postgraduate2);
        students.add(postgraduate3);
        Course course = new Course("First Group", students);
        TreeSet<Postgraduate> postgraduates = course.getPostgraduates("Alexey");
        for (Postgraduate postgraduate : postgraduates) {
            System.out.println(postgraduate);
        }
        Notifier notifier = new Notifier(postgraduates);
        notifier.doNotifyAll("Hello");
    }
}
