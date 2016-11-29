package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.tooth.Adapter.HospitalAdapter;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.SearchHospMessage;
import com.example.tooth.Parameter.HospitalParameter;
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

@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {
    private final String TAG = "SearchActivity";
    @ViewInject(R.id.activity_search_et)
    EditText et;
    @ViewInject(R.id.activity_search_lv)
    ListView lv;
    private HospitalAdapter adapter;
    private List<Hospital> list;
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        init();
    }

    public void init() {
        url = URLList.Domain + URLList.HospitalList;
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /**
                 * 输入框内容改变，执行查询
                 */
                Log.i(TAG,"onTextChanged:"+charSequence + "----" + et.getText().toString());
                getData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(),HospitalDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("clinicId",list.get(i).getCC_ID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getData(String keyword){
        String type = "recomme";
        HospitalParameter hospitalParameter = new HospitalParameter();
        hospitalParameter.setCondition(type);
        hospitalParameter.setPageIndex(1);
        hospitalParameter.setLatitude("22");
        hospitalParameter.setLongitude("123.08");
        hospitalParameter.setKeywords(keyword);
        BDLocation location = GlobalUtils.getInstances().getBdLocation();
        if (location != null){
            hospitalParameter.setLongitude(location.getLongitude()+"");
            hospitalParameter.setLatitude(location.getLatitude()+"");
        }
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(hospitalParameter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.SEARCH_HOSPITAL);
    }


    public void onEvent(SearchHospMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            list = message.getData();
            adapter = new HospitalAdapter(getBaseContext(),message.getData());
            lv.setAdapter(adapter);
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Event(R.id.activity_search_cancel)
    private void OnCancelClick(View view){
        finish();
    }
}
