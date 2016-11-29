package com.example.tooth.Entity;

/**
 * Created by shq1 on 2016/11/2.
 */

public class Activity {
    private String TITLE;
    private String IMGURL;
    private String ACTIVITY_ID;
    private String INTRODUCE;
    private String STATUS;
    private String detailUrl;

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getIMGURL() {
        return IMGURL;
    }

    public void setIMGURL(String IMGURL) {
        this.IMGURL = IMGURL;
    }

    public String getACTIVITY_ID() {
        return ACTIVITY_ID;
    }

    public void setACTIVITY_ID(String ACTIVITY_ID) {
        this.ACTIVITY_ID = ACTIVITY_ID;
    }

    public String getINTRODUCE() {
        return INTRODUCE;
    }

    public void setINTRODUCE(String INTRODUCE) {
        this.INTRODUCE = INTRODUCE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
