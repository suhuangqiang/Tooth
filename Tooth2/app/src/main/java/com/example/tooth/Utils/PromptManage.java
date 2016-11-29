package com.example.tooth.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tooth.R;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by shq1 on 2016/11/8.
 */

public class PromptManage {
    private static PopupWindow popupWindow;
    private Context mContext;
    private View showView,view;
    private TextView textView;
    private ProgressDialog proDialog;
    private static PromptManage promptManage;
    public static PromptManage getInstance(){
        if (promptManage == null){
            promptManage = new PromptManage();
        }
        return promptManage;
    }
    private void init(){
        view = LayoutInflater.from(mContext).inflate(R.layout.pop_loading,null);
        popupWindow = new PopupWindow(view, PtrFrameLayout.LayoutParams.MATCH_PARENT, PtrFrameLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        textView = (TextView)view.findViewById(R.id.pop_load_tv);
    }
    public void ShowProgressDialog(Context context,String content){
        proDialog = new ProgressDialog(context);
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCanceledOnTouchOutside(false);
        proDialog.setMessage(content);
        proDialog.show();
    }
    public void CloseProgressDialog(){
        if (proDialog!=null && proDialog.isShowing()){
            proDialog.dismiss();
        }
    }

    /**
     * 显示提示框
     * @param context  上下文环境
     * @param mView   显示控件的位置
     * @param content 提示字符内容
     */
    public void Show(Context context,View mView,String content){
        this.mContext = context;
        this.showView = mView;
        if (popupWindow == null){
            init();
        }
        textView.setText(content);
        popupWindow.showAsDropDown(mView,0,0);
    }
    public void Close(){
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
    }
}
