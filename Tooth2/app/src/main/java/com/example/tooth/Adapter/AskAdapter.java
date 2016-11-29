package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/31.
 */
public class AskAdapter extends BaseAdapter {
    private Context mContext;
    public List<String> path;
    public DelListener delListener;

    public DelListener getDelListener() {
        return delListener;
    }

    public void setDelListener(DelListener delListener) {
        this.delListener = delListener;
    }

    public AskAdapter(Context context, List<String> list){
        this.mContext = context;
        this.path = list;
    }
    @Override
    public int getCount() {
        return path==null?0:path.size();
    }

    @Override
    public Object getItem(int i) {
        return path.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_ask,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        if (path.get(i).equals(DataDictionary.MOREN)){
            viewHolder.img.setImageResource(R.mipmap.addimage);
            viewHolder.del.setVisibility(View.GONE);
        }else {

            x.image().bind(viewHolder.img,path.get(i));
            viewHolder.del.setVisibility(View.VISIBLE);
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delListener.onDel(i);
                }
            });
        }
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_ask_img)
        private ImageView img;
        @ViewInject(R.id.item_ask_del)
        private ImageView del;
    }
    public interface DelListener{
        void onDel(int position);
    }
}
