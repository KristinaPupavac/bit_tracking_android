package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import ba.bitcamp.bittracking.bittrackingapplication.models.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

public class UserPackagesActivity extends AppCompatActivity {

    public List<Package> list = new LinkedList<Package>();
    Package p1 = new Package(1, Package.READY_FOR_SHIPPING);
    Package p2 = new Package(2, Package.ON_ROUTE);
    Package p3 = new Package(3, Package.OUT_FOR_DELIVERY);
    Package p4 = new Package(4, Package.DELIVERED);
    Package p5 = new Package(5, Package.RECEIVED);

    private Button mCreateRequestButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_packages);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        mCreateRequestButton = (Button)findViewById(R.id.new_request_button);
        recyclerView = (RecyclerView) findViewById(R.id.packages_recycler_view);

        mCreateRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPackagesActivity.this, CreateRequestActivity.class));
            }
        });
    }


    private class PackageHolder extends RecyclerView.ViewHolder {
        public TextView mPackageId;
        public TextView mPackageAR;
        public TextView mPackageStatus;
        public Package mPackage;


        public PackageHolder(View itemView) {
            super(itemView);
            mPackageId = (TextView) itemView.findViewById(R.id.package_id);
            mPackageAR = (TextView) itemView.findViewById(R.id.package_approved_rejested);
            mPackageStatus = (TextView) itemView.findViewById(R.id.package_status);
        }

        public void bindPackage(Package p){
            mPackage = p;
            mPackageId.setText(mPackage.getId().toString());
            mPackageAR.setText(mPackage.getStatusName().toString());
            mPackageStatus.setText(mPackage.getStatus().toString());
        }

        private class PackageAdapter extends RecyclerView.Adapter<PackageHolder>{
            private List<Package> mPackages;
            public PackageAdapter(List<Package> packages){
                mPackages = packages;
            }
            @Override
            public PackageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(UserPackagesActivity.this);
                View view = layoutInflater.inflate(R.layout.activity_user_packages, parent, false);
                return new PackageHolder(view);
            }

            @Override
            public void onBindViewHolder(PackageHolder holder, int position) {
                Package p = mPackages.get(position);
                holder.bindPackage(p);

            }

            @Override
            public int getItemCount() {
                return mPackages.size();
            }
        }

    }
}
