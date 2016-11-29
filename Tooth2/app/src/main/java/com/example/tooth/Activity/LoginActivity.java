package com.example.tooth.Activity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tooth.Entity.Code;
import com.example.tooth.Entity.Login;
import com.example.tooth.Entity.User;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.CodeMessage;
import com.example.tooth.Message.UserMessage;
import com.example.tooth.R;
import com.example.tooth.Utils.DBUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.Utils.PromptManage;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static android.R.id.message;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    private final String TAG = "LOGIN";
    CountDownTimer timer;//计时器

    @ViewInject(R.id.login_username)
    EditText phone;
    @ViewInject(R.id.login_code)
    EditText code;
    @ViewInject(R.id.login_getcode)
    Button getCode;
    @ViewInject(R.id.activity_login_head_img)
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        if (GlobalUtils.getInstances().getUser()!=null){
            User user = GlobalUtils.getInstances().getUser();
            phone.setText(user.getPHONE());
            code.setText("666666");
            if (user.getHEADURL()!=null){
                ImageOptions imageOptions = new ImageOptions.Builder()
                        .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        .setCircular(true) //原形图片
                        .setCrop(true)
                        .setLoadingDrawableId(R.mipmap.ic_launcher)
                        .setFailureDrawableId(R.mipmap.ic_launcher)
                        .build();
                x.image().bind(img,user.getHEADURL(),imageOptions);
            }
        }
    }

    /**
     * 点击获取验证码
     */
    @Event(R.id.login_getcode)
    private void getCode(View view){
        String phoneStr = phone.getText().toString();
        if (phoneStr==null || phoneStr.equals("")){
            //输入手机号码错误
            Toast.makeText(getBaseContext(),R.string.input_phone_number_error,Toast.LENGTH_SHORT).show();
        }else {
            //手机号码输入正确，获取验证码
            getCode.setClickable(false);//设置获取验证码按钮不可点击
            final long tickTime = 1000;//倒计时间隔时间
            long allTime = 60 * tickTime;//总时间
            timer = new CountDownTimer(allTime,tickTime) {
                @Override
                public void onTick(long l) {
                    getCode.setText(l/tickTime+"s后重新发送");
                }

                @Override
                public void onFinish() {
                    //倒计时完成
                    getCode.setText(R.string.get_code);
                    getCode.setClickable(true);
                }
            };
            timer.start();

            Log.i(TAG,"start network");
            String url = URLList.Domain + URLList.GetCode_URL;

            Code code = new Code();
            code.setPhone(phoneStr);
            code.setModule("mobileLogin");
            BaseMessage baseMessage = new BaseMessage();
            Gson gson = new Gson();
            baseMessage.setUSER_ID("");
            baseMessage.setData(code);
            NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.CODE);
            Log.i(TAG,"url:"+url);
        }
    }

    /**
     * 登陆
     * @param view
     */
    @Event(R.id.login_btn)
    private void Login(View view){
        String phoneStr = phone.getText().toString();
        String codeStr = code.getText().toString();

        if (phoneStr==null || phoneStr.equals("")){
            Toast.makeText(getBaseContext(),R.string.input_phone_number_error,Toast.LENGTH_SHORT).show();
            return;
        }
        if (codeStr==null || codeStr.equals("")){
            Toast.makeText(getBaseContext(),"请检查验证码",Toast.LENGTH_SHORT).show();
            return;
        }

        //开始登陆
        Log.i(TAG,"开始登陆");
        PromptManage.getInstance().ShowProgressDialog(LoginActivity.this,"登录中");

        String url = URLList.Domain + URLList.Login_URL;

        Login login = new Login();
        login.setPhone(phoneStr);
        login.setCode(codeStr);
        login.setLoginType("0");
        BaseMessage baseMessage = new BaseMessage();
        Gson gson = new Gson();
        baseMessage.setUSER_ID("2757");
        baseMessage.setData(login);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.LOGIN);

    }
    /**
     * 点击微信登陆
     */
    @Event(R.id.login_weixin)
    private void WXLogin(View view){
        //获取用户微信信息

        Intent intent = new Intent(this,AccountBindActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("login_type",0);//判断微信或者QQ登陆，微信 0
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 点击QQ登陆
     */
    @Event(R.id.login_qq)
    private void QQLogin(View view){
        //获取用户QQ信息

        Intent intent = new Intent(this,AccountBindActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("login_type",1);//判断微信或者QQ登陆，微信 0
        intent.putExtras(bundle);
        startActivity(intent);
    }




    public void onEvent(CodeMessage message) {
        Log.i("EventBus","获取验证码数据："+message.getMsg());
    }

    public void onEvent(UserMessage userMessage) throws DbException {
        Log.i("EventBus","登录数据："+userMessage.getMsg());
        if (userMessage.getCode().equals(NetCodeEnum.SUCCESS)){
            GlobalUtils.getInstances().setUser(userMessage.getData());
            User user = userMessage.getData();
            //DBUtils.getInstance().save(user);
            DBUtils.getInstance().saveOrUpdate(user);
            SharedPreferences sharedPreferences = getSharedPreferences(DataDictionary.SharedPreferencesName,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_id",user.getUSER_ID());
            editor.putBoolean("has_login",true);
            editor.commit();

            PromptManage.getInstance().CloseProgressDialog();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }else {
            Toast.makeText(getBaseContext(),userMessage.getMsg(),Toast.LENGTH_SHORT).show();
            PromptManage.getInstance().CloseProgressDialog();
        }
    }
}
