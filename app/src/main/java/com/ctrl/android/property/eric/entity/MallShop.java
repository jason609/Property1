package com.ctrl.android.property.eric.entity;

/**
 * 商城 商家
 * Created by Eric on 2015/11/11.
 */
public class MallShop {

    private String id;//商家ID
    private String companyName;//商家名称
    private String logoUrl;//Logo地址
    private String businessStartTime;//营业开始时间
    private String businessEndTime;//营业结束时间
    private int dis;//距离

    private float sellersCredit;//卖家信用
    private String orders;//最近订单数
    private String evaluate;//全部评价
    private String mobile;//商家移动电话
    private String introduction;//店铺介绍
    private String address;//公司详细地址
    private int status;//收藏状态（0：未收藏、1：已收藏）

    private String rowCountPerPage;//
    private String index;//

    private String isAllday;//
    private String state;//
    private String longitude;//
    private String latitude;//
    private String disabled;//
    private float evaluatLevel;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public String getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(String businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public float getSellersCredit() {
        return sellersCredit;
    }

    public void setSellersCredit(float sellersCredit) {
        this.sellersCredit = sellersCredit;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    public String getRowCountPerPage() {
        return rowCountPerPage;
    }

    public void setRowCountPerPage(String rowCountPerPage) {
        this.rowCountPerPage = rowCountPerPage;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIsAllday() {
        return isAllday;
    }

    public void setIsAllday(String isAllday) {
        this.isAllday = isAllday;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public float getEvaluatLevel() {
        return evaluatLevel;
    }

    public void setEvaluatLevel(float evaluatLevel) {
        this.evaluatLevel = evaluatLevel;
    }
}
