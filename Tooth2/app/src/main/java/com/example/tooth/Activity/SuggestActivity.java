package com.example.tooth.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.PublishFriendParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_suggest)
public class SuggestActivity extends BaseActivity implements TextWatcher{
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_suggest_et)
    EditText editText;
    @ViewInject(R.id.activity_suggest_btn)
    Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("意见反馈");
        editText.addTextChangedListener(this);
    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 提交
     * @param view
     */
    @Event(R.id.activity_suggest_btn)
    private void OnBtnClick(View view){
        String url = URLList.Domain + URLList.Advice;
        String content = editText.getText().toString();
        PublishFriendParamter paramter = new PublishFriendParamter();
        paramter.setContent(content);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADVICE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String str = charSequence.toString();
        Log.i("aaa","输入框："+str);
        if (str.equals("")){
            btn.setBackgroundColor(Color.parseColor("#ff9da19e"));
            btn.setClickable(false);
        }else {
            btn.setBackgroundColor(Color.parseColor("#3191E8"));
            btn.setClickable(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    public void onEvent(BaseMessage message) {
        if (message==null){

        }else if (message.getCode().equals(NetCodeEnum.SUCCESS ) && message.getType() == ModeEnum.ADVICE){
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
            onBack();
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
