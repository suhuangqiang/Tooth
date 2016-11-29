package com.example.tooth.Activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Entity.Code;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.CodeParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_bind_account)
public class BindAccountActivity extends BaseActivity {

    @ViewInject(R.id.activity_bind_account_phone)
    TextView tv_phone;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.account_bind_username)
    EditText et_phone;
    @ViewInject(R.id.account_bind_code)
    EditText et_code;
    @ViewInject(R.id.account_bind_getcode)
    Button btn_code;
    CountDownTimer timer;//计时器

    private String mode = "changePhone";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("账号绑定");
        tv_phone.setText(GlobalUtils.getInstances().getUser().getPHONE());
    }
    /**
     * 返回事件
    * @param view
    */
    @Event(R.id.back)
    private void Back(View view){
        onBack();
    }

    /**
     * 获取验证码
     * @param view
     */
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
            String url = URLList.Domain + URLList.GetCode_URL;
            Code code = new Code();
            code.setPhone(phoneStr);
            code.setModule(mode);
            BaseMessage baseMessage = new BaseMessage();
            Gson gson = new Gson();
            baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
            baseMessage.setData(code);
            NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.CODE);

        }
    }

    /**
     * 点击确认绑定
     * @param view
     */
    @Event(R.id.activity_bind_account_btn)
    private void OnBtnClick(View view){
        String url = URLList.Domain + URLList.Update_Phone;
        CodeParamter paramter = new CodeParamter();
        paramter.setPhone(et_phone.getText().toString());
        paramter.setCode(et_code.getText().toString());
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.UPDATE_PHONE);
    }


    public void onEvent(BaseMessage message) {
        if (message!=null && message.getType() == ModeEnum.UPDATE_PHONE){
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
