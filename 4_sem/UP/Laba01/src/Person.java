/**
 * Created by alex on 2/8/17.
 */
public class Person implements Comparable<Person> {
    private String name;

    public Person(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Person o) {
        return name.compareTo(o.getName());
    }
}
