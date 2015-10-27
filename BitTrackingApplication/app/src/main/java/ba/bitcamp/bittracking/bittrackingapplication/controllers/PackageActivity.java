package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Kristina Pupavac on 10/27/2015.
 */
public class PackageActivity extends AppCompatActivity {
    private TextView mTrackingNumber;
    private TextView mRecipientName;
    private TextView mRecipientAddress;
    private TextView mWeight;
    private TextView mAR;
    private TextView mPrice;
    private TextView mStatus;
    private TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        Integer position = (Integer)getIntent().getExtras().getSerializable("asd");
        Package p = PackageList.getInstance().getPackageList().get(position);

        mTrackingNumber = (TextView) findViewById(R.id.package_tracking_number);
        mRecipientName = (TextView) findViewById(R.id.package_recipient_name);
        mRecipientAddress = (TextView) findViewById(R.id.package_recipient_address);
        mWeight = (TextView) findViewById(R.id.package_wight_package);
        mAR = (TextView) findViewById(R.id.package_ar);
        mPrice = (TextView) findViewById(R.id.package_price);
        mStatus = (TextView) findViewById(R.id.package_status);
        mTime = (TextView) findViewById(R.id.package_time);


        mTrackingNumber.setText(p.getTrackingNum());
        mRecipientAddress.setText(p.getRecipientAddress());
        mRecipientName.setText(p.getRecipientName());
        mWeight.setText(String.valueOf(p.getWeight()));
        mAR.setText(p.getApproved());
        mStatus.setText(p.getStatus());
        mPrice.setText(String.valueOf(p.getPrice()));
        mTime.setText("");
    }
}
