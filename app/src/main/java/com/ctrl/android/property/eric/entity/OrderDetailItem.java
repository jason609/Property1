package com.ctrl.android.property.eric.entity;

/**
 * 订单详细 内商品
 * Created by Eric on 2015/11/18.
 */
public class OrderDetailItem {
    private String orderItemId;//订单详情ID
    private String productId;//商品ID
    private String productName;//商品名称
    private String originalImg;//商品原图Url
    private String zipImg;//商品缩略图Url
    private double sellingPrice;//商品价格（卖价）
    private double amount;//单个商品合计金额
    private int nums;//商品数量

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public String getZipImg() {
        return zipImg;
    }

    public void setZipImg(String zipImg) {
        this.zipImg = zipImg;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }
}
