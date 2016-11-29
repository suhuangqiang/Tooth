package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Comment;
import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/1.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private int size = 0;
    private List<Comment> comments;
    public CommentAdapter(Context context,List<Comment> list){
        this.mContext = context;
        this.comments = list;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getCount() {
        return comments==null?0:comments.size();
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_comment,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Comment comment = comments.get(i);
        viewHolder.content.setText(comment.getRR_COMENTS());
        viewHolder.name.setText(comment.getNAME());
        viewHolder.time.setText(comment.getTIME());

        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_comment_head)
        ImageView head;
        @ViewInject(R.id.item_commnent_name)
        TextView name;
        @ViewInject(R.id.item_comment_type)
        TextView type;
        @ViewInject(R.id.item_comment_time)
        TextView time;
        @ViewInject(R.id.item_comment_content)
        TextView content;
    }
}
