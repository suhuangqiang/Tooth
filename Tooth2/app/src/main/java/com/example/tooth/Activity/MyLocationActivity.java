package com.example.tooth.Activity;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.tooth.Adapter.BookTypeAdapter;
import com.example.tooth.R;
import com.example.tooth.Utils.BaiduUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.widget.DataDictionary;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContentView(R.layout.activity_my_location)
public class MyLocationActivity extends BaseActivity {

    @ViewInject(R.id.activity_my_location_finish)
    TextView finish;
    @ViewInject(R.id.activity_my_location_lv)
    ListView lv;
    @ViewInject(R.id.activity_my_location_mapview)
    MapView mapView;

    private BaiduMap mBaiduMap;
    private BookTypeAdapter adapter;
    private int index = 0;
    private String address = "";
    private BDLocation bdlocation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        StartLocation();

    }

    @Event(R.id.activity_my_location_search)
    private void OnSearchClicl(View view){
        Intent intent = new Intent(this,SearchAddressActivity.class);
        Bundle bundle = new Bundle();
        BDLocation location = GlobalUtils.getInstances().getBdLocation();
        if (bdlocation!=null){
            bundle.putDouble("Latitude",bdlocation.getLatitude());
            bundle.putDouble("Longitude",bdlocation.getLongitude());
        }else if (location != null){
            bundle.putDouble("Latitude",location.getLatitude());
            bundle.putDouble("Longitude",location.getLongitude());
        }
        intent.putExtras(bundle);
        startActivityForResult(intent,9);
    }
    /**
     * 返回
     * @param view
     */
    @Event(R.id.activity_my_location_back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 完成
     * @param view
     */
    @Event(R.id.activity_my_location_finish)
    private void OnFinishClick(View view){
        String s = bdlocation.getAddrStr();
        s += address;
        Back(s);
    }

    /**
     * 启动百度地位
     */
    private void StartLocation(){
        BaiduUtils.getInstance(this).setOnBDLocationBack(new BaiduUtils.OnBDLocationBack() {
            @Override
            public void CallBack(BDLocation location) {
                Log.i("baidu","定位完成");
                Log.i("baidu",location.getAddrStr());
                Log.i("baidu",location.getCity());
                Log.i("baidu","Latitude:"+location.getLatitude());
                Log.i("baidu","Longitude:"+location.getLongitude());
                List<Poi> list = location.getPoiList();
                Log.i("baidu","poi size:"+list.size());

                bdlocation = location;

                //定义Maker坐标点
                LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.dizi);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));

                final List<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("TEXT",location.getAddrStr());
                map.put(DataDictionary.TYPE,"1");
                list1.add(map);
                for (int i=0;i<list.size();i++){
                    Poi poi = list.get(i);
                    Log.i("baidu","poi name:"+poi.getName());
                    Log.i("baidu"," poi :"+poi.getId());
                    Log.i("baidu"," poi :"+poi.getRank());
                    HashMap<String,String> map1 = new HashMap<String, String>();
                    map1.put("TEXT",poi.getName());
                    map1.put(DataDictionary.TYPE,"0");
                    list1.add(map1);
                }
                adapter = new BookTypeAdapter(getBaseContext(),list1);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        list1.get(i).put(DataDictionary.TYPE,"1");
                        address = list1.get(i).get("TEXT");
                        list1.get(index).put(DataDictionary.TYPE,"0");
                        index = i;
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        BaiduUtils.getInstance(this).Start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9){
            String addr = data.getExtras().getString("addr");
            Back(addr);
        }

    }

    private void Back(String addr){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("addr",addr);
        intent.putExtras(bundle);
        this.setResult(9,intent);
        finish();
    }
}
