package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;


public class BitTrackingActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegister;

    private EditText mMail;
    private EditText mPassword;

    User user = new User ("Mladen", "Teofilovic", "mladen@bitcamp.ba", "mladen1");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RegisterActivity.users.add(user);

        mLoginButton = (Button) findViewById(R.id.loginbtn);

        mRegister = (Button) findViewById(R.id.register);


        mMail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RegisterActivity.users.isEmpty()) {
                    for (int i = 0; i < RegisterActivity.users.size(); i++) {
                        if (RegisterActivity.users.get(i).getMail().equals(mMail.getText().toString()) & RegisterActivity.users.get(i).getPassword().equals(mPassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BitTrackingActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong input!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong input!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BitTrackingActivity.this, RegisterActivity.class));
            }
        });

    }

}
