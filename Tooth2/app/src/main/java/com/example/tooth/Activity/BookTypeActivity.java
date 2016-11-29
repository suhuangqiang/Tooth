package com.example.tooth.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.BookTypeAdapter;
import com.example.tooth.Entity.Project;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.ProjectMessage;
import com.example.tooth.Parameter.PrpjectParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContentView(R.layout.activity_book_type)
public class BookTypeActivity extends BaseActivity {

    @ViewInject(R.id.activity_book_type_lv)
    ListView lv;
    @ViewInject(R.id.activity_book_type_et)
    EditText et;
    @ViewInject(R.id.activity_book_type_sure)
    TextView tv_sure;

    private List<HashMap<String,String>> list;
    int index = -1;
    private String clinicId;
    private List<Project> projects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        //tv_sure.setFocusable(true);
        clinicId = getIntent().getExtras().getString("clinicId");
        tv_sure.requestFocus();
        getData();

    }

    /**
     * 确认
     * @param view
     */
    @Event(R.id.activity_book_type_sure)
    private void OnSureClick(View view){
        String mark = et.getText().toString();
        if (index == -1){
            Toast.makeText(getBaseContext(),"请选择一个项目",Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Project",projects.get(index));
        bundle.putString("CONTENT",projects.get(index).getCP_NAME());
        bundle.putString("MARK",mark);
        intent.putExtras(bundle);
        this.setResult(1,intent);
        finish();
    }


    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    private void getData(){
        String url = URLList.Domain + URLList.ProjectList;
        PrpjectParamter paramter = new PrpjectParamter();
        paramter.setClinicId(clinicId);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.PROJECT_LIST);
    }


    public void onEvent(ProjectMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            list = new ArrayList<>();
            projects = message.getData();
            for (int i=0;i<projects.size();i++){
                HashMap<String,String> map = new HashMap<>();
                map.put(DataDictionary.TYPE,"0");
                map.put("TEXT",projects.get(i).getCP_NAME());
                list.add(map);
            }
            final BookTypeAdapter adapter = new BookTypeAdapter(getBaseContext(),list);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (index != -1){
                        list.get(index).put(DataDictionary.TYPE,"0");
                        index = i;
                        list.get(index).put(DataDictionary.TYPE,"1");
                    }else {
                        index = i;
                        list.get(index).put(DataDictionary.TYPE,"1");
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
