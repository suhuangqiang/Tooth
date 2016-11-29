package com.example.tooth.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tooth.Adapter.BookNewsAdapter;
import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_book_news)
public class BookNewsActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.titlebar_tv)
    TextView clear;
    @ViewInject(R.id.activity_book_news_lv)
    ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        clear.setText("一键已读");

        Bundle bundle = this.getIntent().getExtras();
        int type = bundle.getInt(DataDictionary.TYPE);
        switch (type){
            case 0:
                //预约消息
                title.setText("预约消息");
                break;
            case 1:
                //订单消息
                title.setText("订单消息");
                break;
            case 2:
                //系统消息
                title.setText("系统消息");
                break;
            default:break;
        }

        BookNewsAdapter adapter = new BookNewsAdapter(getBaseContext());
        lv.setAdapter(adapter);
    }

    /**
     * 一键已读
     * @param view
     */
    @Event(R.id.titlebar_tv)
    private void OnClearClick(View view){}

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

}
