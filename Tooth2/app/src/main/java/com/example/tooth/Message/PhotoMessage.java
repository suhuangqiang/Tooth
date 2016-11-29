package com.example.tooth.Message;

import com.example.tooth.Entity.Photo;

/**
 * Created by shq1 on 2016/11/9.
 */

public class PhotoMessage extends Message {
    private Photo data;
    private int type;

    public Photo getData() {
        return data;
    }

    public void setData(Photo data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
