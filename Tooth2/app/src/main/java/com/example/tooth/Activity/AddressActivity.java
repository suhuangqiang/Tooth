package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.AddressAdapter;
import com.example.tooth.Entity.Address;
import com.example.tooth.Message.AddressListMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.Message;
import com.example.tooth.Parameter.AddressParamter;
import com.example.tooth.Parameter.FamilyListPatameter;
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

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

@ContentView(R.layout.activity_address)
public class AddressActivity extends BaseActivity {

    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_address_lv)
    ListView lv;
    @ViewInject(R.id.activity_address_pcf)
    PtrClassicFrameLayout pcf;

    private String url;
    private AddressAdapter adapter;
    private List<Address> addresses;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        title.setText("地址管理");
        url = URLList.Domain + URLList.AddressList;
        pcf.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });
        getData();

    }

    /**
     * 返回
     * @param view
     */
    @Event(R.id.back)
    private void OnBackClick(View view){
        Log.i("aaa","back");
        onBack();
    }

    /**
     * 新增地址
     * @param view
     */
    @Event(R.id.activity_address_add)
    private void OnAddClick(View view){
        Log.i("aaa","OnAddClick");
        Intent intent = new Intent(this,AddAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,0);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getData(){
        FamilyListPatameter patameter = new FamilyListPatameter();
        patameter.setPageIndex(1);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(new Message());
        baseMessage.setData(patameter);
        NetWorkUtils.getInstances().readNetwork(url,baseMessage, ModeEnum.ADDRESS_LIST);
    }


    public void onEvent(AddressListMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)){
                addresses = message.getData();
                adapter = new AddressAdapter(getBaseContext(),addresses);
                adapter.setAdapterListener(new AddressAdapter.AdapterListener() {
                    @Override
                    public void Edit(int position) {
                        EditAddress(position);
                    }

                    @Override
                    public void Del(int position) {
                        DelAddress(position);
                    }
                });
                lv.setAdapter(adapter);
                pcf.refreshComplete();
            }
        }
    }

    /**
     * 编辑
     * @param position
     */
    private void EditAddress(int position){
        Intent intent = new Intent(this,AddAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DataDictionary.TYPE,1);
        bundle.putSerializable("address",addresses.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 删除
     * @param position
     */
    private void DelAddress(int position){
        String delUrl = URLList.Domain + URLList.DelAddress;
        AddressParamter paramter = new AddressParamter();
        paramter.setAddressId(addresses.get(position).getZA_ID());
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);
        NetWorkUtils.getInstances().readNetwork(delUrl,baseMessage,ModeEnum.BASE);

        addresses.remove(position);
        adapter.notifyDataSetChanged();
    }


    public void onEvent(BaseMessage message) {
        if (message==null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getBaseContext(),message.getMsg(),Toast.LENGTH_SHORT).show();
        }
        pcf.refreshComplete();
    }
}
