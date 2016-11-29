package com.example.tooth.Activity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.R;
import com.example.tooth.Utils.BaiduUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_map_view)
public class MapViewActivity extends BaseActivity {

    @ViewInject(R.id.activity_map_view_map)
    MapView mapView;
    @ViewInject(R.id.title)
    TextView title;

    private BaiduMap mBaiduMap;

    /**
     * 最新一次的经纬度
     */
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    /**
     * 当前的精度
     */
    private float mCurrentAccracy;

    /**
     * 方向传感器X方向的值
     */
    private int mXDirection;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    private BitmapDescriptor mIconMaker;
    /**
     * 详细信息的 布局
     */

    private List<Hospital> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("地图");
        list = (List<Hospital>) this.getIntent().getExtras().getSerializable("hospitals");
        Log.i("baidu","list:"+list.size());
        mBaiduMap = mapView.getMap();
        mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        BaiduUtils.setOnBDLocationBack(new BaiduUtils.OnBDLocationBack() {
            @Override
            public void CallBack(BDLocation location) {
                mBaiduMap.clear();
                LatLng latLng = null;
                OverlayOptions overlayOptions = null;
                Marker marker = null;
                for (Hospital info : list)
                {
                    // 位置
                    latLng = new LatLng(info.getLatitude(), info.getLongitude());
                    // 图标
                    overlayOptions = new MarkerOptions().position(latLng)
                            .icon(mIconMaker).zIndex(5);
                    marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info", info);
                    marker.setExtraInfo(bundle);
                }
                // 将地图移到到最后一个经纬度位置
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.setMapStatus(u);
            }
        });
        BaiduUtils.getInstance(getBaseContext()).Start();
        initMapClickEvent();
        initMarkerClickEvent();
    }


    private void initMarkerClickEvent()
    {
        // 对Marker的点击
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(final Marker marker)
            {
                // 获得marker中的数据
                Hospital info = (Hospital) marker.getExtraInfo().get("info");

                InfoWindow mInfoWindow;
                // 生成一个TextView用户在地图中显示InfoWindow
                TextView location = new TextView(getApplicationContext());
                location.setBackgroundResource(R.drawable.location_tips);
                location.setPadding(30, 20, 30, 50);
                location.setText(info.getCC_NAME());
                // 将marker所在的经纬度的信息转化成屏幕上的坐标
                final LatLng ll = marker.getPosition();
                Point p = mBaiduMap.getProjection().toScreenLocation(ll);
                p.y -= 47;
                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
                // 为弹出的InfoWindow添加点击事件

                OnInfoWindowClickListener s = new OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {

                    }
                };
                mInfoWindow = new InfoWindow(location, llInfo,0);
                // 显示InfoWindow
                mBaiduMap.showInfoWindow(mInfoWindow);
                return true;
            }
        });
    }

    private void initMapClickEvent()
    {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener()
        {

            @Override
            public boolean onMapPoiClick(MapPoi arg0)
            {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0)
            {
                mBaiduMap.hideInfoWindow();

            }
        });
    }

    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }


}
