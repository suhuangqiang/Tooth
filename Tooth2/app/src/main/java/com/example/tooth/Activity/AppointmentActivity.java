package com.example.tooth.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tooth.Adapter.PopLVAdapter;
import com.example.tooth.Adapter.PopTimeAdapter;
import com.example.tooth.Entity.ActivityResult;
import com.example.tooth.Entity.Appointment;
import com.example.tooth.Entity.Doctor;
import com.example.tooth.Entity.Family;
import com.example.tooth.Entity.Hospital;
import com.example.tooth.Entity.Project;
import com.example.tooth.Entity.Time;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.BookMessage;
import com.example.tooth.Message.DoctorMessage;
import com.example.tooth.Message.TimeMessage;
import com.example.tooth.Parameter.BaseParameter;
import com.example.tooth.Parameter.BookParamter;
import com.example.tooth.Parameter.DoctorListParameter;
import com.example.tooth.Parameter.TimeParamter;
import com.example.tooth.PopWindow.ListPopWindow;
import com.example.tooth.R;
import com.example.tooth.Utils.DBUtils;
import com.example.tooth.Utils.DateUtils;
import com.example.tooth.Utils.GlobalUtils;
import com.example.tooth.Utils.ModeEnum;
import com.example.tooth.Utils.NetCodeEnum;
import com.example.tooth.Utils.NetWorkUtils;
import com.example.tooth.widget.DataDictionary;
import com.example.tooth.widget.URLList;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_appointment)
public class AppointmentActivity extends BaseActivity {
    /**
     * 记录当前点击的医生下标 第一个为默认不选择，获取数据应下标加1
     */
    private int index = 0,lastIndex = -1;
    /**
     * 记录当前点击的时间的下标 gridview中
     */
    private int gv_index;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.activity_appointment_doctor_name)
    TextView tv_doctor_name;
    @ViewInject(R.id.activity_appointment_time)
    TextView tv_time;
    @ViewInject(R.id.activity_appointment_type)
    TextView tv_type;
    @ViewInject(R.id.activity_appointment_name)
    TextView tv_name;
    @ViewInject(R.id.activity_appointment_phone)
    TextView tv_phone;
    @ViewInject(R.id.activity_appointment_mark)
    EditText et_mark;
    @ViewInject(R.id.activity_appointment_home_service_choose_address)
    AutoRelativeLayout home_service_address_arl;
    @ViewInject(R.id.activity_appointment_address_click)
    AutoRelativeLayout appointment_address_arl;
    @ViewInject(R.id.activity_home_service_address)
    TextView home_service_address;
    @ViewInject(R.id.activity_home_service_name)
    TextView home_service_name;
    @ViewInject(R.id.activity_home_service_phone)
    TextView home_service_phone;
    @ViewInject(R.id.activity_appointment_btn)
    Button appointment_sure_btn;
    /*@ViewInject(R.id.activity_appointment_home_service_next)
    Button next_btn;*/

    private TimeView timeView;//时间弹出框
    private String hosId;//诊所id
    private List<Doctor> doctors;//医生列表
    private int timeIndex = 0;//时间下标
    private int dateIndex = 0;
    private TextView[] lines;//显示时间下划线
    private List<Time> times1, times2, times3, times4, times5;
    private List<Time>[] times;
    private List<String> dates;
    private PopTimeAdapter adapter;
    private List<HashMap<String, String>> data;
    private String dateStr, timeStr, markStr;
    private List<String> date;
    private Family family;
    private int BOOK_TYPE;
    private Project project;
    private Appointment appointment;//为修改时传过来的预约数据
    private boolean isChange = false;//是否为修改
    private Hospital hospital;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void init() {


        title.setText("就诊预约");
        BOOK_TYPE = this.getIntent().getExtras().getInt("TYPE");
        if (BOOK_TYPE == 3) {
            //为修改
            title.setText("修改预约");
            appointment = (Appointment) this.getIntent().getExtras().getSerializable("Appointment");
            /*String type = appointment.getCP_TYPE();
            if (type.equals("1")){
                BOOK_TYPE = 1;
            }else if (type.equals("2")){
                BOOK_TYPE = 2;
            }*/
            BOOK_TYPE = 1;
            isChange = true;
            initChange();
        }else {
            hospital = (Hospital) this.getIntent().getExtras().getSerializable("Hospital");
        }
        if (BOOK_TYPE == 1) {
            appointment_address_arl.setVisibility(View.VISIBLE);
            home_service_address_arl.setVisibility(View.GONE);
            appointment_sure_btn.setText("确认");
        } else if (BOOK_TYPE == 2) {
            appointment_address_arl.setVisibility(View.GONE);
            home_service_address_arl.setVisibility(View.VISIBLE);
            home_service_name.setText("");
            home_service_phone.setText("");
            //设置地址
            home_service_address.setText("");
            appointment_sure_btn.setText("下一步");
        }

        getDocData();


        times = new List[]{times1, times2, times3, times4, times5};
        dates = DateUtils.getDates();
    }

    /**
     * 为修改时初始化
     */
    private void initChange(){
        hosId = appointment.getCC_ID();
        /*family = new Family();
        family.setFAMILY_ID(appointment.getF_ID());*/
        //tv_doctor_name.setText(appointment.getZZ_NAME());
        /*tv_time.setText(appointment.getSUB_TIME()+appointment.getNEXTTIME());
        tv_type.setText(appointment.getCP_NAME());
        et_mark.setText(appointment.getREMARK());*/
    }
    @Event(R.id.back)
    private void OnBackClick(View view) {
        onBack();
    }

    /**
     * 选择时间
     *
     * @param view
     */
    @Event(R.id.activity_appointment_time_click)
    private void OnTimeClick(View view) {
        View pop_view = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_time, null);
        final ListPopWindow time_popWindow = new ListPopWindow(this, pop_view);
        timeView = new TimeView();
        x.view().inject(timeView, pop_view);
        lines = new TextView[]{timeView.line1, timeView.line2, timeView.line3, timeView.line4, timeView.line5};
        timeView.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_popWindow.dismiss();
            }
        });
        /**
         * 确定按钮，保存信息
         */
        timeView.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateStr == null || dateStr.equals("")) {
                    dateStr = date.get(0);
                }
                tv_time.setText(dateStr + " " + timeStr);
                time_popWindow.dismiss();
            }
        });

        timeView.click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate(0);
            }
        });
        timeView.click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate(1);
            }
        });
        timeView.click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate(2);
            }
        });
        timeView.click4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate(3);
            }
        });
        timeView.click5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate(4);
            }
        });


        TextView[] dates = new TextView[]{timeView.data1, timeView.data2, timeView.data3, timeView.data4, timeView.data5};
        TextView[] weeks = new TextView[]{timeView.week1, timeView.week2, timeView.week3, timeView.week4, timeView.week5};
        date = DateUtils.getSevendate();
        List<String> week = DateUtils.get7week();
        for (int i = 0; i < dates.length; i++) {
            dates[i].setText(date.get(i));
            weeks[i].setText(week.get(i));
        }


        timeView.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!data.get(i).get(DataDictionary.POP_TIME_TYPE).equals("1") && !(i == gv_index)) {

                    data.get(i).put(DataDictionary.POP_TIME_TYPE, "2");
                    if (gv_index != -1) {
                        data.get(gv_index).put(DataDictionary.POP_TIME_TYPE, "0");
                    }
                    gv_index = i;
                    adapter.notifyDataSetChanged();
                    timeStr = (String) adapter.getItem(i);
                    Log.i("aaa", "gv_index:" + gv_index);
                }
            }
        });

        time_popWindow.showAtLocation(title, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
        time_popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        if (lastIndex==-1 || lastIndex!=index || times[0] == null) {
            Log.i("aaa", "times[0] is null" + date.get(0));
            ChooseDate(0);
        } else {
            data = new ArrayList<>();
            List<Time> list = times[timeIndex];
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put(DataDictionary.POP_TIME_TYPE, list.get(i).getIswould());
                map.put(DataDictionary.POP_TIME_TIME, list.get(i).getTime());
                data.add(map);
            }
            gv_index = -1;
            adapter = new PopTimeAdapter(getBaseContext(), data);
            timeView.gv.setAdapter(adapter);
        }

    }


    /**
     * 选择医生
     *
     * @param view
     */
    @Event(R.id.activity_appointment_doctor_name_click)
    private void OnDoctorNameClick(View view) {
        View pop_view = LayoutInflater.from(getBaseContext()).inflate(R.layout.list_popwindow, null);

        ImageView cancel = (ImageView) pop_view.findViewById(R.id.pop_cancel);
        ImageView ok = (ImageView) pop_view.findViewById(R.id.pop_ok);
        final ListView lv = (ListView) pop_view.findViewById(R.id.pop_lv);

        final ArrayList<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "懒得选了，还是由牙医安排吧！");
        hashMap.put("type", "1");
        data.add(hashMap);
        for (int i = 0; i < doctors.size(); i++) {
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("name", doctors.get(i).getZE_NAME());
            hashMap1.put("type", "0");
            data.add(hashMap1);
        }
        index = 0;//当前选择的医生在数据中的下标
        final PopLVAdapter adapter = new PopLVAdapter(getBaseContext(), data);
        lv.setAdapter(adapter);
        final ListPopWindow popWindow = new ListPopWindow(this, pop_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                data.get(index).put("type", "0");
                data.get(i).put("type", "1");
                if (lastIndex == -1){
                    index = i;
                    lastIndex = index;
                }else {
                    lastIndex = index;
                    index = i;
                }
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


        popWindow.showAtLocation(title, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                if (index == 0) {
                    tv_doctor_name.setText("懒得选了，还是由牙医安排吧！");
                } else {
                    tv_doctor_name.setText(doctors.get(index - 1).getZE_NAME());
                }
            }
        });


    }

    /**
     * 类型
     *
     * @param view
     */
    @Event(R.id.activity_appointment_type_click)
    private void OnTypeClick(View view) {
        Intent intent = new Intent(this, BookTypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("clinicId", hosId);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    /**
     * 选择就诊者信息
     *
     * @param view
     */
    @Event(value = {R.id.activity_appointment_address_click, R.id.activity_appointment_home_service_choose_address})
    private void OnAddressClick(View view) {
        Intent intent = new Intent(this, FamilyArchivesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        intent.putExtras(bundle);
        startActivityForResult(intent, 2);
    }


    /**
     * 确认
     *
     * @param view
     */
    @Event(R.id.activity_appointment_btn)
    private void OnBtnClick(View view) {

        if (family == null) {
            Toast.makeText(getBaseContext(), "请选择就诊者", Toast.LENGTH_SHORT).show();
            return;
        }
        index--;
        if (index < 0) {
            index = 0;
        }
        if (project == null){
            Toast.makeText(getBaseContext(), "请选择项目", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> dateList = DateUtils.getDates();
        dateStr = dateList.get(dateIndex);
        String url = URLList.Domain + URLList.Order;
        BookParamter paramter = new BookParamter();
        paramter.setClinicId(hosId);
        paramter.setFamilyId(family.getFAMILY_ID());
        paramter.setDocId(doctors.get(index).getDOCTOR_ID());
        paramter.setTime(dateStr + " " + timeStr);
        paramter.setProject(project.getCP_ID());
        paramter.setRemark(et_mark.getText().toString());
        paramter.setType(BOOK_TYPE + "");
        if (BOOK_TYPE == 2 ){
            paramter.setMoney(hospital.getSERVICE_FEE()+"");
        }
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(paramter);

        /*if (BOOK_TYPE == 2) {
            Intent intent = new Intent(this, HomeServicePayActivity.class);
            startActivity(intent);
            return;
        }*/
        if (isChange){
            Log.i("appointment","ischange:"+isChange);
            url = URLList.Domain + URLList.UpdateBook;
            paramter.setId(appointment.getCR_ID());
            baseMessage.setData(paramter);
            NetWorkUtils.getInstances().readNetwork(url, baseMessage, ModeEnum.UPDATE_BOOK);
            return;
        }

        NetWorkUtils.getInstances().readNetwork(url, baseMessage, ModeEnum.ORDER);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void getDocData() {
        String url = URLList.Domain + URLList.DocList;
        if (hosId==null){
            hosId = this.getIntent().getExtras().getString("clinicId");
        }
        DoctorListParameter parameter = new DoctorListParameter();
        parameter.setPageIndex(1);
        parameter.setClinicId(hosId);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
        baseMessage.setData(parameter);
        NetWorkUtils.getInstances().readNetwork(url, baseMessage, ModeEnum.DOC_LIST);
    }

    /**
     * 接收到医生列表数据后的操作
     *
     * @param message
     */
    public void onEvent(DoctorMessage message) {
        if (message == null) {
            Toast.makeText(getBaseContext(), "出错啦。。。", Toast.LENGTH_SHORT).show();
        } else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)) {
                doctors = message.getData();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("aaa", "requestCode:" + requestCode + "---resultCode:" + resultCode);
        if (resultCode == 1) {
            Bundle bundle = data.getExtras();
            project = (Project) bundle.getSerializable("Project");
            markStr = bundle.getString("MARK");
            tv_type.setText(bundle.getString("CONTENT"));
        } else if (resultCode == 2) {
            family = (Family) data.getExtras().getSerializable("family");
            tv_name.setText(family.getF_NAME());
            tv_phone.setText(family.getPHONE());
            home_service_name.setText(family.getF_NAME());
            home_service_phone.setText(family.getPHONE());
            //设置地址
            home_service_address.setText(family.getPROVINCE()+family.getCITY()+family.getAREA()+family.getADDRESS());
        }
    }

    /**
     * 切换日期
     * position位选择的日期的下标
     *
     * @param position
     */
    private void ChooseDate(int position) {
        dateStr = date.get(position);
        dateIndex = position;
        if (lastIndex == index && position == timeIndex && times[position] != null) {
            //点击的时间与当前显示时间一样
            return;
        }
        lines[timeIndex].setVisibility(View.GONE);
        lines[position].setVisibility(View.VISIBLE);
        timeIndex = position;
        if (lastIndex!=index || times[position] == null) {
            //当前还没有获取数据,获取数据
            String url = URLList.Domain + URLList.TimeList;
            TimeParamter paramter = new TimeParamter();
            paramter.setClinicId(hosId);
            int i = index;
            if (i > 0) {
                i--;
            } else {
                i = 0;
            }
            if (doctors == null || doctors.size() <= 0) {
                Toast.makeText(getBaseContext(), "当前没有医生可预约", Toast.LENGTH_SHORT).show();
                return;
            }
            if (doctors.get(i).getDOCTOR_ID() == null) {
                Toast.makeText(getBaseContext(), "请先选择医生", Toast.LENGTH_SHORT).show();
                return;
            }
            paramter.setDocId(doctors.get(i).getDOCTOR_ID());

            paramter.setDate(dates.get(position));
            BaseParameter baseMessage = new BaseParameter();
            baseMessage.setData(paramter);
            baseMessage.setUSER_ID(GlobalUtils.getInstances().getUser().getUSER_ID());
            NetWorkUtils.getInstances().readNetwork(url, baseMessage, ModeEnum.TIME_LIST);
        } else {
            //已经请求过一次数据，直接显示
            /**
             * 类型 0 不能选择  1可选择 2 已选择
             */
            data = new ArrayList<>();
            List<Time> list = times[timeIndex];
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put(DataDictionary.POP_TIME_TYPE, list.get(i).getIswould());
                map.put(DataDictionary.POP_TIME_TIME, list.get(i).getTime());
                data.add(map);
            }
            gv_index = -1;
            adapter = new PopTimeAdapter(getBaseContext(), data);
            timeView.gv.setAdapter(adapter);
        }
    }

    /**
     * 接受时间数据
     *
     * @param message
     */
    public void onEvent(TimeMessage message) {
        if (message == null) {
            Toast.makeText(getBaseContext(), "出错了。。。", Toast.LENGTH_SHORT).show();
        } else {
            if (message.getCode().equals(NetCodeEnum.SUCCESS)) {
                times[timeIndex] = message.getData();

            } else {
                times[timeIndex] = new ArrayList<>();
                Toast.makeText(getBaseContext(), message.getMsg(), Toast.LENGTH_SHORT).show();
            }
            /**
             * 类型 1 不能选择  0可选择 2 已选择
             */
            data = new ArrayList<>();
            List<Time> list = times[timeIndex];
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put(DataDictionary.POP_TIME_TYPE, list.get(i).getIswould());
                map.put(DataDictionary.POP_TIME_TIME, list.get(i).getTime());
                data.add(map);
            }
            gv_index = -1;


            adapter = new PopTimeAdapter(getBaseContext(), data);
            timeView.gv.setAdapter(adapter);
        }
    }

    /**
     * 预约成功后处理
     *
     * @param message
     */
    public void onEvent(BookMessage message) {
        Log.i("order","type:"+message.getType());
        if (message == null) {
            Toast.makeText(getBaseContext(), "出错了。。。", Toast.LENGTH_SHORT).show();
        } else if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.ORDER) {
            if (BOOK_TYPE == 1){
                Toast.makeText(getBaseContext(), message.getMsg(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MyBookActivity.class);
                startActivity(intent);
                finish();
            }else if (BOOK_TYPE == 2){
                Intent intent = new Intent(this, HomeServicePayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ordersn",message.getData().getOrdersn());
                bundle.putString("price",hospital.getSERVICE_FEE()+"");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        } else if (message.getCode().equals(NetCodeEnum.SUCCESS) && message.getType() == ModeEnum.UPDATE_BOOK){
            Toast.makeText(getBaseContext(), message.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityResult result = new ActivityResult();
            result.setType(ActivityResult.UpdateBook);
            EventBus.getDefault().post(result);
            finish();
        }else {
            Log.i("order","tttttype:"+message.getType());
            Toast.makeText(getBaseContext(), message.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 显示时间的view
     */
    class TimeView {
        @ViewInject(R.id.pop_time_cancel)
        TextView cancel;
        @ViewInject(R.id.pop_time_save)
        TextView save;
        @ViewInject(R.id.pop_time_date1)
        TextView data1;
        @ViewInject(R.id.pop_time_date2)
        TextView data2;
        @ViewInject(R.id.pop_time_date3)
        TextView data3;
        @ViewInject(R.id.pop_time_date4)
        TextView data4;
        @ViewInject(R.id.pop_time_date5)
        TextView data5;
        @ViewInject(R.id.pop_time_line1)
        TextView line1;
        @ViewInject(R.id.pop_time_line2)
        TextView line2;
        @ViewInject(R.id.pop_time_line3)
        TextView line3;
        @ViewInject(R.id.pop_time_line4)
        TextView line4;
        @ViewInject(R.id.pop_time_line5)
        TextView line5;
        @ViewInject(R.id.pop_time_week1)
        TextView week1;
        @ViewInject(R.id.pop_time_week2)
        TextView week2;
        @ViewInject(R.id.pop_time_week3)
        TextView week3;
        @ViewInject(R.id.pop_time_week4)
        TextView week4;
        @ViewInject(R.id.pop_time_week5)
        TextView week5;
        @ViewInject(R.id.pop_time_click1)
        AutoRelativeLayout click1;
        @ViewInject(R.id.pop_time_click2)
        AutoRelativeLayout click2;
        @ViewInject(R.id.pop_time_click3)
        AutoRelativeLayout click3;
        @ViewInject(R.id.pop_time_click4)
        AutoRelativeLayout click4;
        @ViewInject(R.id.pop_time_click5)
        AutoRelativeLayout click5;
        @ViewInject(R.id.pop_time_grideview)
        GridView gv;

    }
}
