package com.example.tooth.Entity;

import java.io.Serializable;

/**
 * Created by shq1 on 2016/11/2.
 */

public class Address implements Serializable {
    private String PROVINCE;
    private String CITY;
    private String AREA;
    private String ADDRESS;
    private String ZA_ID;
    private String NAME;
    private String MOBILE;

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getZA_ID() {
        return ZA_ID;
    }

    public void setZA_ID(String ZA_ID) {
        this.ZA_ID = ZA_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }
}
