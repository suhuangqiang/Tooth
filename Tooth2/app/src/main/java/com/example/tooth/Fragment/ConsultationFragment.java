package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tooth.Adapter.ExerciseAdapter;
import com.example.tooth.Adapter.HospitalAdapter;
import com.example.tooth.Entity.Activity;
import com.example.tooth.Entity.Collection;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Message.CollectionMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.CollectionListParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_consultation)
public class ConsultationFragment extends BaseFragment {

    @ViewInject(R.id.fragment_consultation_lv)
    ListView lv;
    @ViewInject(R.id.fragment_consultation_pcf)
    PtrClassicFrameLayout pcf;
    private List<Activity> list;
    private ExerciseAdapter adapter;
    public ConsultationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getData();

    }
    private void init(){
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });
    }

    private void getData(){
        String url = URLList.Domain + URLList.CollectList;
        CollectionListParamter paramter = new CollectionListParamter();
        paramter.setType(1);
        paramter.setPageIndex(1);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.COLLECT_LIST_1);
    }

    public void onEvent(CollectionMessage message) {
        if (message!=null && message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.COLLECT_LIST_1){
            List<Collection> collections = message.getData();
            Log.i("aaa","consultation size is :" + collections.size());
            list = new ArrayList<>();
            for (int i=0;i<collections.size();i++){
                Activity activity = new Activity();
                Collection collection = collections.get(i);
                activity.setIMGURL(collection.getPIC());
                activity.setACTIVITY_ID(collection.getID());
                activity.setINTRODUCE(collection.getCONTENT());
                activity.setTITLE(collection.getNAME());
                list.add(activity);
            }
            adapter = new ExerciseAdapter(getContext(),list);
            lv.setAdapter(adapter);
        }
        pcf.refreshComplete();
    }
}
