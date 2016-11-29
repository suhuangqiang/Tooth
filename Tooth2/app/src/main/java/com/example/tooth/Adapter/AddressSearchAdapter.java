package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shq1 on 2016/11/3.
 */

public class AddressSearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<HashMap<String,String>> list;
    public AddressSearchAdapter(Context context,List<HashMap<String,String>> maps){
        this.mContext = context;
        this.list = maps;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_search_address,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        HashMap<String,String> map = list.get(i);
        viewHolder.addr.setText(map.get("addr"));
        viewHolder.keyword.setText(map.get("keyword"));
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_search_address_addr)
        TextView addr;
        @ViewInject(R.id.item_search_address_keyword)
        TextView keyword;
    }
}
