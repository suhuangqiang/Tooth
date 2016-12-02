package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Activity.AppointmentActivity;
import com.example.tooth.Activity.EvaluateActivity;
import com.example.tooth.Adapter.BookAdapter;
import com.example.tooth.Entity.ActivityResult;
import com.example.tooth.Entity.Appointment;
import com.example.tooth.Entity.Time;
import com.example.tooth.Message.AppointmentListMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AppointmentListParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.CancelBookParamter;
import com.example.tooth.PopWindow.ListPopWindow;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

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
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_book)
public class BookFragment extends BaseFragment {
    @ViewInject(R.id.fragment_book_lv)
    ListView lv;
    @ViewInject(R.id.fragment_book_pcf)
    PtrClassicFrameLayout pcf;
    private int type,book_state;
    private List<Appointment> list;
    private BookAdapter adapter;
    private boolean is = false;//判断是否当前的fragment获取数据
    public BookFragment() {
        // Required empty public constructor
    }

    public BookFragment(int type){
        Log.i("apointment","BookFragment TYPE:"+type);
        this.type = type;
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

    private void getData(int t){
        type = t;
        String url = URLList.Domain + URLList.MyAppointment;
        AppointmentListParamter paramter = new AppointmentListParamter();
        paramter.setPageIndex(1);
        paramter.setStatus(t);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);
        is = true;
        if (type == DataDictionary.AppointmnetFinsh){
            Log.i("aaa","type is finish");
            NetWorkUtils.getInstances().readNetwork(url,baseMessage,ModeEnum.MY_APPOINTMENT);
            book_state = ModeEnum.MY_APPOINTMENT;
        }else {
            Log.i("aaa","type is ing");
            NetWorkUtils.getInstances().readNetwork(url,baseMessage,ModeEnum.MY_APPOINTMENT_ING);
            book_state = ModeEnum.MY_APPOINTMENT_ING;
        }

    }

    public void onEvent(AppointmentListMessage message) {
        Log.i("apointment","onEvent");
        if (message == null) {
            Toast.makeText(getContext(), "出错了。。。", Toast.LENGTH_SHORT).show();
        } else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == book_state) {
                Log.i("aaa","state:"+message.getType() + "---size:"+message.getData().size());
                list = message.getData();
                adapter = new BookAdapter(getContext(), list);
                adapter.setCallBack(new BookAdapter.CallBack() {
                    @Override
                    public void ClickEdit(int position) {
                        Edit(position);
                    }

                    @Override
                    public void ClickDel(int position) {
                        Del(position);
                    }

                    @Override
                    public void ClickEvaluate(int position) {
                        PingJia(position);
                    }
                });
                lv.setAdapter(adapter);
            }
        }
        pcf.refreshComplete();
    }

    /**
     * 更新预约
     * @param position
     */
    private void Edit(int position){
        Intent intent = new Intent(getActivity(),AppointmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",3);
        bundle.putSerializable("Appointment",list.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 删除预约
     * @param position
     */
    private void Del(int position){
        Log.i("book","del");
        final int p = position;
        String state = list.get(p).getCP_TYPE();

        if (state.equals("0")){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_return,null);
            final ListPopWindow popWindow = new ListPopWindow(getActivity(),view);
            AutoRelativeLayout cancel = (AutoRelativeLayout)view.findViewById(R.id.pop_return_cancel);
            AutoRelativeLayout road = (AutoRelativeLayout)view.findViewById(R.id.pop_return_road);
            AutoRelativeLayout count = (AutoRelativeLayout)view.findViewById(R.id.pop_return_count);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popWindow.dismiss();
                }
            });
            road.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnMoney(p,0);
                    popWindow.dismiss();
                }
            });
            count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnMoney(p,1);
                    popWindow.dismiss();
                }
            });
            popWindow.showAtLocation(lv, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
            backgroundAlpha(0.5f);
            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }else {
            new AlertDialog.Builder(getActivity()).setMessage("是否删除")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url = URLList.Domain + URLList.CancelBook;
                            CancelBookParamter paramter = new CancelBookParamter();
                            paramter.setId(list.get(p).getCR_ID());
                            BaseParameter baseParameter = new BaseParameter();
                            baseParameter.setData(paramter);
                            baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
                            NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.CANCEL_BOOK);
                        }
                    }).setNegativeButton("取消",null).show();
        }


    }

    /**
     * 退款
     * @param position  订单下标
     * @param type  退款类型  原路或者退回账户
     */
    private void ReturnMoney(int position,int type){

    }
    /**
     * 评价
     * @param position
     */
    private void PingJia(int position){
        Intent intent = new Intent(getActivity(), EvaluateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("apointment",list.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void onEvent(BaseMessage message) {
        if (message == null){
            Toast.makeText(getActivity(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getType() == ModeEnum.CANCEL_BOOK){
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                pcf.autoRefresh();
            }
            Toast.makeText(getActivity(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 更新预约返回
     * @param result
     */
    public void onEvent(ActivityResult result) {
        if (result.getType() == ActivityResult.UpdateBook){
            pcf.autoRefresh();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
