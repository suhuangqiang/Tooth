package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.Entity.FamilyRecord;
import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/23.
 */

public class FamilyDetailAdapter extends BaseAdapter {
    private List<FamilyRecord> list;
    private Context mContext;
    public FamilyDetailAdapter(Context context,List<FamilyRecord> records){
        list = records;
        mContext = context;
    }
    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_family_detail,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        FamilyRecord familyRecord = list.get(i);
        viewHolder.time.setText(familyRecord.getTIME());
        viewHolder.name.setText(familyRecord.getCC_NAME());
        viewHolder.type.setText(familyRecord.getCP_ID());
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_family_detail_time)
        TextView time;
        @ViewInject(R.id.item_family_detail_name)
        TextView name;
        @ViewInject(R.id.item_family_detail_type)
        TextView type;

    }
}
