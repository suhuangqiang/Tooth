package com.example.tooth.Message;

import com.example.tooth.Entity.FamilyRecord;

import java.util.List;

/**
 * Created by shq1 on 2016/11/23.
 */

public class FamilyDetailMessage extends Message {
    List<FamilyRecord> data;

    public List<FamilyRecord> getData() {
        return data;
    }

    public void setData(List<FamilyRecord> data) {
        this.data = data;
    }
}
