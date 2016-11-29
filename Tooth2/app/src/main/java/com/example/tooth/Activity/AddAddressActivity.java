package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.tooth.Entity.Address;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Parameter.AddressParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_add_address)
public class AddAddressActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_add_address_location)
    TextView location;
    @ViewInject(R.id.activity_add_address_detail)
    EditText detail;
    @ViewInject(R.id.activity_add_address_name)
    EditText name;
    @ViewInject(R.id.activity_add_address_phone)
    EditText phone;

    private String address = "";
    private Address mAddress;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        Bundle bundle = this.getIntent().getExtras();
        //判断新增0或者修改1
        type = bundle.getInt(DataDictionary.TYPE);
        if (type == 0){
            title.setText("新增地址");
        }else if (type == 1){
            title.setText("修改地址");

            mAddress = (Address) bundle.getSerializable("address");

            location.setText(mAddress.getPROVINCE()+mAddress.getCITY()+mAddress.getAREA());
            address = mAddress.getPROVINCE()+mAddress.getCITY()+mAddress.getAREA();
            detail.setText(mAddress.getADDRESS());
            name.setText(mAddress.getNAME());
            phone.setText(mAddress.getMOBILE());
        }
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
     * 显示地位信息
     * @param view
     */
    @Event(R.id.activity_add_address_show_location)
    private void OnShowClick(View view){
        Intent intent = new Intent(this,MyLocationActivity.class);
        startActivityForResult(intent,9);
    }

    /**
     * 保存
     * @param view
     */
    @Event(R.id.activity_add_address_save)
    private void OnSaveClick(View view){
        String address_detail = detail.getText().toString();
        String nameStr = name.getText().toString();
        String phoneStr = phone.getText().toString();
        if (address.equals("")){
            Toast.makeText(getBaseContext(),"请选择地址",Toast.LENGTH_SHORT).show();
            return;
        }
        if (address_detail == null || address_detail.equals("")){
            Toast.makeText(getBaseContext(),"请完善地址信息",Toast.LENGTH_SHORT).show();
            return;
        }
        if (nameStr == null || nameStr.equals("")){
            Toast.makeText(getBaseContext(),"请输入姓名",Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneStr == null || phoneStr.equals("")){
            Toast.makeText(getBaseContext(),"请输入手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
        String url = URLList.Domain + URLList.AddAddress;
        BDLocation location = GlobalUtils.getInstances().getBdLocation();
        AddressParamter paramter = new AddressParamter();
        paramter.setProvince(location.getProvince());
        paramter.setCity(location.getCity());
        paramter.setArea(address);
        paramter.setAddress(address_detail);
        paramter.setName(nameStr);
        paramter.setTel(phoneStr);

        if (type == 1){
            paramter.setAddressId(mAddress.getZA_ID());
            url = URLList.Domain + URLList.UpdateAddress;
        }

        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.BASE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("aaa","requestCode:"+requestCode);
        Log.i("aaa","resultCode:"+resultCode);
        if (requestCode == 9){
            if (data == null){
                Log.i("aaa","wocao");
            }else {
                String addr = data.getExtras().getString("addr");
                address = addr;
                location.setText(addr);
            }
        }
    }


    public void onEvent(BaseMessage baseMessage) {
        if (baseMessage == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getBaseContext(),baseMessage.getMsg(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
