package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.lists.PackageList;
import ba.bitcamp.bittracking.bittrackingapplication.models.*;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Kristina Pupavac on 10/27/2015.
 */
public class PackagePagerActivity extends FragmentActivity{
    private static final String EXTRA_PACKAGE_ID = "ba.bitcamp.bittracking.bittrackingapplication.controllers.package_id";

    private ViewPager mViewPager;
    private List<Package> mPackages;

    public static Intent newIntent(Context packageContext, Long packageId) {
        Intent intent = new Intent(packageContext, PackagePagerActivity.class);
        intent.putExtra(EXTRA_PACKAGE_ID, packageId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle saveInstanceStated){
        super.onCreate(saveInstanceStated);
        setContentView(R.layout.activity_package_pager);

        UUID packageId = (UUID) getIntent().getSerializableExtra(EXTRA_PACKAGE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_package_pager_view_pager);
        mPackages =  PackageList.getInstance().getPackageList();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Package p = mPackages.get(position);
                return null;
            }

            @Override
            public int getCount() {
                return mPackages.size();
            }
        });

        for(int i = 0; i < mPackages.size(); i++){
            if(mPackages.get(i).equals(packageId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }

}