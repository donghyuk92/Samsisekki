package com.example.samsisekki.parsing;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.samsisekki.nmap.nmap;
import com.example.user.menu4u.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by User on 2015-12-17.
 */
public class parsingtest extends Service {

    XmlPullParser xpp;
    String key = "6f6a369937b4c71383c38adaff8955f2"; //Naver 개발자센터 검색 키
    String data;
    String menu;
    int index;

    ArrayList<String> locationx = new ArrayList<String>();
    ArrayList<String> locationy = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    Intent i;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        menu = intent.getStringExtra("menu");
        String[] tmp = menu.split(" ");
        menu = tmp[0];

        for (int i = 0; i < Images.menu.length; i++) {
            if (Images.menu[i].equals(menu)) {
                index = i;
            }
        }

        i = new Intent(this, nmap.class);

        parsing();

        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void parsing() {
        //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
        new Thread(new Runnable() {
            @Override
            public void run() {
                getXmlData(); //아래 메소드를 호출하여 XML data를 파싱

                i.putStringArrayListExtra("locationx", locationx);
                i.putStringArrayListExtra("locationy", locationy);
                i.putStringArrayListExtra("title", title);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        }).start();
    }

    private void getXmlData() {

        StringBuffer buffer = new StringBuffer();

        String str = Images.menu2[index];
        String location = URLEncoder.encode(str); //한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        Log.d("TAG", str + " " + location);
        String queryUrl = "http://openapi.naver.com/search"   //요청 URL
                + "?key=" + key                        //key 값
                + "&target=local"                     //검색서비스 api명세
                + "&query=" + location                 //지역검색 요청값
                + "&display=10"                      //검색 출력 건수  10~100
                + "&start=1";                         //검색 시작 위치  1~1000

        try {
            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("start NAVER XML parsing...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("title")) {
                            buffer.append("업소명 : ");
                            xpp.next();
                            buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                            String tmp = xpp.getText().replaceAll("<b>", "");
                            tmp = tmp.replaceAll("</b>", "");
                            tmp = tmp.replaceAll("&amp;", "&");
                            title.add(tmp);
                        } else if (tag.equals("category")) {
                            buffer.append("업종 : ");
                            xpp.next();
                            buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } else if (tag.equals("description")) {
                            buffer.append("세부설명 :");
                            xpp.next();
                            buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } else if (tag.equals("telephone")) {
                            buffer.append("연락처 :");
                            xpp.next();
                            buffer.append(xpp.getText()); //telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } else if (tag.equals("address")) {
                            buffer.append("주소 :");
                            xpp.next();
                            buffer.append(xpp.getText()); //address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } else if (tag.equals("mapx")) {
                            buffer.append("지도 위치 X :");
                            xpp.next();
                            buffer.append(xpp.getText()); //mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("  ,  ");          //줄바꿈 문자 추가
                            locationx.add(xpp.getText());
                        } else if (tag.equals("mapy")) {
                            buffer.append("지도 위치 Y :");
                            xpp.next();
                            buffer.append(xpp.getText()); //mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                            locationy.add(xpp.getText());
                            Log.d("TAG", xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (tag.equals("item")) buffer.append("\n"); // 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        buffer.append("end NAVER XML parsing...\n");
        //return buffer.toString(); //StringBuffer 문자열 객체 반환
        Log.d("TAG", buffer.toString());
    }//getXmlData method....
}
