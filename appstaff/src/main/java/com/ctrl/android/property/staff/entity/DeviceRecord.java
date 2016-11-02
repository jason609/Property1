package com.ctrl.android.property.staff.entity;

/**
 * 设备养护记录
 * Created by Eric on 2015/11/30.
 */
public class DeviceRecord {

    private String id;//设备养护id
    private String staffId;//养护人id
    private String name;//养护人姓名
    private String content;//养护记录
    private String maintainTime;//养护时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(String maintainTime) {
        this.maintainTime = maintainTime;
    }
}
