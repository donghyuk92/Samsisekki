package com.example.samsisekki.db;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsisekki.DeviceUuidFactory;
import com.example.samsisekki.algorithm.entity.Vector;
import com.example.samsisekki.algorithm.util.VectorUtil;
import com.example.user.menu4u.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class dbimage extends Activity {
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";

    private static final String IP = "117.17.188.146";

    ImageView imView;
    String imgUrl = "http://"+IP+"/donghyuk/img/";
    Bitmap bmImg;
    back task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);

        float[] a1= new float[6];
        a1[0] = (float) 2.9;
        a1[1] = (float) 3.3;
        a1[2] = (float) 3.9;
        Vector vc = new Vector(a1);

        float[] a2= new float[6];
        a2[0] = (float) 2.9;
        a2[1] = (float) 9.3;
        a2[2] = (float) 3.9;
        a2[3] = (float) 12.9;
        a2[5] = (float) 52.9;
        Vector vc1 = new Vector(a2);

        float[] a3= new float[6];
        a3[0] = (float) 2.9;
        a3[1] = (float) 19.3;
        a3[2] = (float) 3.9;
        a3[5] = (float) 3.9;
        Vector vc2 = new Vector(a3);

        float[] a4= new float[6];
        a4[0] = (float) 9.9;
        a4[1] = (float) 3.3;
        a4[2] = (float) 3.9;
        a4[3] = (float) 5.9;
        a4[4] = (float) 9.9;
        Vector vc3 = new Vector(a4);

        ArrayList<Vector> tmp = new ArrayList<Vector>();
        tmp.add(vc);
        tmp.add(vc1);
        tmp.add(vc2);
        tmp.add(vc3);

        int k = 0;
        int index1 = VectorUtil.getRecommended(tmp, k);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.txt);

        textView.setText(Integer.toString(index1));


        //Device ID 생성, sharedpreferences 에 있으면
        SharedPreferences pref = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        String deviceID = pref.getString(PREFS_DEVICE_ID,"NULL");

        if(deviceID=="NULL") {
            DeviceUuidFactory devicefac = new DeviceUuidFactory(this);
            textView2.setText(devicefac.getDeviceID());
        }
        textView2.setText(deviceID);



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