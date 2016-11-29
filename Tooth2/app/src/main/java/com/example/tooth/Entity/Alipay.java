package com.example.tooth.Entity;

import android.util.Log;

import com.example.tooth.Entity.BizContent;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

/**
 * Created by shq1 on 2016/11/17.
 */

public class Alipay {

    public static final String app_id = "2016081501752914";
    public static final String method = "alipay.trade.app.pay";
    public static final String format = "JSON";
    public static final String charset = "utf-8";
    public static final String sign_type = "RSA";
    public String timestamp;
    public static final String version = "1.0";
    public String notify_url = "http://api.tianyunjian.com/mall_api/api_v4/paynotice";
    public BizContent biz_content;

    public String getConten(){
        String content = "";
        Gson gson = new Gson();
        String bizContent = gson.toJson(biz_content);
        content = "app_id=" + app_id + "&"
                + "biz_content=" + bizContent + "&"
                + "charset=" + charset + "&"
                + "format=" + format + "&"
                + "method=" + method + "&"
                + "notify_url=" + notify_url + "&"
                + "sign_type=" + sign_type + "&"
                + "timestamp=" + timestamp + "&"
                + "version=" + version;
        return content;
    }
    public String getEncodeContent(){
        String content = "";
        Gson gson = new Gson();
        String bizContent = gson.toJson(biz_content);
        try {
            content = "app_id=" + URLEncoder.encode(app_id, "UTF-8") + "&"
                    + "biz_content=" + URLEncoder.encode(bizContent, "UTF-8") + "&"
                    + "charset=" + URLEncoder.encode(charset, "UTF-8") + "&"
                    + "format=" + URLEncoder.encode(format, "UTF-8") + "&"
                    + "method=" + URLEncoder.encode(method, "UTF-8") + "&"
                    + "notify_url=" + URLEncoder.encode(notify_url, "UTF-8") + "&"
                    + "sign_type=" + URLEncoder.encode(sign_type, "UTF-8") + "&"
                    + "timestamp=" + URLEncoder.encode(timestamp, "UTF-8") + "&"
                    + "version=" + URLEncoder.encode(version, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    public Alipay(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        Log.i("pay","时间戳："+date);
        timestamp = date;
        biz_content = new BizContent();
    }




    public BizContent getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(BizContent biz_content) {
        this.biz_content = biz_content;
    }


}
