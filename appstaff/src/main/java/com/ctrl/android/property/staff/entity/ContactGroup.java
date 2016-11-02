package com.ctrl.android.property.staff.entity;

import java.util.List;

/**
 * 联系人列表
 * Created by Eric on 2015/12/3.
 */
public class ContactGroup {

    private int flg = 1;//0:展开  1:不展开

    private String id;//	String	分组ID
    private String groupName;//	String	分组名称
    private int staffCount;//	int	当前分组下员工总数

    private List<Contactor> listContactor;

    public int getFlg() {
        return flg;
    }

    public void setFlg(int flg) {
        this.flg = flg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
    }

    public List<Contactor> getListContactor() {
        return listContactor;
    }

    public void setListContactor(List<Contactor> listContactor) {
        this.listContactor = listContactor;
    }
}
