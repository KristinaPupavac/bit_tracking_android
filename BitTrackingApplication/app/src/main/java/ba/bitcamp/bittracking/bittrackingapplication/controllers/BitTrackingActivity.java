package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
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
import ba.bitcamp.bittracking.bittrackingapplication.models.*;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

public class BitTrackingActivity extends AppCompatActivity {
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

        mLoginButton = (Button) findViewById(R.id.loginbtn);

        mRegister = (Button) findViewById(R.id.register);


        mMail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mTrackPackageButton = (ImageButton) findViewById(R.id.info);

        mMaps = (ImageButton) findViewById(R.id.location);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mMail.getText().toString();
                String password = mPassword.getText().toString();
                password = HashHelper.getEncriptedPasswordMD5(password);
                JSONObject json = new JSONObject();
                try {
                    json.put("email", email);
                    json.put("password", password);
                } catch (JSONException e) {
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

    private Callback loginCheck() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJSON = response.body().string();
                JSONArray arr = new JSONArray();
                List<Package> packages = new ArrayList<>();
                try {
                    arr = new JSONArray(responseJSON);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Long id = obj.getLong("id");
                        String trackingNum = obj.getString("trackingNum");
                        String recipientName = obj.getString("recipientName");
                        String recipientAddress = obj.getString("recipientAddress");
                        Double weight = obj.getDouble("weight");
                        String packageType = obj.getString("packageType");
                        String status =obj.getString("status");
                        Package pack = new Package(recipientName, recipientAddress, weight, packageType, trackingNum, status);
                        packages.add(pack);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (packages.size() > 0) {
                    ToastMessage("Redirecting...");
                    startActivity(new Intent(BitTrackingActivity.this, UserPackagesActivity.class));
                } else {
                    ToastMessage("Wrong input");
                }

            }
        };
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

}
