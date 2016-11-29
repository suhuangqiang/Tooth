package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.Entity.Appointment;
import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/28.
 */
public class BookAdapter extends BaseAdapter {
    private Context mContext;
    private List<Appointment> appointments;
    private CallBack callBack;
    public BookAdapter(Context context,List<Appointment> list){
        this.mContext = context;
        this.appointments = list;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return appointments==null?0:appointments.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_book,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Appointment appointment = appointments.get(i);
        if (appointment.getCP_TYPE().equals("1")){
            viewHolder.name.setText("到店"+appointment.getCC_NAME());
        }else {
            viewHolder.name.setText(appointment.getCC_NAME()+"上门服务");
        }

        String t = appointment.getSUB_TIME()+" "+appointment.getDAY();
        viewHolder.time.setText(t);
        viewHolder.type.setText(appointment.getCP_NAME());
        viewHolder.doctor.setText(appointment.getZZ_NAME());
        viewHolder.name1.setText(appointment.getF_NAME());
        int state = appointment.getSTATUS();

        if (state == 2){
            viewHolder.state.setText("预约中");
            viewHolder.operation.setVisibility(View.VISIBLE);
            viewHolder.evaluate.setVisibility(View.GONE);
            viewHolder.nameBlock.setVisibility(View.VISIBLE);
            viewHolder.doctorBlock.setVisibility(View.VISIBLE);
        }else if (state == 5){
            viewHolder.state.setText("到诊");
            viewHolder.operation.setVisibility(View.GONE);
            viewHolder.evaluate.setVisibility(View.VISIBLE);
            viewHolder.nameBlock.setVisibility(View.GONE);
            viewHolder.doctorBlock.setVisibility(View.GONE);
        }else if (state == 1){
            viewHolder.state.setText("取消");
            viewHolder.operation.setVisibility(View.GONE);
            viewHolder.evaluate.setVisibility(View.GONE);
            viewHolder.nameBlock.setVisibility(View.GONE);
            viewHolder.doctorBlock.setVisibility(View.GONE);
        }else if (state == 4){
            viewHolder.state.setText("到诊");
            viewHolder.operation.setVisibility(View.GONE);
            viewHolder.evaluate.setVisibility(View.GONE);
            viewHolder.nameBlock.setVisibility(View.GONE);
            viewHolder.doctorBlock.setVisibility(View.GONE);
        }
        final int position = i;
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.ClickEdit(position);
            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.ClickDel(position);
            }
        });
        viewHolder.evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.ClickEvaluate(position);
            }
        });
        viewHolder.time1.setText(appointment.getNEXTTIME());

        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_book_hospitalname)
        private TextView name;
        @ViewInject(R.id.item_book_time)
        private TextView time;
        @ViewInject(R.id.item_book_type)
        private TextView type;
        @ViewInject(R.id.item_book_state)
        private TextView state;
        @ViewInject(R.id.item_book_time1)
        private TextView time1;
        @ViewInject(R.id.item_book_edit)
        private AutoRelativeLayout edit;
        @ViewInject(R.id.item_book_del)
        private AutoRelativeLayout del;
        @ViewInject(R.id.item_book_operation)
        private AutoLinearLayout operation;
        @ViewInject(R.id.item_book_evaluate)
        private AutoRelativeLayout evaluate;
        @ViewInject(R.id.item_book_doctor)
        private TextView doctor;
        @ViewInject(R.id.item_book_name)
        private TextView name1;
        @ViewInject(R.id.item_book_doctor_block)
        private AutoLinearLayout doctorBlock;
        @ViewInject(R.id.item_book_name_block)
        private AutoLinearLayout nameBlock;

    }

    public interface CallBack{
        public void ClickEdit(int position);
        public void ClickDel(int position);
        public void ClickEvaluate(int position);
    }
}
