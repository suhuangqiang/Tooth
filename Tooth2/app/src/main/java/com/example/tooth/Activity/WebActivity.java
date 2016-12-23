package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Entity.Consult;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AddCollectParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_web)
public class WebActivity extends BaseActivity {
    @ViewInject(R.id.activity_web_webview)
    WebView webView;
    @ViewInject(R.id.activity_web_progressbar)
    ProgressBar progressBar;
    @ViewInject(R.id.activity_web_collection_img)
    ImageView img_collect;
    @ViewInject(R.id.activity_web_share_img)
    ImageView img_share;
    @ViewInject(R.id.activity_web_title)
    TextView title;

    private Consult consult;
    private Hospital hospital;
    private int type;
    private int isHave;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("URL");
        type = bundle.getInt(DataDictionary.TYPE);
        String titleStr = bundle.getString("title");
        if (type == 1){
            consult = (Consult) bundle.getSerializable("Consult");
            isHave = consult.getISHAVE();
            Log.i("webactivity","isHave:"+isHave);
            if (isHave == 0){
                img_collect.setImageResource(R.mipmap.star1);
            }else if (isHave == 1){
                img_collect.setImageResource(R.mipmap.xing);
            }
        }else if (type == 2){
            title.setText("商城");
            img_collect.setVisibility(View.GONE);
            img_share.setVisibility(View.GONE);
        }else if (type == 3){
            img_collect.setVisibility(View.GONE);
            img_share.setVisibility(View.GONE);
        }
        if (titleStr!=null && !titleStr.equals("")){
            title.setText(titleStr);
        }
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    /**
     * 点击收藏
     * @param view
     */
    @Event(R.id.activity_web_collection_img)
    private void OnCollectClick(View view){
        if (isHave == 0){
            isHave = 1;
            img_collect.setImageResource(R.mipmap.xing);
            addCollect();
        }else if (isHave == 1){
            isHave = 0;
            img_collect.setImageResource(R.mipmap.star1);
            cancelCollect();
        }
    }

    /**
     * 点击分享
     * @param view
     */
    @Event(R.id.activity_web_share_img)
    private void OnShareClick(View view){
        new ShareAction(WebActivity.this).setPlatform(SHARE_MEDIA.QQ)
                .withText("hello")
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                })
                .share();
    }

    /**
     * 增加收藏
     */
    private void addCollect(){
        String url = URLList.Domain + URLList.AddCollect;
        AddCollectParamter paramter = new AddCollectParamter();
        paramter.setType(type+"");
        switch (type){
            case 0:
                break;
            case 1:
                paramter.setId(consult.getKQ_ID());
                break;
            case 2:
                break;
        }
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADD_COLLECT);
    }

    @Event(R.id.back)
    private void OnBackClicl(View view){
        onBack();
    }

    /**
     * 取消收藏
     */
    private void cancelCollect(){
        String url = URLList.Domain + URLList.DelCollect;
        AddCollectParamter paramter = new AddCollectParamter();
        //paramter.setType(type+"");
        switch (type){
            case 0:

                break;
            case 1:
                paramter.setId(consult.getKQ_ID());
                break;
            case 2:
                break;
        }
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADD_COLLECT);
    }

    /**
     * 数据通知
     * @param message
     */
    public void onEvent(BaseMessage message) {
        if (message == null){
            return;
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
