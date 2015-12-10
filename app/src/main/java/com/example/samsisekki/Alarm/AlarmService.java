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
		   Intent dialogIntent = new Intent(getBaseContext(), AlarmingActivity.class);
		   dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   getApplication().startActivity(dialogIntent);
	   }

       nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

       Intent notificationIntent = new Intent(this, MainActivity.class);
       PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
       Notification notification = new Notification.Builder(getBaseContext())
               .setSmallIcon(R.drawable.icon_alarm)
               .setTicker("Let's go to eat!")
               .setContentTitle("Alarm")
               .setContentText("GoGo! Eating!")
               .setContentIntent(pendingIntent)
               .setAutoCancel(true)
               .build();

       notification.flags = notification.flags | notification.FLAG_AUTO_CANCEL;
       nm.notify(1234, notification);

       startForeground(startId,notification);

       return START_STICKY;
    }

    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}