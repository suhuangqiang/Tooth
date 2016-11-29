package com.example.tooth.Activity;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.example.tooth.Fragment.DentistFragment;
import com.example.tooth.Fragment.HomeFragment;
import com.example.tooth.Fragment.InfoFragment;
import com.example.tooth.Fragment.MineFragment;
import com.example.tooth.Fragment.ShopFragment;
import com.example.tooth.R;
import com.example.tooth.Utils.BaiduUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;
import java.util.List;


public class MainActivity extends FragmentActivity {

    @ViewInject(android.R.id.tabhost)
    FragmentTabHost mTabhost;

    //底部导航栏文字
    private String item_name[] = new String[]{"首页","看牙","资讯","商城","我"};
    //底部导航图片选择
    private int item_img[] = new int[]{R.drawable.item_home,R.drawable.item_dentist,
            R.drawable.item_info,R.drawable.item_shop,R.drawable.item_mine};
    private Class aClass[] = new Class[]{HomeFragment.class, DentistFragment.class, InfoFragment.class,
            ShopFragment.class, MineFragment.class};
    private long lastClickBackTime = 0;
    private final long TIME_OUT = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

       setContentView(R.layout.activity_main);
        x.view().inject(this);
        init();

        StartLocation();

    }

    private void init() {
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < item_name.length; i++) {
            View indicatorView = inflater.inflate(R.layout.indicator_item, null);
            TextView tv_indicator = (TextView) indicatorView.findViewById(R.id.tv_title_indicator);
            ImageView img_indocator = (ImageView) indicatorView.findViewById(R.id.ima_indicator);
            tv_indicator.setText(item_name[i]);
            img_indocator.setImageResource(item_img[i]);
            mTabhost.addTab(mTabhost.newTabSpec(item_name[i]).setIndicator(indicatorView), aClass[i], null);
        }
    }

    /**
     * 启动百度地位
     */
    private void StartLocation(){
        BaiduUtils.getInstance(this).setOnBDLocationBack(new BaiduUtils.OnBDLocationBack() {
            @Override
            public void CallBack(BDLocation location) {
                Log.i("baidu","定位完成");
                Log.i("baidu",location.getAddrStr());
                Log.i("baidu",location.getCity());
                Log.i("baidu","Latitude:"+location.getLatitude());
                Log.i("baidu","Longitude:"+location.getLongitude());
                List<Poi> list = location.getPoiList();
                Log.i("baidu","poi size:"+list.size());
                for (int i=0;i<list.size();i++){
                    Poi poi = list.get(i);
                    Log.i("baidu","poi name:"+poi.getName());
                }
            }
        });
        BaiduUtils.getInstance(this).Start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int APP_EXIT = 0;
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Date date = new Date(System.currentTimeMillis());
            long currentTime = date.getTime();
            if ((currentTime - lastClickBackTime) <= TIME_OUT ){
                //android.os.Process.killProcess(android.os.Process.myPid());
                Log.i("aaa","退出程序");
                finish();
                //System.exit(0);
            }else {
                lastClickBackTime = currentTime;
                Toast.makeText(getBaseContext(),"再按一次退出",Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
