/**
 * Created by alex on 2/27/17.
 */

import java.util.Scanner;

public class Student {
    private String course;
    private String group;
    private String name;

    public Student(String newCourse, String newGroup, String newName) {
        course = newCourse;
        group = newGroup;
        name = newName;
    }

    public Student(String string) {
        Scanner scan = new Scanner(string);
        course = scan.next();
        group = scan.next();
        name = scan.nextLine().trim();
    }

    public String getCourse() {
        return course;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return other.name.equals(name) && other.group.equals(group) && other.course.equals(course);
    }
}