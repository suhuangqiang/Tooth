package com.example.tooth.Message;

import com.example.tooth.Entity.Doctor;

import java.util.List;

/**
 * Created by shq1 on 2016/11/2.
 */

public class DoctorMessage extends Message {
    private List<Doctor> data;

    public List<Doctor> getData() {
        return data;
    }

    public void setData(List<Doctor> data) {
        this.data = data;
    }
}
