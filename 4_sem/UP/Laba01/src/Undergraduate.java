/**
 * Created by alex on 2/8/17.
 */
public class Undergraduate extends Student {
    private Academic tutor;

    public Undergraduate(String newName, String newLogin, String newEmail, Academic newTutor) {
        super(newName, newLogin, newEmail);
        tutor = newTutor;
    }

    public Academic getTutor() {
        return tutor;
    }

    public void setTutor(Academic newTutor) {
        tutor = newTutor;
    }

    public String toString() {
        return super.toString() + " " + tutor;
    }
}
