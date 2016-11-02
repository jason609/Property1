package com.ctrl.android.property.eric.entity;

/**
 * 物业缴费
 * Created by Eric on 2015/12/14.
 */
public class PropertyPay {

    private String propertyPaymentId;//	String	物业缴费账单id
    private String proprietorId;//业主id
    private String communityId;//社区id
    private String addressId;//住址id
    private String communityName;//社区名称
    private int payType;//收费项目（0：物业服务费、1：采暖费:2：水费、3：电费、4：停车服务费、5：车位租赁费、6：生活垃圾清运费、7：电梯运行费）
    private String payStandard;//收费标准（物业费(单价*面积)0.90、水费(居民)4.20）
    private int status;//缴费状态（0：等待缴费、1：已缴费）
    private double charge;//应缴费用（不包括滞纳金）
    private double penalty;//滞纳金
    private double receivableMoney;//应收金额（应缴费用+滞纳金）
    private double receivedMoney;//已收金额（excel中的，只是显示）
    private double debt;//欠款（最终用户需要缴纳的金额=应收金额-已收金额）最终金额
    private int debtMonth;//欠款月数
    private String receivableTime;//应收日期
    private String applyStartTime;//费用发生开始日期
    private String applyEndTime;//费用发生结束日期

    private int flg = 1;
    private int payFlg = 0;//1:已加入 0:未加入

    public String getPropertyPaymentId() {
        return propertyPaymentId;
    }

    public void setPropertyPaymentId(String propertyPaymentId) {
        this.propertyPaymentId = propertyPaymentId;
    }

    public String getProprietorId() {
        return proprietorId;
    }

    public void setProprietorId(String proprietorId) {
        this.proprietorId = proprietorId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayStandard() {
        return payStandard;
    }

    public void setPayStandard(String payStandard) {
        this.payStandard = payStandard;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(double receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public double getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(double receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public int getDebtMonth() {
        return debtMonth;
    }

    public void setDebtMonth(int debtMonth) {
        this.debtMonth = debtMonth;
    }

    public String getReceivableTime() {
        return receivableTime;
    }

    public void setReceivableTime(String receivableTime) {
        this.receivableTime = receivableTime;
    }

    public String getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(String applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public int getFlg() {
        return flg;
    }

    public void setFlg(int flg) {
        this.flg = flg;
    }

    public int getPayFlg() {
        return payFlg;
    }

    public void setPayFlg(int payFlg) {
        this.payFlg = payFlg;
    }
}
