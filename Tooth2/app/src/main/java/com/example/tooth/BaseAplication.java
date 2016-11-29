package com.example.tooth;

import android.app.Application;


import com.baidu.mapapi.SDKInitializer;
import com.example.tooth.Utils.BaiduUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by 苏煌强 on 2016/10/24.
 */
public class BaseAplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * XUtils初始化
         */
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        /**
         * 百度地图
         */
        SDKInitializer.initialize(this);
        BaiduUtils.getInstance(this);

        init();
    }

    private void init(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.mipmap.ic_launcher) //
                .showImageOnFail(R.mipmap.ic_launcher) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
    }
}
