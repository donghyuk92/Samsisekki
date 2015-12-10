package com.example.samsisekki.nmap;

import android.os.Bundle;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class nmap extends NMapActivity {

    NMapView mMapView; //지도 화면 생성 객체
    NMapController mMapController; //지도 상태 컨트롤 객체
    OnMapStateChangeListener onMapViewStateChangeListener; //지도 상태 변경 콜백 인터페이스
    OnMapViewTouchEventListener onMapViewTouchEventListener; //지도 터치 이벤트 콜백 인터페이스

    //지도 위 오버레이 객체 드로잉에 필요한 리소스 데이터 제공 클래스
    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    //오버레이 객체 관리 클래스
    NMapOverlayManager mOverlayManager;
    //POI 아이템 선택 상태 변경 시 호출퇴는 콜백 인터페이스
    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapView = new NMapView(this); //지도 화면 생성

        mMapView.setApiKey("495063213f4ab42c5b03f12040c020cc");//API 키 설정

        mMapView.setClickable(true); //지도 화면 초기화

        //지도 상태 변경 시 호출되는 콜백 인터페이스 설정
        mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);

        //지도에서 터치 이벤트 처리 후 호출되는 콜백 인터페이스 설정
        mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);

        mMapController = mMapView.getMapController(); //지도 컨트롤러(줌 인/아웃 등) 사용

        mMapView.setBuiltInZoomControls(true, null); // 줌 인/아웃 버튼 생성

        //지도 중심좌표 및 축적 레벨 설정
        mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);

        //맵뷰 모드 설정(일반지도, 위성지도)
        mMapController.setMapViewMode(NMapView.VIEW_MODE_VECTOR);//일반지도

        //mMapController.setMapViewMode(NMapView.VIEW_MODE_HYBRID);//위성지도

        setContentView(mMapView); //화면에 지도 표시

        // create resource provider
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

// create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);


        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        testOverlayMaker();

    }
    private void testOverlayMaker() { //오버레이 아이템 추가 함수
        int markerId = NMapPOIflagType.PIN; //마커 id설정
// POI 아이템 관리 클래스 생성(전체 아이템 수, NMapResourceProvider 상속 클래스)
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2); // POI 아이템 추가 시작
        poiData.addPOIitem(new NGeoPoint(126.978371, 37.5666091), "공릉맛집!", markerId, 0);
        poiData.endPOIdata(); // POI 아이템 추가 종료
//POI data overlay 객체 생성(여러 개의 오버레이 아이템을 포함할 수 있는 오버레이 클래스)
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0); //모든 POI 데이터를 화면에 표시(zomLevel)
//POI 아이템이 선택 상태 변경 시 호출되는 콜백 인터페이스 설정
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

    }
}
