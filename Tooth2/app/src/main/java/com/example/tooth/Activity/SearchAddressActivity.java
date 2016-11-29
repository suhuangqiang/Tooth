package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.tooth.Adapter.AddressSearchAdapter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContentView(R.layout.activity_search_address)
public class SearchAddressActivity extends BaseActivity {

    @ViewInject(R.id.activity_search_address_mapview)
    MapView mapView;
    @ViewInject(R.id.activity_search_address_et)
    EditText et;
    @ViewInject(R.id.activity_search_address_lv)
    ListView lv;

    private int index = 0;
    private PoiSearch mPoiSearch;
    private List<HashMap<String,String>> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                List<PoiInfo> poiInfos = poiResult.getAllPoi();
                mapView.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                if (poiInfos == null){
                    Toast.makeText(getBaseContext(),"查找不到结果",Toast.LENGTH_SHORT).show();
                }else {
                    list = new ArrayList<HashMap<String, String>>();
                    for (int i=0;i<poiInfos.size();i++){
                        Log.i("baidu",poiInfos.get(i).city+"---"+poiInfos.get(i).address);
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("addr",poiInfos.get(i).address);
                        map.put("keyword",poiInfos.get(i).name);
                        list.add(map);
                    }
                    AddressSearchAdapter adapter = new AddressSearchAdapter(getBaseContext(),list);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /**
                 * 输入框内容改变，执行查询
                 */
                BDLocation bdLocation = GlobalUtils.getInstances().getBdLocation();
                mPoiSearch.searchInCity((new PoiCitySearchOption().city(bdLocation.getCity()).keyword(charSequence.toString())));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * 初始情况显示地图
         */
        Double la = getIntent().getExtras().getDouble("Latitude");
        Double lo = getIntent().getExtras().getDouble("Longitude");
        BaiduMap mBaiduMap = mapView.getMap();
        if (la != null && lo != null){
            //定义Maker坐标点
            LatLng point = new LatLng(la,lo);
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
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("addr",list.get(i).get("addr"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(9,intent);
                finish();
            }
        });

    }

    /**
     * 取消
     * @param view
     */
    @Event(R.id.activity_search_address_cancel)
    private void OnCancelClick(View view){

    }

    /**
     * 清空输入框
     * @param view
     */
    @Event(R.id.activity_search_address_del)
    private void OnDelClick(View view){
        et.setText("");
    }


}
