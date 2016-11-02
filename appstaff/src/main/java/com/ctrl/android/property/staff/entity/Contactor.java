package com.ctrl.android.property.staff.entity;

/**
 * 联系人信息
 * Created by Eric on 2015/12/3.
 */
public class Contactor {

    private String id;//	员工ID
    private String groupId;//	员工所属分组ID
    private String groupName;//	所属员工分组名称
    private int grade;//	职位级别（0：总管、1：部门负责人、2：普通员工）
    private String imgUrl;//	头像Url
    private String name;//	员工姓名
    private String mobile;//	手机号码
    private String telephone;//	固话号码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
