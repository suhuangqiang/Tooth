package com.example.tooth.Activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tooth.Adapter.FragmentAdapter;
import com.example.tooth.Fragment.ProblemFragment;
import com.example.tooth.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_problem)
public class MyProblemActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_my_problem_answered_tv)
    TextView tv_answered;
    @ViewInject(R.id.activity_my_problem_no_answered_tv)
    TextView tv_no_answered;
    @ViewInject(R.id.activity_my_problem_answered_bottom)
    TextView bottom_answered;
    @ViewInject(R.id.activity_my_problem_no_answered_bottom)
    TextView bottom_no_answered;
    @ViewInject(R.id.activity_my_problem_viewpage)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        ProblemFragment fragment0 = new ProblemFragment(0);
        ProblemFragment fragment1 = new ProblemFragment(1);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragment0);
        fragmentList.add(fragment1);
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
     * 点击 已回答
     * @param view
     */
    @Event(R.id.activity_my_problem_answered_click)
    private void OnAnseredClick(View view){
        ShowBottom(0);
    }

    /**
     * 点击 未回答
     * @param view
     */
    @Event(R.id.activity_my_problem_no_answered_click)
    private void OnNoClick(View view){
        ShowBottom(1);
    }

    private void ShowBottom(int position){
        if (position == 0){
            tv_answered.setTextColor(Color.parseColor("#3191E8"));
            tv_no_answered.setTextColor(Color.parseColor("#343434"));
            bottom_answered.setVisibility(View.VISIBLE);
            bottom_no_answered.setVisibility(View.GONE);
        }else {
            tv_answered.setTextColor(Color.parseColor("#343434"));
            tv_no_answered.setTextColor(Color.parseColor("#3191E8"));
            bottom_answered.setVisibility(View.GONE);
            bottom_no_answered.setVisibility(View.VISIBLE);
        }
        viewPager.setCurrentItem(position);
    }
}
