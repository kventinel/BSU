import static java.lang.Math.abs;

/**
 * Created by Rak Alexey on 12.11.16.
 */

class Student implements Comparable<Student> {
    private int course, group;
    private double mark;
    private String name;

    Student(int course_, int group_, double mark_, String name_) {
        course = course_;
        group = group_;
        mark = mark_;
        name = name_;
    }

    @Override
    public int compareTo(Student second) {
        if(course != second.course) {
            return course - second.course;
        }
        if(group != second.group) {
            return group - second.group;
        }
        int result = Double.compare(mark, second.mark);
        if(result != 0) {
            return result;
        }
        return name.compareTo(second.name);
    }

    @Override
    public String toString() {
        return "" + course " + " group + " " + "i";
    }
}
