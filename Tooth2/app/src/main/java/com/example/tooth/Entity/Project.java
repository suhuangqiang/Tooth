package com.example.tooth.Entity;

import java.io.Serializable;

/**
 * Created by shq1 on 2016/11/7.
 */

public class Project implements Serializable{
    private String CP_ID;
    private String CP_NAME;

    public String getCP_ID() {
        return CP_ID;
    }

    public void setCP_ID(String CP_ID) {
        this.CP_ID = CP_ID;
    }

    public String getCP_NAME() {
        return CP_NAME;
    }

    public void setCP_NAME(String CP_NAME) {
        this.CP_NAME = CP_NAME;
    }
}
