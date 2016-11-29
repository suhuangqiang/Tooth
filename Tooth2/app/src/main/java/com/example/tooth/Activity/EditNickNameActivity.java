package com.example.tooth.Activity;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tooth.Entity.User;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.UserParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.DBUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_edit_nick_name)
public class EditNickNameActivity extends BaseActivity {

    @ViewInject(R.id.activity_edit_nickname_et)
    EditText editText;

    private String url;
    private String str,nameStr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_edit_nick_name);*/
    }



    public void init() {
        Bundle bundle = this.getIntent().getExtras();
        str = bundle.getString("nickname");
        if (str!=null && !str.equals("")){
            editText.setText(str);
        }
        url = URLList.Domain + URLList.UpdateNickName;

    }

    /**
     * 取消
     * @param view
     */
    @Event(R.id.activity_edit_nickname_cancel)
    private void OnCancelClick(View view){
        onBack();
    }

    /**
     * 保存
     * @param view
     */
    @Event(R.id.activity_edit_nickname_save)
    private void OnSaveClick(View view){
        UserParamter paramter = new UserParamter();
        nameStr = editText.getText().toString();
        if (str.equals(nameStr)){
            Toast.makeText(getBaseContext(),"昵称没有改变",Toast.LENGTH_SHORT).show();
            return;
        }
        paramter.setNickName(nameStr);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.BASE);
    }

    /**
     * img  清除输入框
     * @param view
     */
    @Event(R.id.img)
    private void OnImgClick(View view){
        editText.setText("");
    }


    public void onEvent(BaseMessage message) throws DbException {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            GlobalUtils.getInstances().getUser().setUSERNAME(nameStr);
            String user_id = GlobalUtils.getInstances().getUser().getUSER_ID();
            DBUtils.getInstance().update(User.class,WhereBuilder.b("user_id","=",user_id),new KeyValue("username",nameStr));
            EventBus.getDefault().post(nameStr);
            finish();
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
