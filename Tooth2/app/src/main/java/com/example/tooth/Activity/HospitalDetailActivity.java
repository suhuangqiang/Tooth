package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tooth.Entity.Hospital;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AddCollectParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_hospital_detail)
public class HospitalDetailActivity extends BaseActivity {

    @ViewInject(R.id.activity_hospital_detail_webview)
    WebView webView;
    @ViewInject(R.id.activity_hospital_detail_progressbar)
    ProgressBar progressBar;
    @ViewInject(R.id.activity_hospital_detail_collection_img)
    ImageView img_collect;
    @ViewInject(R.id.activity_hospital_detail_share_img)
    ImageView img_share;
    private String url;
    private Hospital hospital;
    private int isHave;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_hospital_detail);*/
    }

    @Override
    public void init() {
        url = this.getIntent().getExtras().getString("URL");
        hospital = (Hospital) this.getIntent().getExtras().getSerializable("Hospital");
        isHave = hospital.getISHAVE();
        if (isHave == 0){
            img_collect.setImageResource(R.mipmap.star1);
        }else if (isHave == 1){
            img_collect.setImageResource(R.mipmap.xing);
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
     * 在线咨询
     * @param view
     */
    @Event(R.id.activity_hospital_detail_online)
    private void OnOnlineClick(View view){

    }

    /**
     * 我要预约
     * @param view
     */
    @Event(R.id.activity_hospital_detail_book)
    private void OnBookClick(View view){
        Intent intent = new Intent(this,ChooseBookActivity.class);
        intent.putExtras(this.getIntent().getExtras());
        startActivity(intent);
    }

    /**
     * 点击收藏
     */
    @Event(R.id.activity_hospital_detail_collection_img)
    private void OnCollectClick(View view){
        if (isHave == 0){
            isHave = 1;
            img_collect.setImageResource(R.mipmap.xing);

        }else if (isHave == 1){
            isHave = 0;
            img_collect.setImageResource(R.mipmap.star1);
        }
        CollectCenter(isHave);
    }

    /**
     * 点击分享
     */
    @Event(R.id.activity_hospital_detail_share_img)
    private void OnShareClick(View view){}

    /**
     * 收藏处理
     */
    private void CollectCenter(int has){
        String url = URLList.Domain;
        AddCollectParamter paramter = new AddCollectParamter();
        if (has == 1){
            //收藏
            paramter.setType(0+"");
            url += URLList.AddCollect;
        }else if (has == 0){
            //取消收藏
            url += URLList.DelCollect;
        }
        paramter.setId(hospital.getCC_ID());
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.ADD_COLLECT);
    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.activity_hospital_detail_back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 网络请求回调
     * @param message
     */
    public void onEvent(BaseMessage message) {
        if (message!=null){
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
