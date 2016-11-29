package com.example.tooth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tooth.Entity.Question;
import com.example.tooth.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by shq1 on 2016/11/8.
 */

public class QuestionAdapter extends BaseAdapter {
    private Context mContext;
    private List<Question> questions;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .build();
    public QuestionAdapter(Context context,List<Question> list){
        mContext = context;
        this.questions = list;
    }
    @Override
    public int getCount() {
        return questions==null?0:questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_question,null);
            x.view().inject(viewHolder,view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Question question = questions.get(i);
        x.image().bind(viewHolder.img,question.getIMAGE_URL(),imageOptions);
        viewHolder.time.setText(question.getEND_TIME());
        viewHolder.title.setText(question.getNAME());

        return view;
    }
    class ViewHolder{
        @ViewInject(R.id.item_question_img)
        ImageView img;
        @ViewInject(R.id.item_question_state)
        ImageView state;
        @ViewInject(R.id.item_question_title)
        TextView title;
        @ViewInject(R.id.item_question_time)
        TextView time;
    }
}
