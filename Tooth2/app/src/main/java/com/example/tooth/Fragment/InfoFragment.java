package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Activity.AskActivity;
import com.example.tooth.Activity.AskListActivity;
import com.example.tooth.Activity.EncyclopediasActivity;
import com.example.tooth.Activity.ExerciseActivity;
import com.example.tooth.Activity.FriendsActivity;
import com.example.tooth.Activity.QuestionActivity;
import com.example.tooth.Activity.WebActivity;
import com.example.tooth.Adapter.ExerciseAdapter;
import com.example.tooth.Adapter.ViewPagerAdapter;
import com.example.tooth.Entity.Activity;
import com.example.tooth.Entity.Adv;
import com.example.tooth.Message.ActivityListMessage;
import com.example.tooth.Message.AdvMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AdvParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.HospitalParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.View.CommentListView;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_info)
public class InfoFragment extends BaseFragment {
    private final String TAG = "InfoFragment";
    @ViewInject(R.id.info_viewpage)
    ViewPager viewPage;
    @ViewInject(R.id.fragment_info_points)
    AutoLinearLayout points;
    @ViewInject(R.id.fragment_info_activity_lv)
    CommentListView activityLV;


    private int currentIndex = 0,currentPage = 0;
    private int[] imgs;
    private ImageView[] dots;
    private ArrayList views;
    private List<Activity> activities;
    private Handler handler;

    private List<Adv> advList;
    private int dir = -1;
    public InfoFragment() {
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

    private void init(){

        getData();
        getBannerData();
    }
    private void getBannerData(){
        String url = URLList.Domain + URLList.AdvList;
        AdvParamter paramter = new AdvParamter();
        paramter.setType("2");
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADV_LIST2);
    }

    /**
     * 横幅广告接收
     * @param message
     */
    public void onEvent(AdvMessage message) {
        if (message == null){
            Toast.makeText(getContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.ADV_LIST2){
            advList = message.getData();
            initViewPage();
        }
    }

    /**
     * 初始化viewpage
     */
    private void initViewPage() {
        imgs = new int[]{R.mipmap.banner,R.mipmap.banner_2,R.mipmap.hd_xq,R.mipmap.ask_banner};//banner图片


        int num = advList.size();
        views = new ArrayList();
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
                    bundle.putInt(DataDictionary.TYPE,3);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            views.add(iv);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        viewPage.setAdapter(adapter);
        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setCurDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setCurDot(0);
        setCurView(0);

        handler = new Handler();
        handler.postDelayed(new TimerRunnable(),3000);

    }

    /**
     * 口腔百科
     * @param view
     */
    @Event(R.id.info_encyclopedias)
    private void OnencyclopediasClick(View view){
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
    @Event(R.id.info_common_sense)
    private void OnCommonSenseClick(View view){
        Log.i(TAG,"口腔常识");
        Intent intent = new Intent(getActivity(), EncyclopediasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,ModeEnum.CONSULT_LIST_1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 牙友问问
     * @param view
     */
    @Event(R.id.info_ask)
    private void OnAskClick(View view){
        Log.i(TAG,"牙友问问");
        Intent intent = new Intent(getActivity(), AskListActivity.class);
        startActivity(intent);

    }

    /**
     * 牙友圈
     * @param view
     */
    @Event(R.id.info_friends)
    private void OnFriendsClick(View view){
        Log.i(TAG,"牙友圈");
        Intent intent = new Intent(getActivity(), FriendsActivity.class);
        startActivity(intent);
    }

    /**
     * 问卷调查
     * @param view
     */
    @Event(R.id.info_question)
    private void OnQuestionClick(View view){
        Log.i(TAG,"问卷调查");
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",0);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 大转盘
     * @param view
     */
    @Event(R.id.info_turntable)
    private void OnTurntableClick(View view){
        Log.i(TAG,"大转盘");
    }

    /**
     * 推荐活动
     * @param view
     */
    @Event(R.id.fragment_info_activity_recommend)
    private void OnRecommendClick(View view){
        Intent intent = new Intent(getActivity(), ExerciseActivity.class);
        startActivity(intent);
    }
    /**
     * 设置viewpage的点
     * @param position
     */
    private void setCurDot(int position){
        if (position < 0 || position > imgs.length - 1 || currentIndex == position){
            return;
        }
        dots[position].setImageResource(R.mipmap.round_lan);
        dots[currentIndex].setImageResource(R.mipmap.round_hui);
        currentIndex = position;
    }
    /**
     * 设置当前的引导页
     * @param position
     */
    private void setCurView(int position){
        if (position < 0 || position >= views.size()){
            return;
        }
        viewPage.setCurrentItem(position);
    }

    /**
     * 获取活动数据
     */
    private void getData(){
        String url = URLList.Domain + URLList.ActivityList;
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        HospitalParameter patameter = new HospitalParameter();
        patameter.setPageIndex(1);
        baseMessage.setData(patameter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ACTIVITY_LIST);
    }

    /**
     * 活动数据接收
     * @param message
     */
    public void onEvent(ActivityListMessage message) {
        if (message == null){
            Toast.makeText(getContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                activities = message.getData();
                ExerciseAdapter adapter = new ExerciseAdapter(getContext(),activities);
                activityLV.setAdapter(adapter);

                activityLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String url = activities.get(i).getDetailUrl();
                        String titleStr = activities.get(i).getTITLE();
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("URL",url);
                        bundle.putString("title",titleStr);
                        bundle.putInt(DataDictionary.TYPE,3);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
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
}
