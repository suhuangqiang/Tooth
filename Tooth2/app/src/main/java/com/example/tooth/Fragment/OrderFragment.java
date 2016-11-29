package com.example.tooth.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.OrderAdapter;
import com.example.tooth.Entity.Order;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.OrderListMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.CancelBookParamter;
import com.example.tooth.Parameter.OrderListParameter;
import com.example.tooth.Parameter.UpdateOrderParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_book)
public class OrderFragment extends BaseFragment {

    @ViewInject(R.id.fragment_book_lv)
    ListView lv;
    @ViewInject(R.id.fragment_book_pcf)
    PtrClassicFrameLayout pcf;

    private List<Order> list;
    private OrderAdapter adapter;
    private int type;

    public OrderFragment() {
        // Required empty public constructor
    }
    public OrderFragment(int t){
        type = t;
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
    }
    private void init(){
        getData(type);
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(type);
            }
        });

    }

    private void getData(int s){
        Log.i("aaa","getData ---" + s);
        String url = URLList.Domain + URLList.OrderList;
        OrderListParameter parameter = new OrderListParameter();
        parameter.setPageIndex(1);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        switch (s){
            case ModeEnum.ORDER_LIST:
                parameter.setStatus(-1);
                baseMessage.setData(parameter);
                NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ORDER_LIST);
                break;
            case ModeEnum.ORDER_LIST_1:
                parameter.setStatus(0);
                baseMessage.setData(parameter);
                NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ORDER_LIST_1);
                break;
            case ModeEnum.ORDER_LIST_2:
                parameter.setStatus(1);
                baseMessage.setData(parameter);
                NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ORDER_LIST_2);
                break;
            case ModeEnum.ORDER_LIST_3:
                parameter.setStatus(2);
                baseMessage.setData(parameter);
                NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ORDER_LIST_3);
                break;
            case ModeEnum.ORDER_LIST_4:
                parameter.setStatus(3);
                baseMessage.setData(parameter);
                NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ORDER_LIST_4);
                break;
            case ModeEnum.ORDER_LIST_5:
                parameter.setStatus(4);
                baseMessage.setData(parameter);
                NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ORDER_LIST_5);
                break;
            default:
                break;

        }
    }

    /**
     * 取消订单
     * @param position
     */
    private void CancelOrder(int position){
        Toast.makeText(getContext(),"取消订单",Toast.LENGTH_SHORT).show();
        UpdateOrder(position,4);
    }

    /**
     * 删除订单
     * @param position
     */
    private void DelOrder(int position){
        Toast.makeText(getContext(),"删除订单",Toast.LENGTH_SHORT).show();
    }

    /**
     * 立即付款
     * @param position
     */
    private void Pay(int position){
        Toast.makeText(getContext(),"立即付款",Toast.LENGTH_SHORT).show();
    }

    /**
     * 确认收货
     * @param position
     */
    private void  Receipt(int position){
        Toast.makeText(getContext(),"确认收货",Toast.LENGTH_SHORT).show();
        String url = URLList.Domain + URLList.UpdateOrder;
        UpdateOrderParameter parameter = new UpdateOrderParameter();
        parameter.setOrdersn(list.get(position).getORDERSN());
        parameter.setStatus(3);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(parameter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.UPDATE_ORDER);
    }

    private void UpdateOrder(int position,int status){
        String url = URLList.Domain + URLList.UpdateOrder;
        UpdateOrderParameter parameter = new UpdateOrderParameter();
        parameter.setOrdersn(list.get(position).getORDERSN());
        parameter.setStatus(status);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(parameter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.UPDATE_ORDER);
    }


    public void onEvent(OrderListMessage message) {
        Log.i("aaa","onEvent");
        if (message == null){
            Toast.makeText(getContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            Log.i("aaa","type:"+type + "-----getType:"+message.getType());
            if (type == message.getType()){
                list = message.getData();
                adapter = new OrderAdapter(getContext(),list);
                adapter.setListener(new OrderAdapter.ClickListener() {
                    @Override
                    public void Del(int position) {
                        Order order = (Order) adapter.getItem(position);

                        int state = 0;
                        if (order.getSTATUS().equals("未付款")) {
                            state = 0;
                        } else if (order.getSTATUS().equals("待发货")){
                            state = 1;
                        }else if (order.getSTATUS().equals("发货")){
                            state = 2;
                        }else if (order.getSTATUS().equals("已完成")){
                            state = 3;
                        }else if (order.getSTATUS().equals("取消")){
                            state = 4;
                        }
                        Log.i("order","状态："+state);
                        final int p = position;
                        if (state == 0){
                            new AlertDialog.Builder(getActivity()).setMessage("是否取消")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            CancelOrder(p);
                                        }
                                    }).setNegativeButton("否",null).show();

                        }else if (state == 3 || state == 4){
                            new AlertDialog.Builder(getActivity()).setMessage("是否删除")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DelOrder(p);
                                        }
                                    }).setNegativeButton("否",null).show();

                        }
                    }

                    @Override
                    public void sure(int position) {
                        Order order = (Order) adapter.getItem(position);
                        int state = 0;
                        if (order.getSTATUS().equals("未付款")) {
                            state = 0;
                        } else if (order.getSTATUS().equals("待发货")){
                            state = 1;
                        }else if (order.getSTATUS().equals("发货")){
                            state = 2;
                        }else if (order.getSTATUS().equals("已完成")){
                            state = 3;
                        }else if (order.getSTATUS().equals("取消")){
                            state = 4;
                        }
                        Log.i("order","状态："+state);
                        final int p = position;
                        if (state == 0){
                            //立即付款
                            Pay(position);
                        }else if (state == 2){
                            //确认收货
                            Receipt(position);
                        }
                    }
                });
                lv.setAdapter(adapter);
            }
        }else {
            Toast.makeText(getContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
        pcf.refreshComplete();
    }


    public void onEvent(BaseMessage message) {
        if (message!=null && message.getType() == ModeEnum.UPDATE_ORDER){
            Toast.makeText(getContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
            pcf.autoRefresh();
        }
    }
}
