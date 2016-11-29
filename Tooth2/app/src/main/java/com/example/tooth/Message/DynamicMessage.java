package com.example.tooth.Message;

import com.example.tooth.Entity.Dynamic;

import java.util.List;

/**
 * Created by shq1 on 2016/11/3.
 */

public class DynamicMessage extends Message {
    private List<Dynamic> data;
    private int type;

    public List<Dynamic> getData() {
        return data;
    }

    public void setData(List<Dynamic> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
