package com.example.tooth.Message;

import com.example.tooth.Entity.Sign;

/**
 * Created by shq1 on 2016/11/17.
 */

public class SignMessage extends Message{
    private Sign data;
    private int type;

    public Sign getData() {
        return data;
    }

    public void setData(Sign data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
