package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import ba.bitcamp.bittracking.bittrackingapplication.R;
import ba.bitcamp.bittracking.bittrackingapplication.models.*;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Kristina Pupavac on 10/27/2015.
 */
public class PackagePagerActivity extends FragmentActivity{
    private ViewPager mViewPager;
    private List<Package> mPackages;

    @Override
    protected void onCreate(Bundle saveInstanceStated){
        super.onCreate(saveInstanceStated);
        setContentView(R.layout.activity_package_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_package_pager_view_pager);
        //mPackage Lista getinstance

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Package p = mPackages.get(position);
                return p;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });


    }

}
