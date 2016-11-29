package com.example.tooth.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Entity.Dynamic;
import com.example.tooth.Entity.Good;
import com.example.tooth.PhotoView.ImagePagerActivity;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.View.CollapsibleTextView;
import com.example.tooth.View.CommentListView;
import com.example.tooth.View.MyGridView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by shq1 on 2016/11/1.
 */

public class FriendsAdapter extends BaseAdapter {
    private Context mContext;
    private CommentListener commentListener;
    private List<Dynamic> list;
    private FriendCommentAdapter adapter;
    public CommentListener getCommentListener() {
        return commentListener;
    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public FriendsAdapter(Context context,List<Dynamic> dynamics){
        this.mContext = context;
        this.list = dynamics;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_friends,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.init(i);
        Dynamic dynamic = list.get(i);
        adapter = new FriendCommentAdapter(mContext,dynamic.getPL());
        viewHolder.content.setText(dynamic.getMGSCONTENT());
        viewHolder.name.setText(dynamic.getNAME());
        viewHolder.time.setText(dynamic.getTIME());
        x.image().bind(viewHolder.headimg,dynamic.getICONNAME());
        final List<String> photots = dynamic.getFC_PIC();
        if (photots == null || photots.size() == 0){
            viewHolder.gv.setVisibility(View.GONE);
            viewHolder.img.setVisibility(View.GONE);
        }else if (photots.size() == 1){
            viewHolder.gv.setVisibility(View.GONE);
            viewHolder.img.setVisibility(View.VISIBLE);
            x.image().bind(viewHolder.img,photots.get(0));
        }else {
            viewHolder.gv.setVisibility(View.VISIBLE);
            viewHolder.img.setVisibility(View.GONE);
            GridViewImageAdapter gridViewImageAdapter = new GridViewImageAdapter(mContext,photots);
            viewHolder.gv.setAdapter(gridViewImageAdapter);
        }
        /**
         * 点击查看大图
         */
        viewHolder.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ImagePagerActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                ArrayList<String> urls = (ArrayList<String>) photots;
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, i);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mContext.startActivity(intent);
            }
        });
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ImagePagerActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                Log.i("friend","点击单图:"+photots.size());
                ArrayList<String> urls = (ArrayList<String>) photots;
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mContext.startActivity(intent);
            }
        });

        viewHolder.commentListView.setAdapter(adapter);



        viewHolder.content.setText(dynamic.getMGSCONTENT());


        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " you are clicking ~",
                        Toast.LENGTH_SHORT).show();
            }
        };

        viewHolder.good.setMovementMethod(LinkMovementMethod.getInstance());
        //获取Drawable资源
        Drawable d = mContext.getResources().getDrawable(R.drawable.like_zx);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //创建ImageSpan
        ImageSpan span = new ImageSpan(d,ImageSpan.ALIGN_BASELINE);
        SpannableString s = new SpannableString("  ");
        s.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.good.setText(s);

        if (dynamic.getDZ() == null || dynamic.getDZ().size()<=0){

            viewHolder.good.setVisibility(View.GONE);
        }else {
            viewHolder.good.setVisibility(View.VISIBLE);
            for (int j=0;j<dynamic.getDZ().size();j++){
                Good good = dynamic.getDZ().get(j);
                SpannableString spanableInfo = new SpannableString(good.getNAME());
                int start = 0;
                int end = spanableInfo.length();
                spanableInfo.setSpan(new Clickable(l),start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.good.append(spanableInfo);
                if (j!=dynamic.getDZ().size()-1){
                    viewHolder.good.append(",");
                }
            }
        }


        return view;
    }
    class ViewHolder implements View.OnClickListener{
        @ViewInject(R.id.item_friends_headimg)
        ImageView headimg;
        @ViewInject(R.id.item_friends_name)
        TextView name;
        @ViewInject(R.id.item_friends_content)
        CollapsibleTextView content;
        @ViewInject(R.id.item_friends_gv)
        MyGridView gv;
        @ViewInject(R.id.item_friends_img)
        ImageView img;
        @ViewInject(R.id.item_friends_pop)
        ImageView popImg;
        @ViewInject(R.id.item_friends_comment_lv)
        CommentListView commentListView;
        @ViewInject(R.id.item_friends_good)
        TextView good;
        @ViewInject(R.id.item_friends_time)
        TextView time;

        private PopupWindow window;
        private AutoRelativeLayout pop_comment,pop_good;
        private int[] location;
        private int index;


        private void init(int i){
            index = i;
            popImg.setOnClickListener(this);
            View view1 = LayoutInflater.from(mContext).inflate(R.layout.pop_comment,null);
            view1.measure(0,0);
            window = new PopupWindow(view1,view1.getMeasuredWidth(),view1.getMeasuredHeight(),true);
            window.setFocusable(true);
            window.setOutsideTouchable(true);
            window.setBackgroundDrawable(new BitmapDrawable());
            window.setAnimationStyle(R.style.comment_anim);
            pop_comment = (AutoRelativeLayout)view1.findViewById(R.id.pop_comment_comment);
            pop_good = (AutoRelativeLayout)view1.findViewById(R.id.pop_comment_good);

            pop_comment.setOnClickListener(this);
            pop_good.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pop_comment_comment:
                    commentListener.onCommentClick(index);
                    window.dismiss();
                    break;
                case R.id.pop_comment_good:
                    Log.i("friend","adapter index:"+index);
                    commentListener.onGoodClick(index);
                    window.dismiss();
                    break;
                case R.id.item_friends_pop:
                    location = new int[2];
                    popImg.getLocationOnScreen(location);
                    window.showAtLocation(popImg, Gravity.NO_GRAVITY, location[0] - window.getWidth(), location[1]);
                    break;
            }
        }
    }
    public interface CommentListener{
        void onGoodClick(int position);
        void onCommentClick(int position);
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
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }
}
