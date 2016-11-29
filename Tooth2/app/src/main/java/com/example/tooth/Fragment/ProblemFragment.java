package com.example.tooth.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tooth.Message.DynamicMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.ProblemParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_problem)
public class ProblemFragment extends BaseFragment {

    @ViewInject(R.id.fragment_problem_pcf)
    PtrClassicFrameLayout pcf;
    @ViewInject(R.id.fragment_problem_lv)
    ListView lv;
    private int type;
    public ProblemFragment(int t) {
        // Required empty public constructor
        this.type = t;
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
    }
    private void init(){
        getData();
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });
    }
    private void getData(){
        String url = URLList.Domain + URLList.MyProblemList;
        ProblemParamter paramter = new ProblemParamter();
        paramter.setPageIndex(1);
        paramter.setType(type);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.MY_PROBLEM_LIST);
    }


    public void onEvent(DynamicMessage message) {
        if(message == null){
            Toast.makeText(getContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){

        }else {
            Toast.makeText(getContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
