package com.example.samsisekki;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.samsisekki.dbtest.DBController;
import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.user.menu4u.R;

import java.util.ArrayList;


public class RankingFragment extends Fragment {

    SQLiteDatabase database;
    private ListView m_ListView;
    private CustomAdapter   m_Adapter;
    String deviceID;
    DBController db;

    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.ranklist_layout, container, false);

        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter(getActivity());

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) rootView.findViewById(R.id.ranklist);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);
        DeviceUuidFactory dev = new DeviceUuidFactory(getContext());
        deviceID = dev.getDeviceID();

        ArrayList<String> menu = new ArrayList<String>();
        //getRank();
        Cursor result = db.getRank();
        while(!result.isAfterLast()){
            String tmp = result.getString(0);
            int index;
            for(int i=0;i<Images.menu.length;i++) {
                if (Images.menu[i].equals(tmp)) {
                    index = i;
                    m_Adapter.add(Images.menu2[i] + " 평점 : " + result.getString(1));
                    break;
                }
            }
            m_Adapter.addurl(result.getString(2));
            menu.add(result.getString(0));
            result.moveToNext();
        }
        result.close();

        for(int i=0; i<menu.size(); i++) {
            Cursor result2 = db.getRate(deviceID, menu.get(i));
            if (!result2.isAfterLast())
                m_Adapter.addrating(result2.getString(0));
            else
                m_Adapter.addrating("Null");
            result2.close();

        }
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