package com.example.tooth.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Order;
import com.example.tooth.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/28.
 */
public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order> orderList;
    private ClickListener listener;
    public OrderAdapter(Context context,List<Order> list){
        this.mContext = context;
        this.orderList = list;
    }

    public ClickListener getListener() {
        return listener;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return orderList==null?0:orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_order,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("order","pay btn");
            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("order","del btn");
            }
        });
        Order order = orderList.get(i);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        x.image().bind(viewHolder.img,order.getPRODUCT_URL(),imageOptions);
        viewHolder.title.setText(order.getPRODUCT_NAME());
        viewHolder.price.setText(order.getTOTALPRICE());
        viewHolder.num.setText("x"+order.getTOTAL()+"件");
        final String s = order.getSTATUS();
        int state = 0;
        if (s.equals("未付款")) {
            state = 0;
        } else if (s.equals("待发货")){
            state = 1;
        }else if (s.equals("发货")){
            state = 2;
        }else if (s.equals("已完成")){
            state = 3;
        }else if (s.equals("取消")){
            state = 4;
        }
        viewHolder.state.setText(s);
        switch (state){
            case 0:
                //未付款
                viewHolder.del.setVisibility(View.VISIBLE);
                viewHolder.del.setText("取消订单");
                viewHolder.pay.setText("立即付款");
                viewHolder.pay.setVisibility(View.VISIBLE);
                break;
            case 1:
                //待发货
                viewHolder.del.setVisibility(View.GONE);
                viewHolder.del.setText("取消订单");
                viewHolder.pay.setText("立即付款");
                viewHolder.pay.setVisibility(View.GONE);
                break;
            case 2:
                //待收货
                viewHolder.del.setVisibility(View.GONE);
                viewHolder.del.setText("取消订单");
                viewHolder.pay.setText("确认收货");
                viewHolder.pay.setVisibility(View.VISIBLE);
                break;
            case 3:
                //交易成功
                viewHolder.del.setVisibility(View.VISIBLE);
                viewHolder.del.setText("删除订单");
                viewHolder.pay.setText("立即付款");
                viewHolder.pay.setVisibility(View.GONE);
                break;
            case 4:
                //交易关闭
                viewHolder.del.setVisibility(View.GONE);
                viewHolder.del.setText("删除订单");
                viewHolder.pay.setText("立即付款");
                viewHolder.pay.setVisibility(View.GONE);
                break;
        }
        final int finalState = state;
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("order","order adapter:"+s+ finalState);
                if (finalState == 0 || finalState == 3 || finalState == 4){
                    listener.Del(i);
                }
            }
        });
        viewHolder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sure(i);
            }
        });
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_order_img)
        private ImageView img;
        @ViewInject(R.id.item_order_title)
        private TextView title;
       /* @ViewInject(R.id.item_order_type)
        private TextView type;*/
        @ViewInject(R.id.item_order_price)
        private TextView price;
        @ViewInject(R.id.item_order_pay_btn)
        private Button pay;
        @ViewInject(R.id.item_order_del)
        private Button del;
        @ViewInject(R.id.item_order_num)
        private TextView num;
        @ViewInject(R.id.item_order_state)
        TextView state;
    }
    public interface ClickListener{
        void Del(int position);
        void sure(int position);
    }
}
