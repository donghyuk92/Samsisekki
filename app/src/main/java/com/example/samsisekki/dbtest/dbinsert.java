package com.example.samsisekki.dbtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 2015-11-16.
 */
public class dbinsert extends AppCompatActivity {
    private static final String IP = "117.17.188.146";
    phpInsert task_insert = new phpInsert();
    Context context;

    public dbinsert(Context context) {
        this.context = context;
    }

    public void dbinsert(String deviceID, String clas, String menu, Float rating, String url) {
        String query = "http://" + IP + "/donghyuk/insert.php?deviceID=" + deviceID + "&class=" + clas + "&menu=" + menu + "&rating=" + rating + "&url=" + url;
        Log.d("TAG", query);
        task_insert.execute(query);
    }

    private class phpInsert extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... string) {
            try {
                // 연결 url 설정
                URL url = new URL(string[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        conn.connect();
                    }
                    conn.disconnect();
                } else return "0";
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = mPref.edit();
            int check = mPref.getInt("number", 0);

            if (check < 10) {
                check++;
                editor.putInt("number", check);
                editor.commit();
            } else if (check==10) {
                check++;
            }
            Log.d("TAG", " " + check);
            return "1";
        }

        @Override
        protected void onPostExecute(String str) {
            if(str=="1")
                Toast.makeText(context.getApplicationContext(), "취향이 반영됬습니다! 감사합니다", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context.getApplicationContext(), "취향이 반영안됬습니다.\n네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
            super.onPostExecute(str);
        }

    }
}