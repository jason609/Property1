package com.ctrl.android.property.staff.entity;

/**
 * 设备详细
 * Created by Eric on 2015/11/27.
 */
public class DeviceDetail {

    private String location;//	String	设备位置
    private String name;//	设备名称
    private String purchaseTime;//	购置时间
    private String curingCycle;//	养护周期
    private String managerName;//	责任人姓名
    private String factoryTelephone;//	厂家电话
    private String factoryName;//	厂家姓名

    private String staffId;//员工id

    private String index;
    private String rowCountPerPage;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getCuringCycle() {
        return curingCycle;
    }

    public void setCuringCycle(String curingCycle) {
        this.curingCycle = curingCycle;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getFactoryTelephone() {
        return factoryTelephone;
    }

    public void setFactoryTelephone(String factoryTelephone) {
        this.factoryTelephone = factoryTelephone;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRowCountPerPage() {
        return rowCountPerPage;
    }

    public void setRowCountPerPage(String rowCountPerPage) {
        this.rowCountPerPage = rowCountPerPage;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
