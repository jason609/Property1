package com.ctrl.android.property.eric.entity;

/**
 * 订单 详细
 * Created by Eric on 2015/11/18.
 */
public class OrderDetail {

    private String orderId;//订单ID
    private String orderNum;//订单编号
    private String companyId;//	商家ID
    private String companyName;//商家名称
    private String createTime;//下单时间
    private int payMode;//支付方式（1：余额支付、2：线下支付【货到付款】、3、支付宝、4：微信、5：银联卡）
    private String receiverName;//收货人姓名
    private String receiverMobile;//收货人电话
    private double totalCost;//总金额
    private String carriagePrice;//配送费用
    private String provinceName;//收货地址（所在省）
    private String cityName;//收货地址（所在市）
    private String areaName;//收货地址（所在区）
    private String address;//详细收货地址
    private String arriveTime;//成交时间
    private int evaluationState;//评价状态（0：未评价、1：已评价）
    private int orderStatus;//订单状态
    private int payStatus;//支付状态(0:未付款、1:已付款)


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCarriagePrice() {
        return carriagePrice;
    }

    public void setCarriagePrice(String carriagePrice) {
        this.carriagePrice = carriagePrice;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(int evaluationState) {
        this.evaluationState = evaluationState;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
}
