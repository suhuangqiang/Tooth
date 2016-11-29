package com.example.tooth.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.AskAdapter;
import com.example.tooth.Entity.ActivityResult;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.PhotoMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.PublishFriendParamter;
import com.example.tooth.Parameter.UploadPicParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.BitMapUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.Utils.PromptManage;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

@ContentView(R.layout.activity_ask)
public class AskActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.titlebar_tv)
    TextView public_tv;
    @ViewInject(R.id.activity_ask_gridview)
    GridView gv;
    @ViewInject(R.id.activity_ask_et)
    EditText et;

    private ArrayList<String> list;
    private int REQUEST_IMAGE = 1;
    private List<String> path;
    private AskAdapter adapter;
    String urls = ""; //图片路径
    private int index = -1;//点击某张图片的下标，更换单张图片
    private int PicCount = 0;//已上传图片数量
    private int allCount;//所有需要上传的图片
    private int CurrentImg = 0;//当前上传图片
    private int type;//判断是问问1或者牙友圈0
    private Handler imgHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        type = this.getIntent().getExtras().getInt("TYPE");
        if (type == 0){
            title.setText("说说");
        }else if (type == 1){
            title.setText("问问");
        }
        public_tv.setText("发布");
        list = new ArrayList<>();
        path = new ArrayList<>();
        path.add(DataDictionary.MOREN);
        adapter = new AskAdapter(getBaseContext(),path);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (path.get(i).equals(DataDictionary.MOREN)){
                    int num = path.size();
                    if (path.get(path.size()-1).equals(DataDictionary.MOREN)){
                        num --;
                    }
                    StartImageSelect(9-num);
                }else {
                    index = i;
                    StartImageSelect(1);
                }
            }
        });
        adapter.setDelListener(new AskAdapter.DelListener() {
            @Override
            public void onDel(int position) {
                path.remove(position);
                if (!path.get(path.size()-1).equals(DataDictionary.MOREN)){
                    path.add(DataDictionary.MOREN);
                }
                adapter.notifyDataSetChanged();
            }
        });

        imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                UploadPic(path.get(CurrentImg));
                CurrentImg ++;
                return false;
            }
        });
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
     * 开启选择照片
     */
    private void StartImageSelect(int i){
        MultiImageSelector.create(getBaseContext())
                .showCamera(true) // show camera or not. true by default
                .count(i) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                //.origin(list) // original select data set, used width #.multi()
                .start(this,REQUEST_IMAGE);
    }

    /**
     * 发布
     * @param view
     */
    @Event(R.id.titlebar_tv)
    private void OnPubilcClick(View view){
        String content = et.getText().toString();
        if (content == null || content.equals("")){
            Toast.makeText(getBaseContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        PromptManage.getInstance().ShowProgressDialog(AskActivity.this,"发布中");
        if (path.get(path.size()-1).equals(DataDictionary.MOREN)){
            path.remove(path.size()-1);
        }
        imgHandler.sendEmptyMessage(0);
        allCount = path.size() - 1;
        /*for (int i=0;i<path.size();i++){
            UploadPic(path.get(i));
            Log.i("publish","img path:"+path.get(i));
        }*/

    }

    /**
     * 上传图片
     * @param path
     */
    private void UploadPic(String path){
        String url = URLList.Domain + URLList.UploadPic;
        String base64 = "";
        base64 = BitMapUtils.encode(path);
        if (base64 == null){
            Toast.makeText(getBaseContext(),"发布失败",Toast.LENGTH_SHORT).show();
            PromptManage.getInstance().CloseProgressDialog();
        }
        UploadPicParamter picParamter = new UploadPicParamter();
        picParamter.setPhotoBase64(base64);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(picParamter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.UPLOAD_Friends_PIC);
    }

    /**
     * 发布
     */
    private void Publish(){
        Log.i("publish","in Publish");
        String content = et.getText().toString();
        String url = URLList.Domain;
        int modeEnum = ModeEnum.PUBLISH_FRIEND;
        if (type == 0){
            url += URLList.PublishFriend;
            modeEnum = ModeEnum.PUBLISH_FRIEND;
        }else if (type == 1){
            url += URLList.PublishAsk;
            modeEnum = ModeEnum.PUBLISH_ASK;
        }
        if ( ( content == null || content.equals("") ) && urls.equals("") ){
            return;
        }
        PublishFriendParamter paramter = new PublishFriendParamter();
        paramter.setContent(content);
        if (!urls.equals("")){
            paramter.setPathUrl(urls);
        }
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseParameter.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseParameter,modeEnum);
    }

    /**
     * 接受上传图片返回信息
     * @param message
     */
    public void onEvent(PhotoMessage message) {

        if (message==null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
            return;
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            Log.i("ask","上传图片信息："+message.getMsg());
            urls += message.getData().getPhotoURL();
            urls += ",";
            PicCount ++;
            Log.i("ask","总的图片数量："+allCount+"-----已上传图片数量："+CurrentImg);
            if (CurrentImg <= allCount){
                imgHandler.sendEmptyMessage(0);
            }else {
                Publish();
            }
            /*if (allCount == PicCount){
                Publish();
            }*/
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发布结果返回
     * @param message
     */
    public void onEvent(BaseMessage message) {
        if(message==null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)
                && (message.getType()==ModeEnum.PUBLISH_ASK || message.getType() == ModeEnum.PUBLISH_FRIEND)){
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
            ActivityResult result = new ActivityResult();
            result.setType(ActivityResult.PublicFriends);
            EventBus.getDefault().post(result);
            finish();
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
        PromptManage.getInstance().CloseProgressDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

        if (requestCode == REQUEST_IMAGE && list!=null && list.size()>0){
            if (index == -1){
                //添加图片
                path.remove(path.size()-1);
                for (int i=0;i<list.size();i++){
                    path.add(list.get(i));
                }
                if (path.size()<9){
                    path.add(DataDictionary.MOREN);
                }

            }else {
                //更换某张图片
                path.set(index,list.get(0));
                index = -1;
            }
            adapter.notifyDataSetChanged();
        }
    }
}
