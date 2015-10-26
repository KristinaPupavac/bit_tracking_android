package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;

public class CreateRequestActivity extends AppCompatActivity {

    private Button mCreateRequestButton;
    private EditText mRecipientName;
    private EditText mRecipientAddress;
    private EditText mWeight;
    private Spinner mPackageType;
    private Spinner mPostOffices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        mCreateRequestButton = (Button)findViewById(R.id.create_request);
        mRecipientName = (EditText)findViewById(R.id.recipient_name);
        mRecipientAddress = (EditText)findViewById(R.id.recipient_address);
        mWeight = (EditText)findViewById(R.id.package_weight);
        mPackageType = (Spinner)findViewById(R.id.package_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_of_packages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPackageType.setAdapter(adapter);
        String url = getString(R.string.service_create_request);
        ServiceRequest.get(url, getPostOffices());


        mCreateRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientName = mRecipientName.getText().toString();
                String recipientAddress = mRecipientAddress.getText().toString();
                String weight = mWeight.getText().toString();
                String postOffice = mPostOffices.getSelectedItem().toString();
                String packageType = mPackageType.getSelectedItem().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("recipientName", recipientName);
                    json.put("recipientAddress", recipientAddress);
                    json.put("weight", weight);
                    json.put("packageType", packageType);
                    json.put("postOfficeName", postOffice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = getString(R.string.service_create_request_post);
                ServiceRequest.post(url, json.toString(), createRequest());
                ToastMessage("Redirecting...");
            }
        });

    }
    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateRequestActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Callback createRequest() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                ToastMessage("Added Successfully");

            }
        };
    }

    private Callback getPostOffices() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJSON= response.body().string();
                JSONArray arr = new JSONArray();
                List<String> stringList = new ArrayList<>();
                try {
                     arr = new JSONArray(responseJSON);
                    for(int i=0;i<arr.length();i++){
                        JSONObject obj = arr.getJSONObject(i);
                        String name = obj.getString("name");
                        stringList.add(name);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

                mPostOffices = (Spinner) findViewById(R.id.send_via);
                ArrayAdapter<String> spinerAdapter = new ArrayAdapter<String>(CreateRequestActivity.this, android.R.layout.simple_spinner_item, stringList);
                spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mPostOffices.setAdapter(spinerAdapter);
            }
        };
    }
}
