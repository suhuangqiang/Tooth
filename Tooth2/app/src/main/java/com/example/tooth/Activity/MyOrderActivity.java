package com.example.tooth.Activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.FragmentAdapter;
import com.example.tooth.Adapter.OrderAdapter;
import com.example.tooth.Entity.Order;
import com.example.tooth.Fragment.OrderFragment;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.OrderListMessage;
import com.example.tooth.Parameter.OrderListParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_order)
public class MyOrderActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_my_order_all)
    TextView tv_all_bottom;
    @ViewInject(R.id.activity_my_order_not_deliver)
    TextView tv_not_deliver_bottom;
    @ViewInject(R.id.activity_my_order_not_pay)
    TextView tv_not_pay_bottom;
    @ViewInject(R.id.activity_my_order_not_receipt)
    TextView tv_not_receipt_bottom;
    @ViewInject(R.id.activity_my_order_successful_trade)
    TextView tv_successful_trade_bottom;
    @ViewInject(R.id.activity_my_order_close_trade)
    TextView tv_close_trade_bottom;
    @ViewInject(R.id.activity_my_order_all_tv)
    TextView tv_all;
    @ViewInject(R.id.activity_my_order_not_deliver_tv)
    TextView tv_not_deliver;
    @ViewInject(R.id.activity_my_order_not_pay_tv)
    TextView tv_not_pay;
    @ViewInject(R.id.activity_my_order_not_receipt_tv)
    TextView tv_not_receipt;
    @ViewInject(R.id.activity_my_order_successful_trade_tv)
    TextView tv_successful_trade;
    @ViewInject(R.id.activity_my_order_close_trade_tv)
    TextView tv_close_trade;
    /*@ViewInject(R.id.activity_my_order_lv)
    ListView lv;*/
    @ViewInject(R.id.activity_my_order_viewpager)
    ViewPager viewPager;

    private TextView[] bottoms;//选中下滑线
    private TextView[] tvs;//选中文本
    private int index = 0;//当前选择下标
    private int state = 0,state0 = 0,state1 = 1,state2 = 2,state3 = 3,state4 = 4;
    private List<Order> orders_all,orders0,orders1,orders2,orders3,orders4;
    private List<Order>[] orders;
    private OrderAdapter adapter;
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("我的订单");
        bottoms = new TextView[]{tv_all_bottom,tv_not_pay_bottom,tv_not_deliver_bottom,tv_not_receipt_bottom,tv_successful_trade_bottom,tv_close_trade_bottom};
        tvs = new TextView[]{tv_all,tv_not_pay,tv_not_deliver,tv_not_receipt,tv_successful_trade,tv_close_trade};

        orders = new List[]{orders_all,orders0,orders1,orders2,orders3,orders4};
        url = URLList.Domain + URLList.OrderList;
        getData(0);

        List<Fragment> fragmentList = new ArrayList<>();
        OrderFragment fragment = new OrderFragment(ModeEnum.ORDER_LIST);
        OrderFragment fragment1 = new OrderFragment(ModeEnum.ORDER_LIST_1);
        OrderFragment fragment2 = new OrderFragment(ModeEnum.ORDER_LIST_2);
        OrderFragment fragment3 = new OrderFragment(ModeEnum.ORDER_LIST_3);
        OrderFragment fragment4 = new OrderFragment(ModeEnum.ORDER_LIST_4);
        OrderFragment fragment5 = new OrderFragment(ModeEnum.ORDER_LIST_5);
        fragmentList.add(fragment);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
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
     * 全部
     * @param view
     */
    @Event(R.id.activity_my_order_all_click)
    private void OnAllClick(View view){
        ShowBottom(0);
        getData(0);
    }
    /**
     * 待付款
     * @param view
     */
    @Event(R.id.activity_my_order_not_pay_click)
    private void OnPayClick(View view){
        ShowBottom(1);
        getData(1);
    }
    /**
     * 待发货
     * @param view
     */
    @Event(R.id.activity_my_order_not_deliver_click)
    private void OnDeliverClick(View view){
        ShowBottom(2);
        getData(2);
    }
    /**
     * 待收货
     * @param view
     */
    @Event(R.id.activity_my_order_not_receipt_click)
    private void OnReceiptClick(View view){
        ShowBottom(3);
        getData(3);
    }
    /**
     * 交易成功
     * @param view
     */
    @Event(R.id.activity_my_order_successful_trade_click)
    private void OnSuccessClick(View view){
        ShowBottom(4);
        getData(4);
    }
    /**
     * 交易关闭
     * @param view
     */
    @Event(R.id.activity_my_order_close_trade_click)
    private void OnCloseClick(View view){
        ShowBottom(5);
        getData(5);
    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 设置选中样式改变
     * @param i
     */
    private void ShowBottom(int i){
        bottoms[index].setVisibility(View.GONE);
        tvs[index].setTextColor(Color.parseColor("#343434"));
        bottoms[i].setVisibility(View.VISIBLE);
        tvs[i].setTextColor(Color.parseColor("#3191E8"));
        index = i;
    }

    private void getData(int s){
        viewPager.setCurrentItem(s);
    }


}
