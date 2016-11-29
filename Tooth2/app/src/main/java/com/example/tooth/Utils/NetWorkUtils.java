package com.example.tooth.Utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Objects;

import de.greenrobot.event.EventBus;


/**
 * Created by shq1 on 2016/11/2.
 */

public class NetWorkUtils {

    private static NetWorkUtils netWorkUtils;

    public synchronized static NetWorkUtils getInstances(){
        if (netWorkUtils == null){
            netWorkUtils = new NetWorkUtils();
        }
        return netWorkUtils;
    }

    public void readNetwork(String url, Object request,final int type){
        Log.e("NET_WORK","地址：" + url);
        Gson gson = new Gson();
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        if (request != null){
            if (type == ModeEnum.SIGN){
                Gson gson1 = new GsonBuilder().disableHtmlEscaping().create();
                params.setBodyContent(gson1.toJson(request));
            }else {
                params.setBodyContent(gson.toJson(request));
            }

            Log.e("NET_WORK","数据：" + params.getBodyContent());
        }
        //最大请求错误重试次数
        params.setMaxRetryCount(1);
        //连接超时时间
        params.setConnectTimeout(10*1000);
        params.setCacheMaxAge(0);
//	    params.setSslSocketFactory(...); // 设置ssl
//	    params.addQueryStringParameter("wd", "xUtils");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               /* Log.e("NET_WORK","返回：" + result);
                EventBus.getDefault().post(new MyEvents(ModeEnum.NETWORK,type,result));*/
                Log.i("NET_WORK","onSuccess:"+result);
                EventBus.getDefault().post(MyEvents.getInstace().getMode(type,result));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                /*getError(ex);*/
               /* Log.e("NET_WORK","返回E：" + getErrorMsg() + "==" + ex.getMessage() + "==" + ex.toString());
                EventBus.getDefault().post(new MyEvents(ModeEnum.ERROR,type,getErrorMsg()));*/
                Log.i("NET_WORK","onError" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.i("NET_WORK","onFinished");
               /* EventBus.getDefault().post(new MyEvents(ModeEnum.DISMISS, EnumRequest.DIALOG_NET_DISMISS.toInt()));*/
            }
        });
    }
}
