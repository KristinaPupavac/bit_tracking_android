package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.lists.MapsActivity;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;

public class UserPanelActivity extends AppCompatActivity {

    private Button mSignOut;
    private Button myPackages;
    private TextView mName;
    private TextView mSurname;
    private TextView mEmail;
    private Button mTracking;
    private Button mCreateRequestButton;
    private Button mTrackPackageButton;
    private Button mMaps;
    private Button mFacebook;
    private Button mTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        mSignOut = (Button)findViewById(R.id.sign_out_button);
        myPackages = (Button)findViewById(R.id.my_packages_button);
        mName = (TextView) findViewById(R.id.user_name);
        mSurname = (TextView) findViewById(R.id.user_surname);
        mEmail = (TextView) findViewById(R.id.user_email);
        mTracking = (Button) findViewById(R.id.user_tracking);
        mCreateRequestButton = (Button) findViewById(R.id.new_request_button);

        mName.setText(User.getInstance().getName().toString());
        mSurname.setText(User.getInstance().getSurname().toString());
        mEmail.setText(User.getInstance().getMail().toString());

        mTrackPackageButton = (Button) findViewById(R.id.info_user);
        mMaps = (Button) findViewById(R.id.location_user);
        mFacebook = (Button) findViewById(R.id.facebook_button_user);
        mTwitter = (Button) findViewById(R.id.twitter_button_user);

        mTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPanelActivity.this, TrackPackageActivity.class);
                startActivity(intent);
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

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPanelActivity.this);
                builder.setMessage("Are you sure you want to sign out?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        myPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent goToMyPackages = new Intent(UserPanelActivity.this, UserPackagesActivity.class);
                goToMyPackages.putExtra("email", User.getInstance().getMail());
                startActivity(goToMyPackages);

            }
        });
        mCreateRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserPanelActivity.this, CreateRequestActivity.class));
            }
        });

        mTrackPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserPanelActivity.this, TrackPackageActivity.class));
            }
        });

        mMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    logout(UserPanelActivity.this);
                    startActivity(new Intent(UserPanelActivity.this, BitTrackingActivity.class));
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        }
    };



    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }

    public static void logout(Context ctx){
        SharedPreferences sharedpreferences = ctx.getSharedPreferences
                (BitTrackingActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        User.getInstance().setMail(null);
        User.getInstance().setPassword(null);
        Intent it = new Intent(ctx, BitTrackingActivity.class);
        ctx.startActivity(it);
    }

    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserPanelActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
