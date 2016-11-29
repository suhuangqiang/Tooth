package com.example.tooth.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * Created by hp on 2015/12/31.
 */
public class NetUtil {
    /**
     *
     * @param context
     * @return 返回是否有网络连接
     */
    public static boolean checkNet(Context context){
        //判断WIFI连接
        boolean isWifi = isWifiConnection(context);
        //判断Mobile连接
        boolean isMobile = isMobileConnection(context);
        //如果Mobile连接，需要判断哪个VPN被选中，并获取代理的信息
        if(isMobile){
            readVpn(context);
        }
        if(!isWifi && !isMobile){
            return false;
        }
        return true;
    }
    /**
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnection(Context context){
        //获取系统网络服务管理器
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo info =  manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(info != null){
            return info.isConnected();
        }
        return false;
    }

    /**
     *
     * @param context
     * @return
     */
    private static boolean isMobileConnection(Context context){
        //获取系统网络服务管理器
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(info != null){
            return info.isConnected();
        }
        return false;
    }

    /**
     * 如果VPN被选中，判断代理的内容，并进行保存代理信息
     * @param context
     */
    public static void readVpn(Context context){
        //4.0模拟器会屏蔽掉该权限，所以需要try处理
        try {
            Uri vpnuri = Uri.parse("content://telephony/carries/preferapn");
            ContentResolver resolver = context.getContentResolver();
            //判断哪个APN被选中
            Cursor cursor = resolver.query(vpnuri,null,null,null,null);
            //判断有没有找到VPN数据，并且有没有proxy列
            if(cursor != null && cursor.moveToFirst()){

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
