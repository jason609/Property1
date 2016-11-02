package com.ctrl.android.property.staff.entity;

/**
 * 报修实体类
 * Created by jason on 2015/11/30.
 */
public class ComplaintInfo {
    private String id;//投诉id
    private String communityId;//社区id
    private String proprietorId;//业主id
    private String addressId;//地区id
    private String title;//投诉标题
    private String acceptState;//任务接收状态（0：待接收、1：已接收、2：已拒收）
    private String handleStatus;//处理状态（0：待处理、1：处理中、2：已处理、3：已结束）
    private String complaintNum;//投诉编号
    private String createTime;//投诉时间
    private String content;//投诉内容
    private String complaintKindName;//投诉类型名称
    private String complaintKindId;//投诉类型id
    private String building;//楼号
    private String unit;//单元号
    private String room;//房间号
    private String communityName;//社区名称
    private String result;//物业处理结果
    private String hasEvaluate;//是否评价过（0：未评价1：已评价）
    private String evaluateLevel;//评价等级
    private String evaluateContent;//评价内容
    private String staffName;//员工姓名
    private String assignTime;//任务分配时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcceptState() {
        return acceptState;
    }

    public void setAcceptState(String acceptState) {
        this.acceptState = acceptState;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getComplaintNum() {
        return complaintNum;
    }

    public void setComplaintNum(String complaintNum) {
        this.complaintNum = complaintNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComplaintKindName() {
        return complaintKindName;
    }

    public void setComplaintKindName(String complaintKindName) {
        this.complaintKindName = complaintKindName;
    }

    public String getComplaintKindId() {
        return complaintKindId;
    }

    public void setComplaintKindId(String complaintKindId) {
        this.complaintKindId = complaintKindId;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHasEvaluate() {
        return hasEvaluate;
    }

    public void setHasEvaluate(String hasEvaluate) {
        this.hasEvaluate = hasEvaluate;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }
}
