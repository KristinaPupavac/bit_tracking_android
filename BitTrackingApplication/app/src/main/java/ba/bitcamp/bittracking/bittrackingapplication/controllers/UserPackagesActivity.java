package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;
import ba.bitcamp.bittracking.bittrackingapplication.models.User;

public class UserPackagesActivity extends AppCompatActivity {

    private Button mCreateRequestButton;
    private RecyclerView recyclerView;
    private PackageAdapter packageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_packages);
        Intent intnt = getIntent();
        String email = intnt.getStringExtra("email");
        String url = getString(R.string.service_get_user_packages);
        JSONObject json = new JSONObject();
         PackageList.getInstance().getPackageList().clear();
        try{
            json.put("email", email);
        }catch(JSONException e){
            e.printStackTrace();
        }
        ServiceRequest.post(url, json.toString(), getPackages());
        mCreateRequestButton = (Button)findViewById(R.id.new_request_button);

        recyclerView = (RecyclerView) findViewById(R.id.packages_recycler_view);
        packageAdapter = new PackageAdapter(PackageList.getInstance().getPackageList());
        recyclerView.setAdapter(packageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        packageAdapter.notifyDataSetChanged();

        mCreateRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPackagesActivity.this, CreateRequestActivity.class));
            }
        });
    }

    private class PackageAdapter extends RecyclerView.Adapter<PackageHolder>{
        private List<Package> mPackages;
        public PackageAdapter(List<Package> packages){
            mPackages = packages;
        }
        @Override
        public PackageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(UserPackagesActivity.this);
            View view = layoutInflater.inflate(R.layout.activity_packages, parent, false);
            return new PackageHolder(view);
        }

        @Override
        public void onBindViewHolder(PackageHolder holder, int position) {
            Package p = PackageList.getInstance().getPackageList().get(position);
            holder.bindPackage(p);

        }

        @Override
        public int getItemCount() {
            return mPackages.size();
        }
    }

    private class PackageHolder extends RecyclerView.ViewHolder {
        public TextView mPackageId;
        public TextView mPackageAR;
        public TextView mPackageStatus;
        public Package mPackage;


        public PackageHolder(View itemView) {
            super(itemView);

            mPackageId = (TextView) itemView.findViewById(R.id.package_id);
            mPackageAR = (TextView) itemView.findViewById(R.id.package_approved_rejected);
            mPackageStatus = (TextView) itemView.findViewById(R.id.package_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserPackagesActivity.this, PackageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("asd", findPosition(mPackage));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }

        public void bindPackage(Package p) {
            mPackage = p;
            mPackageId.setText(mPackage.getId().toString());
            mPackageAR.setText(mPackage.getApproved().toString());
            mPackageStatus.setText(mPackage.getStatus().toString());
        }

    }

    private Callback getPackages() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJSON = response.body().string();
                JSONArray arr = new JSONArray();
                PackageList lista = PackageList.getInstance();
                try {
                    arr = new JSONArray(responseJSON);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Long id = obj.getLong("id");
                        String trackingNum = obj.getString("trackingNum");
                        String recipientName = obj.getString("recipientName");
                        String recipientAddress = obj.getString("recipientAddress");
                        Double weight = obj.getDouble("weight");
                        String packageType = obj.getString("packageType");
                        String status =obj.getString("status");
                        Boolean approved = obj.getBoolean("approved");
                        String approvedStatus = "";
                        if(approved==null){
                            approvedStatus = "Waiting for approval";
                        }else if(approved){
                            approvedStatus = "Approved";
                        }else if(!approved){
                            approvedStatus = "Rejected";
                        }
                        Package pack = new Package(id, recipientName, recipientAddress, weight, packageType, trackingNum, status, approvedStatus);
                        lista.getPackageList().add(pack);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void ToastMessage(final String message) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserPackagesActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static int findPosition(Package pack){
        for(int i =0;i<PackageList.getInstance().getPackageList().size();i++){
            if(pack.getId().equals(PackageList.getInstance().getPackageList().get(i).getId())){
                return i;
            }
        }
        return -1;
    }

}
