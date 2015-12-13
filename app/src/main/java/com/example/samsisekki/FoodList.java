package com.example.samsisekki;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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

import com.example.samsisekki.parsing.parsing;
import com.example.user.menu4u.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by slave on 2015-11-17.
 */
public class FoodList extends AppCompatActivity {

    TextView tv01;
    ListView foodlist;
    String[] values = new String[] { "후라이드치킨", "양념치킨", "간장치킨", "퓨전치킨", "마늘치킨" };
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);

        CustomList adapter = new CustomList(this);
        foodlist = (ListView) findViewById(R.id.foodlist);
        foodlist.setAdapter(adapter);
        foodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), values[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
        /**
        tv01 = (TextView) findViewById(R.id.text1);
        rating = (RatingBar) findViewById(R.id.ratingBar1);
        rating.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        rating.setRating((float) 2.5);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
        rating.setIsIndicator(false);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                tv01.setText("평점 : " + rating);
            }
        });
    }
**/
    /**
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, parsing.class);
        startActivity(intent);
    }
    **/
    public void move(View v) {
        Intent intent = new Intent(this, parsing.class);
        intent.putExtra("name","치킨");
        startActivity(intent);
    }
    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context) {
            super(context, R.layout.listitem, values);
            this.context = context;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem,null,true);
            return rowView;
        }
    }
}
