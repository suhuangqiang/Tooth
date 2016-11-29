package com.example.tooth.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tooth.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_raiders)
public class RaidersActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("积分攻略");
    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

}
