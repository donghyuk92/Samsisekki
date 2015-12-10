package com.example.samsisekki.parsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samsisekki.nmap.nmap;
import com.example.user.menu4u.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class parsing extends Activity {

        EditText edit;
        TextView text;

        XmlPullParser xpp;
        String key="6f6a369937b4c71383c38adaff8955f2"; //Naver 개발자센터 검색 키

        String data;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.parsing);

            edit= (EditText)findViewById(R.id.edit);
            text= (TextView)findViewById(R.id.text);

            Intent intent = getIntent();
            String name = intent.getStringExtra("name");

            edit.setText(name);

            Button button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    change();
                }
            });


        }

    private void change() {
        Intent i = new Intent(this, nmap.class);
        startActivity(i);
    }

    //Button을 클릭했을 때 자동으로 호출되는 callback method....
        public void mOnClick(View v){

            switch( v.getId() ){
                case R.id.button:

                    //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            data= getXmlData(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                            //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                            //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    text.setText(data);  //TextView에 문자열  data 출력
                                }
                            });

                        }
                    }).start();

                    break;
            }

        }//mOnClick method..


        //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
        String getXmlData(){

            StringBuffer buffer=new StringBuffer();

            String str= edit.getText().toString(); //EditText에 작성된 Text얻어오기
            String location = URLEncoder.encode(str); //한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..

            String queryUrl="http://openapi.naver.com/search"   //요청 URL
                    +"?key="+key                        //key 값
                    +"&target=local"                     //검색서비스 api명세
                    +"&query="+location                 //지역검색 요청값
                    +"&display=10"                      //검색 출력 건수  10~100
                    +"&start=1";                         //검색 시작 위치  1~1000

            try {
                URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
                InputStream is= url.openStream();  //url위치로 입력스트림 연결

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser xpp= factory.newPullParser();
                xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

                String tag;

                xpp.next();
                int eventType= xpp.getEventType();

                while( eventType != XmlPullParser.END_DOCUMENT ){
                    switch( eventType ){
                        case XmlPullParser.START_DOCUMENT:
                            buffer.append("start NAVER XML parsing...\n\n");
                            break;

                        case XmlPullParser.START_TAG:
                            tag= xpp.getName();    //테그 이름 얻어오기

                            if(tag.equals("item")) ;// 첫번째 검색결과
                            else if(tag.equals("title")){
                                buffer.append("업소명 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("category")){
                                buffer.append("업종 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("description")){
                                buffer.append("세부설명 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("telephone")){
                                buffer.append("연락처 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("address")){
                                buffer.append("주소 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("mapx")){
                                buffer.append("지도 위치 X :");
                                xpp.next();
                                buffer.append(xpp.getText()); //mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("  ,  ");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("mapy")){
                                buffer.append("지도 위치 Y :");
                                xpp.next();
                                buffer.append(xpp.getText()); //mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tag= xpp.getName();    //테그 이름 얻어오기

                            if(tag.equals("item")) buffer.append("\n"); // 첫번째 검색결과종료..줄바꿈

                            break;
                    }

                    eventType= xpp.next();
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            buffer.append("end NAVER XML parsing...\n");

            return buffer.toString(); //StringBuffer 문자열 객체 반환

        }//getXmlData method....

    }//MainActivity class..