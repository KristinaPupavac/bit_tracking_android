package ba.bitcamp.bittracking.bittrackingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class RegisterActivity  extends AppCompatActivity {
    private Button mRegisterButton;
    private EditText mName;
    private EditText mSurname;

    private EditText mEmail;
    private EditText mRegPassword;
    private EditText mConfPassword;

    public static List<User> users = new LinkedList<User>();


    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        RegisterActivity.users = users;
    }

    public static void addUser(User user){
        RegisterActivity.users.add(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisere);

        mRegisterButton = (Button) findViewById(R.id.registerbtn);

        mName = (EditText) findViewById(R.id.reg_name);
        mSurname = (EditText) findViewById(R.id.reg_surname);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mRegPassword = (EditText)findViewById(R.id.reg_password);
        mConfPassword = (EditText) findViewById(R.id.conf_password);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String surname = mSurname.getText().toString();
                String mail = mEmail.getText().toString();
                String password = mRegPassword.getText().toString();
                String confPass = mConfPassword.getText().toString();



                if (isValidEmail(mail) & checkPassword(confPass) & checkName(name) & checkName(surname) & passConf(password, confPass)) {

                    User user = new User(name, surname, mail, password);

                    users.add(user);

                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, BitTrackingActivity.class);

                    Log.i(user.toString(), "user radi liiii");
                    startActivity(i);

                } else if (!isValidEmail(mail)) {
                    mEmail.setError("Invalid Email");
                } else if (!checkName(name)) {
                    mName.setError("Your name should have only letters.");
                }else if (!checkName(surname)) {
                    mSurname.setError("Your surname should have only letters.");
                } else if (!checkPassword(confPass)) {
                    mConfPassword.setError("Invalid Password");
                } else if (!passConf(password, confPass)){
                    mConfPassword.setError("Passwords need to mach.");
                }
            }
        });

    }

    /**
     *
     * @param pass
     * @param passconf
     * @return
     */
    private boolean passConf (String pass, String passconf) {
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
    private boolean isValidEmail(String email) {
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
