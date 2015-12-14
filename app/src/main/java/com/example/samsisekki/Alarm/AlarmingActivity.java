package com.example.samsisekki.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.menu4u.R;

public class AlarmingActivity extends Activity{

    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_alarming);

        PendingIntent pendingIntent;

        Intent myIntent = new Intent(AlarmingActivity.this, Receiver.class);
        myIntent.putExtra("requestCode",  getIntent().getIntExtra("requestCode", 0));

        pendingIntent = PendingIntent.getBroadcast(AlarmingActivity.this, getIntent().getIntExtra("requestCode", 0), myIntent,0);

        long next = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000);

        Toast.makeText(getApplicationContext(), DateFormat.format("yyyy.MM.dd hh:mm:ss aa", next).toString(), Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, next, pendingIntent);


        // Play media
        mPlayer = MediaPlayer.create(AlarmingActivity.this, R.raw.alarm_01);
        mPlayer.setLooping(true);
        mPlayer.start();

        Button button = (Button)findViewById(R.id.btnCancel);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Stop media and request next alarm in next week (same as setrepeat)
                mPlayer.stop();
                finish();
            }
        });
    }

}