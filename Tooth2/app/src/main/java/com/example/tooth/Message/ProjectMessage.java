package com.example.tooth.Message;

import com.example.tooth.Entity.Project;

import java.util.List;

/**
 * Created by shq1 on 2016/11/7.
 */

public class ProjectMessage extends Message {
    private List<Project> data;
    private int type;

    public List<Project> getData() {
        return data;
    }

    public void setData(List<Project> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
