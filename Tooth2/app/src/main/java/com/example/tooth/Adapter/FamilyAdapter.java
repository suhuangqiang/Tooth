package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.Entity.Family;
import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/1.
 */

public class FamilyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Family> families;
    private FamilyListener listener;
    public FamilyAdapter(Context context,List<Family> list){
        this.mContext = context;
        this.families = list;
    }

    public FamilyListener getListener() {
        return listener;
    }

    public void setListener(FamilyListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return families==null?0:families.size();
    }

    @Override
    public Object getItem(int i) {
        return families.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_family,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Family family = families.get(i);
        viewHolder.relationship.setText(family.getRELATIONSHIP());
        viewHolder.birthday.setText(family.getBIR_DATE());
        viewHolder.name.setText(family.getF_NAME());
        if (family.getSEX() == 0){
            viewHolder.sex.setText("男");
        }else if (family.getSEX() == 1){
            viewHolder.sex.setText("女");
        }
        viewHolder.phone.setText(family.getPHONE());
        final int position = i;
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelClick(position);
            }
        });
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_family_relationship)
        TextView relationship;
        @ViewInject(R.id.item_family_name)
        TextView name;
        @ViewInject(R.id.item_family_sex)
        TextView sex;
        @ViewInject(R.id.item_family_birthday)
        TextView birthday;
        @ViewInject(R.id.item_family_phone)
        TextView phone;
        @ViewInject(R.id.item_family_del)
        TextView del;
    }
    public interface FamilyListener{
        public void onDelClick(int position);
    }
}
