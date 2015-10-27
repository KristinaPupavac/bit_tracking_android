package ba.bitcamp.bittracking.bittrackingapplication.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ba.bitcamp.bittracking.bittrackingapplication.R;

/**
 * Set up a basic Google Map on Android
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * This method eill manipulate google mape once is available
     * This is where we can add markers or lines, add listeners or move the camera.Marker is added near Sarajevo
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sarajevo and move the camera
        LatLng sarajevo = new LatLng(43.8667, 18.4167);
        // Add a marker with a title that is shown in its info window.
        mMap.addMarker(new MarkerOptions().position(sarajevo).title("Marker in Sarajevo"));
        // Move the camera to show the marker.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sarajevo));
    }
}