package com.ctrl.android.property.staff.entity;

/**
 *
 * Created by Eric on 2015/12/2.
 */
public class TaskDetail {

    private String id;//	任务id
    private String content	;//	任务详情
    private String communityId	;//	社区id
    private String taskName	;//	任务名称
    private int taskStatus	;//	任务执行状态（0：执行中、1：已完成）

    private String name;//下达人 名字

    private String staffId	;//	任务执行人id
    private String newStaffId	;//	新增任务执行人id
    private String staffName	;//	任务执行人姓名
    private String newStaffName	;//	新增任务执行人姓名

    private String taskFeedback	;//	任务反馈
    private String feedbackTime	;//	任务反馈时间
    private String newTaskContent	;//	新增任务说明
    private int newTaskStatus	;//	新增任务执行状态（0：执行中、1：已完成）
    private String newTaskFeedback	;//	新增任务反馈
    private String newFeedbackTime	;//	新增任务反馈时间
    private String createTime	;//	创建时间
    private String userName	;//	用户名
    private int status	;//	判断是否可以追加任务(0:可以,1:不可以)

    private String rowCountPerPage;
    private String index;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskFeedback() {
        return taskFeedback;
    }

    public void setTaskFeedback(String taskFeedback) {
        this.taskFeedback = taskFeedback;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getNewTaskContent() {
        return newTaskContent;
    }

    public void setNewTaskContent(String newTaskContent) {
        this.newTaskContent = newTaskContent;
    }

    public int getNewTaskStatus() {
        return newTaskStatus;
    }

    public void setNewTaskStatus(int newTaskStatus) {
        this.newTaskStatus = newTaskStatus;
    }

    public String getNewTaskFeedback() {
        return newTaskFeedback;
    }

    public void setNewTaskFeedback(String newTaskFeedback) {
        this.newTaskFeedback = newTaskFeedback;
    }

    public String getNewFeedbackTime() {
        return newFeedbackTime;
    }

    public void setNewFeedbackTime(String newFeedbackTime) {
        this.newFeedbackTime = newFeedbackTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRowCountPerPage() {
        return rowCountPerPage;
    }

    public void setRowCountPerPage(String rowCountPerPage) {
        this.rowCountPerPage = rowCountPerPage;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getNewStaffId() {
        return newStaffId;
    }

    public void setNewStaffId(String newStaffId) {
        this.newStaffId = newStaffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNewStaffName() {
        return newStaffName;
    }

    public void setNewStaffName(String newStaffName) {
        this.newStaffName = newStaffName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
