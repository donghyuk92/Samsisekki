package com.example.samsisekki;

/**
 * Created by User on 2015-12-12.
 */

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsisekki.db.dbinsert;
import com.example.samsisekki.displayingbitmaps.provider.Images;
import com.example.samsisekki.displayingbitmaps.ui.RecyclingImageView;
import com.example.samsisekki.displayingbitmaps.util.ImageCache;
import com.example.samsisekki.displayingbitmaps.util.ImageFetcher;
import com.example.samsisekki.parsing.parsing;
import com.example.user.menu4u.R;

public class CustomAdapter extends BaseAdapter {

    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_List = new ArrayList<String>();
    private ArrayList<String> URL = new ArrayList<String>();
    private ArrayList<String> Rating = new ArrayList<String>();
    Context context;
    String deviceID;
    RatingBar ratingBar;
    Boolean ratingunable = false;

    CustomAdapter(Context context) {
        this.context = context;
        deviceID = new DeviceUuidFactory(context).getDeviceID();
    }

    ImageFetcher mImageFetcher;
    private static final String IMAGE_CACHE_DIR = "thumbs";

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent, false);
        }
        // TextView에 현재 position의 문자열 추가
        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        String menu = m_List.get(position);
        textView.setText(menu);
        final int index = Arrays.asList(Images.menu).indexOf(menu);

        // 버튼을 터치 했을 때 이벤트 발생
        Button btn = (Button) convertView.findViewById(R.id.move);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(context, m_List.get(pos), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, parsing.class);
                intent.putExtra("menu", m_List.get(pos));
                context.startActivity(intent);
            }
        });
        /**
         // 리스트 아이템을 터치 했을 때 이벤트 발생
         convertView.setOnClickListener(new OnClickListener() {

        @Override public void onClick(View v) {
        // 터치 시 해당 아이템 이름 출력
        Toast.makeText(context, "리스트 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
        }
        });

         // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
         convertView.setOnLongClickListener(new OnLongClickListener() {

        @Override public boolean onLongClick(View v) {
        // 터치 시 해당 아이템 이름 출력
        Toast.makeText(context, "리스트 롱 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
        return true;
        }
        });
         **/

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(context, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        int mImageThumbSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageFetcher = new ImageFetcher(context, mImageThumbSize);
        mImageFetcher.addImageCache(context, cacheParams);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        mImageFetcher.loadImage(URL.get(position), imageView);



        ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
        ratingBar.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        ratingBar.setNumStars(5);

        try {
            String rate = Rating.get(position);
            if (rate != "Null") {
                float rat = Float.parseFloat(rate);
                ratingBar.setRating(rat);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
            }
        } catch (IndexOutOfBoundsException e) {

        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser) {
                    dbinsert db = new dbinsert();
                    db.dbinsert(deviceID, "Han", Images.menu[index], rating, Images.imageThumbUrls[index]);
                }
            }
        });
        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg) {
        m_List.add(_msg);
    }

    public void addurl(String url) {
        URL.add(url);
    }

    public void addrating(String rating) {
        Rating.add(rating);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }
}
