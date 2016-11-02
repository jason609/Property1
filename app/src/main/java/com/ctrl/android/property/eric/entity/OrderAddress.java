package com.ctrl.android.property.eric.entity;

/**
 * 订单内  地址
 * Created by Eric on 2015/11/17.
 */
public class OrderAddress {

    private String addressId;//收货地址ID
    private String mobile;//手机号码
    private String receiveName;//收货人姓名
    private String receiveAddress;//详细收货地址
    private double longitude;//经度
    private double latitude;//纬度

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
