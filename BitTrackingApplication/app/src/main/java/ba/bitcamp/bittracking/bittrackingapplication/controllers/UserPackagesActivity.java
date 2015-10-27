package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

public class UserPackagesActivity extends AppCompatActivity {

    private Button mCreateRequestButton;
    private RecyclerView recyclerView;
    private PackageAdapter packageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_packages);

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

    public static int findPosition(Package pack){
        for(int i =0;i<PackageList.getInstance().getPackageList().size();i++){
            if(pack.getId().equals(PackageList.getInstance().getPackageList().get(i).getId())){
                return i;
            }
        }
        return -1;
    }

}
