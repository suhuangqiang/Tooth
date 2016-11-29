package com.example.tooth.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Entity.Appointment;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.BookCommentParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.Utils.StarBar;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_evaluate)
public class EvaluateActivity extends BaseActivity {
    @ViewInject(R.id.activity_evaluate_img)
    ImageView img;
    @ViewInject(R.id.activity_evaluate_name)
    TextView name;
    @ViewInject(R.id.activity_evaluate_starbar1)
    StarBar starBar1;
    @ViewInject(R.id.activity_evaluate_starbar2)
    StarBar starBar2;
    @ViewInject(R.id.activity_evaluate_starbar3)
    StarBar starBar3;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_evaluate_et)
    EditText et;

    private Appointment appointment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("评价");
        starBar1.setClickable(true);
        starBar1.setisClickable(false);
        starBar2.setClickable(true);
        starBar3.setClickable(true);
        appointment = (Appointment) this.getIntent().getExtras().getSerializable("apointment");
        starBar1.setStarMark((float)appointment.getASSESS()*5);
    }

    /**
     * 提交
     * @param view
     */
    @Event(R.id.activity_evaluate_btn)
    private void OnBtnClick(View view){
        String content = et.getText().toString();
        if (content == null || content.equals("")){
            Toast.makeText(getBaseContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String url = URLList.Domain + URLList.BookComment;
        BookCommentParamter paramter = new BookCommentParamter();
        paramter.setClinicId(appointment.getCC_ID());
        paramter.setId(appointment.getCR_ID());
        paramter.setContent(content);
        float s1 = starBar2.getStarMark();
        float s2 = starBar3.getStarMark();
        int assess = (int) ((s1+s2)/2);
        paramter.setAssess(assess);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.BOOK_COMMENT);
    }


    public void onEvent(BaseMessage message) {
        if (message!=null && message.getType() == ModeEnum.BOOK_COMMENT){
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                onBack();
            }
        }
    }

    /**
     * 关闭
     * @param view
     */
    @Event(R.id.titlebar_tv)
    private void OnCloseClick(View view){
        onBack();
    }
}
