package com.example.tooth.Entity;

/**
 * Created by shq1 on 2016/11/23.
 */

public class FamilyRecord {
    private String TIME;//就诊时间
    private String CP_ID;//服务项目
    private String CC_NAME;//诊所姓名
    private String M_ID;//就诊id

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getCP_ID() {
        return CP_ID;
    }

    public void setCP_ID(String CP_ID) {
        this.CP_ID = CP_ID;
    }

    public String getCC_NAME() {
        return CC_NAME;
    }

    public void setCC_NAME(String CC_NAME) {
        this.CC_NAME = CC_NAME;
    }

    public String getM_ID() {
        return M_ID;
    }

    public void setM_ID(String m_ID) {
        M_ID = m_ID;
    }
}
