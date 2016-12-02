package com.example.tooth.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.example.tooth.Activity.MyOrderActivity;
import com.example.tooth.Entity.PayResult;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.SignMessage;
import com.example.tooth.Parameter.AlipayParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.R;
import com.example.tooth.Entity.Alipay;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_shop)
public class ShopFragment extends BaseFragment {

    @ViewInject(R.id.main_webview)
    WebView webView;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.fragment_shop_progressbar)
    ProgressBar progressBar;

    private Alipay alipayUtils;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。

                    }
                   break;
                case SDK_AUTH_FLAG: {

                    break;
                }
                default:
                    break;
            }
        };
    };
    public ShopFragment() {

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
        String url = GlobalUtils.getInstances().getUser().getGoodUrl();
        Log.i("shop","url:"+url);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInteration(), "android");
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
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
        //如果有缓存 就使用缓存数据 如果没有 就从网络中获取
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        title.setText("商城");

    }

    /**
     * 支付宝支付
     */
    private void Alipay(String price,String ordersn){
        alipayUtils = new Alipay();
        alipayUtils.getBiz_content().setBody("测试body");
        alipayUtils.getBiz_content().setSubject("测试标题");
        alipayUtils.getBiz_content().setTotal_amount(price);
        alipayUtils.getBiz_content().setOut_trade_no(ordersn);
        String url = URLList.Domain + URLList.Sign;
        AlipayParamter paramter = new AlipayParamter();
        paramter.setContent(alipayUtils.getConten());
        paramter.setOrdersn(ordersn);
        paramter.setTotalPrice(price);
        Log.i("shop","finalOrderInfo:"+alipayUtils.getConten());
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.SIGN);
    }

    /**
     * 微信支付
     */
    private void WeixinPay(){}

    /**
     * 支付宝验签完成
     * @param message
     */
    public void onEvent(SignMessage message) {
        if (message!=null){
            if (message.getType() == ModeEnum.SIGN && message.getCode().equals(NetCodeEnum.SUCCESS)){
                Log.i("shop","签名："+message.getData().getSign());
                String orderInfo = alipayUtils.getEncodeContent();
                orderInfo = orderInfo + "&sign=" + message.getData().getSign();
                //orderInfo = URLEncoder.encode(orderInfo,"utf-8");
                final String finalOrderInfo = orderInfo;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(finalOrderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }else {
                Log.i("shop",message.getMsg());
            }
        }
    }

    public class JsInteration {

        @JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void onSumResult(String price,String ordersn,String payType) {
            Log.i("shop", "onSumResult price=" + price);
            Log.i("shop", "onSumResult ordersn=" + ordersn);
            Log.i("shop", "onSumResult payType=" + payType);
            if (payType.indexOf("zhifubao")!=-1){
                Alipay(price,ordersn);
            }else if (payType.indexOf("weixin")!=-1){
                WeixinPay();
            }
        }
    }

}
