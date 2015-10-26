package ba.bitcamp.bittracking.bittrackingapplication.controllers;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ba.bitcamp.bittracking.bittrackingapplication.R;

/**
 * Created by Kristina Pupavac on 10/17/2015.
 */
public class TrackPackageActivity extends AppCompatActivity {

    private EditText mTrackingNumber;
    private Button mButton;
    private Button mPackageList;
    private LinearLayout mLinearLayout;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_package);

        mTrackingNumber = (EditText) findViewById(R.id.trucking_number);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout);

        t = new TextView(TrackPackageActivity.this);
        t.setTextColor(Color.WHITE);
        mLinearLayout.addView(t);
        t.setText("");

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
