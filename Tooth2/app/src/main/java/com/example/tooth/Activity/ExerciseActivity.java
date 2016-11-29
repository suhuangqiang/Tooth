package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.ExerciseAdapter;
import com.example.tooth.Entity.Activity;
import com.example.tooth.Message.ActivityListMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.FamilyListPatameter;
import com.example.tooth.Parameter.HospitalParameter;
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

/**
 * 活动avtivity
 */
@ContentView(R.layout.activity_exercise)
public class ExerciseActivity extends BaseActivity {
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.titlebar_img)
    ImageView search;
    @ViewInject(R.id.activity_exercise_lv)
    ListView lv;
    @ViewInject(R.id.activity_exercise_pcf)
    PtrClassicFrameLayout pcf;

    private ExerciseAdapter adapter;
    private List<Activity> activities;

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
        search.setVisibility(View.GONE);
        title.setText("活动");

        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                getData();
            }
        });

        getData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Activity activity = (Activity) adapter.getItem(i);
                Intent intent = new Intent(getBaseContext(),WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("URL",activity.getDetailUrl());
                bundle.putInt(DataDictionary.TYPE,3);
                bundle.putString("title",activity.getTITLE());
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

    /**
     * 搜索
     * @param view
     */
    @Event(R.id.titlebar_img)
    private void OnSearchClick(View view){}

    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 获取数据
     */
    private void getData(){
        String url = URLList.Domain + URLList.ActivityList;
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        HospitalParameter patameter = new HospitalParameter();
        patameter.setPageIndex(pageIndex);
        pageIndex ++;
        baseMessage.setData(patameter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ACTIVITY_LIST);
    }

    public void onEvent(ActivityListMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                List<Activity> list = message.getData();
                if (isRefresh || activities == null || activities.size()==0){
                    activities = message.getData();
                    adapter = new ExerciseAdapter(getBaseContext(),activities);
                    lv.setAdapter(adapter);
                    isRefresh = false;
                }else {
                    activities.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                if (list.size() < pageNum){
                    hasMoreData = false;
                }

            }
        }
        pcf.refreshComplete();
    }
}
