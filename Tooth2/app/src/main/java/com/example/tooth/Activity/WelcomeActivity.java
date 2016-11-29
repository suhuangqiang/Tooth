package com.example.tooth.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tooth.Entity.Adv;
import com.example.tooth.Entity.User;
import com.example.tooth.Message.AdvMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AdvParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.DBUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetUtil;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {

    @ViewInject(R.id.welcomeRL)
    RelativeLayout rl;
    @ViewInject(R.id.welcomeBtn)
    Button btn;
    @ViewInject(R.id.welcome_img)
    ImageView imageView;
    @ViewInject(R.id.welcome_rl1)
    AutoRelativeLayout adv;
    @ViewInject(R.id.activity_welcome_tv)
    TextView tv;

    private boolean isclick = false;//判断是否点击直接进入
    private Handler handler1,handler2;
    private CountDownTimer timer;//计时器
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_welcome);
        //XUtils初始化操作
        //x.view().inject(this);
        init();
        getData();
        final long tickTime = 1000;//倒计时间隔时间
        long allTime = 5 * tickTime;//总时间

        timer = new CountDownTimer(allTime,tickTime) {
            @Override
            public void onTick(long l) {
                tv.setText(l/tickTime+"跳转");
            }

            @Override
            public void onFinish() {
                if (!isclick){
                    try {
                        Start();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void init() {
        rl.setBackgroundResource(R.drawable.start_bj);
        rl.setVisibility(View.VISIBLE);
        adv.setVisibility(View.GONE);
        btn.setText(R.string.welcomeBtn);
        btn.setVisibility(View.GONE);
        handler1 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                rl.setVisibility(View.GONE);
                adv.setVisibility(View.VISIBLE);
                timer.start();
                return false;
            }
        });

        handler1.sendEmptyMessageDelayed(0,3000);

    }

    @Event(R.id.activity_welcome_click)
    private void OnClick(View view){
        timer.onFinish();
    }


    /**
     * 跳转方法
     */
    private void Start() throws DbException {
        Log.i("welcome","Start");
        isclick = true;
        SharedPreferences sharedPreferences = getSharedPreferences(DataDictionary.SharedPreferencesName,0);
        boolean hasLogin = sharedPreferences.getBoolean("has_login",false);
        String user_id = sharedPreferences.getString("user_id","");

        if (!hasLogin){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            User user = DBUtils.getInstance().selector(User.class).where("user_id","=",user_id).findFirst();
            GlobalUtils.getInstances().setUser(user);
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getData(){
        String url = URLList.Domain + URLList.AdvList;
        AdvParamter paramter = new AdvParamter();
        paramter.setType("0");
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADV_LIST0);
    }

    public void onEvent(AdvMessage message) {
        if (message!=null && message.getCode().equals(NetCodeEnum.SUCCESS)){
            List<Adv> list = message.getData();
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    /*.setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher)*/
                    .build();
            if (list!=null && list.size()>0){
                x.image().bind(imageView,list.get(0).getIMAGEURL(),imageOptions);
            }
        }
    }
}
