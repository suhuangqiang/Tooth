package com.example.tooth.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.GridViewImageAdapter;
import com.example.tooth.Entity.Record;
import com.example.tooth.Message.RecordMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.RecordParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_record)
public class RecordActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_record_time)
    TextView tv_time;
    @ViewInject(R.id.activity_record_hospital_name)
    TextView tv_name;
    @ViewInject(R.id.activity_record_type)
    TextView tv_type;
    @ViewInject(R.id.activity_record_docotr)
    TextView tv_doctor;
    @ViewInject(R.id.activity_record_message1)
    TextView tv_message1;
    @ViewInject(R.id.activity_record_message2)
    TextView tv_message2;
    @ViewInject(R.id.activity_record_message3)
    TextView tv_message3;
    @ViewInject(R.id.activity_record_gv)
    GridView gv;
    private String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        Log.i("family","on record activity");
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        title.setText("病历");
        getData();
    }

    /**
     * 获取数据
     */
    private void getData(){
        String url = URLList.Domain + URLList.RecordDetail;
        RecordParamter paramter = new RecordParamter();
        paramter.setRecordId(id);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.RECORD_DETAIL);
    }

    /**
     * 接受数据
     * @param message
     */
    public void onEvent(RecordMessage message) {
        if(message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            Record record = message.getData();
            tv_time.setText(record.getTIME());
            tv_name.setText(record.getCC_NAME());
            tv_type.setText(record.getCP_NAME());
            tv_doctor.setText(record.getZE_NAME());
            tv_message1.setText(record.getDETAIL());
            tv_message2.setText(record.getPRESCRIBESMS());
            tv_message3.setText(record.getADVICE());
            List<String> imgs = record.getIMAGEURL();
            GridViewImageAdapter adapter = new GridViewImageAdapter(getBaseContext(),imgs);
            gv.setAdapter(adapter);
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }
}
