package com.ctrl.android.property.eric.entity;

/**
 * 收货地址
 * Created by Eric on 2015/10/30.
 */
public class ReceiveAddress {

    private String receiveAddressId;//收货地址ID
    private String mobile;//手机号码
    private String communityName;//社区名称
    private String receiveName;//收货人姓名
    private String address;//收货地址
    private String isDefault;//是否默认地址（0：非默认、 1：默认）

    public String getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(String receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

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
