package com.ctrl.android.property.staff.entity;

import java.util.List;

/**
 * 到访实体
 * Created by jasom on 2015/11/26
 */
public class Visit {
    private String communityVisitId;//社区到访id
    private String communityId;//社区id
    private String proprietorId;//业主id
    private String addressId;//业主住址id
    private int type;//到访类型（0：预约到访、1：突发到访）
    private String visitorName;//到访人姓名
    private String arriveTime ;//到访时间
    private int peopleNum;//到访人数
    private String numberPlates;//车牌号码
    private String residenceTime;//预计停留时间（注：单位已输入为准）
    private String visitNum;//到访编号
    private String createTime;//创建时间
    private int systemStatus;//系统状态（0：无操作、1：系统关闭）
    private int handleStatus;//处理状态（0：待处理、1：同意到访、2：拒绝到访、3：业主不在家）

    //获取社区到访信息时用
    private String memberName;//拜访人姓名
    private String visitorMobile;//拜访人电话
    private String visitorHeadImg;//拜访人照片
    private String qrImgUrl;//二维码url
    private String proprietorName;//业主姓名

    private String building;//到访楼号
    private String unit;//到访单元号
    private String room;//到访房号
    private String communityName;//到访社区名称

    private List<Img2> communityVisitPictureList;//到访图片

    public List<Img2> getCommunityVisitPictureList() {
        return communityVisitPictureList;
    }

    public void setCommunityVisitPictureList(List<Img2> communityVisitPictureList) {
        this.communityVisitPictureList = communityVisitPictureList;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getCommunityVisitId() {
        return communityVisitId;
    }

    public void setCommunityVisitId(String communityVisitId) {
        this.communityVisitId = communityVisitId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getProprietorId() {
        return proprietorId;
    }

    public void setProprietorId(String proprietorId) {
        this.proprietorId = proprietorId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getNumberPlates() {
        return numberPlates;
    }

    public void setNumberPlates(String numberPlates) {
        this.numberPlates = numberPlates;
    }

    public String getResidenceTime() {
        return residenceTime;
    }

    public void setResidenceTime(String residenceTime) {
        this.residenceTime = residenceTime;
    }

    public String getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(String visitNum) {
        this.visitNum = visitNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(int systemStatus) {
        this.systemStatus = systemStatus;
    }

    public int getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    public String getVisitorHeadImg() {
        return visitorHeadImg;
    }

    public void setVisitorHeadImg(String visitorHeadImg) {
        this.visitorHeadImg = visitorHeadImg;
    }

    public String getQrImgUrl() {
        return qrImgUrl;
    }

    public void setQrImgUrl(String qrImgUrl) {
        this.qrImgUrl = qrImgUrl;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
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

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
