package com.example.samsisekki.db;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsisekki.algorithm.entity.Vector;
import com.example.user.menu4u.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class dbimage extends Activity {

    private static final String IP = "117.17.188.146";

    ImageView imView;
    String imgUrl = "http://"+IP+"/donghyuk/img/";
    Bitmap bmImg;
    back task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);
        float[] asd= new float[3];
        asd[0] = (float) 2.9;
        asd[1] = (float) 99.3;
        asd[2] = (float) 3.9;
        Vector vc = new Vector(asd);
        float[] qwe= new float[3];
        qwe[0] = (float) 99.9;
        qwe[1] = (float) 2.3;
        qwe[2] = (float) 3.9;
        Vector vc1 = new Vector(qwe);
        float tmp = vc.distanceTo(vc1);
        TextView textView = (TextView) findViewById(R.id.textView);
        String k = Float.toString(tmp);
        textView.setText(k);



        task = new back();

        imView = (ImageView) findViewById(R.id.imageView1);

        task.execute(imgUrl + "search.jpg");


    }


    private class back extends AsyncTask<String, Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img){
            imView.setImageBitmap(bmImg);
        }

    }
}