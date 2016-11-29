package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Activity;
import com.example.tooth.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/1.
 */

public class ExerciseAdapter extends BaseAdapter {
    private Context mContext;
    private List<Activity> activities;
    public ExerciseAdapter(Context context,List<Activity> list){
        this.mContext = context;
        this.activities = list;
    }
    @Override
    public int getCount() {
        return activities==null?0:activities.size();
    }

    @Override
    public Object getItem(int i) {
        return activities.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_exercise,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        Activity activity = activities.get(i);
        viewHolder.title.setText(activity.getTITLE());
        viewHolder.des.setText(activity.getINTRODUCE());
        x.image().bind(viewHolder.pic,activity.getIMGURL());

        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_exercise_pic)
        ImageView pic;
        @ViewInject(R.id.item_exercise_type)
        ImageView typeImg;
        @ViewInject(R.id.item_exercise_title)
        TextView title;
        @ViewInject(R.id.item_exercise_des)
        TextView des;
    }
}
