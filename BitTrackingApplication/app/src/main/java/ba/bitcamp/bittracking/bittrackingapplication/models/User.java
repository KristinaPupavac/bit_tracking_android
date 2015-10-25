package ba.bitcamp.bittracking.bittrackingapplication.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /**
     *
     * @param pass
     * @param passconf
     * @return
     */
    public static boolean passConf (String pass, String passconf) {
        if (pass.equals(passconf)){
            return true;
        }
        return false;
    }

    /**
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * This method checks if entered character are only letters from A to  Z
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if ((name.charAt(i) < 65) || (name.charAt(i) > 90 &&
                    name.charAt(i) < 97) || (name.charAt(i) > 122 && name.charAt(i) < 262) ||
                    (name.charAt(i) > 263 && name.charAt(i) < 268) ||
                    (name.charAt(i) > 269 && name.charAt(i) < 272) ||
                    (name.charAt(i) > 273 && name.charAt(i) < 352) ||
                    (name.charAt(i) > 353 && name.charAt(i) < 381) ||
                    name.charAt(i) > 382) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if password has more then 6 letters
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        int letters = 0;
        int numbers = 0;
        if (password.length() < 6) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) > 47 && password.charAt(i) < 58) {
                numbers++;
            } else if ((password.charAt(i) > 64 && password.charAt(i) < 91) || (password.charAt(i) > 96 && password.charAt(i) < 123)) {
                letters++;
            }
        }
        if (letters == 0 || numbers == 0) {
            return false;
        }
        return true;
    }
}
