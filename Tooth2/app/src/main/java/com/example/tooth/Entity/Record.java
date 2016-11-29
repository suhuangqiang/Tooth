package com.example.tooth.Entity;

import java.util.List;

/**
 * Created by shq1 on 2016/11/23.
 */

public class Record {
    private String ZE_NAME;//医生姓名
    private String ADVICE;//医嘱
    private String CP_NAME;//服务项目
    private String TIME;//就诊时间
    private String DETAIL;//详情
    private List<String> IMAGEURL;//图片信息
    private String PRESCRIBESMS;//开药信息
    private String CC_NAME;//诊所信息

    public String getZE_NAME() {
        return ZE_NAME;
    }

    public void setZE_NAME(String ZE_NAME) {
        this.ZE_NAME = ZE_NAME;
    }

    public String getADVICE() {
        return ADVICE;
    }

    public void setADVICE(String ADVICE) {
        this.ADVICE = ADVICE;
    }

    public String getCP_NAME() {
        return CP_NAME;
    }

    public void setCP_NAME(String CP_NAME) {
        this.CP_NAME = CP_NAME;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public void setDETAIL(String DETAIL) {
        this.DETAIL = DETAIL;
    }

    public List<String> getIMAGEURL() {
        return IMAGEURL;
    }

    public void setIMAGEURL(List<String> IMAGEURL) {
        this.IMAGEURL = IMAGEURL;
    }

    public String getPRESCRIBESMS() {
        return PRESCRIBESMS;
    }

    public void setPRESCRIBESMS(String PRESCRIBESMS) {
        this.PRESCRIBESMS = PRESCRIBESMS;
    }

    public String getCC_NAME() {
        return CC_NAME;
    }

    public void setCC_NAME(String CC_NAME) {
        this.CC_NAME = CC_NAME;
    }
}
