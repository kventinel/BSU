package Laba12.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends ArrayList<AbstractPass> implements Serializable {
    private String name;

    public Student(String name, List<AbstractPass> list) {
        super(list);
        this.name = name;
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractPass> getList() {
        return this;
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t<Student name=\"").append(name).append("\">");
        for (AbstractPass x : this) {
            sb.append("\n");
            sb.append(x.toXML());
        }
        sb.append("\n\t</Student>");
        return sb.toString();
    }
}
