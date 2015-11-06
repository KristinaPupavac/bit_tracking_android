package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PostOfficeList;
import ba.bitcamp.bittracking.bittrackingapplication.models.PostOffice;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;

public class CreateRequestActivity extends AppCompatActivity {

    private Button mCreateRequestButton;
    private EditText mRecipientName;
    private EditText mRecipientAddress;
    private EditText mWeight;
    private Spinner mPackageType;
    private Spinner mPostOffices;
    static ArrayList<PostOffice> offices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        String url = getString(R.string.service_create_request);
        PostOfficeList postOffices = PostOfficeList.getInstance();
        if(postOffices.getPostOfficeList().size()==0){
            postOffices.getPostOfficeListRequest(url);
            offices = postOffices.getPostOfficeList();
        }
        List<String> stringList = new ArrayList<>();

        for(int i =0;i<offices.size();i++){
            stringList.add(offices.get(i).getName());
        }
        Collections.sort(stringList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);
            }
        });
        mPostOffices = (Spinner) findViewById(R.id.send_via);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPostOffices.setAdapter(spinnerAdapter);

        mCreateRequestButton = (Button)findViewById(R.id.create_request);
        mRecipientName = (EditText)findViewById(R.id.recipient_name);
        mRecipientAddress = (EditText)findViewById(R.id.recipient_address);
        mWeight = (EditText)findViewById(R.id.package_weight);
        mPackageType = (Spinner)findViewById(R.id.package_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_of_packages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPackageType.setAdapter(adapter);


        mCreateRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientName = mRecipientName.getText().toString();
                String recipientAddress = mRecipientAddress.getText().toString();
                String weightAsString = mWeight.getText().toString();
                Double weight = 0.0;
                try {
                    weight = Double.parseDouble(weightAsString);
                }catch (NumberFormatException e){
                    mWeight.setError("Please insert correct values!");
                }
                String postOffice = mPostOffices.getSelectedItem().toString();
                String packageType = mPackageType.getSelectedItem().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("userId", User.getInstance().getId().toString());
                    json.put("recipientName", recipientName);
                    json.put("recipientAddress", recipientAddress);
                    json.put("weight", weight.toString());
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
                ToastMessage("Fill this form correctly!");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJSON = response.body().string();
                try{
                    JSONObject obj = new JSONObject(responseJSON);
                }catch(JSONException e){
                    e.printStackTrace();
                }
                ToastMessage("Added Successfully");

            }
        };
    }
}
