package com.ctrl.android.property.staff.entity;

/**
 * 设备养护记录
 * Created by Eric on 2015/11/30.
 */
public class DeviceRecordDetail {
    private String name;//	养护人
    private String maintainTime;//	养护时间
    private String kindName;//	设备状态名称
    private String content;//	养护内容

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(String maintainTime) {
        this.maintainTime = maintainTime;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
