package ba.bitcamp.bittracking.bittrackingapplication.controllers;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    public  List<ba.bitcamp.bittracking.bittrackingapplication.models.Package> list = new LinkedList<Package>();
    Package p1 = new Package("mladen1", Package.READY_FOR_SHIPPING );
    Package p2 = new Package("mladen2", Package.ON_ROUTE);
    Package p3 = new Package("mladen3", Package.OUT_FOR_DELIVERY);
    Package p4 = new Package("mladen4", Package.DELIVERED);
    Package p5 = new Package("mladen5", Package.RECEIVED);

    private EditText mTruckingNumber;
    private Button mButton;
    private Button mSignOut;
    private LinearLayout mLinearLayout;
    private  TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        mTruckingNumber = (EditText) findViewById(R.id.trucking_number);
        mButton = (Button) findViewById(R.id.ok_button);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout);
        mSignOut = (Button) findViewById(R.id.sign_out);
        t = new TextView(HomeActivity.this);
        t.setTextColor(Color.WHITE);
        mLinearLayout.addView(t);
        t.setText("");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Package p = Package.getPackageByToken(list, mTruckingNumber.getText().toString());

                    if (p != null) {
                        t.setText("Package status: " + p.getStatusName());
                    } else {
                        t.setText("");
                        mTruckingNumber.setError("Wrong trucking number!");
                    }
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

    }
