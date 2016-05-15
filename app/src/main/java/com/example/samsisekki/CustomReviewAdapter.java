package com.example.samsisekki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.menu4u.R;

import java.util.ArrayList;

/**
 * Created by DAWON on 2016-01-26.
 */
public class CustomReviewAdapter extends BaseAdapter {
    Context main;
    LayoutInflater inflater;
    ArrayList<Review> arraylist;
    int layout;

    public CustomReviewAdapter(Context context, int layout, ArrayList<Review> arraylist) {

        main = context;
        this.arraylist = arraylist;
        this.layout = layout;

    }
    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
             inflater = (LayoutInflater) main
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listreviewitem, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView contents = (TextView)convertView.findViewById(R.id.contents);

        name.setText(arraylist.get(position).getName() + ":");
        contents.setText(arraylist.get(position).getContent());
        return convertView;
    }
    }

