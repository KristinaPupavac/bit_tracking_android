package ba.bitcamp.bittracking.bittrackingapplication.models;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String toString(){
        return name + surname + mail + password;
    }
}
