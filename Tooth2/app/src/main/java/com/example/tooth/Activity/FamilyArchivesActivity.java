package com.example.tooth.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.FamilyAdapter;
import com.example.tooth.Entity.Family;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.FamilyListMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.DelFamilyParamter;
import com.example.tooth.Parameter.FamilyListPatameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.View.ListSlideView;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

@ContentView(R.layout.activity_family_archives)
public class FamilyArchivesActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_family_archives_lv)
    ListSlideView lv;
    @ViewInject(R.id.activity_family_archives_pcf)
    PtrClassicFrameLayout pcf;

    private List<Family> families;
    private FamilyAdapter adapter;
    private int type = 0;

    private int pageIndex = 1;//加载页数
    private int pageNum = 10;//每页加载数量
    private int FirstVisibleItem = 0,lastVisbleItem;
    private boolean hasMoreData = true;
    private boolean isRefresh = false;
    private int delIndex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void init() {
        title.setText("家人档案");
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            type = bundle.getInt("type");
        }
        getData();
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                getData();
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
    private void getData(){
        String url = URLList.Domain + URLList.FamilyListUrl;
        FamilyListPatameter message = new FamilyListPatameter();
        message.setPageIndex(pageIndex);
        pageIndex ++;
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(message);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.FAMILY_LIST);
    }

    /**
     * 添加
     * @param view
     */
    @Event(value = {R.id.titlebar_tv,R.id.activity_family_archives_btn})
    private void OnAddClick(View view){
        Intent intent = new Intent(this,AddFamilyActivity.class);
        startActivity(intent);
    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }


    public void onEvent(FamilyListMessage message) {
        if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            List<Family> list = message.getData();
            if (isRefresh || families==null || families.size()==0){
                families = message.getData();
                adapter = new FamilyAdapter(getBaseContext(),families);
                adapter.setListener(new FamilyAdapter.FamilyListener() {
                    @Override
                    public void onDelClick(int position) {
                        Del(position);
                    }
                });
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (type == 1){
                            String familyId = families.get(i).getFAMILY_ID();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("family",families.get(i));
                            Intent intent = new Intent();
                            intent.putExtras(bundle);
                            setResult(2,intent);
                            finish();
                        }else {
                            Intent intent = new Intent(getBaseContext(),RecordDetailActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("family",families.get(i));
                            intent.putExtras(bundle1);
                            startActivity(intent);
                        }
                    }
                });
                isRefresh = false;
            }else {
                families.addAll(list);
                adapter.notifyDataSetChanged();
            }
            if (list.size() < pageNum){
                hasMoreData = false;
            }
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
        pcf.refreshComplete();
    }

    /**
     * 删除
     * @param position
     */
    private void Del(int position){
        delIndex = position;
        String url = URLList.Domain + URLList.DelFamily;
        DelFamilyParamter paramter = new DelFamilyParamter();
        paramter.setFamilyId(families.get(position).getFAMILY_ID());
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.DEL_FAMILY);
    }


    public void onEvent(BaseMessage message) {
        if (message!=null && message.getType() == ModeEnum.DEL_FAMILY){
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                families.remove(delIndex);
                adapter.notifyDataSetChanged();
            }
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(Family family) {
        if (families == null){
            families = new ArrayList<>();
        }
        families.add(family);
        adapter.notifyDataSetChanged();
    }
}
