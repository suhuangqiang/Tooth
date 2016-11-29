package com.example.tooth.Activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Entity.Photo;
import com.example.tooth.Entity.User;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.UserParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.BitMapUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.Utils.PromptManage;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

@ContentView(R.layout.activity_my_info)
public class MyInfoActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView tv_title;
    @ViewInject(R.id.my_info_headimg)
    ImageView headImg;
    @ViewInject(R.id.my_info_nickname)
    TextView tv_nickName;
    @ViewInject(R.id.my_info_phone)
    TextView tv_phone;
    @ViewInject(R.id.my_info_weixin)
    TextView tv_weixin;
    @ViewInject(R.id.my_info_qq)
    TextView tv_qq;

    private int REQUEST_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_my_info);*/
        init();
    }

    public void init() {
        User user = GlobalUtils.getInstances().getUser();
        tv_title.setText("我的信息");
        tv_nickName.setText(user.getUSERNAME());
        tv_phone.setText(user.getPHONE());
        if (user.getHEADURL()!=null){
            x.image().bind(headImg,user.getHEADURL());
        }
        if (user.getWEIXIN_BIND()!=null && user.getWEIXIN_BIND().equals("1")){
            tv_weixin.setText("已绑定");
        }else {
            tv_weixin.setText("未绑定");
        }
        if (user.getQQ_BIND()!=null && user.getQQ_BIND().equals("1")){
            tv_qq.setText("已绑定");
        }else {
            tv_qq.setText("未绑定");
        }

    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 头像
     * @param view
     */
    @Event(R.id.my_info_headimg_click)
    private void OnHeadClick(View view){
        MultiImageSelector.create(getBaseContext())
                .showCamera(true) // show camera or not. true by default
                .count(1) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                //.origin(list) // original select data set, used width #.multi()
                .start(this,REQUEST_IMAGE);
    }

    /**
     * 昵称
     * @param view
     */
    @Event(R.id.my_info_nickname_click)
    private void OnNicknameClick(View view){
        Intent intent = new Intent(this,EditNickNameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nickname",tv_nickName.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 手机号
     * @param view
     */
    @Event(R.id.my_info_phone_click)
    private void OnPhoneClick(View view){
        Intent intent = new Intent(this,BindAccountActivity.class);
        startActivity(intent);
    }

    /**
     * 微信号
     * @param view
     */
    @Event(R.id.my_info_weixin_click)
    private void OnWeixinClick(View view){}

    /**
     * QQ号
     * @param view
     */
    @Event(R.id.my_info_qq_click)
    private void OnQQClick(View view){}

    /**
     * 地址管理
     * @param view
     */
    @Event(R.id.my_info_address_click)
    private void OnAddressClick(View view){
        Intent intent = new Intent(this,AddressActivity.class);
        startActivity(intent);
    }

    public void onEvent(String Name) {
        tv_nickName.setText(Name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && data!=null){

            List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if(list!=null && list.size()>0){
                String imgURL = list.get(0);
                //x.image().bind(headImg,imgURL);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeFile(imgURL);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();

                Intent intent = new Intent(this, CutActivity.class);
                intent.putExtra("bitmap", datas);
                startActivity(intent);
            }

        }
    }

    public void onEvent(BaseMessage message) {
        if (message != null && message.getCode().equals(NetCodeEnum.SUCCESS)){
            PromptManage.getInstance().CloseProgressDialog();
            Photo photo = (Photo)message.getData();
            GlobalUtils.getInstances().getUser().setHEADURL(photo.getPhotoURL());
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }else if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }
    }


    public void onEvent(Bitmap bitmap) {
        headImg.setImageBitmap(bitmap);
        PromptManage.getInstance().ShowProgressDialog(MyInfoActivity.this,"上传头像中");
        String photoBase64 = BitMapUtils.encode(bitmap);
        String url = URLList.Domain + URLList.UpdateHeadUrl;
        UserParamter paramter = new UserParamter();
        paramter.setPhotoBase64(photoBase64);
        BaseMessage message = new BaseMessage();
        message.setData(paramter);
        message.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,message, ModeEnum.BASE);
    }
}
