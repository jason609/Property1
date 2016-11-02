package com.ctrl.android.property.jason.entity;

import java.io.Serializable;

/**
 * 商品
 * Created by Administrator on 2015/10/30.
 */
public class Product implements Serializable {
    private String name;//产品名称
    private double sellingPrice;//卖价
    private String salesVolume;//销量
    private String originalImg;//产品图片

    //商品详情使用
    private String id;//商品id
    private String companyId;//商家id
    private String categoryId;//分类id
    private double originalPrice;//原价
    private String specification;//规格/单位
    private int stock;//库存
    private String infomation;//商品详情
    private String isAdded;//上架/下架（0：下架、1：上架）
    private int sortNum;//顺序号
    private String goodKind;//商品区分（0：实物商品、1：虚拟商品）
    private String collect;//是否收藏（0：未收藏 1：已收藏）

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getInfomation() {
        return infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getGoodKind() {
        return goodKind;
    }

    public void setGoodKind(String goodKind) {
        this.goodKind = goodKind;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }
}
