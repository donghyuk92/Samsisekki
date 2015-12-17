package com.example.samsisekki;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsisekki.algorithm.entity.Vector;
import com.example.samsisekki.algorithm.util.VectorUtil;
import com.example.samsisekki.dbtest.dbinsert;
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
import java.util.Collections;
import java.util.HashMap;

public class RecommendFragment extends Fragment {

    SQLiteDatabase database;
    String deviceID;
    DBController db;
    ImageFetcher mImageFetcher;
    private static final String IMAGE_CACHE_DIR = "thumbs";
    HashMap<String, String> queryValues;
    ArrayList<Integer> indexlist;
    int index;
    ProgressDialog prgDialog;
    ArrayList<Integer> ranNumber;
    int k;
    int i;
    int check;

    public static RecommendFragment newInstance() {
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

        final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //데이터 호출
        check = mPref.getInt("number", 0);

        //*호출하는 키에 대응하는 값이 없을 경우 디폴트로 설정한 데이터가 출력된다.
        ranNumber = new ArrayList<Integer>();
        for (int i = 0; i < Images.menu.length; i++) {
            ranNumber.add(i);
        }
        Collections.shuffle(ranNumber);
        index = ranNumber.get(0);

        if (check ==11) {
            ArrayList<String> IDs = new ArrayList<String>();
            Cursor result = db.getIDs();
            while (!result.isAfterLast()) {
                IDs.add(result.getString(0));
                result.moveToNext();
            }
            result.close();

            ArrayList<Vector> vectors = new ArrayList<Vector>();

            int size = Images.menu.length;
            for (int j = 0; j < IDs.size(); j++) {
                float[] vec = new float[size];
                String deviceID = IDs.get(j);
                Cursor result2 = db.getIDVector(deviceID);
                while (!result2.isAfterLast()) {
                    int k = Arrays.asList(Images.menu).indexOf(result2.getString(0));
                    vec[k] = Float.parseFloat(result2.getString(1));
                    result2.moveToNext();
                }
                result2.close();

                Vector vc = new Vector(vec);
                vectors.add(vc);
            }

            int me = IDs.indexOf(deviceID);
            java.util.Vector raw = VectorUtil.getRecommended(vectors, me);

            indexlist = (ArrayList<Integer>) raw.get(0);
            index = (int) raw.get(1);
        }

        final TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        textView2.setText(Images.menu2[index]);

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        int mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        //mImageFetcher.setLoadingImage(R.drawable.);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
        final ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("parsingtest");//context, parsingtest.class
                intent.putExtra("menu", Images.menu2[index]);
                getActivity().startService(intent);
            }
        });
        mImageFetcher.loadImage(Images.imageThumbUrls[index], imageView);

        final RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar2);
        ratingBar.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser && check == 11) {
                    dbinsert db = new dbinsert(getContext());
                    db.dbinsert(deviceID, "Han", Images.menu[index], rating, Images.imageThumbUrls[index]);
                }
            }
        });

        i = 0;
        k = 0;
        final Button button = (Button) v.findViewById(R.id.btnCancel);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int ch = check;
                Log.d("TAG", ch + "" + check);
                switch (ch) {
                    case 11:
                        try {
                            index = indexlist.get(i);
                        } catch (IndexOutOfBoundsException e) {
                            k++;
                            index = ranNumber.get(k);
                        }
                        i++;
                        break;
                    case 10:
                        if (ratingBar.getRating() == 0f) {
                            index = ranNumber.get(k);
                            k++;
                            dbinsert db = new dbinsert(getContext());
                            db.dbinsert(deviceID, "Han", Images.menu[index], ratingBar.getRating(), Images.imageThumbUrls[index]);
                            Toast.makeText(getActivity(), "취향 정보를 분석합니다!", Toast.LENGTH_SHORT);
                            SharedPreferences.Editor editor = mPref.edit();;
                            ch++;
                            editor.putInt("number", ch);
                            editor.commit();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } else
                            Toast.makeText(getActivity(), "취향 반영이 안됬습니다.\n 별점을 메겨 주세요~", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        if (ratingBar.getRating() == 0f) {
                            index = ranNumber.get(k);
                            k++;
                            dbinsert db = new dbinsert(getContext());
                            db.dbinsert(deviceID, "Han", Images.menu[index], ratingBar.getRating(), Images.imageThumbUrls[index]);
                        } else
                            Toast.makeText(getActivity(), "취향 반영이 안됬습니다.\n 별점을 메겨 주세요~", Toast.LENGTH_SHORT).show();
                        break;
                }
                mImageFetcher.loadImage(Images.imageThumbUrls[index], imageView);
                textView2.setText(Images.menu2[index]);
                ratingBar.setRating(0f);
                Log.d("TAG", ch + "" + check);
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

    public void updateSQLite(String response) {
        // Create GSON object
        Gson gson = new GsonBuilder().create();
        try {
            // Extract JSON array from the response
            JSONArray arr = new JSONArray(response);
            // If no of array elements is not zero
            if (arr.length() != 0) {
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
