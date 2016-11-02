package com.ctrl.android.property.jason.entity;

import java.io.Serializable;

/**
 * 收货地址
 * Created by Administrator on 2015/10/29.
 */
public class MemberAddress implements Serializable{
    private String receiveAddressId;//收货地址ID
    private String mobile;//手机号码
    private String communityName;//社区名称
    private String receiveName;//收货人姓名
    private String provinceName;//省份
    private String cityName;//市
    private String areaName;//区
    private String streetName;//街道
    private String address;//详细地址
    private String isDefault;//是否默认地址（0：非默认、 1：默认）

    public String getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(String receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public String getProvinceName(){return provinceName;}

    public void setProvinceName(){this.provinceName=provinceName;}

    public String getCityName(){return cityName;}

    public void setCityName(){this.cityName=cityName;}

    public String getAreaName(){return areaName;}

    public void setAreaName(){this.areaName=areaName;}

    public String getStreetName(){return streetName;}

    public void setStreetName(){this.streetName=streetName;}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
