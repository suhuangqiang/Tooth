package com.example.tooth.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.N;
import com.example.tooth.Activity.AskListActivity;
import com.example.tooth.Activity.CollectionActivity;
import com.example.tooth.Activity.FamilyArchivesActivity;
import com.example.tooth.Activity.FriendsActivity;
import com.example.tooth.Activity.IntegralActivity;
import com.example.tooth.Activity.MyBookActivity;
import com.example.tooth.Activity.MyInfoActivity;
import com.example.tooth.Activity.MyNewsActivity;
import com.example.tooth.Activity.MyOrderActivity;
import com.example.tooth.Activity.MySettingActivity;
import com.example.tooth.Activity.QuestionActivity;
import com.example.tooth.Entity.User;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.NetCodeEnum;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {
    private final String TAG = "MineFragment";
    @ViewInject(R.id.mine_head_img)
    ImageView headImg;
    @ViewInject(R.id.mine_user_nickname)
    TextView nickName;
    @ViewInject(R.id.mine_user_phone)
    TextView phoneNumber;
    @ViewInject(R.id.mine_integral)
    TextView integral;
    @ViewInject(R.id.mine_balance)
    TextView balance;
    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        User user = GlobalUtils.getInstances().getUser();
        //头像
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setCircular(true) //原形图片
                .setCrop(true)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
        if (GlobalUtils.getInstances().getUser().getHEADURL()!=null){
            x.image().bind(headImg,GlobalUtils.getInstances().getUser().getHEADURL(), imageOptions);//绑定图片资源到imagview
        }

        nickName.setText(user.getUSERNAME());
        phoneNumber.setText(user.getPHONE());
        integral.setText(user.getINTEGRAL_NUM());
    }

    /**
     * 消息
     * @param view
     */
    @Event(R.id.mine_news)
    private void OnNewsClick(View view){
        Log.i(TAG,"消息");
        Intent intent = new Intent(getActivity(), MyNewsActivity.class);
        startActivity(intent);
    }

    /**
     * 设置
     * @param view
     */
    @Event(R.id.mine_setting)
    private void OnSettingClick(View view){
        Log.i(TAG,"设置");
        Intent intent = new Intent(getActivity(), MySettingActivity.class);
        startActivity(intent);
    }

    /**
     * 点击头像
     * @param view
     */
    @Event(R.id.mine_head_img)
    private void OnImgClick(View view){
        Intent intent = new Intent(getActivity(), MyInfoActivity.class);
        startActivity(intent);
    }

    /**
     * 积分
     * @param view
     */
    @Event(R.id.mine_integral_click)
    private void OnIntegralClick(View view){
        Log.i(TAG,"积分");
        Intent intent = new Intent(getActivity(), IntegralActivity.class);
        startActivity(intent);
    }

    /**
     * 余额
     * @param view
     */
    @Event(R.id.mine_balance_click)
    private void OnBalanceClick(View view){
        Log.i(TAG,"余额");
    }

    /**
     * 家人档案
     * @param view
     */
    @Event(R.id.mine_family_click)
    private void OnFamilyClick(View view){
        Log.i(TAG,"家人档案");
        Intent intent = new Intent(getActivity(), FamilyArchivesActivity.class);
        startActivity(intent);
    }

    /**
     * 我的预约
     * @param view
     */
    @Event(R.id.mine_book_click)
    private void OnBookClick(View view){
        Log.i(TAG,"我的预约");
        Intent intent = new Intent(getActivity(), MyBookActivity.class);
        startActivity(intent);
    }

    /**
     * 我的订单
     * @param view
     */
    @Event(R.id.mine_order_click)
    private void OnOrderClick(View view){
        Log.i(TAG,"我的订单");
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        startActivity(intent);
    }

    /**
     * 我的收藏
     * @param view
     */
    @Event(R.id.mine_collection_click)
    private void OnCollectionClick(View view){
        Log.i(TAG,"我的收藏");
        Intent intent = new Intent(getActivity(), CollectionActivity.class);
        startActivity(intent);
    }

    /**
     * 我的问卷
     * @param view
     */
    @Event(R.id.mine_question_click)
    private void OnQuestionClick(View view){
        Log.i(TAG,"我的问卷");
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 我的问问
     * @param view
     */
    @Event(R.id.mine_ask_click)
    private void OnAskClick(View view){
        Log.i(TAG,"我的问问");
        Intent intent = new Intent(getActivity(), AskListActivity.class);
        startActivity(intent);
    }

    /**
     * 牙友圈
     * @param view
     */
    @Event(R.id.mine_friends_click)
    private void OnFriendsClick(View view){
        Log.i(TAG,"牙友圈");
        Intent intent = new Intent(getActivity(), FriendsActivity.class);
        startActivity(intent);
    }


    public void onEvent(String Name) {
        Log.i("aaa","MineFragment onevent");
        nickName.setText(Name);
    }
}
