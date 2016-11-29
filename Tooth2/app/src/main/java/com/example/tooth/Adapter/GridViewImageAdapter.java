package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tooth.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/1.
 */

public class GridViewImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> path;
    public GridViewImageAdapter(Context context,List<String> list){
        this.mContext = context;
        this.path = list;
    }
    @Override
    public int getCount() {
        return path==null?0:path.size();
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_img,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        x.image().bind(viewHolder.imageView,path.get(i),imageOptions);
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_img_pic)
        ImageView imageView;
    }
}
