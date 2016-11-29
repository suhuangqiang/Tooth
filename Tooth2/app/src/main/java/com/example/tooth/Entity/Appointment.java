package com.example.tooth.Entity;

import java.io.Serializable;

/**
 * Created by shq1 on 2016/11/2.
 */

public class Appointment implements Serializable{
    private String SUB_TIME;
    private String NEXTTIME;
    private String DAY;
    private String CC_NAME;
    private String CP_NAME;
    private int STATUS;
    private String CLINIC_PIC;
    private double ASSESS;
    private String CC_ID;//诊所id
    private String CR_ID;
    private String REMARK;
    private String ZZ_NAME;
    private String CP_TYPE;
    private String F_ID;
    private String F_NAME;

    public String getSUB_TIME() {
        return SUB_TIME;
    }

    public void setSUB_TIME(String SUB_TIME) {
        this.SUB_TIME = SUB_TIME;
    }

    public String getNEXTTIME() {
        return NEXTTIME;
    }

    public void setNEXTTIME(String NEXTTIME) {
        this.NEXTTIME = NEXTTIME;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getCC_NAME() {
        return CC_NAME;
    }

    public void setCC_NAME(String CC_NAME) {
        this.CC_NAME = CC_NAME;
    }

    public String getCP_NAME() {
        return CP_NAME;
    }

    public void setCP_NAME(String CP_NAME) {
        this.CP_NAME = CP_NAME;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getCLINIC_PIC() {
        return CLINIC_PIC;
    }

    public void setCLINIC_PIC(String CLINIC_PIC) {
        this.CLINIC_PIC = CLINIC_PIC;
    }

    public double getASSESS() {
        return ASSESS;
    }

    public void setASSESS(double ASSESS) {
        this.ASSESS = ASSESS;
    }

    public void setASSESS(int ASSESS) {
        this.ASSESS = ASSESS;
    }

    public String getCC_ID() {
        return CC_ID;
    }

    public void setCC_ID(String CC_ID) {
        this.CC_ID = CC_ID;
    }

    public String getCR_ID() {
        return CR_ID;
    }

    public void setCR_ID(String CR_ID) {
        this.CR_ID = CR_ID;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getZZ_NAME() {
        return ZZ_NAME;
    }

    public void setZZ_NAME(String ZZ_NAME) {
        this.ZZ_NAME = ZZ_NAME;
    }

    public String getCP_TYPE() {
        return CP_TYPE;
    }

    public void setCP_TYPE(String CP_TYPE) {
        this.CP_TYPE = CP_TYPE;
    }

    public String getF_ID() {
        return F_ID;
    }

    public void setF_ID(String f_ID) {
        F_ID = f_ID;
    }

    public String getF_NAME() {
        return F_NAME;
    }

    public void setF_NAME(String f_NAME) {
        F_NAME = f_NAME;
    }
}
