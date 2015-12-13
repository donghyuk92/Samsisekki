package com.example.samsisekki;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.menu4u.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class History extends Fragment {

    ArrayList<String> arrlist = null;
    ArrayList<String> arr_id_list = null;
    SQLiteDatabase database;
    String dbName = "test_db_name";
    private ListView    m_ListView;
    private CustomAdapter   m_Adapter;


    public static History newInstance(){
        History fragment = new History();
        return fragment;
    }


    public History() {
        // Required empty public constructor
    }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            final View rootView = inflater.inflate(R.layout.history_layout,container, false);


            // 커스텀 어댑터 생성
            m_Adapter = new CustomAdapter();

            // Xml에서 추가한 ListView 연결
            m_ListView = (ListView) rootView.findViewById(R.id.historylist);

            // ListView에 어댑터 연결
            m_ListView.setAdapter(m_Adapter);

            // ListView에 아이템 추가
            m_Adapter.add("하스스톤");
            m_Adapter.add("몬스터 헌터");
            m_Adapter.add("디아블로");
            m_Adapter.add("와우");
            m_Adapter.add("리니지");
            m_Adapter.add("안드로이드");
            m_Adapter.add("아이폰");            m_Adapter.add("하스스톤");
            m_Adapter.add("몬스터 헌터");
            m_Adapter.add("디아블로");
            m_Adapter.add("와우");
            m_Adapter.add("리니지");
            m_Adapter.add("안드로이드");
            m_Adapter.add("아이폰");            m_Adapter.add("하스스톤");
            m_Adapter.add("몬스터 헌터");
            m_Adapter.add("디아블로");
            m_Adapter.add("와우");
            m_Adapter.add("리니지");
            m_Adapter.add("안드로이드");
            m_Adapter.add("아이폰");

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
                    //    String position = get(selectedPos);
                    //    final String sql = "delete from test_table where id = "+ position;
                        dialog.dismiss();
                    //    database.execSQL(sql);
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
    public void selectData(){
        String sql = "select * from test_table";
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        while(!result.isAfterLast()){
            arr_id_list.add(result.getString(0));
            arrlist.add(result.getString(1));
            result.moveToNext();
        }
        result.close();
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