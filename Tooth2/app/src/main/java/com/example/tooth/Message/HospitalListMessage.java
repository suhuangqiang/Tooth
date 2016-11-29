package com.example.tooth.Message;

import com.example.tooth.Entity.Hospital;

import java.util.List;

/**
 * Created by shq1 on 2016/11/2.
 */

public class HospitalListMessage extends Message {
    private List<Hospital> data;

    public List<Hospital> getData() {
        return data;
    }

    public void setData(List<Hospital> data) {
        this.data = data;
    }
}
