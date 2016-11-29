package com.example.tooth.Activity;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.BookAdapter;
import com.example.tooth.Adapter.FragmentAdapter;
import com.example.tooth.Entity.Appointment;
import com.example.tooth.Fragment.BookFragment;
import com.example.tooth.Message.AppointmentListMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AppointmentListParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_book)
public class MyBookActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_my_book_booking)
    TextView tv_booking;
    @ViewInject(R.id.activity_my_book_booking_bottom)
    TextView bottom_booking;
    @ViewInject(R.id.activity_my_book_finish)
    TextView tv_finish;
    @ViewInject(R.id.activity_my_book_finish_bottom)
    TextView bottom_finish;
    @ViewInject(R.id.activity_mybook_vp)
    ViewPager viewPager;
    private List<Appointment> appointments_finsh;
    private List<Appointment> appointments_ing;
    private int type;
    private BookAdapter adapter;
    private String url;
    private int index = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("我的预约");
        url = URLList.Domain + URLList.MyAppointment;
        //getData(DataDictionary.AppointmentIng);
        List<Fragment> fragmentList = new ArrayList<>();
        BookFragment fragment1 = new BookFragment(DataDictionary.AppointmentIng);
        BookFragment fragment2 = new BookFragment(DataDictionary.AppointmnetFinsh);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Change(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @Event(R.id.back)
    private void OnBackClick(View ivew){
        onBack();
    }

    /**
     * 预约中
     * @param view
     */
    @Event(R.id.activity_my_book_booking_click)
    private void OnBookingClick(View view){
        Log.i("aaa","ing:"+index);
        if (index != 0){
            Change(0);
            viewPager.setCurrentItem(0);
            viewPager.setCurrentItem(0);
        }

    }

    /**
     * 已完成
     * @param view
     */
    @Event(R.id.activity_my_book_finish_click)
    private void OnFinishClick(View view){
        Log.i("aaa","finish:"+index);
        if (index != 1){
            Change(1);
            viewPager.setCurrentItem(1);
        }

    }
    private void Change(int position) {
        index = position;
        if (position == 0){
            bottom_booking.setVisibility(View.VISIBLE);
            bottom_finish.setVisibility(View.GONE);
            tv_booking.setTextColor(Color.parseColor("#3191E8"));
            tv_finish.setTextColor(Color.parseColor("#343434"));

        }else {
            bottom_booking.setVisibility(View.GONE);
            bottom_finish.setVisibility(View.VISIBLE);
            tv_finish.setTextColor(Color.parseColor("#3191E8"));
            tv_booking.setTextColor(Color.parseColor("#343434"));
        }
    }


}
