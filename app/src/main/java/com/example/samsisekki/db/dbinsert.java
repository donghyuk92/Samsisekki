package com.example.samsisekki.db;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 2015-11-16.
 */
public class dbinsert extends Activity {
    private static final String IP = "117.17.188.146";
    phpInsert task_insert = new phpInsert();

    public void insert(String deviceID, String clas, String menu, Float rating) {
        String query = "http://"+IP+"/donghyuk/insert.php?deviceID="+deviceID+"&class="+clas+"&menu="+menu+"&rating="+rating;
        task_insert.execute(query);

    }

    private class phpInsert extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... string) {
            try{
                // 연결 url 설정
                URL url = new URL(string[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        conn.connect();
                    }
                    conn.disconnect();
                } else return "0";

            } catch(Exception ex){
                ex.printStackTrace();
            }
            return "1";

        }
        /**
        protected void onPostExecute(String str){
            if(str.equals("1")){
                Toast.makeText(getCurrentFocus().getContext(), "평가가 반영됬습니다!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getCurrentFocus().getContext(),"평가가 반영되지 않았습니다. 인터넷 연결 상태를 확인해주세요!",Toast.LENGTH_LONG).show();
            }
        }
         **/
    }
}
