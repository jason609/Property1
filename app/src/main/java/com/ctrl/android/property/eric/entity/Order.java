package com.ctrl.android.property.eric.entity;

/**
 * 订单
 * Created by Eric on 2015/11/18.
 */
public class Order {

    private String id;//订单ID
    private String companyId;//商家ID
    private String companyName;//商家名称
    private String orderNum;//订单编号
    private String createTime;//下单时间
    private double totalCost;//总金额
    private String logoUrl;//商家徽标（logo）地址
    /*订单状态（
    "0"：已取消、
    "1"：未付款、
    "2"：未接单/等待接单、
    "3"：商家取消订单、
    "4"：已接单/等待送达、
    "5"：配送中、
    "6"：待退款、
    "7"：已退款、
    "8"：卖家拒绝退款、
    "9"：退款中、
    "10"：待退货、
    "11"：已退货、
    "12"：卖家拒绝退货、
    "13"：已送达(交易成功)、
    ）*/
    private int orderStatus;

    private int evaluationState;//评价状态（0：未评价、1：已评价）


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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
}
