package ba.bitcamp.bittracking.bittrackingapplication;

/**
 * Created by Kristina Pupavac on 10/16/2015.
 */
public class User {
    private String name;
    private String surname;
    private String mail;
    private String password;

    public User (){

    }

    public User(String name, String surname, String mail, String password) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
    }

    public User (String mail, String password){
        this.mail = mail;
        this.password = password;
    }
}
