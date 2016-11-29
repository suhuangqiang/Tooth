package com.example.tooth.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_account_bind)
public class AccountBindActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.account_bind_username)
    EditText et_phone;
    @ViewInject(R.id.account_bind_code)
    EditText et_code;
    @ViewInject(R.id.account_bind_type)
    TextView tv_type;
    @ViewInject(R.id.account_bind_nickname)
    TextView tv_nickname;
    @ViewInject(R.id.account_bind_getcode)
    Button btn_code;
    @ViewInject(R.id.account_bind_phone_number)
    TextView tv_phone;
    @ViewInject(R.id.account_bind_1)
    AutoLinearLayout AL1;
    @ViewInject(R.id.account_bind_2)
    AutoRelativeLayout AR2;

    private final String TAG = "ACCOUNT_BIND";
    CountDownTimer timer;//计时器

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    public void init(){
        title.setText(R.string.account_bind_title);//设置标题

        Bundle bundle = this.getIntent().getExtras();
        int login_type = bundle.getInt("login_type");
        if (login_type == 0){
            tv_type.setText(R.string.weixin_user);
        }else if (login_type == 1){
            tv_type.setText(R.string.qq_user);
        }
    }

    /**
     * 返回事件
     * @param view
     */
    @Event(R.id.back)
    private void Back(View view){
        onBack();
    }
    @Event(R.id.account_bind_getcode)
    private void getCode(View view){
        String phoneStr = et_phone.getText().toString();
        if (phoneStr==null || phoneStr.equals("")){
            //输入手机号码错误
            Toast.makeText(getBaseContext(),R.string.input_phone_number_error,Toast.LENGTH_SHORT).show();
        }else {
            //手机号码输入正确，获取验证码
            btn_code.setClickable(false);//设置获取验证码按钮不可点击
            final long tickTime = 1000;//倒计时间隔时间
            long allTime = 60 * tickTime;//总时间
            timer = new CountDownTimer(allTime,tickTime) {
                @Override
                public void onTick(long l) {
                    btn_code.setText(l/tickTime+"s后重新发送");
                }

                @Override
                public void onFinish() {
                    //倒计时完成
                    btn_code.setText(R.string.get_code);
                    btn_code.setClickable(true);
                }
            };
            timer.start();
        }
    }
    /**
     * 点击确认
     */
    @Event(R.id.account_bind_sure)
    private void Sure(View view){
        String phoneStr = et_phone.getText().toString();
        String codeStr = et_code.getText().toString();

        if (phoneStr==null || phoneStr.equals("")){
            Toast.makeText(getBaseContext(),R.string.input_phone_number_error,Toast.LENGTH_SHORT).show();
            return;
        }
        if (codeStr==null || codeStr.equals("")){
            Toast.makeText(getBaseContext(),"请检查验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        //点击确认按钮
        Log.i(TAG,"点击确认");


        AL1.setVisibility(View.GONE);
        AR2.setVisibility(View.VISIBLE);
        tv_phone.setText("用户手机号码");
    }

    @Event(R.id.account_bind_login)
    private void GoToLogin(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
