package com.example.tooth.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tooth.Adapter.CommentAdapter;
import com.example.tooth.Adapter.GridViewImageAdapter;
import com.example.tooth.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_friends_detail)
public class FriendsDetailActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.titlebar_img)
    ImageView img_return;
    @ViewInject(R.id.activity_friends_detail_good_img)
    ImageView img_good;
    @ViewInject(R.id.activity_friends_detail_good_text)
    TextView tv_good;
    @ViewInject(R.id.activity_friends_detail_lv)
    ListView lv;

    private View HeadView;
    private CommentAdapter adapter;
    private boolean isGood = false;//是否点赞
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        back.setVisibility(View.GONE);
        title.setText("详情");
        img_return.setImageResource(R.mipmap.return_);

        intHeadView();
        lv.addHeaderView(HeadView);
       /* adapter = new CommentAdapter(getBaseContext());
        lv.setAdapter(adapter);*/
    }

    /**
     * 初始化头布局
     */
    private void intHeadView(){
        HeadView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_friends,null);
        ViewHolder viewHolder = new ViewHolder();
        x.view().inject(viewHolder,HeadView);
        viewHolder.img.setVisibility(View.GONE);

        List<String> path = new ArrayList<>();
        path.add("http://pic33.nipic.com/20130916/3420027_192919547000_2.jpg");
        path.add("http://img4.imgtn.bdimg.com/it/u=2291015098,628551162&fm=21&gp=0.jpg");
        path.add("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");

        GridViewImageAdapter adapter1 = new GridViewImageAdapter(getBaseContext(),path);
        viewHolder.gv.setVisibility(View.VISIBLE);
        viewHolder.gv.setAdapter(adapter1);

    }

    /**
     * 点赞
     * @param view
     */
    @Event(R.id.activity_friends_detail_good_click)
    private void OnGoodClick(View view){
        isGood = !isGood;
        if (isGood){
            img_good.setImageResource(R.mipmap.like2_zx_off);
            tv_good.setTextColor(Color.parseColor("#FE6B26"));
        }else {
            img_good.setImageResource(R.mipmap.like_zx);
            tv_good.setTextColor(Color.parseColor("#343434"));
        }
    }
    /**
     * 评论
     * @param view
     */
    @Event(R.id.activity_friends_detail_comment_click)
    private void OnCommentClick(View view){}


    class ViewHolder{
        @ViewInject(R.id.item_friends_headimg)
        ImageView headimg;
        @ViewInject(R.id.item_friends_name)
        TextView name;
        @ViewInject(R.id.item_friends_content)
        TextView content;

        ImageView goodImg;
        @ViewInject(R.id.item_friends_gv)
        GridView gv;
        @ViewInject(R.id.item_friends_img)
        ImageView img;
    }
}
