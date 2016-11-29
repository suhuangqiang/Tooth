package com.example.tooth.Message;

import com.example.tooth.Entity.Family;

import java.util.List;

/**
 * Created by shq1 on 2016/11/2.
 */

public class FamilyListMessage extends Message {
    public List<Family> data;

    public List<Family> getData() {
        return data;
    }

    public void setData(List<Family> data) {
        this.data = data;
    }
}
