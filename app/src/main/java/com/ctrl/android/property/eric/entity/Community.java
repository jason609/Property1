package com.ctrl.android.property.eric.entity;

/**
 * 社区列表
 * Created by Eric on 2015/11/2.
 */
public class Community {

    private String id;//社区ID
    private String communityName;//社区名称

    private String housesBindId;// 业主绑定ID
    private String proprietorId;// 业主ID
    private String proprietorName;// 业主姓名
    private String addressId;// 业主住址ID
    private String building;// 楼号
    private String unit;// 单元号
    private String room;// 房号


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getHousesBindId() {
        return housesBindId;
    }

    public void setHousesBindId(String housesBindId) {
        this.housesBindId = housesBindId;
    }

    public String getProprietorId() {
        return proprietorId;
    }

    public void setProprietorId(String proprietorId) {
        this.proprietorId = proprietorId;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
