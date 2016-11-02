package com.ctrl.android.property.eric.entity;

/**
 * 用户实体
 * Created by Eric on 2015/10/30.
 */
public class MemberInfo {

    private String memberId;//会员Id
    private String userName;//用户名（登录手机号码或者邮箱）
    private String nickName;//昵称
    private int gender;//性别（1:男、 2:女）
    private int point;//积分
    private String imgUrl;//头像地址
    private String hasPayPwd;//是否设置了支付密码（0：未设置、1：已设置）

    private String communityName;//社区名称
    private String addressId;//社区住址ID
    private String building;//楼号
    private String unit;//单元号
    private String room;//房号

    private int orderCount;//订单数
    private int expressCount;//快递数

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHasPayPwd() {
        return hasPayPwd;
    }

    public void setHasPayPwd(String hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getExpressCount() {
        return expressCount;
    }

    public void setExpressCount(int expressCount) {
        this.expressCount = expressCount;
    }
}
