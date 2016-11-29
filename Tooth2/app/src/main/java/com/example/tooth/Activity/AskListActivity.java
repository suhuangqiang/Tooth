package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.AskListAdapter;
import com.example.tooth.Message.DynamicMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.DynamicParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

@ContentView(R.layout.activity_ask_list)
public class AskListActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.titlebar_img)
    ImageView tv_public;
    @ViewInject(R.id.activity_ask_list_lv)
    ListView lv;
    @ViewInject(R.id.activity_ask_list_pcf)
    PtrClassicFrameLayout pcf;

    private AskListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("牙友问问");
        tv_public.setImageResource(R.mipmap.write);

        getData();
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });


    }

    /**
     * 发布
     * @param view
     */
    @Event(R.id.titlebar_img)
    private void OnWriteClick(View view){
        Intent intent = new Intent(this,AskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",1);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void getData(){
        String url = URLList.Domain + URLList.FriendsAskList;
        DynamicParamter paramter = new DynamicParamter();
        paramter.setPageIndex(1);
        paramter.setType(1);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.FRIENDS_LIST);
    }


    public void onEvent(DynamicMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            adapter = new AskListAdapter(getBaseContext(),message.getData());
            lv.setAdapter(adapter);
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
        pcf.refreshComplete();
    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }
}
