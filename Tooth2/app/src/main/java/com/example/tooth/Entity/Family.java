package com.example.tooth.Entity;

import java.io.Serializable;

/**
 * Created by shq1 on 2016/11/2.
 */

public class Family implements Serializable {
    public String F_NAME;
    public int SEX;
    public String PHONE;
    public String RELATIONSHIP;
    public String BIR_DATE;
    public String AGE;
    public String USERNAME;
    public String FAMILY_ID;
    private String PROVINCE;
    private String CITY;
    private String AREA;
    private String ADDRESS;
    public String phone;
    public String name;
    public int sex;
    public String relationship;
    public String birTime;


    public String getF_NAME() {
        return F_NAME;
    }

    public void setF_NAME(String f_NAME) {
        F_NAME = f_NAME;
    }

    public int getSEX() {
        return SEX;
    }

    public void setSEX(int SEX) {
        this.SEX = SEX;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getRELATIONSHIP() {
        return RELATIONSHIP;
    }

    public void setRELATIONSHIP(String RELATIONSHIP) {
        this.RELATIONSHIP = RELATIONSHIP;
    }

    public String getBIR_DATE() {
        return BIR_DATE;
    }

    public void setBIR_DATE(String BIR_DATE) {
        this.BIR_DATE = BIR_DATE;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getFAMILY_ID() {
        return FAMILY_ID;
    }

    public void setFAMILY_ID(String FAMILY_ID) {
        this.FAMILY_ID = FAMILY_ID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getBirTime() {
        return birTime;
    }

    public void setBirTime(String birTime) {
        this.birTime = birTime;
    }

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
}
