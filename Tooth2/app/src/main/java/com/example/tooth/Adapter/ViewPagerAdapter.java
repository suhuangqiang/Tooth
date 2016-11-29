package com.example.tooth.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 苏煌强 on 2016/7/28.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    public ViewPagerAdapter(List<View> views){
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }


    @Override
    public int getCount() {
        if (views != null){
            return views.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(views.get(position),0);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
