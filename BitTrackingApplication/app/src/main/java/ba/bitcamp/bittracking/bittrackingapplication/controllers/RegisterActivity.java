package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.HashHelper;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;


/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class RegisterActivity extends AppCompatActivity {
    private Button mRegisterButton;
    private EditText mName;
    private EditText mSurname;

    private EditText mEmail;
    private EditText mRegPassword;
    private EditText mConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisere);


        mRegisterButton = (Button) findViewById(R.id.registerbtn);

        mName = (EditText) findViewById(R.id.reg_name);
        mSurname = (EditText) findViewById(R.id.reg_surname);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mRegPassword = (EditText) findViewById(R.id.reg_password);
        mConfPassword = (EditText) findViewById(R.id.conf_password);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String surname = mSurname.getText().toString();
                String mail = mEmail.getText().toString();
                String password = mRegPassword.getText().toString();
                String confPass = mConfPassword.getText().toString();

                if (User.isValidEmail(mail) & User.checkPassword(confPass) & User.checkName(name) & User.checkName(surname) & User.passConf(password, confPass)) {

                    User user = new User(name, surname, mail, password);
                    user.setPassword(HashHelper.getEncriptedPasswordMD5(user.getPassword()));
                    JSONObject json = new JSONObject();
                    try {
                        json.put("firstName", user.getName());
                        json.put("lastName", user.getSurname());
                        json.put("email", user.getMail());
                        json.put("password", user.getPassword());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url = getString(R.string.service_sign_up);
                    ServiceRequest.post(url, json.toString(), register());
                    ToastMessage("Redirecting...");
                } else if (!User.isValidEmail(mail)) {
                    mEmail.setError("Invalid Email");
                } else if (!User.checkName(name)) {
                    mName.setError("Your first name should have only letters.");
                } else if (!User.checkName(surname)) {
                    mSurname.setError("Your last name should have only letters.");
                } else if (!User.checkPassword(confPass)) {
                    mConfPassword.setError("Invalid Password");
                } else if (!User.passConf(password, confPass)) {
                    mConfPassword.setError("Passwords need to match.");
                }
            }
        });

    }

    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Callback register() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                Intent i = new Intent(RegisterActivity.this, BitTrackingActivity.class);
                startActivity(i);


            }
        };
    }
}
