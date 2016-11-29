package com.example.tooth.PopWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.tooth.R;

/**
 * Created by 苏煌强 on 2016/10/26.
 */
public class ListPopWindow extends PopupWindow{

    private View view;
    public ListPopWindow(Activity context,View view1){
        super(context);

        /*LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_popwindow,null);*/

        view = view1;
        //设置view
        this.setContentView(view1);
        //设置弹出窗体的宽,高
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置窗体可点击
        this.setFocusable(true);
        //设置窗体的动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        //设置颜色为半透明
        ColorDrawable dw = new ColorDrawable(/*0xb0000000*/);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
