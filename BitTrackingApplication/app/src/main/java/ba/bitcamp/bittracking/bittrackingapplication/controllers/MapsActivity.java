package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ba.bitcamp.bittracking.bittrackingapplication.R;

/**
 * Set up a current location Google Map on Android
 */
public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private LatLng myPosition;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = mf.getMap();
        mMap.setMyLocationEnabled(true);
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        //creating criteria to retreive provider
        Criteria criteria = new Criteria();
        //getting the best provider
        String provider = lm.getBestProvider(criteria, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        //getting current location
        Location location = lm.getLastKnownLocation(provider);
        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),5));
        }
    }
}


