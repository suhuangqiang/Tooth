package com.example.tooth.Entity;

/**
 * Created by shq1 on 2016/11/17.
 */

public class ApliResult {
    private String memo;
    private int resultStatus;
    private PayResult alipay_trade_app_pay_response;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public PayResult getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(PayResult alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }
}
