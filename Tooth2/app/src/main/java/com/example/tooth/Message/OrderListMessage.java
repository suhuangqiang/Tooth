package com.example.tooth.Message;

import com.example.tooth.Entity.Order;

import java.util.List;

/**
 * Created by shq1 on 2016/11/3.
 */

public class OrderListMessage extends Message {
    private List<Order> data;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
