package com.ctrl.android.property.eric.entity;

/**
 * 活动 详细
 * Created by Administrator on 2015/10/29.
 */
public class ActDetail {

    private String content;//内容
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private String originalImg;//图片
    private String title;//标题
    private String actionPeopleName;//活动发起人姓名
    private int status;//参与状态(0:参与,1:未参与)

    public String getActionPeopleName() {
        return actionPeopleName;
    }

    public void setActionPeopleName(String actionPeopleName) {
        this.actionPeopleName = actionPeopleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
