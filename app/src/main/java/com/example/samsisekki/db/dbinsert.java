package com.example.samsisekki.db;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.menu4u.R;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 2015-11-16.
 */
public class dbinsert extends Activity {

    private static final String IP = "117.17.188.146";
    TextView txtView;
    phpInsert task_insert;
    EditText messageInput;

    Button sendID_btn ;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);
        txtView = (TextView)findViewById(R.id.txt);

        // 서버 : 전송할 메시지 입력 박스
        messageInput = (EditText) findViewById(R.id.messageInput);

        sendID_btn = (Button) findViewById(R.id.send);
        id = messageInput.getText().toString();

    }

    public void onClick2(View v)
    {
        task_insert = new phpInsert();
        id = messageInput.getText().toString();
        String query = "http://"+IP+"/donghyuk/insert.php?menu="+this.id+"&rating="+this.id+"&etc="+this.id;
        task_insert.execute(query);

        Intent intent = new Intent(this, dbtext.class);
        startActivity(intent);
    }

    private class phpInsert extends AsyncTask<String, Integer,String> {

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

        protected void onPostExecute(String str){
            if(str.equals("1")){
                Toast.makeText(getApplicationContext(), "DB Insert Complete.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"DB Insert Failed.",Toast.LENGTH_LONG).show();
            }

        }

    }

}
