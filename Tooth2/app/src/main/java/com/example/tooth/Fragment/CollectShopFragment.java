package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.tooth.Adapter.ExerciseAdapter;
import com.example.tooth.Adapter.ShopAdapter;
import com.example.tooth.Entity.Activity;
import com.example.tooth.Entity.Collection;
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
@ContentView(R.layout.fragment_collect_shop)
public class CollectShopFragment extends BaseFragment {

    @ViewInject(R.id.fragment_collect_shop_gv)
    GridView gv;
    @ViewInject(R.id.fragment_collect_shop_pcf)
    PtrClassicFrameLayout pcf;

    private List<Collection> list;
    private ShopAdapter adapter;
    public CollectShopFragment() {
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
        paramter.setType(2);
        paramter.setPageIndex(1);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.COLLECT_LIST_2);
    }

    public void onEvent(CollectionMessage message) {
        if (message!=null && message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.COLLECT_LIST_2){
            list = message.getData();
            adapter = new ShopAdapter(getContext(),list);
            gv.setAdapter(adapter);
        }
        pcf.refreshComplete();
    }
}
