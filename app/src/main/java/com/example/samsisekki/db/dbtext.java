package com.example.samsisekki.db;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsisekki.nmap.nmap;
import com.example.user.menu4u.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class dbtext extends Activity {
    private static final String IP = "117.17.188.146";
    ImageView imView;
    TextView txtView;
    Bitmap bmImg;
    //back task;
    phpDown task;
    ArrayList<ListItem> listItem= new ArrayList<ListItem>();
    ListItem Item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);
        task = new phpDown();
        txtView = (TextView)findViewById(R.id.txt);

        //imView = (ImageView) findViewById(R.id.imageView1);

        task.execute("http://"+IP+"/donghyuk/index.php");





    }

    private class phpDown extends AsyncTask<String, Integer,String> {


        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();

        }

        protected void onPostExecute(String str) {

            String menu;
            String rating;
            String etc;
            try {

                JSONObject jObject = new JSONObject(str);
                JSONArray results = jObject.getJSONArray("results");
                String zz = "";
                zz += "Status : " + jObject.get("status");
                zz += "\n";
                zz += "Number of results : " + jObject.get("num_result");
                zz += "\n";
                zz += "Results : \n";

                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    zz += "\tdoc_idx : " + temp.get("doc_idx");
                    zz += "\tmember_idx : " + temp.get("member_idx");
                    zz += "\tsubject : " + temp.get("subject");
                    zz += "\tcontent : " + temp.get("content");
                    zz += "\treg_date : " + temp.get("reg_date");
                    zz += "\n\t--------------------------------------------\n";
                }
                txtView.setText(zz);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**JSONArray ja = root.getJSONArray("results");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    menu = jo.getString("menu");
                    rating = jo.getString("rating");
                    etc = jo.getString("etc");
                    listItem.add(new ListItem(menu, rating, etc));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //txtView.setText("menu :" + listItem.get(0) + "\nrating :" + listItem.get(0).getData(1) + "\netc :" + listItem.get(0).getData(2));
            txtView.setText(str);**/
        }
    }

    public void onClick(View v) {
        Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, nmap.class);
        startActivity(intent);
    }

}