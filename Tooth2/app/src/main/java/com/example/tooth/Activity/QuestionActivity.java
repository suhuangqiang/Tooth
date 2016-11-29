package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.QuestionAdapter;
import com.example.tooth.Entity.Question;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.QuestionMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.PageParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_question)
public class QuestionActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_question_lv)
    ListView lv;
    @ViewInject(R.id.activity_question_item)
    AutoRelativeLayout item;
    //跳转类型  0 问卷调查  1 我的问卷
    private int type;
    private QuestionAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("问卷调查");
        type = this.getIntent().getExtras().getInt("TYPE");
        if (type == 0){
            title.setText("问卷调查");
            item.setVisibility(View.VISIBLE);
        }else {
            title.setText("我的问卷");
            item.setVisibility(View.GONE);
        }
        getData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Question question = (Question) adapter.getItem(i);
                Intent intent = new Intent(getBaseContext(),WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("URL",question.getDetailUrl());
                bundle.putInt(DataDictionary.TYPE,3);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    private void getData(){
        String url = URLList.Domain;
        if (type == 0){
            url += URLList.QuestionList;
        }else {
            url += URLList.MyQuestion;
        }

        PageParamter pageParamter = new PageParamter();
        pageParamter.setPageIndex(1);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(pageParamter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.QUESTION_LIST);
    }


    public void onEvent(QuestionMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){

            adapter = new QuestionAdapter(getBaseContext(),message.getData());
            lv.setAdapter(adapter);
        }else {
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }
    }
}
