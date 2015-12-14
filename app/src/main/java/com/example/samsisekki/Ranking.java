package com.example.samsisekki;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.samsisekki.db.dbinsert;
import com.example.samsisekki.dbtest.DBController;
import com.example.user.menu4u.R;


public class Ranking extends Fragment {

    SQLiteDatabase database;
    private ListView m_ListView;
    private CustomAdapter   m_Adapter;
    String deviceID;
    DBController db;

    public static Ranking newInstance() {
        Ranking fragment = new Ranking();
        return fragment;
    }

    public Ranking() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), dbinsert.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.ranklist_layout, container, false);

        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) rootView.findViewById(R.id.ranklist);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);
        DeviceUuidFactory dev = new DeviceUuidFactory(getContext());
        deviceID = dev.getDeviceID();

        //getHist();
        Cursor result = db.getRank();
        while(!result.isAfterLast()){
            m_Adapter.add(result.getString(0));
            m_Adapter.add(result.getString(1));
            result.moveToNext();
        }
        result.close();

        return rootView;
    }

    @Override
    public void onStart() {
        Log.d(this.getClass().getSimpleName(), "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(this.getClass().getSimpleName(), "onResume()");
        super.onResume();
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
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        db = new DBController(activity);
    }

}