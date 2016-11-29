package com.example.tooth.Adapter;

import android.content.Context;
import android.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Hospital;
import com.example.tooth.R;
import com.example.tooth.Utils.StarBar;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/25.
 */
public class HospitalAdapter extends BaseAdapter {
    private Context mContext;
    private List<Hospital> hospitals;
    public HospitalAdapter(Context context,List<Hospital> list){
        this.mContext = context;
        hospitals = list;
    }
    @Override
    public int getCount() {
        return hospitals==null?0:hospitals.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_dentist,null);
            viewHolder.hopsImg = (ImageView)view.findViewById(R.id.item_fragment_dentist_img);
            viewHolder.hospName = (TextView)view.findViewById(R.id.item_fragment_dentist_hos_name);
            viewHolder.hospStarBar = (StarBar)view.findViewById(R.id.item_fragment_dentist_starbar);
            viewHolder.hospDistance = (TextView)view.findViewById(R.id.item_fragment_dentist_distance);
            viewHolder.hospAddress = (TextView)view.findViewById(R.id.item_fragment_dentist_address);
            viewHolder.recomment = (ImageView)view.findViewById(R.id.item_fragment_dentist_recomment);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Hospital hospital = hospitals.get(i);
        viewHolder.hospStarBar.setStarMark((float) hospital.getASSESS()* 5);
        viewHolder.hospStarBar.setClickable(false);
        viewHolder.hospStarBar.setisClickable(false);
        viewHolder.hospDistance.setText(hospital.getDIS()+"km");
        viewHolder.hospName.setText(hospital.getCC_NAME());
        viewHolder.hospAddress.setText(hospital.getCC_ADDRESS());
        int recomment = hospital.getRECOMMEND();
        if (recomment == 0){
            viewHolder.recomment.setVisibility(View.VISIBLE);
        }else {
            viewHolder.recomment.setVisibility(View.GONE);
        }
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
        x.image().bind(viewHolder.hopsImg,hospital.getCLINIC_PIC(),imageOptions);
        return view;
    }
    class ViewHolder{
        ImageView hopsImg;
        TextView hospName;
        StarBar hospStarBar;
        TextView hospDistance;
        TextView hospAddress;
        ImageView recomment;
    }
}
