package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.AskAdapter;
import com.example.tooth.Adapter.BookAdapter;
import com.example.tooth.Adapter.FriendsAdapter;
import com.example.tooth.Entity.ActivityResult;
import com.example.tooth.Entity.Comment;
import com.example.tooth.Entity.Dynamic;
import com.example.tooth.Entity.Good;
import com.example.tooth.Entity.User;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.DynamicMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.DynamicParamter;
import com.example.tooth.Parameter.DzParamter;
import com.example.tooth.Parameter.PlParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

@ContentView(R.layout.activity_friends)
public class FriendsActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.titlebar_img)
    ImageView title_img;
    @ViewInject(R.id.activity_friends_lv)
    ListView lv;
    @ViewInject(R.id.activity_friends_pcf)
    PtrClassicFrameLayout pcf;
    @ViewInject(R.id.activity_friends_comment)
    AutoRelativeLayout comment;
    @ViewInject(R.id.activity_friends_et)
    EditText et;


    private View HeadView,FooterView;
    private FriendsAdapter adapter;
    private User user;
    private List<Dynamic> list;
    private int index;

    private int pageIndex = 1;//加载页数
    private int pageNum = 1;//每页加载数量
    private int FirstVisibleItem = 0,lastVisbleItem;
    private boolean hasMoreData = true;
    private boolean isRefresh = false;
    private TextView tv_footeview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("牙友圈");
        title_img.setImageResource(R.mipmap.write);

        user = GlobalUtils.getInstances().getUser();
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                tv_footeview.setText("点击加载更多");
               getData();
            }
        });

        initHeadView();
        initFooterView();
        //getData();
        pcf.autoRefresh();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Intent intent = new Intent(getBaseContext(),FriendsDetailActivity.class);
                startActivity(intent);*/
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int lastIndex = adapter.getCount() - 1;
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastVisbleItem == lastIndex) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    if (hasMoreData){
                        getData();
                    }else {
                        Toast.makeText(getBaseContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                        tv_footeview.setText("没有更多数据了");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                FirstVisibleItem = i;
                lastVisbleItem = i + i1 - 1;
            }
        });
    }

    /**
     * 初始化底部view
     */
    private void initFooterView(){
        FooterView = LayoutInflater.from(getBaseContext()).inflate(R.layout.footerview_listview,null);
        lv.addFooterView(FooterView);
        tv_footeview = (TextView)FooterView.findViewById(R.id.footerview_listview_tv);
        AutoRelativeLayout click = (AutoRelativeLayout)FooterView.findViewById(R.id.footerview_listview_click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }



    /**
     * 初始化headview
     */
    private void initHeadView(){
        HeadView = LayoutInflater.from(getBaseContext()).inflate(R.layout.head_view_friends,null);
        lv.addHeaderView(HeadView);
        ImageView headImg = (ImageView)HeadView.findViewById(R.id.head_view_friend_headimg);
        ImageView backImg = (ImageView)HeadView.findViewById(R.id.head_view_friend_img);
        TextView name = (TextView) HeadView.findViewById(R.id.head_view_friend_name);

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setCircular(true) //原形图片
                .setCrop(true)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();

        x.image().bind(headImg,GlobalUtils.getInstances().getUser().getHEADURL());
        name.setText(user.getUSERNAME());
    }



    /**
     * 发布
     * @param view
     */
    @Event(R.id.titlebar_img)
    private void OnWriteClick(View view){
        Intent intent = new Intent(this,AskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",0);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 获取数据
     */
    private void getData(){
        String url = URLList.Domain + URLList.FriendsAskList;
        DynamicParamter paramter = new DynamicParamter();
        paramter.setType(0);
        paramter.setPageIndex(pageIndex);
        pageIndex ++;
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.FRIENDS_LIST);
    }


    public void onEvent(BaseMessage baseMessage) {
        Log.i("friends","onEvent");
        if (baseMessage != null && (baseMessage.getType() == ModeEnum.FRIENDS_DZ || baseMessage.getType() == ModeEnum.FRIENDS_PL)){
            Toast.makeText(getBaseContext(),baseMessage.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(DynamicMessage message) {
        Log.i("friend","onEvent");
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                List<Dynamic> dynamics = message.getData();
                if (isRefresh || list == null || list.size()==0){
                    list = message.getData();
                    adapter = new FriendsAdapter(getBaseContext(),list);
                    adapter.setCommentListener(new FriendsAdapter.CommentListener() {
                        @Override
                        public void onGoodClick(int position) {
                            String url = URLList.Domain + URLList.DZ;
                            Dynamic dynamic = list.get(position);
                            DzParamter paramter = new DzParamter();
                            paramter.setId(dynamic.getZZE_FRIENDSCIRCLE_ID());
                            int state = dynamic.getISPAISE();
                            Log.i("friend","点赞情况："+state);
                            paramter.setStatus(state);
                            BaseParameter baseParameter = new BaseParameter();
                            baseParameter.setData(paramter);
                            baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
                            NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.FRIENDS_DZ);
                            Good good = new Good();
                            good.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
                            good.setNAME(GlobalUtils.getInstances().getUser().getUSERNAME());
                            if (state == 0){
                                state = 1;
                                if (list.get(position).getDZ() == null){
                                    list.get(position).setDZ(new ArrayList<Good>());
                                }
                                list.get(position).getDZ().add(good);
                            }else if (state == 1){
                                state = 0;
                                for (int i=0;i<list.get(position).getDZ().size();i++){
                                    Good good1 = list.get(position).getDZ().get(i);
                                    if (good.getUSER_ID().equals(good1.getUSER_ID())){
                                        Log.i("friend","列表有点赞名");
                                        list.get(position).getDZ().remove(i);
                                        i --;
                                    }
                                }
                                //list.get(position).getDZ().remove(good);
                            }
                            Log.i("friend","点赞数量："+list.get(position).getDZ().size());
                            list.get(position).setISPAISE(state);
                            adapter.notifyDataSetChanged();
                            Log.i("friend","adapter index:"+position);
                        }

                        @Override
                        public void onCommentClick(int position) {
                            Log.i("friend","comment:"+position);
                            comment.setVisibility(View.VISIBLE);
                            index = position;
                        }
                    });
                    lv.setAdapter(adapter);
                    isRefresh = false;
                }else {
                    list.addAll(dynamics);
                    adapter.notifyDataSetChanged();
                }
                if (dynamics.size() < pageNum){
                    hasMoreData = false;
                }

            }
        }
        pcf.refreshComplete();
    }

    /**
     * 发布返回
     * @param result
     */
    public void onEvent(ActivityResult result) {
        if (result.getType() == ActivityResult.PublicFriends){
            pcf.autoRefresh();
        }
    }

    /**
     * 点击 发送评论按钮
     * @param view
     */
    @Event(R.id.activity_friends_btn)
    private void OnBtnClick(View view){
        comment.setVisibility(View.GONE);
        String content = et.getText().toString();
        et.setText("");
        if (content == null || content.equals("")){
            return;
        }
        Comment comment = new Comment();
        comment.setFIRSTNAME(GlobalUtils.getInstances().getUser().getUSERNAME());
        comment.setRR_COMENTS(content);
        list.get(index).getPL().add(comment);
        String url = URLList.Domain + URLList.PL;
        PlParamter plParamter = new PlParamter();
        plParamter.setContent(content);
        plParamter.setId(list.get(index).getZZE_FRIENDSCIRCLE_ID());
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(plParamter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,ModeEnum.FRIENDS_PL);
        adapter.notifyDataSetChanged();
    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    @Event(R.id.activity_friends_comment)
    private void OnComentClick(View view){
        comment.setVisibility(View.GONE);
    }
}
