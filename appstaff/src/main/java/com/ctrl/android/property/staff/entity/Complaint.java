package com.ctrl.android.property.staff.entity;

/**
 * 报修实体类
 * Created by jason on 2015/11/30.
 */
public class Complaint {
    private String id;//投诉id
    private String handleStatus;//处理状态（0：待处理、1：处理中、2：已处理、3：已结束）
    private String complaintNum;//投诉编号
    private String createTime;//报修创建时间
    private String repairKindName;//投诉类型名称
    private String complaintKindName;
    private String assignTime;

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getComplaintKindName() {
        return complaintKindName;
    }

    public void setComplaintKindName(String complaintKindName) {
        this.complaintKindName = complaintKindName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRepairKindName() {
        return repairKindName;
    }

    public void setRepairKindName(String repairKindName) {
        this.repairKindName = repairKindName;
    }
}
