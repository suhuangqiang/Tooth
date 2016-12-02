package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tooth.Adapter.HospitalAdapter;
import com.example.tooth.Entity.Collection;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Entity.Login;
import com.example.tooth.Message.CollectionMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.CollectionListParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
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
@ContentView(R.layout.fragment_hospital)
public class HospitalFragment extends BaseFragment {

    @ViewInject(R.id.fragment_hospital_lv)
    ListView lv;
    @ViewInject(R.id.fragment_hospital_pcf)
    PtrClassicFrameLayout pcf;
    private HospitalAdapter adapter;
    private List<Hospital> list;

    public HospitalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
        paramter.setType(0);
        paramter.setPageIndex(1);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.COLLECT_LIST_0);
    }

    public void onEvent(CollectionMessage message) {
        Log.i("aaa","onevent hospital:"+message.getType());
        if (message!=null && message.getType() == ModeEnum.COLLECT_LIST_0){
            List<Collection> collections = message.getData();
            list = new ArrayList<>();
            Log.i("aaa","hospital size is :" + collections.size());
            for (int i=0;i<collections.size();i++){
                Hospital hospital = new Hospital();
                Collection collection = collections.get(i);
                hospital.setCLINIC_PIC(collection.getPIC());
                hospital.setCC_ID(collection.getID());
                hospital.setASSESS(Double.parseDouble(collection.getCONTENT()));
                hospital.setCC_NAME(collection.getNAME());
                hospital.setCC_ADDRESS(collection.getOTHER());
                list.add(hospital);
            }
            adapter = new HospitalAdapter(getContext(),list);
            lv.setAdapter(adapter);
        }
        pcf.refreshComplete();
    }
}
