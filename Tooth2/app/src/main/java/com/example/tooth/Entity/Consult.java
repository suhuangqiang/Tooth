package com.example.tooth.Entity;

import java.io.Serializable;

/**
 * Created by shq1 on 2016/11/14.
 */

public class Consult implements Serializable{
    private String KQ_TITLE;
    private String KQ_PIC_URL;
    private String KQ_ID;
    private String KQ_DESCRIPTION;
    private String KQ_URL;
    private int ISHAVE;

    public String getKQ_TITLE() {
        return KQ_TITLE;
    }

    public void setKQ_TITLE(String KQ_TITLE) {
        this.KQ_TITLE = KQ_TITLE;
    }

    public String getKQ_PIC_URL() {
        return KQ_PIC_URL;
    }

    public void setKQ_PIC_URL(String KQ_PIC_URL) {
        this.KQ_PIC_URL = KQ_PIC_URL;
    }

    public String getKQ_ID() {
        return KQ_ID;
    }

    public void setKQ_ID(String KQ_ID) {
        this.KQ_ID = KQ_ID;
    }

    public String getKQ_DESCRIPTION() {
        return KQ_DESCRIPTION;
    }

    public void setKQ_DESCRIPTION(String KQ_DESCRIPTION) {
        this.KQ_DESCRIPTION = KQ_DESCRIPTION;
    }

    public String getKQ_URL() {
        return KQ_URL;
    }

    public void setKQ_URL(String KQ_URL) {
        this.KQ_URL = KQ_URL;
    }

    public int getISHAVE() {
        return ISHAVE;
    }

    public void setISHAVE(int ISHAVE) {
        this.ISHAVE = ISHAVE;
    }
}
