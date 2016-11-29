package com.example.tooth.Activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tooth.Adapter.FragmentAdapter;
import com.example.tooth.Fragment.CollectShopFragment;
import com.example.tooth.Fragment.ConsultationFragment;
import com.example.tooth.Fragment.HospitalFragment;
import com.example.tooth.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_collection)
public class CollectionActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_collection_department)
    TextView tv_department;
    @ViewInject(R.id.activity_collection_info)
    TextView tv_info;
    @ViewInject(R.id.activity_collection_shop)
    TextView tv_shop;
    @ViewInject(R.id.activity_collection_department_bottom)
    TextView bottom_department;
    @ViewInject(R.id.activity_collection_info_bottom)
    TextView bottom_info;
    @ViewInject(R.id.activity_collection_shop_bottom)
    TextView bottom_shop;
    @ViewInject(R.id.activity_collection_viewpager)
    ViewPager viewPager;



    private TextView[] bottoms,tvs;
    private int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("我的收藏");
        bottoms = new TextView[]{bottom_department,bottom_info,bottom_shop};
        tvs = new TextView[]{tv_department,tv_info,tv_shop};

        List<Fragment> fragmentList = new ArrayList<>();
        HospitalFragment hospitalFragment = new HospitalFragment();
        fragmentList.add(hospitalFragment);
        ConsultationFragment consultationFragment = new ConsultationFragment();
        fragmentList.add(consultationFragment);
        CollectShopFragment collectShopFragment = new CollectShopFragment();
        fragmentList.add(collectShopFragment);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ShowBottom(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 牙科诊所
     * @param view
     */
    @Event(R.id.activity_collection_department_click)
    private void OnDepClick(View view){
        ShowBottom(0);
    }

    /**
     * 口腔资讯
     * @param view
     */
    @Event(R.id.activity_collection_info_click)
    private void OnInfoClick(View view){
        ShowBottom(1);
    }

    /**
     * 商品
     * @param view
     */
    @Event(R.id.activity_collection_shop_click)
    private void OnShopClick(View view){
        ShowBottom(2);
    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }
    private void ShowBottom(int i){
        viewPager.setCurrentItem(i);
        tvs[index].setTextColor(Color.parseColor("#343434"));
        tvs[i].setTextColor(Color.parseColor("#3191E8"));
        bottoms[index].setVisibility(View.GONE);
        bottoms[i].setVisibility(View.VISIBLE);
        index = i;
    }
}
