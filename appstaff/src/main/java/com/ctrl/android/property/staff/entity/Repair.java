package com.ctrl.android.property.staff.entity;

/**
 * 报修实体类
 * Created by jason on 2015/11/30.
 */
public class Repair {
    private String repairDemandId;//报修id
    private String repairNum;//报修编号
    private String repairKindName;//报修类型名称
    private String createTime;//报修创建时间
    private String handleStatus;//处理状态
    private String id;
    private String assignTime;




    //报修详情使用
    private String communityName;//社区名称
    private String building;//楼号
    private String unit;//单元号
    private String room;//房间号
    private String content;//报修内容
    private String evaluateLevel;//评价等级
    private String evaluateContent;//评价内容
    private String hasEvaluate;//是否评价过（0：未评价1：已评价）
    private String result;//物业处理结果

    private String communityId;//社区ID
    private String proprietorId;//业主id
    private String addressId;//地区ID
    private String title;//报修标题
    private String acceptState;//任务接收状态
    private String staffName;//员工姓名
    private String complaintKindId;//投诉类型id

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getComplaintKindId() {
        return complaintKindId;
    }

    public void setComplaintKindId(String complaintKindId) {
        this.complaintKindId = complaintKindId;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepairDemandId() {
        return repairDemandId;
    }

    public void setRepairDemandId(String repairDemandId) {
        this.repairDemandId = repairDemandId;
    }

    public String getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(String repairNum) {
        this.repairNum = repairNum;
    }

    public String getRepairKindName() {
        return repairKindName;
    }

    public void setRepairKindName(String repairKindName) {
        this.repairKindName = repairKindName;
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


}
