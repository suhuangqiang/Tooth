package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Dynamic;
import com.example.tooth.R;
import com.example.tooth.View.CommentListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/1.
 */

public class AskListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Dynamic> dynamics;
    public AskListAdapter(Context context,List<Dynamic> list){
        this.mContext = context;
        this.dynamics = list;
    }
    @Override
    public int getCount() {
        return dynamics==null?0:dynamics.size();
    }

    @Override
    public Object getItem(int i) {
        return dynamics.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_ask_list,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Dynamic dynamic = dynamics.get(i);
        viewHolder.name.setText(dynamic.getNAME());
        viewHolder.time.setText(dynamic.getTIME());
        viewHolder.content.setText(dynamic.getMGSCONTENT());
        x.image().bind(viewHolder.img_head,dynamic.getICONNAME());
        List<String> pic = dynamic.getFC_PIC();
        if (pic==null || pic.size() == 0){
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.gv.setVisibility(View.GONE);
        }else if (pic.size() == 1){
            viewHolder.img.setVisibility(View.VISIBLE);
            viewHolder.gv.setVisibility(View.GONE);
            String url = pic.get(0);
            x.image().bind(viewHolder.img,url);
        }else {
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.gv.setVisibility(View.VISIBLE);
            GridViewImageAdapter adapter = new GridViewImageAdapter(mContext,pic);
            viewHolder.gv.setAdapter(adapter);
        }
        FriendCommentAdapter adapter = new FriendCommentAdapter(mContext,dynamic.getPL());
        viewHolder.commentListView.setAdapter(adapter);
        int pl_count = dynamic.getPL()==null?0:dynamic.getPL().size();
        viewHolder.commentCount.setText(pl_count+"");
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.item_ask_list_headimg)
        ImageView img_head;
        @ViewInject(R.id.item_ask_list_name)
        TextView name;
        @ViewInject(R.id.item_ask_list_time)
        TextView time;
        @ViewInject(R.id.item_ask_list_content)
        TextView content;
        @ViewInject(R.id.item_ask_list_img)
        ImageView img;
        @ViewInject(R.id.item_ask_list_gv)
        GridView gv;
        @ViewInject(R.id.item_ask_list_commentlist)
        CommentListView commentListView;
        @ViewInject(R.id.item_ask_list_commentcount)
        TextView commentCount;

    }
}
