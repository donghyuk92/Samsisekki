package com.example.samsisekki.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 2015-11-02.
 */
public class Receiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Intent service = new Intent(context, AlarmService.class);
            context.startService(service);
        }
    }

