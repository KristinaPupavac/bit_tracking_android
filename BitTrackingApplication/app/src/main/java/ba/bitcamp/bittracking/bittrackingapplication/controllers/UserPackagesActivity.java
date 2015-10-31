package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

import static ba.bitcamp.bittracking.bittrackingapplication.R.id.package_list_info_btn;

public class UserPackagesActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private PackageAdapter packageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_packages);
        Intent intent= getIntent();
        String id = intent.getStringExtra("id");
        String email = intent.getStringExtra("email");
        String url = getString(R.string.service_get_user_packages);
        JSONObject json = new JSONObject();
        PackageList.getInstance().getPackageList().clear();

        try {
            json.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServiceRequest.post(url, json.toString(), getPackages());


        recyclerView = (RecyclerView) findViewById(R.id.packages_recycler_view);
        packageAdapter = new PackageAdapter(PackageList.getInstance().getPackageList());
        recyclerView.setAdapter(packageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        packageAdapter.notifyDataSetChanged();


    }

    private class PackageAdapter extends RecyclerView.Adapter<PackageHolder> {
        private List<Package> mPackages;

        public PackageAdapter(List<Package> packages) {
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
        public TextView mPackageAR;
        public TextView mPackageStatus;
        public Package mPackage;
        public Button mInfo;


        public PackageHolder(View itemView) {
            super(itemView);

            mPackageAR = (TextView) itemView.findViewById(R.id.package_approved_rejected);
            mPackageStatus = (TextView) itemView.findViewById(R.id.package_status);
            mInfo = (Button) itemView.findViewById(package_list_info_btn);

            mInfo.setOnClickListener(new View.OnClickListener() {
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

            if (mPackage.getApproved().toString().toLowerCase().equals("approved")){
                mPackageAR.setTextColor(Color.GREEN);
                mPackageAR.setText(mPackage.getApproved().toString());
            } else {
                mPackageAR.setTextColor(Color.RED);
                mPackageAR.setText(mPackage.getApproved().toString());
            }
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

                    if (arr.length() > 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            Long id = obj.getLong("id");
                            String trackingNum = obj.getString("trackingNum");
                            String recipientName = obj.getString("recipientName");
                            String recipientAddress = obj.getString("recipientAddress");
                            Double weight = obj.getDouble("weight");
                            String packageType = obj.getString("packageType");
                            String status = obj.getString("status");
                            String approved = obj.getString("approved");
                            String timestamp = obj.getString("timestamp");
                            String price = obj.getString("price");
                            Package pack = new Package(id, recipientName, recipientAddress, weight, packageType, trackingNum, status, approved, timestamp, Double.parseDouble(price));
                            lista.getPackageList().add(pack);
                        }
                    } else {
                        ToastMessage("No Packages Available!");
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

    public static int findPosition(Package pack) {
        for (int i = 0; i < PackageList.getInstance().getPackageList().size(); i++) {
            if (pack.getId().equals(PackageList.getInstance().getPackageList().get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

}
