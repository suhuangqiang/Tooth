package com.example.tooth.Entity;

import java.io.Serializable;

/**
 * Created by shq1 on 2016/11/2.
 */

public class Hospital implements Serializable{
    private String CLINIC_PIC;
    private String CC_ADDRESS;
    private String CC_ID;
    private String CC_NAME;
    private double ASSESS;
    private String detailUrl;
    private double DIS;
    private double SERVICE_FEE;
    private int ISHAVE;
    private double latitude;
    private double longitude;
    private int RECOMMEND;

    public String getCLINIC_PIC() {
        return CLINIC_PIC;
    }

    public void setCLINIC_PIC(String CLINIC_PIC) {
        this.CLINIC_PIC = CLINIC_PIC;
    }

    public String getCC_ADDRESS() {
        return CC_ADDRESS;
    }

    public void setCC_ADDRESS(String CC_ADDRESS) {
        this.CC_ADDRESS = CC_ADDRESS;
    }

    public String getCC_ID() {
        return CC_ID;
    }

    public void setCC_ID(String CC_ID) {
        this.CC_ID = CC_ID;
    }

    public String getCC_NAME() {
        return CC_NAME;
    }

    public void setCC_NAME(String CC_NAME) {
        this.CC_NAME = CC_NAME;
    }

    public double getASSESS() {
        return ASSESS;
    }

    public void setASSESS(double ASSESS) {
        this.ASSESS = ASSESS;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public double getDIS() {
        return DIS;
    }

    public void setDIS(double DIS) {
        this.DIS = DIS;
    }

    public double getSERVICE_FEE() {
        return SERVICE_FEE;
    }

    public void setSERVICE_FEE(double SERVICE_FEE) {
        this.SERVICE_FEE = SERVICE_FEE;
    }

    public int getISHAVE() {
        return ISHAVE;
    }

    public void setISHAVE(int ISHAVE) {
        this.ISHAVE = ISHAVE;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRECOMMEND() {
        return RECOMMEND;
    }

    public void setRECOMMEND(int RECOMMEND) {
        this.RECOMMEND = RECOMMEND;
    }
}
