package com.example.tooth.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.tooth.Activity.EncyclopediasActivity;
import com.example.tooth.Activity.ExerciseActivity;
import com.example.tooth.Activity.FamilyArchivesActivity;
import com.example.tooth.Activity.FriendsActivity;
import com.example.tooth.Activity.WebActivity;
import com.example.tooth.Adapter.ViewPagerAdapter;
import com.example.tooth.Entity.Adv;
import com.example.tooth.Entity.Login;
import com.example.tooth.Message.AdvMessage;
import com.example.tooth.Message.LocationMessage;
import com.example.tooth.Parameter.AdvParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.citylist.CityList;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.LogRecord;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    private final String TAG = "HomeFragment";
    @ViewInject(R.id.viewpage)
    ViewPager banner;
    @ViewInject(R.id.banner2)
    ImageView banner1;
    @ViewInject(R.id.banner3)
    ImageView banner2;
    @ViewInject(R.id.banner4)
    ImageView banner3;
    @ViewInject(R.id.fragment_home_city)
    TextView tv_city;
    @ViewInject(R.id.fragment_home_points)
    AutoLinearLayout points;

    private int currentIndex = 0,currentPage = 0;
    private int[] imgs;
    private ImageView[] dots;
    private ArrayList views;
    private Handler handler;
    private List<Adv> advList;
    private List<Adv> advList1;
    private int dir = -1;
    private ImageView[] banners;
    private SuggestionSearch mSuggestionSearch;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void getData(){
        String url = URLList.Domain + URLList.AdvList;
        AdvParamter paramter = new AdvParamter();
        paramter.setType("1");
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADV_LIST1);
    }

    private void init(){
        banners = new ImageView[]{banner1,banner2,banner3};
        getData();
        getRecomData();
    }

    /**
     * 获取牙友推荐列表
     */
    private void getRecomData(){
        String url = URLList.Domain + URLList.AdvList;
        AdvParamter paramter = new AdvParamter();
        paramter.setType("2");
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADVLIST_3);
    }

    private void initViewPage() {

        imgs = new int[]{R.mipmap.banner,R.mipmap.banner_2,R.mipmap.ask_banner};//banner图片
        //dots = new ImageView[]{point1,point2,point3};//banner下标原点
        views = new ArrayList();
        int num = advList.size();
        dots = new ImageView[num];
        for (int i=0;i<num;i++){
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(25,25);
            imageView.setLayoutParams(mParams);
            imageView.setPadding(5,0,5,0);
            imageView.setImageResource(R.mipmap.round_hui);
            dots[i] = imageView;
            points.addView(imageView);
        }
        dots[0].setImageResource(R.mipmap.round_lan);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i=0;i<num;i++){
            ImageView iv = new ImageView(getContext());
            iv.setLayoutParams(mParams);
            //iv.setImageResource(imgs[i]);
            iv.setBackgroundResource(imgs[i]);
            final int index = i%num;
            x.image().bind(iv,advList.get(index).getIMAGEURL());
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("URL",advList.get(index).getLINKURL());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            views.add(iv);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        banner.setAdapter(adapter);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurDot(position);
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setCurView(0);
        setCurDot(0);
        handler = new Handler();
        handler.postDelayed(new TimerRunnable(),3000);

    }

    /**
     * 积分兑换
     * @param view
     */
    @Event(R.id.points_exchange)
    private void OnExChangeClick(View view){
        Log.i(TAG,"积分兑换");
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL",GlobalUtils.getInstances().getUser().getGoodUrl());
        bundle.putInt(DataDictionary.TYPE,2);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 最新活动
     */
    @Event(R.id.latest_activities)
    private void OnActivitiesClick(View view){
        Log.i(TAG,"最新活动");
        Intent intent = new Intent(getActivity(), ExerciseActivity.class);
        startActivity(intent);
    }

    /**
     * 牙友圈
     * @param view
     */
    @Event(R.id.friends)
    private void OnFriendsClick(View view){
        Log.i(TAG,"牙友圈");
        Intent intent = new Intent(getActivity(), FriendsActivity.class);
        startActivity(intent);
    }
    /**
     * 口腔百科
     * @param view
     */
    @Event(R.id.encyclopedias)
    private void OnEncyclopediasClick(View view){
        Log.i(TAG,"口腔百科");
        Intent intent = new Intent(getActivity(), EncyclopediasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,ModeEnum.CONSULT_LIST_0);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 口腔常识
     * @param view
     */
    @Event(R.id.common_sense)
    private void OnCommonSenseClic(View view){
        Log.i(TAG,"口腔常识");
        Intent intent = new Intent(getActivity(), EncyclopediasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,ModeEnum.CONSULT_LIST_1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 家人档案
     * @param view
     */
    @Event(R.id.family)
    private void OnFamilyClick(View view){
        Log.i(TAG,"家人档案");
        Intent intent = new Intent(getActivity(), FamilyArchivesActivity.class);
        startActivity(intent);
    }

    /**
     * 选择城市
     * @param view
     */
    @Event(R.id.fragment_home_city)
    private void OnCityClick(View view){
        Intent intent = new Intent(getActivity(), CityList.class);
        startActivity(intent);
    }


    public void onEvent(AdvMessage message) {
        Log.i("aaa","onEvent");
        if (message == null){
            Toast.makeText(getContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.ADV_LIST1){
            advList = message.getData();
            initViewPage();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.ADVLIST_3){
            advList1 = message.getData();
            initRecomment();
        }
    }

    /**
     * 初始化推荐
     */
    private void initRecomment(){
        ImageView[] banners = new ImageView[]{banner1,banner2,banner3};

        for (int i=0;i<banners.length;i++){
            if (advList1.get(i) != null){
                banners[i].setVisibility(View.VISIBLE);
                x.image().bind(banners[i],advList1.get(i).getIMAGEURL());
                final String url = advList1.get(i).getLINKURL();
                banners[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("URL",url);
                        bundle.putInt(DataDictionary.TYPE,3);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }else {
                banners[i].setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置当前的引导页
     * @param position
     */
    private void setCurView(int position){
        if (position < 0 || position >= views.size()){
            return;
        }
        banner.setCurrentItem(position);
    }
    private void setCurDot(int position){
        if (position < 0 || position > imgs.length - 1 || currentIndex == position){
            return;
        }
        dots[position].setImageResource(R.mipmap.round_lan);
        dots[currentIndex].setImageResource(R.mipmap.round_hui);
        currentIndex = position;
    }

    class TimerRunnable implements Runnable{

        @Override
        public void run() {
            int max = dots.length - 1;
            if (currentPage == max || currentPage == 0){
                dir = -dir;
            }
            currentPage += dir;
            setCurView(currentPage);
            //int i = currentPage%3;
            setCurDot(currentPage);
            if (handler!=null){
                handler.postDelayed(this,3000);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null;
    }

    /**
     * 选择城市后返回
     * @param message
     */
    public void onEvent(LocationMessage message) {
        tv_city.setText(message.getCity());
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                    mSuggestionSearch.destroy();
                    return;
                    //未找到相关结果
                }
                //获取在线建议检索结果
                Log.i("baidu","size:"+suggestionResult.getAllSuggestions().size());
               for (int i=0;i<suggestionResult.getAllSuggestions().size();i++){
                   SuggestionResult.SuggestionInfo info = suggestionResult.getAllSuggestions().get(i);
                   Log.i("baidu","district:"+info.district);
                   Log.i("baidu","city:"+info.city);
                   Log.i("baidu","pt:"+info.pt);
                   if (info.pt!=null){
                       Log.i("baidu","longitude:" + info.pt.longitude);
                       Log.i("baidu","latitude:" + info.pt.latitude);
                       if (GlobalUtils.getInstances().getBdLocation() == null){
                           GlobalUtils.getInstances().setBdLocation(new BDLocation());
                       }
                       GlobalUtils.getInstances().getBdLocation().setLatitude(info.pt.latitude);
                       GlobalUtils.getInstances().getBdLocation().setLongitude(info.pt.longitude);
                   }
               }
            }
        });
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword("市政府")
                .city(message.getCity()));
    }
}
