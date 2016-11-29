package com.example.tooth.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 苏煌强 on 2016/10/26.
 */
public class PopLVAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<HashMap<String,String>> names;
    public PopLVAdapter(Context context,ArrayList<HashMap<String,String>> list){
        this.mContext = context;
        this.names = list;
    }
    @Override
    public int getCount() {
        return names!=null?names.size():0;
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_text,null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tv.setText(names.get(i).get("name"));
        if (names.get(i).get("type").equals("1")){
            viewHolder.tv.setTextColor(Color.parseColor("#FE6B26"));
        }else {
            viewHolder.tv.setTextColor(Color.parseColor("#343434"));
        }

        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_tv)
        private TextView tv;
    }

}
