package com.example.samsisekki;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samsisekki.Alarm.AlarmFragment;
import com.example.samsisekki.dbtest.DBController;
import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.user.menu4u.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    private CharSequence mTitle;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    ListView lvDrawerList;
    ArrayAdapter<String> adtDrawerList;
    String[] menuItems = new String[]{"옛다!","어떤 음식있지?","나의 식지록","최고의 메뉴","알람 설정"};

    AlarmFragment alarmFragment;
    RecommendFragment recommendFragment;
    ImageGridFragment imageGridFragment;
    HistoryFragment historyFragment;
    RankingFragment rankingFragment;
    SettingFragment settingFragment;

    // DB Class to perform DB related operations
    DBController controller;
    // Progress Dialog Object
    ProgressDialog prgDialog;
    HashMap<String, String> queryValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new DBController(this);
        controller.createtbl();

        // Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Transferring Data from Remote MySQL DB and Syncing SQLite. Please wait...");
        prgDialog.setCancelable(false);

        // Fragments
        imageGridFragment = ImageGridFragment.newInstance();
        historyFragment = HistoryFragment.newInstance();
        rankingFragment = RankingFragment.newInstance();
        settingFragment = SettingFragment.newInstance();
        alarmFragment = AlarmFragment.newInstance();
        recommendFragment = RecommendFragment.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, recommendFragment).commit();

        // Navigation drawer : menu lists
        lvDrawerList = (ListView) findViewById(R.id.lv_activity_main);
        adtDrawerList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        lvDrawerList.setAdapter(adtDrawerList);
        lvDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        mTitle = getString(R.string.title_section0);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, recommendFragment).commit();
                        break;
                    case 1:
                        mTitle = getString(R.string.title_section1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, imageGridFragment).commit();
                        break;
                    case 2:
                        mTitle = getString(R.string.title_section2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, historyFragment).commit();
                        break;
                    case 3:
                        mTitle = getString(R.string.title_section3);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, rankingFragment).commit();
                        break;
                    case 4:
                        mTitle = getString(R.string.title_section4);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, alarmFragment).commit();
                        break;
                }
                dlDrawer.closeDrawer(lvDrawerList);
            }
        });

        // Navigation drawer : ActionBar Toggle
        dlDrawer = (DrawerLayout) findViewById(R.id.dl_activity_main);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    // Options Menu (ActionBar Menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    // When Options Menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(dtToggle.onOptionsItemSelected(item)){
            return true;
        } else if (item.getItemId() == R.id.refresh) {
            delcreate();
            syncSQLiteMySQLDB();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        Log.d(this.getClass().getSimpleName(), "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(this.getClass().getSimpleName(), "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(this.getClass().getSimpleName(), "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(this.getClass().getSimpleName(), "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName(), "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(this.getClass().getSimpleName(), "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }
    public void syncSQLiteMySQLDB() {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
        prgDialog.show();
        // Make Http call to getusers.php
        client.post("http://117.17.188.146/donghyuk/sync/script/getusers.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide ProgressBar
                prgDialog.hide();
                // Update SQLite DB with response sent by getusers.php
                updateSQLite(response);
            }

            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub
                // Hide ProgressBar
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
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
                    controller.insertUser(queryValues);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void delcreate() {
        controller.delete();
        controller.createtbl();
    }
}