package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Collection;
import com.example.tooth.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/8.
 */

public class ShopAdapter extends BaseAdapter {
    private Context mContext;
    private List<Collection> collectionList;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .build();

    public ShopAdapter(Context context,List<Collection> list){
        mContext = context;
        collectionList = list;
    }
    @Override
    public int getCount() {
        return collectionList.size();
    }

    @Override
    public Object getItem(int i) {
        return collectionList.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_shop,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Collection collection = collectionList.get(i);
        viewHolder.title.setText(collection.getNAME());
        viewHolder.money.setText(collection.getCONTENT());

        x.image().bind(viewHolder.img,collection.getPIC(),imageOptions);
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_shop_img)
        ImageView img;
        @ViewInject(R.id.item_shop_title)
        TextView title;
        @ViewInject(R.id.item_shop_car)
        ImageView car;
        @ViewInject(R.id.item_shop_money)
        TextView money;
    }
}
