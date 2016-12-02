package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.tooth.Activity.HospitalDetailActivity;
import com.example.tooth.Activity.MapViewActivity;
import com.example.tooth.Activity.SearchActivity;
import com.example.tooth.Adapter.BookTypeAdapter;
import com.example.tooth.Adapter.HospitalAdapter;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Entity.Project;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.HospitalListMessage;
import com.example.tooth.Message.ProjectMessage;
import com.example.tooth.Parameter.HospitalParameter;
import com.example.tooth.Parameter.PrpjectParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_dentist)
public class DentistFragment extends BaseFragment {
    private final String TAG = "DentistFragment";

    @ViewInject(R.id.fragment_dentist_lv)
    ListView main_lv;
    @ViewInject(R.id.img_all)
    ImageView all;
    @ViewInject(R.id.close_tv)
    TextView tv_close;
    @ViewInject(R.id.img_close)
    ImageView close;
    @ViewInject(R.id.fragment_dentist_pcf)
    PtrClassicFrameLayout main_pcf;
    @ViewInject(R.id.fragment_dentist_close)
    AutoRelativeLayout closeView;
    @ViewInject(R.id.fragment_dentist_all)
    AutoRelativeLayout allView;
    @ViewInject(R.id.all_tv)
    TextView all_tv;
    @ViewInject(R.id.fragment_dentist_et)
    EditText et;

    private String TYPE_recomme = "recomme";
    private String TYPE_dis = "dis";
    private String TYPE_start = "start";
    private String TYPE,TYPE_1 = TYPE_recomme;
    private HospitalAdapter adapter;
    private List<Hospital> hospitals;
    private String url;


    private PopupWindow popupWindow1,popupWindow2;
    private View popView1,popView2;
    private ListView project_lv;

    private int pageIndex = 1;//加载页数
    private int pageNum = 10;//每页加载数量
    private int FirstVisibleItem = 0,lastVisbleItem;
    private boolean hasMoreData = true;
    private boolean isRefresh = false;
    private boolean issearch = false;
    private boolean isInit = false;
    private boolean isKeyWords = false;
    public DentistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);

        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initEt();
    }

    /**
     * 初始化方法
     */
    private void init() {
        pageIndex = 1;
        isInit = true;
        url = URLList.Domain + URLList.HospitalList;
        /**
         * 初始化下拉刷新控件
         */
        main_pcf.setPtrHandler(new PtrDefaultHandler() {
            //下拉刷新放开开始的时候调用该方法，一般在这里开始耗时操作
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                getData(TYPE,null,null);
            }
        });

        //main_pcf.autoRefresh();


        /**
         * 初始化listview
         */
        getData(TYPE_recomme,null,null);

        main_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),HospitalDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("clinicId",hospitals.get(i).getCC_ID());
                bundle.putString("URL",hospitals.get(i).getDetailUrl());
                bundle.putSerializable("Hospital",hospitals.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        main_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int lastIndex = adapter.getCount() - 1;
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastVisbleItem == lastIndex) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    if (hasMoreData){
                        pageIndex ++;
                        getData(TYPE_1,TYPE,null);
                    }else {
                        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
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
     * 点击搜索
     * @param view
     */
    @Event(R.id.map)
    private void Search(View view){
        /*Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(getActivity(), MapViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("hospitals", (Serializable) hospitals);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 点击全部
     * @param view
     */
    @Event(R.id.fragment_dentist_all)
    private void onAllClicked(View view){
       ShowTypePop();
    }

    /**
     * 点击离我最近
     * @param view
     */
    @Event(R.id.fragment_dentist_close)
    private void onCloseClicked(View view){
        ShowClinicTypePop();
    }

    private void getData(String condition,String type,String keywords){
        TYPE = type;
        HospitalParameter hospitalParameter = new HospitalParameter();
        if (keywords!=null){
            hospitalParameter.setKeywords(keywords);
        }else {
            hospitalParameter.setCondition(condition);
            if (type != null){
                hospitalParameter.setType(type);
            }
        }

        hospitalParameter.setPageIndex(pageIndex);
        hospitalParameter.setLatitude("22");
        hospitalParameter.setLongitude("123.08");
        BDLocation location = GlobalUtils.getInstances().getBdLocation();
        if (location != null){
            Log.i("dentist","Longitude:"+location.getLongitude());
            Log.i("dentist","Latitude:"+location.getLatitude());
            hospitalParameter.setLongitude(location.getLongitude()+"");
            hospitalParameter.setLatitude(location.getLatitude()+"");
        }
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(hospitalParameter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.HOSPITAL_LIST);
    }


    public void onEvent(HospitalListMessage message) {
        if (message == null){
            Toast.makeText(getContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                List<Hospital> list = message.getData();
                Log.i(TAG,"isKeyWords:"+isKeyWords);
                Log.i(TAG,"isInit:"+isInit);
                Log.i(TAG,"issearch:"+issearch);
                Log.i(TAG,"isRefresh:"+isRefresh);
                if (isKeyWords || isInit || issearch || isRefresh || hospitals==null || hospitals.size() == 0){
                    Log.i("dentist","if");
                    hospitals = new ArrayList<>();
                    hospitals.addAll(list);
                    adapter = new HospitalAdapter(getContext(),hospitals);
                    main_lv.setAdapter(adapter);
                    issearch = false;
                    isRefresh = false;
                    isInit = false;
                    isKeyWords = false;
                }else {
                    Log.i("dentist","else");
                    Log.i("dentist","adapter size:"+adapter.getCount());
                    hospitals.addAll(list);
                    /*adapter.notifyDataSetChanged();*/

                }
                if (list.size()<pageNum){
                    hasMoreData = false;
                }

            }else {
                Toast.makeText(getContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
        main_pcf.refreshComplete();
    }

    /**
     * 点击全部 显示就诊类型的popwindow
     */
    private void ShowTypePop(){
        all.setImageResource(R.mipmap.dian);
        if (popupWindow2 == null){

            popView2 = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_listview,null);
            project_lv = (ListView)popView2.findViewById(R.id.popwindow_lv);

            popupWindow2 = new PopupWindow(popView2, PtrFrameLayout.LayoutParams.MATCH_PARENT, PtrFrameLayout.LayoutParams.WRAP_CONTENT);
            popupWindow2.setAnimationStyle(R.style.type_anim);
            popupWindow2.setOutsideTouchable(true);
            popupWindow2.setFocusable(true);
            //让pop可以点击外面消失掉
            popupWindow2.setBackgroundDrawable(new ColorDrawable(0));

            popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    all.setImageResource(R.mipmap.hui_jt);
                }
            });
            getData();
        }else {
            popupWindow2.showAsDropDown(allView);
        }
    }
    /**
     * 显示选择 诊所 的条件的 popwindow
     */
    private void ShowClinicTypePop(){

        close.setImageResource(R.mipmap.dian);
        if (popupWindow1 == null){

            popView1 = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_hosp_type,null);
            AutoRelativeLayout recomment = (AutoRelativeLayout)popView1.findViewById(R.id.popwindow_hosp_type_recomme);
            AutoRelativeLayout dis  = (AutoRelativeLayout)popView1.findViewById(R.id.popwindow_hosp_type_dis);
            AutoRelativeLayout start  = (AutoRelativeLayout)popView1.findViewById(R.id.popwindow_hosp_type_start);
            recomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    issearch = true;
                    pageIndex = 1;
                    getData(TYPE_recomme,null,null);
                    TYPE_1 = TYPE_recomme;
                    tv_close.setText("推荐");
                    popupWindow1.dismiss();
                }
            });
            dis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    issearch = true;
                    pageIndex = 1;
                    getData(TYPE_dis,null,null);
                    TYPE_1 = TYPE_dis;
                    tv_close.setText("离我最近");
                    popupWindow1.dismiss();
                }
            });
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    issearch = true;
                    pageIndex = 1;
                    getData(TYPE_start,null,null);
                    TYPE_1 = TYPE_start;
                    tv_close.setText("口碑最佳");
                    popupWindow1.dismiss();
                }
            });
            popupWindow1 = new PopupWindow(popView1, PtrFrameLayout.LayoutParams.MATCH_PARENT, PtrFrameLayout.LayoutParams.WRAP_CONTENT);
            popupWindow1.setAnimationStyle(R.style.type_anim);
            popupWindow1.setOutsideTouchable(true);
            popupWindow1.setFocusable(true);
            //让pop可以点击外面消失掉
            popupWindow1.setBackgroundDrawable(new ColorDrawable(0));

            popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    close.setImageResource(R.mipmap.hui_jt);
                }
            });
        }
        popupWindow1.showAsDropDown(closeView);

    }

    /**
     * 获取服务列表
     */
    private void getData(){
        String url = URLList.Domain + URLList.ProjectList;
        PrpjectParamter paramter = new PrpjectParamter();
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.LOOK_PROJECT_LIST);
    }

    /**
     * 初始化edittext
     */
    private void initEt(){
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
                pageIndex = 1;
                isKeyWords = true;
                String s = charSequence.toString();
                if (s!=null && !s.equals("")){
                    getData("","",s);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onEvent(ProjectMessage message) {
        if (message!=null && message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.LOOK_PROJECT_LIST){
            final List<Project> projects = message.getData();
            List<HashMap<String,String>> list = new ArrayList<>();
            for (int i=0;i<projects.size();i++){
                HashMap<String,String> map = new HashMap<>();
                map.put(DataDictionary.TYPE,"0");
                map.put("TEXT",projects.get(i).getCP_NAME());
                list.add(map);
            }
            final BookTypeAdapter adapter = new BookTypeAdapter(getContext(),list);
            project_lv.setAdapter(adapter);
            project_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    HashMap<String,String> map = (HashMap<String, String>) adapter.getItem(i);
                    String content = map.get("TEXT");
                    all_tv.setText(content);
                    popupWindow2.dismiss();
                    issearch = true;
                    pageIndex = 1;
                    if (content.equals("全部")){
                        content = null;
                    }else {
                        content = projects.get(i).getCP_ID();
                    }
                    TYPE = content;
                    Log.i("dentist",TYPE_1);
                    getData(TYPE_1,content,null);
                }
            });
            popupWindow2.showAsDropDown(allView);
        }
    }
}
