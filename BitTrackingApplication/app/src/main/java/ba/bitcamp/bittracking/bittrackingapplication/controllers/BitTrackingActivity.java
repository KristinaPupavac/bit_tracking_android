package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.HashHelper;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;

public class BitTrackingActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegister;
    private ImageButton mFacebook;
    private ImageButton mTwitter;
    private ImageButton mLocation;
    private ImageButton mInfo;



    private EditText mMail;
    private EditText mPassword;

    User user = new User ("Mladen", "Teofilovic", "mladen@bitcamp.ba", "mladen1");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.loginbtn);
        mRegister = (Button) findViewById(R.id.register);
        mFacebook = (ImageButton) findViewById(R.id.facebook_button);
        mTwitter = (ImageButton) findViewById(R.id.twitter_button);
        mLocation = (ImageButton) findViewById(R.id.location_button);
        mInfo = (ImageButton) findViewById(R.id.info_button);
        
        mMail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mMail.getText().toString();
                String password = mPassword.getText().toString();
                password = HashHelper.getEncriptedPasswordMD5(password);
                JSONObject json = new JSONObject();
                try{
                    json.put("email", email);
                    json.put("password", password);
                }catch(JSONException e){
                    e.printStackTrace();
                }
                String url = getString(R.string.service_sign_in);

                ServiceRequest.post(url, json.toString(), loginCheck());
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BitTrackingActivity.this, RegisterActivity.class));
            }
        });

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/Bit-Tracking-462032430635765/"));
                startActivity(intent);
            }
        });

        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/BitTracking"));
                startActivity(intent);

            }
        });

    }

    private Callback loginCheck() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    String responseJSON= response.body().string();
                    JSONObject json = new JSONObject(responseJSON);
                    Long id = json.getLong("id");

                    if(id > 0){
                        ToastMessage("Redirecting...");
                        startActivity(new Intent(BitTrackingActivity.this, HomeActivity.class));

                    }else{
                    ToastMessage("Wrong input");
                    }

                } catch (JSONException e) {
                   ToastMessage("Wrong input");
                }
            }
        };
    }

    private void ToastMessage(final String message){
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

}
