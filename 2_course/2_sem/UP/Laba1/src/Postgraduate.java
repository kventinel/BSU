/**
 * Created by alex on 2/9/17.
 */
public class Postgraduate extends Student {
    private Academic supervisor;

    public Postgraduate(String newName, String newLogin, String newEmail, Academic newSupervisor) {
        super(newName, newLogin, newEmail);
        supervisor = newSupervisor;
    }

    public Academic getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Academic newSupervisor) {
        supervisor = newSupervisor;
    }

    public String toString() {
        return super.toString() + " " + supervisor;
    }
}
