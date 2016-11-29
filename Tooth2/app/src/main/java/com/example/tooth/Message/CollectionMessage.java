package com.example.tooth.Message;

import com.example.tooth.Entity.Collection;

import java.util.List;

/**
 * Created by shq1 on 2016/11/8.
 */

public class CollectionMessage extends Message {
    private List<Collection> data;
    private int type;

    public List<Collection> getData() {
        return data;
    }

    public void setData(List<Collection> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
