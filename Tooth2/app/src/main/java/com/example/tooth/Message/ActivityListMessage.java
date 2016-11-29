package com.example.tooth.Message;

import com.example.tooth.Entity.Activity;

import java.util.List;

/**
 * Created by shq1 on 2016/11/2.
 */

public class ActivityListMessage extends Message {
    private List<Activity> data;

    public List<Activity> getData() {
        return data;
    }

    public void setData(List<Activity> data) {
        this.data = data;
    }
}
