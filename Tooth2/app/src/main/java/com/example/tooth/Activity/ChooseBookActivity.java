package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tooth.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_choose_book)
public class ChooseBookActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("预约");
    }

    /**
     * 预约
     * @param view
     */
    @Event(R.id.activity_choose_book_pic1)
    private void OnPic1Click(View view){
        Intent intent = new Intent(this,AppointmentActivity.class);
        Bundle bundle = this.getIntent().getExtras();
        bundle.putInt("TYPE",1);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 上门
     * @param view
     */
    @Event(R.id.activity_choose_book_pic2)
    private void OnPic2Click(View view){
        Intent intent = new Intent(this,AppointmentActivity.class);
        Bundle bundle = this.getIntent().getExtras();
        bundle.putInt("TYPE",2);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }
}
