package com.ctrl.android.property.eric.entity;

/**
 * 周边商家列表
 * Created by Eric on 2015/11/4.
 */
public class Shop {
    private String picUrl;//商家分类图标URL
    private String com;//周边商家名称
    private String mobile;//商家电话
    private String createTime;//创建时间

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
