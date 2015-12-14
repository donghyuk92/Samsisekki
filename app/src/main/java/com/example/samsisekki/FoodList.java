package com.example.samsisekki;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.samsisekki.parsing.parsing;
import com.example.user.menu4u.R;

import com.example.samsisekki.db.dbinsert;

/**
 * Created by slave on 2015-11-17.
 */
public class FoodList extends AppCompatActivity {

    SQLiteDatabase database;
    private ListView    m_ListView;
    private CustomAdapter   m_Adapter;
    DBController db;

    String[] values = new String[] { "후라이드치킨", "양념치킨", "간장치킨", "퓨전치킨", "마늘치킨" };
    DeviceUuidFactory dev;
    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);
        dev = new DeviceUuidFactory(this);
        deviceID = dev.getDeviceID();

        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter(this);

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.foodlist);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        for(String item : values)
            m_Adapter.add(item);
        /**
        Cursor result = db.getHist(deviceID);
        result.moveToFirst();
        while(!result.isAfterLast()){
            m_Adapter.add(result.getString(0));
            m_Adapter.add(result.getString(1));
            result.moveToNext();
        }
        result.close();
**/
        m_ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), values[position], Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), values[position], Toast.LENGTH_SHORT).show();
                String item = (String) m_ListView.getSelectedItem();
                Toast.makeText(getApplicationContext(), item + " selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), parsing.class);
                intent.putExtra("name","치킨");
                startActivity(intent);
            }
        });
    }
        /**
        rating.setRating((float) 2.5);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
        rating.setIsIndicator(false);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능
**/

    public void move(View v) {
        Intent intent = new Intent(this, parsing.class);
        intent.putExtra("name","치킨");
        startActivity(intent);
    }
}
