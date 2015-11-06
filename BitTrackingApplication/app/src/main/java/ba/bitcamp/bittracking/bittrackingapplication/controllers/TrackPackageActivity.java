package ba.bitcamp.bittracking.bittrackingapplication.controllers;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class TrackPackageActivity extends AppCompatActivity {

    private EditText mTrackingNumber;
    private Button mOkButton;
    private Button mPackageList;
    private LinearLayout mLinearLayout;
    private TextView t;
    static ArrayList<Package> packages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_package);

        mTrackingNumber = (EditText) findViewById(R.id.tracking_number);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout);
        mOkButton = (Button) findViewById(R.id.ok_button);
        PackageList packageList = PackageList.getInstance();
        String url = getString(R.string.service_get_package_list);
        if(packageList.getPackageList().size() == 0){
            packageList.getPackageListRequest(url);
            packages = packageList.getPackageList();
        }

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTrackingNumber = mTrackingNumber.getText().toString();

                    for (int i = 0; i < packages.size(); i++) {
                        if (getTrackingNumber.equals(packages.get(i).getTrackingNum())) {
                            t = new TextView(TrackPackageActivity.this);
                            t.setTextColor(Color.YELLOW);
                            mLinearLayout.addView(t);
                            if (!packages.get(i).getStatus().equals("DELIVERED")) {
                                t.setText("OUT FOR DELIVERY (" + packages.get(i).getTimestamp() + ")");
                            } else {
                                t.setText("DELIVERED (" + packages.get(i).getTimestamp()+ ")");
                            }
                            break;
                        } else {
                            ToastMessage("Tracking number doesn't exist!");
                        }
                    }
            }
        });

    }

    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TrackPackageActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
