package com.example.tooth.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/27.
 */
public class BookTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<HashMap<String,String>> data;
    public BookTypeAdapter(Context context,List<HashMap<String,String>> list){
        this.mContext = context;
        this.data = list;
    }
    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_book_type,null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        String type = data.get(i).get(DataDictionary.TYPE);
        if (type.equals("0")){
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.tv.setTextColor(Color.parseColor("#343434"));
        }else if (type.equals("1")){
            viewHolder.img.setVisibility(View.VISIBLE);
            viewHolder.tv.setTextColor(Color.parseColor("#FE6B26"));
        }
        viewHolder.tv.setText(data.get(i).get("TEXT"));
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_book_type_tv)
        private TextView tv;
        @ViewInject(R.id.item_book_type_img)
        private ImageView img;
    }
}
