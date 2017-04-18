import java.util.TreeSet;

/**
 * Created by alex on 2/8/17.
 */
public class Course {
    private String name;
    private TreeSet<Student> students;

    public Course (String newName, TreeSet<Student> newStudents) {
        name = newName;
        students = new TreeSet<Student>(newStudents);
    }

    public TreeSet<Postgraduate> getPostgraduates(String nameOfProvisor) {
        TreeSet<Postgraduate> postgraduates = new TreeSet<Postgraduate>();
        for (Student student : students) {
            if (student instanceof Postgraduate) {
                if (((Postgraduate) student).getSupervisor().getName().equals(nameOfProvisor)) {
                    postgraduates.add((Postgraduate) student);
                }
            }
        }
        return postgraduates;
    }
}
