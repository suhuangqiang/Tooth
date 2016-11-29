package com.example.tooth.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.EncyclopediaAdapter;
import com.example.tooth.Entity.Consult;
import com.example.tooth.Message.ConsultListMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.PageParamter;
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

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

@ContentView(R.layout.activity_encyclopedias)
public class EncyclopediasActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.titlebar_img)
    ImageView serach;
    @ViewInject(R.id.activity_encyclopedias_lv)
    ListView lv;
    @ViewInject(R.id.activity_encyclopedias_pcf)
    PtrClassicFrameLayout pcf;

    private EncyclopediaAdapter adapter;
    private List<Consult> consults;
    private int type;

    private int pageIndex = 1;//加载页数
    private int pageNum = 10;//每页加载数量
    private int FirstVisibleItem = 0,lastVisbleItem;
    private boolean hasMoreData = true;
    private boolean isRefresh = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        serach.setVisibility(View.GONE);

        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                getData();
            }
        });
        type = this.getIntent().getExtras().getInt(DataDictionary.TYPE);
        if (type == ModeEnum.CONSULT_LIST_0){
            title.setText("口腔百科");
        }else if (type == ModeEnum.CONSULT_LIST_1){
            title.setText("口腔常识");
        }

        getData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Consult consult = (Consult)adapter.getItem(i);
                String url = consult.getKQ_URL();
                Intent intent = new Intent(getBaseContext(),WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(DataDictionary.TYPE,1);
                bundle.putSerializable("Consult",consult);
                bundle.putString("URL",url);
                bundle.putString("title",consult.getKQ_TITLE());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int lastIndex = adapter.getCount() - 1;
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastVisbleItem == lastIndex) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    if (hasMoreData){
                        getData();
                    }else {
                        Toast.makeText(getBaseContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                FirstVisibleItem = i;
                lastVisbleItem = i + i1 - 1;
            }
        });
    }

    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 搜索
     * @param view
     */
    @Event(R.id.titlebar_img)
    private void OnSearchClick(View view){

    }

    private void getData(){
        String url = URLList.Domain + URLList.ConsultList;
        PageParamter paramter = new PageParamter();
        paramter.setPageIndex(pageIndex);
        pageIndex ++;
        if (type == ModeEnum.CONSULT_LIST_0){
            paramter.setType("0");
        }else if (type == ModeEnum.CONSULT_LIST_1){
            paramter.setType("1");
        }

        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,type);
    }

    public void onEvent(ConsultListMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT);
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == type){
            List<Consult> list = message.getData();
            if (isRefresh || consults == null || consults.size() == 0){
                consults = message.getData();
                adapter = new EncyclopediaAdapter(getBaseContext(),consults);
                lv.setAdapter(adapter);
                isRefresh = false;
            }else {
                consults.addAll(list);
                adapter.notifyDataSetChanged();
            }
            if (list.size() < pageNum){
                hasMoreData = false;
            }

        }
        pcf.refreshComplete();
    }
}
