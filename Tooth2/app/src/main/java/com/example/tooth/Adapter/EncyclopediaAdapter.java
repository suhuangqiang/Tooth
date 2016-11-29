package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Consult;
import com.example.tooth.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/10/31.
 */
public class EncyclopediaAdapter extends BaseAdapter {
    private Context mContext;
    private List<Consult> consults;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .build();
    public EncyclopediaAdapter(Context context,List<Consult> list){
        this.mContext = context;
        this.consults = list;
    }
    @Override
    public int getCount() {
        return consults==null?0:consults.size();
    }

    @Override
    public Object getItem(int i) {
        return consults.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_encyclopedias,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Consult consult = consults.get(i);
        viewHolder.content.setText(consult.getKQ_DESCRIPTION());
        viewHolder.title.setText(consult.getKQ_TITLE());
        x.image().bind(viewHolder.img,consult.getKQ_PIC_URL(),imageOptions);
        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_encyclopedisa_img)
        private ImageView img;
        @ViewInject(R.id.item_encyclopedisa_title)
        private TextView title;
        @ViewInject(R.id.item_encyclopedisa_content)
        private TextView content;
    }
}
