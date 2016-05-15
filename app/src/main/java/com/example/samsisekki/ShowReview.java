package com.example.samsisekki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.samsisekki.displayingbitmaps.util.AsyncTask;
import com.example.user.menu4u.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by DAWON on 2016-01-26.
 */
public class ShowReview extends AppCompatActivity {
    String myJson ;

    private static final String TAG_RESULTS="results";
    private static final String TAG_NAME = "name";
    private static final String TAG_CONTENT = "content";

    JSONArray reviews = null;
    ArrayList<Review> reviewList;
    ListView m_ListView;
    CustomReviewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listreview);
        reviewList = new ArrayList<Review>();
        adapter = new CustomReviewAdapter(this,R.layout.listreviewitem,reviewList);

        m_ListView = (ListView) findViewById(R.id.list);
        m_ListView.setAdapter(adapter);
        getData("http://203.246.82.97:48780/select.php");

        Button button = (Button)findViewById(R.id.makereview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakingReview.class);//context, parsingtest.class
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetDataJSON g = new GetDataJSON();
        g.execute("http://203.246.82.97:48780/select.php");
    }

    class GetDataJSON extends AsyncTask<String, Void, String> {
        StringBuilder sb;
        @Override
        protected String doInBackground(String... params) {

            String uri = params[0];

            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                sb = new StringBuilder();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String json;
                while((json = bufferedReader.readLine())!= null){
                    sb.append(json + "\n");
                }

                return sb.toString().trim();

            }catch(Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result){
            myJson = result;
            try {
                JSONObject jsonObj = new JSONObject(myJson);
                reviews = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i=0;i<reviews.length();i++){
                    JSONObject c = reviews.getJSONObject(i);
                    String name = c.getString(TAG_NAME);
                    String content = c.getString(TAG_CONTENT);
                    Review review = new Review();
                    review.setName(name);
                    review.setContent(content);
                    reviewList.add(review);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getData(String url){

        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }



}

