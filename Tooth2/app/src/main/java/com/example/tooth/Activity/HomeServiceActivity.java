package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tooth.Adapter.PopLVAdapter;
import com.example.tooth.Adapter.PopTimeAdapter;
import com.example.tooth.PopWindow.ListPopWindow;
import com.example.tooth.R;
import com.example.tooth.widget.DataDictionary;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContentView(R.layout.activity_home_service)
public class HomeServiceActivity extends BaseActivity {
    private final String TAG = "HomeServiceActivity";
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_home_service_next)
    Button btn;
    @ViewInject(R.id.activity_home_service_show_address)
    AutoLinearLayout showAddress;
    @ViewInject(R.id.activity_home_service_doctor_name)
    TextView tv_doctor_name;
    @ViewInject(R.id.activity_home_service_time)
    TextView tv_time;
    @ViewInject(R.id.activity_home_service_type)
    TextView tv_type;
    @ViewInject(R.id.activity_home_service_name)
    TextView tv_name;
    @ViewInject(R.id.activity_home_service_phone)
    TextView tv_phone;
    @ViewInject(R.id.activity_home_service_address)
    TextView tv_address;
    @ViewInject(R.id.activity_home_service_mark)
    EditText mark;

    boolean has_choose_doctor;//是否选择医生
    boolean has_choose_time;//是否选择时间
    boolean has_choose_type;//是否选择服务类型
    boolean has_choose_address;//是否选择地址
    //记录当前点击的医生下标 第一个为默认不选择，获取数据应下标加1
    private int index;
    //记录当前点击的时间的下标 gridview中
    private int gv_index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("上门服务预约");
        btn.setClickable(false);
        showAddress.setVisibility(View.GONE);
    }

    /**
     * 选择服务类型
     * @param view
     */
    @Event(R.id.activity_home_service_type_click)
    private void OnTypeClick(View view){
        has_choose_type = true;
        ChangeBtnState();
        tv_type.setText("补牙");
        Intent intent = new Intent(this,BookTypeActivity.class);
        startActivityForResult(intent,0);
    }


    /**
     * 选择时间
     * @param view
     */
    @Event(R.id.activity_home_service_time_click)
    private void OnTimeClick(View view){
        View pop_view = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_time,null);
        final ListPopWindow time_popWindow = new ListPopWindow(this,pop_view);
        TextView cancel = (TextView)pop_view.findViewById(R.id.pop_time_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_popWindow.dismiss();
            }
        });
        /**
         * 确定按钮，保存信息
         */
        TextView save = (TextView)pop_view.findViewById(R.id.pop_time_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_popWindow.dismiss();
            }
        });

        /**
         * 类型 0 不能选择  1可选择 2 已选择
         */
        final List<HashMap<String,String>> data = new ArrayList<>();

        for (int i=0;i<3;i++){
            HashMap<String,String> map = new HashMap<>();
            map.put(DataDictionary.POP_TIME_TYPE,"0");
            map.put(DataDictionary.POP_TIME_TIME,"10:00-11:00");
            data.add(map);
        }
        for (int i=0;i<9;i++){
            HashMap<String,String> map = new HashMap<>();
            map.put(DataDictionary.POP_TIME_TYPE,"1");
            map.put(DataDictionary.POP_TIME_TIME,"10:00-11:00");
            data.add(map);
        }

        gv_index = -1;




        final GridView gridView = (GridView)pop_view.findViewById(R.id.pop_time_grideview);
        final PopTimeAdapter adapter = new PopTimeAdapter(getBaseContext(),data);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!data.get(i).get(DataDictionary.POP_TIME_TYPE).equals("0")){
                    has_choose_time = true;
                    ChangeBtnState();
                    data.get(i).put(DataDictionary.POP_TIME_TYPE,"2");
                    if (gv_index!=-1){
                        data.get(gv_index).put(DataDictionary.POP_TIME_TYPE,"1");
                    }
                    gv_index = i;
                    adapter.notifyDataSetChanged();
                    Log.i("aaa","gv_index:"+gv_index);
                }
            }
        });

        time_popWindow.showAtLocation(title, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
        time_popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 选择医生
     * @param view
     */
    @Event(R.id.activity_home_service_doctor_name_click)
    private void OnDoctorNameClick(View view){
        has_choose_doctor = true;
        ChangeBtnState();
        View pop_view = LayoutInflater.from(getBaseContext()).inflate(R.layout.list_popwindow,null);

        ImageView cancel = (ImageView)pop_view.findViewById(R.id.pop_cancel);
        ImageView ok = (ImageView)pop_view.findViewById(R.id.pop_ok);
        final ListView lv = (ListView)pop_view.findViewById(R.id.pop_lv);

        final ArrayList<HashMap<String,String>> data = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name","懒得选了，还是由牙医安排吧！");
        hashMap.put("type","1");
        data.add(hashMap);
        for (int i=0;i<5;i++){
            HashMap<String,String> hashMap1 = new HashMap<>();
            hashMap1.put("name","主治医生XXX");
            hashMap1.put("type","0");
            data.add(hashMap1);
        }
        index = 0;//当前选择的医生在数据中的下标
        final PopLVAdapter adapter = new PopLVAdapter(getBaseContext(),data);
        lv.setAdapter(adapter);
        final ListPopWindow popWindow = new ListPopWindow(this,pop_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                data.get(index).put("type","0");
                data.get(i).put("type","1");
                index = i;
               /* PopLVAdapter adapter = new PopLVAdapter(getBaseContext(),data);
                lv.setAdapter(adapter);*/
                adapter.notifyDataSetChanged();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });


        popWindow.showAtLocation(title,Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });


    }

    /**
     * 选择就诊人信息
     * @param view
     */
    @Event(R.id.activity_home_service_choose_address)
    private void OnChooseAddressClick(View view){
        has_choose_address = true;
        ChangeBtnState();
        showAddress.setVisibility(View.VISIBLE);
    }

    /**
     * 点击下一步
     * @param view
     */
    @Event(R.id.activity_home_service_next)
    private void OnNextClick(View view){
        Log.i(TAG,"next:" + mark.getText().toString());

        Intent intent = new Intent(this,HomeServicePayActivity.class);
        startActivity(intent);
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    /**
     * 改变按钮状态，当必选项全部选择后改变颜色并设置为可点击
     */
    private void ChangeBtnState(){
        Log.i(TAG,"has_choose_doctor:"+has_choose_doctor);
        Log.i(TAG,"has_choose_time:"+has_choose_time);
        Log.i(TAG,"has_choose_type:"+has_choose_type);
        Log.i(TAG,"has_choose_address:"+has_choose_address);
        if (has_choose_address && has_choose_doctor && has_choose_time && has_choose_type){
            //满足所有必选项
            btn.setBackgroundResource(R.color.title_bar_blue);
            btn.setClickable(true);
        }else {
            btn.setBackgroundResource(R.color.line1);
            btn.setClickable(false);
        }
    }

    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"onActivityResult");
        if (resultCode == 1){
            Log.i(TAG,"resultCode");

            Bundle bundle = data.getExtras();
            tv_type.setText(bundle.getString("CONTENT"));
        }
    }
}
