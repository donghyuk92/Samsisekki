package com.example.samsisekki.Alarm;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.example.samsisekki.MainActivity;
import com.example.user.menu4u.R;


public class AlarmService extends Service {
    private NotificationManager nm = null;

     @Override
     public IBinder onBind(Intent arg0)
     {
       // TODO Auto-generated method stub
        return null;
     }

    @Override  
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }

   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
   @Override
   public int onStartCommand(Intent intent, int flags, int startId)
   {
	   if(intent != null) {
		   Intent dialogIntent = new Intent(getBaseContext(), MainActivity.class);
		   dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   getApplication().startActivity(dialogIntent);
	   }

       return START_STICKY;
    }

    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}