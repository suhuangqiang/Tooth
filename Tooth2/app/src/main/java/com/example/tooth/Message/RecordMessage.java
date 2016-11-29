package com.example.tooth.Message;

import com.example.tooth.Entity.Record;

import java.util.List;

/**
 * Created by shq1 on 2016/11/23.
 */

public class RecordMessage extends Message {
    private Record data;

    public Record getData() {
        return data;
    }

    public void setData(Record data) {
        this.data = data;
    }
}
