package com.example.tooth.Entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by shq1 on 2016/11/2.
 * 用户信息
 */
@Table(name = "User")
public class User {
    @Column(name = "weixin_bind")
    public String WEIXIN_BIND;
    @Column(name = "realname")
    public String REALNAME;
    @Column(name = "phone")
    public String PHONE;
    @Column(name = "qq_bind")
    public String QQ_BIND;
    @Column(name = "doctor_times")
    public String DOCTOR_TIMES;
    @Column(name = "service_times")
    public String SERVICE_TIMES;
    @Column(name = "integral_num")
    public String INTEGRAL_NUM;
    @Column(name = "username")
    public String USERNAME;
    @Column(name = "user_id" , isId = true)
    public String USER_ID;
    @Column(name = "yyq_num")
    public String YYQ_NUM;
    @Column(name = "post_num")
    public String POST_NUM;
    @Column(name = "head_url")
    public String HEADURL;

    private String goodUrl;

    public String getWEIXIN_BIND() {
        return WEIXIN_BIND;
    }

    public void setWEIXIN_BIND(String WEIXIN_BIND) {
        this.WEIXIN_BIND = WEIXIN_BIND;
    }

    public String getREALNAME() {
        return REALNAME;
    }

    public void setREALNAME(String REALNAME) {
        this.REALNAME = REALNAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getQQ_BIND() {
        return QQ_BIND;
    }

    public void setQQ_BIND(String QQ_BIND) {
        this.QQ_BIND = QQ_BIND;
    }

    public String getDOCTOR_TIMES() {
        return DOCTOR_TIMES;
    }

    public void setDOCTOR_TIMES(String DOCTOR_TIMES) {
        this.DOCTOR_TIMES = DOCTOR_TIMES;
    }

    public String getSERVICE_TIMES() {
        return SERVICE_TIMES;
    }

    public void setSERVICE_TIMES(String SERVICE_TIMES) {
        this.SERVICE_TIMES = SERVICE_TIMES;
    }

    public String getINTEGRAL_NUM() {
        return INTEGRAL_NUM;
    }

    public void setINTEGRAL_NUM(String INTEGRAL_NUM) {
        this.INTEGRAL_NUM = INTEGRAL_NUM;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getYYQ_NUM() {
        return YYQ_NUM;
    }

    public void setYYQ_NUM(String YYQ_NUM) {
        this.YYQ_NUM = YYQ_NUM;
    }

    public String getPOST_NUM() {
        return POST_NUM;
    }

    public void setPOST_NUM(String POST_NUM) {
        this.POST_NUM = POST_NUM;
    }

    public String getHEADURL() {
        return HEADURL;
    }

    public void setHEADURL(String HEADURL) {
        this.HEADURL = HEADURL;
    }

    public String getGoodUrl() {
        return goodUrl;
    }

    public void setGoodUrl(String goodUrl) {
        this.goodUrl = goodUrl;
    }
}
