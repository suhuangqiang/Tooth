package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.Entity.Address;
import com.example.tooth.R;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/31.
 */
public class AddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<Address> list;
    private AdapterListener adapterListener;

    public AdapterListener getAdapterListener() {
        return adapterListener;
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public AddressAdapter(Context context,List<Address> list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_address,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.Edit(i);
            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.Del(i);
            }
        });

        Address address = list.get(i);
        viewHolder.tv_name.setText(address.getNAME());
        viewHolder.tv_phone.setText(address.getMOBILE());
        String addressStr = address.getPROVINCE()+address.getCITY()+address.getAREA()+address.getADDRESS();
        viewHolder.tv_content.setText(addressStr);
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_address_name)
        private TextView tv_name;
        @ViewInject(R.id.item_address_phone)
        TextView tv_phone;
        @ViewInject(R.id.item_address_content)
        TextView tv_content;
        @ViewInject(R.id.item_address_edit)
        AutoRelativeLayout edit;
        @ViewInject(R.id.item_address_del)
        AutoRelativeLayout del;
    }
    public interface AdapterListener{
        void Edit(int position);
        void Del(int position);
    }
}
