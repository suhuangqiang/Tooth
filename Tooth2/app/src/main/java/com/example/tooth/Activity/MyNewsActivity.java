package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_news)
public class MyNewsActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_my_news_book_news)
    ImageView img_book;
    @ViewInject(R.id.activity_my_news_order_news)
    ImageView img_order;
    @ViewInject(R.id.activity_my_news_sys_news)
    ImageView img_sys;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("我的消息");
    }

    /**
     * 预约消息
     * @param view
     */
    @Event(R.id.activity_my_news_booknews_click)
    private void OnBookClick(View view){
        Intent intent = new Intent(this,BookNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,0);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 订单消息
     * @param view
     */
    @Event(R.id.activity_my_news_ordernews_click)
    private void OnOrderClick(View view){
        Intent intent = new Intent(this,BookNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 系统消息
     * @param view
     */
    @Event(R.id.activity_my_news_sysnews_click)
    private void OnSysClick(View view){
        Intent intent = new Intent(this,BookNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,2);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

}
