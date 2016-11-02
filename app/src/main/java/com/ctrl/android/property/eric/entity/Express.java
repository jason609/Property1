package com.ctrl.android.property.eric.entity;

/**
 * 快递实体
 * Created by Administrator on 2015/10/28.
 */
public class Express {
    private String id;//快递ID
    private String logisticsCompanyName;//快递公司名称
    private int status;//领取状态（0：待领取、1：已领取）
    private String createTime;//发布时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
