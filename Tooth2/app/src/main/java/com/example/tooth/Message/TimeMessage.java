package com.example.tooth.Message;

import com.example.tooth.Entity.Time;

import java.util.List;

/**
 * Created by shq1 on 2016/11/4.
 */

public class TimeMessage  extends Message{
    private List<Time> data;

    public List<Time> getData() {
        return data;
    }

    public void setData(List<Time> data) {
        this.data = data;
    }
}
