package com.example.tooth.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.example.tooth.Entity.Alipay;
import com.example.tooth.Entity.PayResult;
import com.example.tooth.Message.SignMessage;
import com.example.tooth.Parameter.AlipayParamter;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

@ContentView(R.layout.activity_home_service_pay)
public class HomeServicePayActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_home_service_pay_text)
    TextView text;
    @ViewInject(R.id.activity_home_service_pay_money)
    TextView tv_money;
    @ViewInject(R.id.activity_home_service_pay_img1)
    ImageView zhifubao_img;
    @ViewInject(R.id.activity_home_service_pay_img2)
    ImageView weixin_img;

    private int type = 0;//选择支付宝0或者微信支付1
    private String ordersn,price;
    private Alipay alipay;

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
                        Intent intent = new Intent(getBaseContext(), MyBookActivity.class);
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("上门服务预约须知");
        Bundle bundle = this.getIntent().getExtras();
        ordersn = bundle.getString("ordersn");
        price = bundle.getString("price");
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
     * 选择支付宝支付
     * @param view
     */
    @Event(R.id.activity_home_service_pay_zhifubao)
    private void OnZhifubaoClick(View view){
        zhifubao_img.setImageResource(R.mipmap.xuanz);
        weixin_img.setImageResource(R.mipmap.xuanz_2);
        type = 0;
    }

    /**
     * 选择微信支付
     * @param view
     */
    @Event(R.id.activity_home_service_pay_weixin)
    private void OnWeixinClick(View view){
        zhifubao_img.setImageResource(R.mipmap.xuanz_2);
        weixin_img.setImageResource(R.mipmap.xuanz);
        type = 1;
    }
    @Event(R.id.activity_home_service_pay_btn)
    private void OnBtnClick(View view){
        Log.i("pay","点击确认："+type);
        if (type == 1){

        }else if (type == 0){
            Alipay();
        }
    }

    /**
     * 支付宝支付
     */
    private void Alipay(){
        alipay = new Alipay();
        alipay = new Alipay();
        alipay.getBiz_content().setBody("测试body");
        alipay.getBiz_content().setSubject("测试标题");
        alipay.getBiz_content().setTotal_amount(price);
        alipay.getBiz_content().setOut_trade_no(ordersn);
        String url = URLList.Domain + URLList.DoorSign;
        AlipayParamter paramter = new AlipayParamter();
        paramter.setContent(alipay.getConten());
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.DOOR_SIGN);
    }

    /**
     * 支付宝验签完成
     * @param message
     */
    public void onEvent(SignMessage message) {
        if (message!=null){
            if (message.getType() == ModeEnum.DOOR_SIGN && message.getCode().equals(NetCodeEnum.SUCCESS)){
                Log.i("shop","签名："+message.getData().getSign());
                String orderInfo = alipay.getEncodeContent();
                orderInfo = orderInfo + "&sign=" + message.getData().getSign();
                //orderInfo = URLEncoder.encode(orderInfo,"utf-8");
                final String finalOrderInfo = orderInfo;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(HomeServicePayActivity.this);
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
}
