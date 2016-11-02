package com.ctrl.android.property.eric.entity;

/**
 * 活动
 * Created by Administrator on 2015/10/29.
 */
public class Act {
    private String id;//活动id
    private String memberId;//会员id（物业发起的时候该处为空、会员发起的活动该处是会员id）
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private int actionType;//活动类型（0：个人发起、1：物业发起）
    private int visibleType;//可见类型（0：对外公开、1：仅社区内可见）
    private int checkStatus;//审核状态（0：待审核、1：审核通过、1：审核未通过）
    private int disabled;//是否启用（0：启用、1：禁用）
    private String originalImg;//图片
    private String participateStatus;//参与状态(0:参与,1:未参与)
    private String title;//标题

    private String zipImg;//缩略图

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getVisibleType() {
        return visibleType;
    }

    public void setVisibleType(int visibleType) {
        this.visibleType = visibleType;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public String getParticipateStatus() {
        return participateStatus;
    }

    public void setParticipateStatus(String participateStatus) {
        this.participateStatus = participateStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZipImg() {
        return zipImg;
    }

    public void setZipImg(String zipImg) {
        this.zipImg = zipImg;
    }
}
