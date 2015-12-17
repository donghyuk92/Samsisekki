package com.example.samsisekki;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samsisekki.dbtest.DBController;
import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.user.menu4u.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HistoryFragment extends Fragment {

    SQLiteDatabase database;
    private ListView    m_ListView;
    private CustomAdapter   m_Adapter;
    String deviceID;
    DBController db;

    public static HistoryFragment newInstance(){
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }


    public HistoryFragment() {
        // Required empty public constructor
    }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            final View rootView = inflater.inflate(R.layout.history_layout,container, false);


            // 커스텀 어댑터 생성
            m_Adapter = new CustomAdapter(getActivity());

            // Xml에서 추가한 ListView 연결
            m_ListView = (ListView) rootView.findViewById(R.id.historylist);

            // ListView에 어댑터 연결
            m_ListView.setAdapter(m_Adapter);
            DeviceUuidFactory dev = new DeviceUuidFactory(getContext());
            deviceID = dev.getDeviceID();

            //getHist();
            Cursor result = db.getHist(deviceID);
            while(!result.isAfterLast()){
                String tmp = result.getString(0);
                int index;
                for(int i=0;i< Images.menu.length;i++) {
                    if (Images.menu[i].equals(tmp)) {
                        index = i;
                        m_Adapter.add(Images.menu2[i]);
                        break;
                    }
                }
                m_Adapter.addurl(result.getString(1));
                m_Adapter.addrating(result.getString(2));
                result.moveToNext();
            }
            result.close();

            m_ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                Toast.makeText(getActivity(), "삭제?", Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(rootView.getContext());
                alertDlg.setTitle("삭제?");
                alertDlg.setPositiveButton("넵", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String position = Integer.toString(which);
                        final String sql = "delete from test where deviceID='"+ deviceID+"' and menu='"+deviceID+"';";
                        dialog.dismiss();
                        database.execSQL(sql);
                    }
                });


                alertDlg.setNegativeButton( "No!" , new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                     /**
                        String position = arr_id_list.get(selectedPos);
                        dialog.dismiss();
                        Log.i("test", "1");
                        Intent intent = new Intent(second.this, updatedb.class);
                        intent.putExtra("p_id", position);
                        Log.i("test", "2");
                        startActivity(intent);
                      **/
                    }
                });

                alertDlg.setMessage("안지웠어요!");
                alertDlg.show();
                return false;
            }
        });
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


    /**
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Integer selectedPos = i;
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
        alertDlg.setTitle(R.string.alert_title_question);
        Log.i("test", "1");
        alertDlg.setPositiveButton( R.string.button_yes, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String position = arr_id_list.get(selectedPos);
                final String sql = "delete from test_table where id = "+ position;
                dialog.dismiss();
                Log.i("test", "onclick");
                database.execSQL(sql);
            }
        });

        alertDlg.setNegativeButton( R.string.button_no, new DialogInterface.OnClickListener(){

            @Override
            public void onClick( DialogInterface dialog, int which ) {
                String position = arr_id_list.get(selectedPos);
                dialog.dismiss();
                Log.i("test", "1");
                Intent intent = new Intent(second.this, updatedb.class);
                intent.putExtra("p_id", position);
                Log.i("test", "2");
                startActivity(intent);
            }
        });

        alertDlg.setMessage(R.string.alert_msg_delete);
        alertDlg.show();
        return false;

    }**/
}