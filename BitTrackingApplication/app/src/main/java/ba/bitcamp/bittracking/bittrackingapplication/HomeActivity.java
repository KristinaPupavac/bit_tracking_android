package ba.bitcamp.bittracking.bittrackingapplication;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class HomeActivity extends AppCompatActivity {
    public  List<Package> list = new LinkedList<Package>();
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
        mButton = (Button) findViewById(R.id.button);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout);
        mSignOut = (Button) findViewById(R.id.sign_out);
        t = new TextView(HomeActivity.this);
        t.setTextColor(Color.WHITE);
        mLinearLayout.addView(t);
        t.setText("");

      /*  TextView text = new TextView(HomeActivity.this);
        text.setText("Tracking package numbers:  p1: " + p1.getToken() + " p2: " +p2.getToken()
                + " p3: " +p3.getToken()+ " p4: " +p4.getToken()+ " p5: " +p5.getToken());
        text.setTextColor(Color.WHITE);
        mLinearLayout.addView(text); */


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Package p = Package.getPackageByToken(list, mTruckingNumber.getText().toString());
               // for (int i = 0; i < list.size(); i++) {
                    if (p != null) {
                        t.setText("Package status: " + p.getStatusName());

//                        if (list.get(i).getStatus() == 1) {
//                            t.setText("Package status: READY FOR SHIPPING");
//                            break;
//                        } else if (list.get(i).getStatus() == 2) {
//                            t.setText("Package status: ON ROUTE");
//                            break;
//                        } else if (list.get(i).getStatus() == 3) {
//                            t.setText("Package status: OUT FOR DELIVERY");
//                            break;
//                        } else if (list.get(i).getStatus() == 4) {
//                            t.setText("Package status: DELIVERED");
//                            break;
//                        } else if (list.get(i).getStatus() == 5) {
//                            t.setText("Package status: RECEIVED");
//                            break;
//                        } else {
//                            mTruckingNumber.setError("Wrong trucking number!");
//                        }
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
