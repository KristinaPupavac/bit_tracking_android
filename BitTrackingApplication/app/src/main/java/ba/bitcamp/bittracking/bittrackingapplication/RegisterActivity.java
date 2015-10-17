package ba.bitcamp.bittracking.bittrackingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class RegisterActivity  extends AppCompatActivity {
    private Button mRegisterButton;
    private EditText mName;
    private EditText mSurname;

    private EditText mEmail;
    private EditText mRegPassword;

    public static List<User> users;


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

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String surname = mSurname.getText().toString();
                String mail = mEmail.getText().toString();
                String password = mRegPassword.getText().toString();

                User user = new User(name, surname, mail, password);

                users.add(user);

                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterActivity.this, HomeActivity.class);

                Log.i(user.toString(), "user radi liiii");
                startActivity(i);

            }
        });

    }

    }
