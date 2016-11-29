package com.example.tooth.Message;

import com.example.tooth.Entity.Adv;

import java.util.List;

/**
 * Created by shq1 on 2016/11/8.
 */

public class AdvMessage extends Message {
    private List<Adv> data;
    private int type;

    public List<Adv> getData() {
        return data;
    }

    public void setData(List<Adv> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
