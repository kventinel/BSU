/**
 * Created by alex on 2/8/17.
 */
public class Student extends Person implements Notifiable {
    private String login;
    private String email;

    public Student(String newName, String newLogin, String newEmail) {
        super(newName);
        login = newLogin;
        email = newEmail;
    }

    public String getLogin() {
        return login;
    }

    public void setString(String newLogin) {
        login = newLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public String toString() {
        return super.toString() + " " + login + " " + email;
    }

    @Override
    public void notify(String message) {
        System.out.println(super.getName() + ": " + message);
    }
}
