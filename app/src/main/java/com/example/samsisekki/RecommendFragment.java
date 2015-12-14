package com.example.samsisekki;

import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.samsisekki.Alarm.Receiver;
import com.example.user.menu4u.R;

public class RecommendFragment extends Fragment {

    private MediaPlayer mPlayer;

    public static RecommendFragment newInstance(){
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    public RecommendFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.alarm_alarming, container, false);

        PendingIntent pendingIntent;

        Intent myIntent = new Intent(getActivity(), Receiver.class);

        myIntent.putExtra("requestCode",  getActivity().getIntent().getIntExtra("requestCode", 0));

        pendingIntent = PendingIntent.getBroadcast(getActivity(), getActivity().getIntent().getIntExtra("requestCode", 0), myIntent,0);

        long next = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000);

        Toast.makeText(getActivity(), DateFormat.format("yyyy.MM.dd hh:mm:ss aa", next).toString(), Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, next, pendingIntent);


        // Play media
        mPlayer = MediaPlayer.create(getActivity(), R.raw.alarm_01);
        mPlayer.setLooping(true);
        mPlayer.start();

        Button button = (Button) v.findViewById(R.id.btnCancel);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop media and request next alarm in next week (same as setrepeat)
                mPlayer.stop();
                getActivity().finish();
            }
        });
        return v;
    }
}
