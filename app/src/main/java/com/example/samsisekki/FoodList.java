package com.example.samsisekki;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samsisekki.parsing.parsing;
import com.example.user.menu4u.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by slave on 2015-11-17.
 */
public class FoodList extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);

        String[] values = new String[] { "후라이드치킨", "양념치킨", "간장치킨", "퓨전치킨", "마늘치킨" };
        // use your custom layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.foodlist, R.id.listview, values);
        setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, parsing.class);
        startActivity(intent);
    }
    public void move(View v) {
        Intent intent = new Intent(this, parsing.class);
        intent.putExtra("name","치킨");
        startActivity(intent);
    }
}
