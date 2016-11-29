package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tooth.Entity.Score;
import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/28.
 */
public class IntegralAdapter extends BaseAdapter {
    private Context mContext;
    private List<Score> scoreList;
    public IntegralAdapter(Context context,List<Score> list){
        this.mContext = context;
        scoreList = list;
    }
    @Override
    public int getCount() {
        return scoreList==null?0:scoreList.size();
    }

    @Override
    public Object getItem(int i) {
        return scoreList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_integral,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Score score = scoreList.get(i);
        viewHolder.title.setText(score.getRULE());
        viewHolder.time.setText(score.getTIME());
        viewHolder.price.setText(score.getSOCRECHANGE());
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_integral_title)
        private TextView title;
        @ViewInject(R.id.item_integral_time)
        private TextView time;
        @ViewInject(R.id.item_integral_price)
        private TextView price;
    }
}
