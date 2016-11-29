package com.example.tooth.Message;

import com.example.tooth.Entity.Appointment;

import java.util.List;

/**
 * Created by shq1 on 2016/11/2.
 */

public class AppointmentListMessage extends Message {
    private List<Appointment> data;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Appointment> getData() {
        return data;
    }

    public void setData(List<Appointment> data) {
        this.data = data;
    }
}
