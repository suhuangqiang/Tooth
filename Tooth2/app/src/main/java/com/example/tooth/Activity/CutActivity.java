package com.example.tooth.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tooth.ClippicView.ClipImageLayout;
import com.example.tooth.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_cut)
public class CutActivity extends BaseActivity {

    @ViewInject(R.id.activity_cut_cut)
    Button cut;
    @ViewInject(R.id.activity_cut_cancel)
    Button cancel;
    @ViewInject(R.id.id_clipImageLayout)
    ClipImageLayout clipImageLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        byte[] b = getIntent().getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        clipImageLayout.setImageBitmap(bitmap);
    }

    @Event(R.id.activity_cut_cut)
    private void OnCutClick(View view){
        Bitmap bitmap = clipImageLayout.clip();
        EventBus.getDefault().post(bitmap);
        finish();
    }
    @Event(R.id.activity_cut_cancel)
    private void OnCancelClick(View view){
        finish();
    }
}
