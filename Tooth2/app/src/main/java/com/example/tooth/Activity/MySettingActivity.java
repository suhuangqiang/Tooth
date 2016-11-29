package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tooth.R;
import com.example.tooth.Utils.DataCleanManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_setting)
public class MySettingActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_my_setting_cache)
    TextView cache;
    private boolean hasCache = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("系统设置");
        try {
            String c = DataCleanManager.getTotalCacheSize(getBaseContext());
            cache.setText(c);
            if (c.equals("0K") || c.equals("0k")){
                hasCache = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 关于我们
     * @param view
     */
    @Event(R.id.activity_my_setting_about_click)
    private void OnAboutClick(View view){
        Intent intent = new Intent(this,AoutActivity.class);
        startActivity(intent);
    }

    /**
     * 帮助
     * @param view
     */
    @Event(R.id.activity_my_setting_help_click)
    private void OnHelpClick(View view){

    }

    /**
     * 清除缓存
     * @param view
     */
    @Event(R.id.activity_my_setting_clear_click)
    private void OnClearClick(View view){
        if (hasCache){
            DataCleanManager.clearAllCache(getBaseContext());
        }
        cache.setText("已清除");
    }

    /**
     * 意见反馈
     * @param view
     */
    @Event(R.id.activity_my_setting_yijian_click)
    private void OnYijianClick(View view){
        Intent intent = new Intent(this,SuggestActivity.class);
        startActivity(intent);
    }

    /**
     * 退出登录
     * @param view
     */
    @Event(R.id.activity_my_setting_btn)
    private void OnBtnClick(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
