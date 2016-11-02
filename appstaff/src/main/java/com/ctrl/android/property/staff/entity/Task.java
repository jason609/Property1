package com.ctrl.android.property.staff.entity;

/**
 * 任务列表
 * Created by Eric on 2015/12/2.
 */
public class Task {

    private String id;//	任务id
    private String taskName;//	任务名称
    private String staffId = "";//	执行人id（员工id）
    private String newStaffId = "";//	新增任务执行人id（员工id）
    private int newTaskStatus;//	任务执行状态（0：执行中、1：已完成）
    private String adminId;//	操作者id（任务下达者id）
    private String userName;//	用户名
    private int taskStatus;//	任务执行状态（0：执行中、1：已完成）

    private String name;//任务来自

    private String rowCountPerPage;
    private String index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public int getNewTaskStatus() {
        return newTaskStatus;
    }

    public void setNewTaskStatus(int newTaskStatus) {
        this.newTaskStatus = newTaskStatus;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
