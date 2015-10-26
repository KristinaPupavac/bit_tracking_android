package ba.bitcamp.bittracking.bittrackingapplication.lists;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ba.bitcamp.bittracking.bittrackingapplication.helpers.ServiceRequest;
import ba.bitcamp.bittracking.bittrackingapplication.models.*;
import ba.bitcamp.bittracking.bittrackingapplication.models.Package;

/**
 * Created by Mladen13 on 26.10.2015.
 */
public class PostOfficeList {

    private static PostOfficeList postOfficesInstance = new PostOfficeList();

    public static PostOfficeList getInstance() {
        return postOfficesInstance;
    }

    private ArrayList<PostOffice> mPostOffices;

    private PostOfficeList() {
        mPostOffices = new ArrayList<PostOffice>();
    }

    public void getPostOfficeListRequest(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public ArrayList<PostOffice> getPostOfficeList(){
        return mPostOffices;
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
                        String name = obj.getString("name");
                        String address = obj.getString("address");

                        mPostOffices.add(new PostOffice(name, address));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
