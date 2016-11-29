package com.example.tooth.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/26.
 */
public class PopTimeAdapter extends BaseAdapter {
    private Context mContext;
    private List<HashMap<String,String>> date;
    public PopTimeAdapter(Context context,List<HashMap<String,String>> list){
        mContext = context;
        this.date = list;
    }
    @Override
    public int getCount() {
        return date==null?0:date.size();
    }

    @Override
    public Object getItem(int i) {
        return date.get(i).get(DataDictionary.POP_TIME_TIME);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_pop_time,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        String type = date.get(i).get(DataDictionary.POP_TIME_TYPE);
        if (type.equals("1")){
            viewHolder.btn.setBackgroundResource(R.drawable.btn_shape1);
            viewHolder.btn.setTextColor(Color.parseColor("#343434"));
        }else if (type.equals("0")){
            viewHolder.btn.setBackgroundResource(R.drawable.btn_shape2);
            viewHolder.btn.setTextColor(Color.parseColor("#343434"));
        }else if (type.equals("2")){
            viewHolder.btn.setBackgroundResource(R.drawable.btn_shape);
            viewHolder.btn.setTextColor(Color.parseColor("#FFFFFF"));
        }
        viewHolder.btn.setText(date.get(i).get(DataDictionary.POP_TIME_TIME));

        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_pop_time_btn)
        private Button btn;
    }
}
