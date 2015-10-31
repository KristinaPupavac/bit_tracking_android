package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.HashHelper;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.lists.MapsActivity;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;

public class BitTrackingActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    public static final String SHARED_PREFERENCES = "ba.bitcamp.bittracking.shared_preferences";
    private Button mLoginButton;
    private Button mRegister;
    private EditText mMail;
    private EditText mPassword;
    private ImageButton mTrackPackageButton;
    private ImageButton mMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String email = mSharedPreferences.getString(
                getString(R.string.key_user_email),
                null
        );

        String password = mSharedPreferences.getString(
                getString(R.string.key_user_password),
                null
        );

        if (email != null && password != null) {
            setUserData(email, password);
            loginUser();
        }

        mLoginButton = (Button) findViewById(R.id.loginbtn);
        mRegister = (Button) findViewById(R.id.register);
        mMail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mTrackPackageButton = (ImageButton) findViewById(R.id.info);
        mMaps = (ImageButton) findViewById(R.id.location);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.setBackgroundColor(Color.rgb(0, 153, 51));

                String email = mMail.getText().toString();
                String password = mPassword.getText().toString();
                if ("".equals(email)) {
                    mMail.setError("This field is required");
                }
                if ("".equals(password)) {
                    mPassword.setError("This field is required");
                }
                mLoginButton.setBackgroundColor(Color.rgb(90, 144, 221));
                password = HashHelper.getEncriptedPasswordMD5(password);
                setUserData(email, password);
                loginUser();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BitTrackingActivity.this, RegisterActivity.class));
            }
        });

        mTrackPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BitTrackingActivity.this, TrackPackageActivity.class));
            }
        });

        mMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveUserCr() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        User u = User.getInstance();

        editor.putString(
                getString(R.string.key_user_email),
                u.getMail()
        );

        editor.putString(
                getString(R.string.key_user_password),
                u.getPassword()
        );
        editor.commit();
    }

    private Callback loginCheck() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ToastMessage("Email or password incorrect. Please enter valid email and password.");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJSON = response.body().string();

                try {
                    JSONObject user = new JSONObject(responseJSON);
                    Long id = user.getLong("id");
                    if (id > 0) {
                        String firstName = user.getString("firstName");
                        String lastName = user.getString("lastName");
                        String email = user.getString("email");
                        User u = User.getInstance();
                        u.setId(id);
                        u.setName(firstName);
                        u.setSurname(lastName);
                        u.setMail(email);
                        saveUserCr();
                        goToUserPanel();
                    } else {
                        ToastMessage("Email or password incorrect. Please enter valid email and password.");
                    }
                } catch (JSONException e) {
                    ToastMessage("Email or password incorrect. Please enter valid email and password.");
                }
            }
        };
    }

    private void goToUserPanel() {
        Intent test = new Intent(this, UserPanelActivity.class);
        startActivity(test);
    }

    private void loginUser() {
        String url = getString(R.string.service_sign_in);
        Callback callback = loginCheck();
        String json = User.getInstance().toJson();
        ServiceRequest.post(url, json, callback);
    }

    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BitTrackingActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setUserData(String email, String password) {
        User user = User.getInstance();
        user.setMail(email);
        user.setPassword(password);

    }

}
