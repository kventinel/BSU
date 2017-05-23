package Laba11.data;

import java.io.Serializable;

public abstract class AbstractPass implements Serializable {
    String name;
    String examinator;

    AbstractPass(String name, String examinator) {
        this.name = name;
        this.examinator = examinator;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExaminator() {
        return examinator;
    }

    public void setExaminator(String examinator) {
        this.examinator = examinator;
    }
    public abstract boolean isPassed();

    public abstract String toXML();
}
