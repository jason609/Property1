package com.ctrl.android.property.staff.entity;

/**
 * 快递详细信息
 * Created by Administrator on 2015/10/28.
 */
public class ExpressRecive {
    private String recipientName;//收件人姓名
    private String mobile;//收件人电话
    private String communityName;//社区名称
    private String building;//楼号
    private String unit;//单元号
    private String room;//房号
    private String kindName;//快递公司名称
    private String logisticsNum;//快递编号（快递单号）
    private String qrImgUrl = "aa";//区间二维码图片URL
    private int status;//领取状态（0：待领取、1：已领取）
    private String createTime;//创建时间
    private String signTime;//取件时间
    //private List<GoodPic> expressPicList;//快递图片

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public String getQrImgUrl() {
        return qrImgUrl;
    }

    public void setQrImgUrl(String qrImgUrl) {
        this.qrImgUrl = qrImgUrl;
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

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

//    public List<GoodPic> getExpressPicList() {
//        return expressPicList;
//    }
//
//    public void setExpressPicList(List<GoodPic> expressPicList) {
//        this.expressPicList = expressPicList;
//    }
}
