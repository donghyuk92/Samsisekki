package com.example.samsisekki;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.samsisekki.Alarm.AlarmFragment;
import com.example.user.menu4u.R;

public class MainActivity extends AppCompatActivity {
    private CharSequence mTitle;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    ListView lvDrawerList;
    ArrayAdapter<String> adtDrawerList;
    String[] menuItems = new String[]{"Home", "History","Rank","Setting"};

    AlarmFragment alarmFragment;
    ImageGridFragment fragGrid;
    History history;
    Ranking rank;
    Setting setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragments
        fragGrid = ImageGridFragment.newInstance();
        history = History.newInstance();
        rank= Ranking.newInstance();
        setting = Setting.newInstance();
        alarmFragment = AlarmFragment.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, fragGrid).commit();

        // Navigation drawer : menu lists
        lvDrawerList = (ListView) findViewById(R.id.lv_activity_main);
        adtDrawerList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        lvDrawerList.setAdapter(adtDrawerList);
        lvDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        mTitle = getString(R.string.title_section1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, fragGrid).commit();
                        break;
                    case 1:
                        mTitle = getString(R.string.title_section2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, history).commit();
                        break;
                    case 2:
                        mTitle = getString(R.string.title_section3);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_activity_main, rank).commit();
                        break;
                    case 3:
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(dtToggle.onOptionsItemSelected(item)){
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
}