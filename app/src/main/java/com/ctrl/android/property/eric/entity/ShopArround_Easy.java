package com.ctrl.android.property.eric.entity;

/**
 * 便民商家列表的item 实体
 * Created by Eric on 2015/10/20.
 */
public class ShopArround_Easy {

    /**商家名称*/
    private String shopName;
    /**商家地址*/
    private String shopAddress;
    /**商家电话号码*/
    private String shopPhoneNum;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhoneNum() {
        return shopPhoneNum;
    }

    public void setShopPhoneNum(String shopPhoneNum) {
        this.shopPhoneNum = shopPhoneNum;
    }
}
