package ba.bitcamp.bittracking.bittrackingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.List;


public class BitTrackingActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegister;

    private EditText mMail;
    private EditText mPassword;
    public static List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.loginbtn);

        mRegister = (Button) findViewById(R.id.register);


        mMail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for (int i = 0; i < users.size(); i++) {
                   if (users.get(i).getMail().equals(mMail.getText().toString()) & users.get(i).getPassword().equals(mPassword.getText().toString())) {
                       startActivity(new Intent(BitTrackingActivity.this, HomeActivity.class));
                   } else {
                        //TODO
                   }
               }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BitTrackingActivity.this, RegisterActivity.class));
            }
        });





    }
}
