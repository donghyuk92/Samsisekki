package com.example.samsisekki.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.menu4u.R;

import java.util.ArrayList;

public class AlarmFragment extends Fragment implements OnClickListener{


	private Button btnCreate;
	private Button btnDelete;
	private ListView lvAlarm;
	private AlarmAdapter adapter;

	private ArrayList<AlarmList> arGroupAlarm = new ArrayList<AlarmList>();
	private AlarmDB db;

	public static AlarmFragment newInstance(){
		AlarmFragment fragment = new AlarmFragment();
		return fragment;
	}


	public AlarmFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.alarm_layout, container, false);

		btnCreate = (Button)v.findViewById(R.id.btnCreate);
		btnDelete = (Button)v.findViewById(R.id.btnDelete);

		btnCreate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);

		return v;
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// Init listview
		db = new AlarmDB(getActivity().getApplicationContext());
		arGroupAlarm = requestAlarmList();

		lvAlarm = (ListView)getActivity().findViewById(R.id.lvAlarm);
		adapter = new AlarmAdapter(getActivity().getApplicationContext());
		lvAlarm.setAdapter(adapter);
	}

	public ArrayList<AlarmList> requestAlarmList() {
		ArrayList<AlarmList> arGroupAlarm = new ArrayList<AlarmList>();
		ArrayList<Alarm> arAlarm = db.query();

		int mgc = 0;

		for(int i = 0 ; i < arAlarm.size(); i++) {
			if(mgc < arAlarm.get(i).getGc()) {
				mgc = arAlarm.get(i).getGc();
			}
		}

		for(int i = 0 ; i < mgc; i++) {
			arGroupAlarm.add(new AlarmList(db.queryByGroup(i+1), false));
		}

		return arGroupAlarm;
	}
/*
 * Adapter of list
 */
public class AlarmAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;

	public AlarmAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return arGroupAlarm.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		View rowView = mInflater.inflate(R.layout.alarm_item, parent, false);

		TextView txtTime = (TextView) rowView.findViewById(R.id.txtTime);
		CheckBox cbDelete = (CheckBox) rowView.findViewById(R.id.cbDelete);
		Button btn1 = (Button) rowView.findViewById(R.id.btn1);
		Button btn2 = (Button) rowView.findViewById(R.id.btn2);
		Button btn3 = (Button) rowView.findViewById(R.id.btn3);
		Button btn4 = (Button) rowView.findViewById(R.id.btn4);
		Button btn5 = (Button) rowView.findViewById(R.id.btn5);
		Button btn6 = (Button) rowView.findViewById(R.id.btn6);
		Button btn7 = (Button) rowView.findViewById(R.id.btn7);

		txtTime.setText(arGroupAlarm.get(pos).getArDate().get(0).getTime());

		cbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					arGroupAlarm.get(pos).setDeleted(true);
				} else {
					arGroupAlarm.get(pos).setDeleted(false);
				}
			}
		});

		for (int i = 0; i < arGroupAlarm.get(pos).getArDate().size(); i++) {
			switch (arGroupAlarm.get(pos).getArDate().get(i).getWeekday()) {
				case 0:
					btn1.setBackgroundResource(R.drawable.day_bg_selected);
					break;
				case 1:
					btn2.setBackgroundResource(R.drawable.day_bg_selected);
					break;
				case 2:
					btn3.setBackgroundResource(R.drawable.day_bg_selected);
					break;
				case 3:
					btn4.setBackgroundResource(R.drawable.day_bg_selected);
					break;
				case 4:
					btn5.setBackgroundResource(R.drawable.day_bg_selected);
					break;
				case 5:
					btn6.setBackgroundResource(R.drawable.day_bg_selected);
					break;
				case 6:
					btn7.setBackgroundResource(R.drawable.day_bg_selected);
					break;
			}
		}
		return rowView;
	}
}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnCreate:
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), AlarmSettingActivity.class);
				startActivity(intent);
				break;
			case R.id.btnDelete:
				for (int i = 0; i < arGroupAlarm.size(); i++) {
					if (arGroupAlarm.get(i).isDeleted()) {
						db.DELETE_GROUP(arGroupAlarm.get(i).getArDate().get(0).getGc());

						for (int j = 0; j < arGroupAlarm.get(i).getArDate().size(); j++) {
							Intent intent2 = new Intent(getActivity().getBaseContext(), Receiver.class);
							PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(), arGroupAlarm.get(i).getArDate().get(j).getId(), intent2, 0);
							AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
							alarmManager.cancel(pendingIntent);
						}
					}
				}

				arGroupAlarm = requestAlarmList();

				// Notify refresh listview
				adapter.notifyDataSetChanged();

				break;
			}
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onStart() {
		Log.d(this.getClass().getSimpleName(), "onStart()");
		super.onStart();
	}

	@Override
	public void onPause() {
		Log.d(this.getClass().getSimpleName(), "onPause()");
		super.onPause();
	}




	@Override
	public void onStop() {
		Log.d(this.getClass().getSimpleName(), "onStop()");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.d(this.getClass().getSimpleName(), "onDestroyView()");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.d(this.getClass().getSimpleName(), "onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d(this.getClass().getSimpleName(), "onDetach()");
		super.onDetach();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(this.getClass().getSimpleName(), "onSaveInstanceState()");
		super.onSaveInstanceState(outState);
	}

}
