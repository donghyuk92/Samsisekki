package com.example.samsisekki;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsisekki.dbtest.dbinsert;
import com.example.user.menu4u.R;


/**
 * Created by DAWON on 2016-01-18.
 */
public class MakingReview extends AppCompatActivity{
    EditText name;
    EditText content;
    String deviceID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewitem);
       deviceID =  new DeviceUuidFactory(getApplicationContext()).getDeviceID();
        name = (EditText) findViewById(R.id.editTextName);
        content = (EditText) findViewById(R.id.editTextContent);

        Button upbutton = (Button) this.findViewById(R.id.reviewupload);
        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbinsert db = new dbinsert(getApplicationContext());
                db.dbinsert(deviceID, name.getText().toString(), content.getText().toString());
                Toast.makeText(getApplicationContext(),"등록완료",Toast.LENGTH_SHORT);
                finish();
            }
        });

    }
}
