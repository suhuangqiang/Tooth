package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 苏煌强 on 2016/10/31.
 */
public class BookNewsAdapter extends BaseAdapter {
    private Context mContext;
    private OnDelClickListener onDelClickListener;

    public OnDelClickListener getOnDelClickListener() {
        return onDelClickListener;
    }

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }

    public BookNewsAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return 10;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_book_news,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelClickListener.onDel(i);
            }
        });
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_book_news_img)
        private ImageView img;
        @ViewInject(R.id.item_book_news_content)
        private TextView content;
        @ViewInject(R.id.item_book_news_click)
        private TextView click;
        @ViewInject(R.id.item_book_news_time)
        private TextView time;
        @ViewInject(R.id.item_book_news_del)
        private ImageView del;
    }
    interface OnDelClickListener{
        void onDel(int position);
    }
}
