package com.example.tooth.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.tooth.Entity.Family;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.FamilyParamter;
import com.example.tooth.PopWindow.ListPopWindow;
import com.example.tooth.R;
import com.example.tooth.Utils.BaiduUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.View.ChangeAddressPopwindow;
import com.example.tooth.widget.PickerView;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_add_family)
public class AddFamilyActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_add_family_relationship)
    EditText et_relationship;
    @ViewInject(R.id.activity_add_family_name)
    EditText et_name;
    @ViewInject(R.id.activity_add_family_phone)
    EditText et_phone;
    @ViewInject(R.id.activity_add_family_sex)
    TextView tv_sex;
    @ViewInject(R.id.activity_add_family_birthday)
    TextView tv_birthday;
    @ViewInject(R.id.activity_add_family_address_tv)
    TextView address;
    @ViewInject(R.id.activity_add_family_address_detail)
    EditText detail;
    @ViewInject(R.id.activity_add_family_age)
    EditText age;
    private Family family;
    private String year="1984年",month="7月",day="16日";
    private String sexStr = "女";
    private int sex_type = 0;
    private String mProvince,mCity,mArea;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("添加家人");
        tv_birthday.setText(year+"-"+month+"-"+day);
        tv_sex.setText(sexStr);

        BaiduUtils.getInstance(getBaseContext()).setOnBDLocationBack(new BaiduUtils.OnBDLocationBack() {
            @Override
            public void CallBack(BDLocation location) {
                address.setText(location.getProvince() + " " + location.getCity() + " " + location.getAddrStr());
                mProvince = location.getProvince();
                mCity = location.getCity();
                mArea = location.getAddrStr();
            }
        });
        BaiduUtils.getInstance(getBaseContext()).Start();


    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    /**
     * 添加
     * @param view
     */
    @Event(R.id.titlebar_tv)
    private void OnAddClick(View view){
        String relationshipStr = et_relationship.getText().toString();
        String nameStr = et_name.getText().toString();
        String phoneStr = et_phone.getText().toString();
        if (relationshipStr == null || relationshipStr.equals("")){
            Toast.makeText(getBaseContext(),"请填写关系",Toast.LENGTH_SHORT).show();
            return;
        }
        if (nameStr == null || nameStr.equals("")){
            Toast.makeText(getBaseContext(),"请填写姓名",Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneStr == null || phoneStr.equals("")){
            Toast.makeText(getBaseContext(),"请填写手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
        family = new Family();

        FamilyParamter paramter = new FamilyParamter();
        paramter.setPhone(phoneStr);
        paramter.setSex(sex_type);
        paramter.setName(nameStr);
        paramter.setRelationship(relationshipStr);
        paramter.setBirTime(tv_birthday.getText().toString());
        paramter.setAge(age.getText().toString());
        paramter.setProvince(mProvince);
        paramter.setCity(mCity);
        paramter.setArea(mArea);
        paramter.setAddress(detail.getText().toString());

        family.setPHONE(phoneStr);
        family.setSEX(sex_type);
        family.setF_NAME(nameStr);
        family.setRELATIONSHIP(relationshipStr);
        family.setBIR_DATE(tv_birthday.getText().toString());
        family.setAGE(age.getText().toString());

        BaseMessage message = new BaseMessage();
        message.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        message.setData(paramter);
        String url = URLList.Domain + URLList.AddFamily;
        NetWorkUtils.getInstances().readNetwork(url,message, ModeEnum.ADD_FAMILY);

    }

    /**
     * 选择性别
     * @param view
     */
    @Event(R.id.activity_add_family_sex_click)
    private void OnSexClick(View view){

        View pop_view = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_sex,null);
        AutoRelativeLayout man = (AutoRelativeLayout)pop_view.findViewById(R.id.pop_sex_man);
        AutoRelativeLayout cancel = (AutoRelativeLayout)pop_view.findViewById(R.id.pop_sex_cancel);
        AutoRelativeLayout women = (AutoRelativeLayout)pop_view.findViewById(R.id.pop_sex_women);

        final ListPopWindow popWindow = new ListPopWindow(this,pop_view);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sex.setText("男");
                tv_sex.setTextColor(Color.parseColor("#666666"));
                sex_type = 0;
                popWindow.dismiss();
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sex.setText("女");
                tv_sex.setTextColor(Color.parseColor("#666666"));
                sex_type = 1;
                popWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        popWindow.showAtLocation(title, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 选择生日
     * @param view
     */
    @Event(R.id.activity_add_family_birthday_click)
    private void OnBirthdayClick(View view){

        View pop_view = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_birthday,null);
        final ListPopWindow popWindow = new ListPopWindow(this,pop_view);
        PickerView pickerView1 = (PickerView)pop_view.findViewById(R.id.pop_birthday_picverview1);
        PickerView pickerView2 = (PickerView)pop_view.findViewById(R.id.pop_birthday_picverview2);
        PickerView pickerView3 = (PickerView)pop_view.findViewById(R.id.pop_birthday_picverview3);
        TextView sure = (TextView)pop_view.findViewById(R.id.pop_birthday_sure);
        TextView cancel = (TextView)pop_view.findViewById(R.id.pop_birthday_cancle);
        List<String> data1 = new ArrayList<>();
        for (int i=1950;i<2018;i++){
            data1.add(i+"年");
        }
        pickerView1.setData(data1);
        pickerView1.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                year = text;
            }
        });
        List<String> data2 = new ArrayList<>();
        for (int i=1;i<13;i++){
            data2.add(i+"月");
        }
        pickerView2.setData(data2);
        pickerView2.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                month = text;
            }
        });
        List<String> data3 = new ArrayList<>();
        for (int i=1;i<32;i++){
            data3.add(i+"日");
        }
        pickerView3.setData(data3);
        pickerView3.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                day = text;
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_birthday.setText(year+month+day);
                tv_birthday.setTextColor(Color.parseColor("#666666"));
                popWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });

        popWindow.showAtLocation(title, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    @Event(R.id.activity_add_family_address)
    private void OnAddressClick(View view){
        backgroundAlpha(0.5f);
        ChangeAddressPopwindow mChangeAddressPopwindow = new ChangeAddressPopwindow(AddFamilyActivity.this);
        mChangeAddressPopwindow.setAddress("广东", "深圳", "福田区");
        mChangeAddressPopwindow.showAtLocation(tv_birthday, Gravity.BOTTOM, 0, 0);
        mChangeAddressPopwindow.setAnimationStyle(R.style.take_photo_anim);
        mChangeAddressPopwindow
                .setAddresskListener(new ChangeAddressPopwindow.OnAddressCListener() {

                    @Override
                    public void onClick(String province, String city, String area) {
                        // TODO Auto-generated method stub

                        address.setText(province+" "+city+" "+area);
                        Log.i("aaa","province:"+province+"---city："+city+"--area:"+area);
                        mProvince = province;
                        mCity = city;
                        mArea = area;
                    }
                });
        mChangeAddressPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
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


    public void onEvent(BaseMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                EventBus.getDefault().post(family);
                onBack();
            }
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }
}
