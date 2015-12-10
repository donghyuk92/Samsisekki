package com.example.samsisekki.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.menu4u.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmSettingActivity extends Activity {

	// Global mutable variables
	private PendingIntent pendingIntent;
	
	private Button btn_HourUp;
	private Button btn_HourDown;
	private Button btn_MinuteUp;
	private Button btn_MinuteDown;
	
	private TextView txtTime;
	
	private ArrayList<Button> arDate = new ArrayList<Button>();
	private boolean[] arSelectedDate = new boolean[7];
	
	private Button btnSave;
	private Button btnCancel;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_setting);
		
		initTimeControl();
		
		initDateControl();
		
		initButtonControl();
		
	}
	
	public void initTimeControl() {
		txtTime = (TextView)findViewById(R.id.txtTime);
		Time time = new Time();
		time.setToNow();
		txtTime.setText(String.format("%02d : %02d", time.hour, time.minute));
		
		btn_HourUp = (Button)findViewById(R.id.btn_HourUp);
		btn_HourUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtTime.setText(updateTime(1));
			}
		});
		btn_HourDown = (Button)findViewById(R.id.btn_HourDown);
		btn_HourDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtTime.setText(updateTime(2));
			}
		});
		btn_MinuteUp = (Button)findViewById(R.id.btn_MinuteUp);
		btn_MinuteUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtTime.setText(updateTime(3));
			}
		});
		btn_MinuteDown = (Button)findViewById(R.id.btn_MinuteDown);
		btn_MinuteDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtTime.setText(updateTime(4));
			}
		});
	}
	
	public void initDateControl() {
		for(int i = 0 ; i < 7; i++) {
			
			Button button = (Button)findViewById(getResources().getIdentifier("btn" + (i + 1), "id", getPackageName()));
			
			final int pos = i;
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!arSelectedDate[pos]) {
						arSelectedDate[pos] = true;
						arDate.get(pos).setBackgroundResource(R.drawable.day_bg_selected);
						int paddingPixel = 5;
						float density = getResources().getDisplayMetrics().density;
						int paddingDp = (int)(paddingPixel * density);
						
						arDate.get(pos).setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
					} else {
						arSelectedDate[pos] = false;
						arDate.get(pos).setBackgroundResource(R.drawable.day_bg);
						int paddingPixel = 5;
						float density = getResources().getDisplayMetrics().density;
						int paddingDp = (int)(paddingPixel * density);
						
						arDate.get(pos).setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
					}
				}
			});
			arDate.add(button);
		}
	}
	
	public void initButtonControl() {
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlarmDB db = new AlarmDB(getApplicationContext());
				ArrayList<Alarm> arData = db.query();
				
				int gc = 0;
				
				for(int i = 0 ; i < arData.size(); i++) {
					if(arData.get(i).getGc() > gc) {
						gc = arData.get(i).getGc();
					}
				}
				
				for(int i = 0 ; i < arSelectedDate.length; i++) {
					if(arSelectedDate[i]) {
						db.INSERT(gc+1, txtTime.getText().toString(), i);
						ArrayList<Alarm> arAlarm = db.query();
						
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(System.currentTimeMillis());
						switch (i) {
							case 0:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

								break;
								
							case 1:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

								break;
								
							case 2:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

								break;
								
							case 3:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
								
								break;
								
							case 4:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

								break;
								
							case 5:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
								
								break;
								
							case 6:
								calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
								
								break;  
							default:
								break;
						}
						
						calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtTime.getText().toString().split(" : ")[0]));
						calendar.set(Calendar.MINUTE, Integer.parseInt(txtTime.getText().toString().split(" : ")[1]));
						
						if(calendar.getTimeInMillis() + 10 < System.currentTimeMillis()) {
				        	calendar.add(Calendar.DATE, 7);
				        }
						
						Intent myIntent = new Intent(AlarmSettingActivity.this, Receiver.class);
						pendingIntent = PendingIntent.getBroadcast(AlarmSettingActivity.this, arAlarm.get(arAlarm.size()-1).getId(), myIntent,0);
						
					    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
					    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
					    
					}
				}
				
				finish();
				
			}
		});
		
		// Init button cancel, click cancel to close setting page
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
				
	}
	
	public String updateTime(int type) {
		
		String currentTime = txtTime.getText().toString();
		
		int hour   = Integer.parseInt(currentTime.split(" : ")[0]);
		int minute = Integer.parseInt(currentTime.split(" : ")[1]);
		
		switch (type) {
		case 1:
			if(hour < 24) {
				hour+=1;
			} else {
				hour = 0;
			}
			
			break;
		case 2:
			
			if(hour > 0) {
				hour-=1;
			} else {
				hour = 23;
			}
			
			break;
			
		case 3:
			
			if(minute < 59) {
				minute += 1;
			} else {
				minute = 0;
			}
			break;
			
		case 4:
			
			if(minute > 0) {
				minute -= 1;
			} else {
				minute = 59;
			}
			break;
		default:
			break;
		}
		
		return String.format("%02d : %02d", hour, minute);
		
	}
}
