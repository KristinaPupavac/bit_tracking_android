package ba.bitcamp.bittracking.bittrackingapplication.lists;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.models.*;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Mladen13 on 26.10.2015.
 */
public class PackageList {

    private static PackageList packagesInstance = new PackageList();

    public static PackageList getInstance() {
        return packagesInstance;
    }

    private ArrayList<Package> mPackage;

    private PackageList() {
        mPackage = new ArrayList<Package>();
    }

    public void getPackageListRequest(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public ArrayList<Package> getPackageList(){
        return mPackage;
    }

    private Callback parseResponse(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Response", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {

                    JSONArray array = new JSONArray(response.body().string());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        Long id = obj.getLong("id");
                        String trackingNum = obj.getString("trackingNum");
                        String recipientName = obj.getString("recipientName");
                        String recipientAddress = obj.getString("recipientAddress");
                        Double weight = obj.getDouble("weight");
                        String packageType = obj.getString("packageType");
                        String status = obj.getString("status");

                        mPackage.add(new Package(recipientName, recipientAddress, weight, packageType, trackingNum, status));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
