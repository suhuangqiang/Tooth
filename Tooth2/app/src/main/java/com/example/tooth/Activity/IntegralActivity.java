package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.IntegralAdapter;
import com.example.tooth.Entity.Score;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.ScoreMessage;
import com.example.tooth.Parameter.DoctorListParameter;
import com.example.tooth.Parameter.HospitalParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_integral)
public class IntegralActivity extends BaseActivity {
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.activity_integral_lv)
    private ListView lv;
    @ViewInject(R.id.activity_integral_no_data)
    private AutoRelativeLayout no_data;
    @ViewInject(R.id.activity_integral_integral)
    private TextView integral;

    private IntegralAdapter adapter;
    private List<Score> scoreList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("积分明细");

        integral.setText(GlobalUtils.getInstances().getUser().getINTEGRAL_NUM());
        getData();

    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 积分攻略
     * @param view
     */
    @Event(R.id.activity_integral_raiders)
    private void OnRaidersClick(View view){
        Intent intent = new Intent(this,RaidersActivity.class);
        startActivity(intent);
    }
    /**
     * 积分商城
     * @param view
     */
    @Event(R.id.activity_integral_shop)
    private void OnShopClick(View view){}

    /**
     * 获取积分明细列表的数据
     */
    private void getData(){
        String url = URLList.Domain + URLList.ScoreDetail;
        DoctorListParameter parameter = new DoctorListParameter();
        parameter.setPageIndex(1);
        BaseMessage message = new BaseMessage();
        message.setData(parameter);
        message.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,message, ModeEnum.SCORE_DETAIL);
    }

    /**
     * 数据接收处理
     * @param message
     */
    public void onEvent(ScoreMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            scoreList = message.getData();
            if (scoreList.size()>0){
                no_data.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                adapter = new IntegralAdapter(getBaseContext(),scoreList);
                lv.setAdapter(adapter);
            }else {
                no_data.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
            }

        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
