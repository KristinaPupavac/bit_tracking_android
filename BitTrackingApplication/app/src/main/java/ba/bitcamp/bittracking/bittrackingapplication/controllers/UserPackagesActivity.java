package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ba.bitcamp.bittracking.bittrackingapplication.R;

public class UserPackagesActivity extends AppCompatActivity {

    private Button mCreateRequestButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_packages);

        mCreateRequestButton = (Button)findViewById(R.id.new_request_button);

        mCreateRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPackagesActivity.this, CreateRequestActivity.class));
            }
        });
    }
}
