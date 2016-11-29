package com.example.tooth.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.FamilyAdapter;
import com.example.tooth.Adapter.FamilyDetailAdapter;
import com.example.tooth.Entity.Family;
import com.example.tooth.Entity.FamilyRecord;
import com.example.tooth.Entity.Record;
import com.example.tooth.Message.FamilyDetailMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.FamilyDetailParamter;
import com.example.tooth.R;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.URLList;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_record_detail)
public class RecordDetailActivity extends BaseActivity {
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.activity_record_detail_lv)
    private ListView lv;
    private Family family;
    private FamilyDetailAdapter adapter;
    private List<FamilyRecord> list;
    private int pageIndex = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        Bundle bundle = this.getIntent().getExtras();
        family = (Family) bundle.getSerializable("family");
        title.setText("详情");
        initHeadView();
        getData();
        Log.i("family","on record detail");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("family","on item click");
                if (list.size()>0){
                    Intent intent = new Intent(getBaseContext(),RecordActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("id",list.get(i - 1).getM_ID());
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData(){
        String url = URLList.Domain + URLList.FamilyDetail;
        FamilyDetailParamter paramter = new FamilyDetailParamter();
        paramter.setFamilyId(family.getFAMILY_ID());
        paramter.setPageIndex(pageIndex);
        BaseParameter baseParameter = new BaseParameter();
        baseParameter.setData(paramter);
        baseParameter.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        NetWorkUtils.getInstances().readNetwork(url,baseParameter, ModeEnum.FAMILY_DETAIL);
    }

    /**
     * 接受数据
     * @param message
     */
    public void onEvent(FamilyDetailMessage message) {
        if (message == null){
            Toast.makeText(getBaseContext(),"出错了。。。",Toast.LENGTH_SHORT).show();
        }else if (message.getCode().equals(NetCodeEnum.SUCCESS)){
            list = message.getData();
            adapter = new FamilyDetailAdapter(getBaseContext(),list);
            lv.setAdapter(adapter);
        }
    }

    /**
     * 初始化listview的headview
     */
    private void initHeadView(){
        ViewHolder viewHolder = new ViewHolder();
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_family,null);
        x.view().inject(viewHolder,view);
        viewHolder.relationship.setText(family.getRELATIONSHIP());
        viewHolder.birthday.setText(family.getBIR_DATE());
        viewHolder.name.setText(family.getF_NAME());
        if (family.getSEX() == 0){
            viewHolder.sex.setText("男");
        }else if (family.getSEX() == 1){
            viewHolder.sex.setText("女");
        }

        viewHolder.phone.setText(family.getPHONE());
        viewHolder.headView.setVisibility(View.VISIBLE);
        lv.addHeaderView(view);
    }

    @Event(R.id.back)
    private void OnBackClick(View view){
        onBack();
    }

    class ViewHolder{
        @ViewInject(R.id.item_family_relationship)
        TextView relationship;
        @ViewInject(R.id.item_family_name)
        TextView name;
        @ViewInject(R.id.item_family_sex)
        TextView sex;
        @ViewInject(R.id.item_family_birthday)
        TextView birthday;
        @ViewInject(R.id.item_family_phone)
        TextView phone;
        @ViewInject(R.id.item_family_headview)
        AutoRelativeLayout headView;
    }
}
