package com.example.tooth.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.Entity.Comment;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.example.tooth.Adapter.FriendsAdapter.*;

/**
 * Created by shq1 on 2016/11/14.
 */

public class FriendCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> comments;

    public FriendCommentAdapter(Context context,List<Comment> list){
        this.mContext = context;
        this.comments = list;
    }
    @Override
    public int getCount() {
        return comments==null?0:comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_friend_comment,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Comment comment = comments.get(i);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        SpannableString spanableInfo = new SpannableString(comment.getFIRSTNAME());
        int start = 0;
        int end = spanableInfo.length();
        spanableInfo.setSpan(new Clickable(l),start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tv.setText(spanableInfo);
        if (comment.getSECONDNAME() !=null && !comment.getSECONDUSER().equals("")){
            viewHolder.tv.append(" 回复 ");
            SpannableString spanableInfo1 = new SpannableString(comment.getSECONDNAME());
            int start1 = 0;
            int end1 = comment.getSECONDNAME().length();
            spanableInfo1.setSpan(new Clickable(l),start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tv.append(spanableInfo1);
        }
        viewHolder.tv.append(":");
        viewHolder.tv.append(comment.getRR_COMENTS());
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_friend_comment_tv)
        TextView tv;
    }
    /**
     * 内部类，用于截获点击富文本后的事件
     */
    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(R.color.title_bar_blue);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }
}
