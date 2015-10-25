package ba.bitcamp.bittracking.bittrackingapplication.controllers;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class HomeActivity extends AppCompatActivity {

    private EditText mTruckingNumber;
    private Button mButton;
    private Button mSignOut;
    private Button mCreateRequest;
    private Button mPackageList;
    private LinearLayout mLinearLayout;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTruckingNumber = (EditText) findViewById(R.id.trucking_number);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout);
        mSignOut = (Button) findViewById(R.id.sign_out);
        mCreateRequest = (Button) findViewById(R.id.new_request_button);
        t = new TextView(HomeActivity.this);
        t.setTextColor(Color.WHITE);
        mLinearLayout.addView(t);
        t.setText("");


        mCreateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMessage("Redirecting...");
                Intent i = new Intent(HomeActivity.this, CreateRequestActivity.class);
                startActivity(i);
            }
        });


        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HomeActivity.this, BitTrackingActivity.class);
                startActivity(i);
            }
        });


    }

    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
