package com.example.samsisekki;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.samsisekki.algorithm.entity.Vector;
import com.example.samsisekki.algorithm.util.VectorUtil;
import com.example.samsisekki.db.dbinsert;
import com.example.samsisekki.dbtest.DBController;
import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.samsisekki.displayingbitmaps.util.ImageCache;
import com.example.samsisekki.displayingbitmaps.util.ImageFetcher;
import com.example.user.menu4u.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RecommendFragment extends Fragment {

    SQLiteDatabase database;
    String deviceID;
    DBController db;
    int index;
    ImageFetcher mImageFetcher;
    private static final String IMAGE_CACHE_DIR = "thumbs";
    HashMap<String, String> queryValues;

    public static RecommendFragment newInstance(){
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    public RecommendFragment() {
        syncSQLiteMySQLDB();
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        syncSQLiteMySQLDB();
        // Initialize Progress Dialog properties

        View v = inflater.inflate(R.layout.recommend, container, false);

        DeviceUuidFactory dev = new DeviceUuidFactory(getContext());
        deviceID = dev.getDeviceID();

        ArrayList<String> IDs = new ArrayList<String>();
        Cursor result = db.getIDs();
        while(!result.isAfterLast()){
            IDs.add(result.getString(0));
            result.moveToNext();
        }
        result.close();

        ArrayList<Vector> vectors = new ArrayList<Vector>();

        int size = Images.menu.length;
        for(int j=0; j<IDs.size(); j++) {
            float[] vec = new float[size];
            String deviceID = IDs.get(j);
            Cursor result2 = db.getIDVector(deviceID);
            while(!result2.isAfterLast()){
                int k = Arrays.asList(Images.menu).indexOf(result2.getString(0));
                vec[k] = Float.parseFloat(result2.getString(1));
                result2.moveToNext();
            }
            result2.close();

            Vector vc = new Vector(vec);
            vectors.add(vc);
        }

        int me = IDs.indexOf(deviceID);
        index = VectorUtil.getRecommended(vectors, me);

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        int mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        //mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);

        final ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        mImageFetcher.loadImage(Images.imageThumbUrls[index],imageView);
        Button button = (Button) v.findViewById(R.id.btnCancel);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar2);
        ratingBar.setNumStars(3);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                index += 1;
                mImageFetcher.loadImage(Images.imageThumbUrls[index], imageView);

            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                dbinsert db = new dbinsert();
                db.dbinsert(deviceID,"asd", Images.menu[index] ,rating, Images.imageThumbUrls[index]);
            }
        });
        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        db = new DBController(activity);
    }
    public void syncSQLiteMySQLDB() {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
        // Make Http call to getusers.php
        client.post("http://117.17.188.146/donghyuk/sync/script/getusers.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar

                // Update SQLite DB with response sent by getusers.php
                updateSQLite(response);
            }

            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // Hide ProgressBar
                if (statusCode == 404) {
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateSQLite(String response){
        // Create GSON object
        Gson gson = new GsonBuilder().create();
        try {
            // Extract JSON array from the response
            JSONArray arr = new JSONArray(response);
            // If no of array elements is not zero
            if(arr.length() != 0){
                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < arr.length(); i++) {
                    // Get JSON object
                    JSONObject obj = (JSONObject) arr.get(i);
                    // DB QueryValues Object to insert into SQLite
                    queryValues = new HashMap<String, String>();
                    // Add userID extracted from Object
                    queryValues.put("deviceID", obj.get("deviceID").toString());
                    // Add userName extracted from Object
                    queryValues.put("inserttime", obj.get("inserttime").toString());
                    queryValues.put("class", obj.get("class").toString());
                    queryValues.put("menu", obj.get("menu").toString());
                    queryValues.put("rating", obj.get("rating").toString());
                    queryValues.put("url", obj.get("url").toString());
                    // Insert User into SQLite DB
                    db.insertUser(queryValues);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
