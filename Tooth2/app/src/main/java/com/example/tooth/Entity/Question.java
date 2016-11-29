package com.example.tooth.Entity;

/**
 * Created by shq1 on 2016/11/10.
 */

public class Question {
    private String NAME;
    private String END_TIME;
    private String IMAGE_URL;
    private String QUESTIONNAIRE_ID;
    private String detailUrl;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public String getQUESTIONNAIRE_ID() {
        return QUESTIONNAIRE_ID;
    }

    public void setQUESTIONNAIRE_ID(String QUESTIONNAIRE_ID) {
        this.QUESTIONNAIRE_ID = QUESTIONNAIRE_ID;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
