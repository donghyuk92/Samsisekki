package com.example.samsisekki;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsisekki.dbtest.DBController;
import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.samsisekki.displayingbitmaps.ui.ImageDetailActivity;
import com.example.samsisekki.parsing.parsing;
import com.example.user.menu4u.R;

import com.example.samsisekki.db.dbinsert;

import java.util.ArrayList;

/**
 * Created by slave on 2015-11-17.
 */
public class FoodList extends AppCompatActivity {

    SQLiteDatabase database;
    private ListView    m_ListView;
    private CustomAdapter   m_Adapter;
    DBController db;

    DeviceUuidFactory dev;
    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);
        dev = new DeviceUuidFactory(this);
        deviceID = dev.getDeviceID();
        db = new DBController(this);
        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter(this);

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.foodlist);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        Intent intent = getIntent();
        int position = intent.getIntExtra(ImageDetailActivity.EXTRA_IMAGE, 1);

        ArrayList<String> menu = new ArrayList<String>();
        for(int i=0; i<Images.menu.length; i++) {
            if (Images.menu[i].contains(Images.menu[position])) {
                m_Adapter.add(Images.menu[i]);
                m_Adapter.addurl(Images.imageThumbUrls[i]);
                menu.add(Images.menu[i]);
            }
        }

        for(int i=0; i<menu.size(); i++) {
            Cursor result = db.getRate(deviceID, menu.get(i));
            if (!result.isAfterLast())
                m_Adapter.addrating(result.getString(0));
            else
                m_Adapter.addrating("Null");
            result.close();

        }

        m_ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), Images.menu[position], Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), Images.menu[position], Toast.LENGTH_SHORT).show();
                String item = (String) m_ListView.getSelectedItem();
                Toast.makeText(getApplicationContext(), item + " selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), parsing.class);
                intent.putExtra("name", "치킨");
                startActivity(intent);
            }
        });
    }
    public void move(View v) {
        Intent intent = new Intent(this, parsing.class);
        intent.putExtra("name","치킨");
        startActivity(intent);
    }
}
