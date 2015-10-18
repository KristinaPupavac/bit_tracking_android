package ba.bitcamp.bittracking.bittrackingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


public class BitTrackingActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegister;

    private EditText mMail;
    private EditText mPassword;


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
               for (int i = 0; i < RegisterActivity.users.size(); i++) {
                   if (RegisterActivity.users.get(i).getMail().equals(mMail.getText().toString()) & RegisterActivity.users.get(i).getPassword().equals(mPassword.getText().toString())) {
                       Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(BitTrackingActivity.this, HomeActivity.class));
                   } else {
                       Toast.makeText(getApplicationContext(), "Wrong input!",
                               Toast.LENGTH_SHORT).show();
                   }
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
