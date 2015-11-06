package ba.bitcamp.bittracking.bittrackingapplication.lists;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.jar.Manifest;
import java.util.logging.Logger;

import ba.bitcamp.bittracking.bittrackingapplication.R;

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
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Toast.makeText(getApplicationContext(), getCurrentLocation(this).toString(), Toast.LENGTH_SHORT).show();
        String location = getStringLocation(getCurrentLocation(this));
        if(networkInfo != null && networkInfo.isConnected()) {
            new DownloadDataTask().execute("http://10.0.82.146:9000/json/" + location);

        } else {

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(getCurrentLocation(this)).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getCurrentLocation(this)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        mMap.animateCamera(zoom);
    }

    public LatLng getCurrentLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String locationProvider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(locationProvider);
        boolean isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled){
            return null;
        }else{
            if (isNetworkEnabled){
                if (locationManager != null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
            if (isGPSEnabled){
                if (location == null){
                    if (locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
        }

        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public String getStringLocation(LatLng lng){
        return lng.longitude + "&" + lng.latitude;
    }

    private class DownloadDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadData(params[0]);
            } catch (IOException e) {
                return "Unable to retreive data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String[] postOfficeLoc = result.split(",");
            LatLng officeLocation = new LatLng(Double.parseDouble(postOfficeLoc[0]), Double.parseDouble(postOfficeLoc[1]));
            mMap.addMarker(new MarkerOptions().position(officeLocation).title(postOfficeLoc[2]));
            onMapReady(mMap);
        }

        private String downloadData(String myUrl) throws IOException {
            InputStream is = null;

            int length = 5500;
            try {
                URL url = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();
                String dataAsString = readIt(is, length);
                return dataAsString;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        public String readIt(InputStream stream, int length) throws IOException, UnsupportedEncodingException {

            Reader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[length];
            reader.read(buffer);
            String readBuffer = new String(buffer);

            return readBuffer;
        }
    }
}
