package com.example.tooth.Entity;

/**
 * Created by shq1 on 2016/11/3.
 */

public class Order {
    private String STATUS;
    private int TOTAL;
    private String PRODUCT_URL;
    private String ORDERSN;
    private String TOTALPRICE;
    private String PRODUCT_NAME;

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getPRODUCT_URL() {
        return PRODUCT_URL;
    }

    public void setPRODUCT_URL(String PRODUCT_URL) {
        this.PRODUCT_URL = PRODUCT_URL;
    }

    public String getORDERSN() {
        return ORDERSN;
    }

    public void setORDERSN(String ORDERSN) {
        this.ORDERSN = ORDERSN;
    }

    public String getTOTALPRICE() {
        return TOTALPRICE;
    }

    public void setTOTALPRICE(String TOTALPRICE) {
        this.TOTALPRICE = TOTALPRICE;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }
}
