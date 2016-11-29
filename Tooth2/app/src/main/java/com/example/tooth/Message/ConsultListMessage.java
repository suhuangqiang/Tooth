package com.example.tooth.Message;

import com.example.tooth.Entity.Consult;

import java.util.List;

/**
 * Created by shq1 on 2016/11/14.
 */

public class ConsultListMessage extends Message {
    private List<Consult> data;
    private int type;

    public List<Consult> getData() {
        return data;
    }

    public void setData(List<Consult> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
