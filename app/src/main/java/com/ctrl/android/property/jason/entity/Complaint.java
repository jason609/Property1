package com.ctrl.android.property.jason.entity;

/**
 * 投诉
 * Created by Administrator on 2015/11/2.
 */
public class Complaint {
    private String id;//投诉id
    private String complaintNum;//投诉编号
    private String complaintKindName;//投诉类型名称
    private String createTime;//投诉创建时间
    private String handleStatus;//处理状态

    //投诉详情使用
    private String communityName;//社区名称
    private String building;//楼号
    private String unit;//单元号
    private String room;//房间号
    private String content;//投诉内容
    private String evaluateLevel;//评价等级
    private String evaluateContent;//评价内容
    private String hasEvaluate;//是否评价过（0：未评价1：已评价）
    private String result;//物业处理结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplaintNum() {
        return complaintNum;
    }

    public void setComplaintNum(String complaintNum) {
        this.complaintNum = complaintNum;
    }

    public String getComplaintKindName() {
        return complaintKindName;
    }

    public void setComplaintKindName(String complaintKindName) {
        this.complaintKindName = complaintKindName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEvaluateLevel() {
        return evaluateLevel;
    }

    public void setEvaluateLevel(String evaluateLevel) {
        this.evaluateLevel = evaluateLevel;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getHasEvaluate() {
        return hasEvaluate;
    }

    public void setHasEvaluate(String hasEvaluate) {
        this.hasEvaluate = hasEvaluate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
