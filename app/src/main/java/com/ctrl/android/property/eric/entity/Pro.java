package com.ctrl.android.property.eric.entity;

/**
 * 商品
 * Created by Eric on 2015/11/12.
 */
public class Pro {

    private String id;//商品id
    private String name;//商品名称
    private String sellingPrice;//卖价
    private String salesVolume;//销量
    private String zipImg;//缩略图Url

    private String originalPrice;//原价
    private String stock;//库存
    private String isAdded;//上架/下架（0：下架、1：上架）
    private int goodKind;//商品区分（0：实物商品、1：虚拟商品）
    private String createTime;//创建时间

    private String index;
    private String rowCountPerPage;
    private String companyId;//商家id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getZipImg() {
        return zipImg;
    }

    public void setZipImg(String zipImg) {
        this.zipImg = zipImg;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public int getGoodKind() {
        return goodKind;
    }

    public void setGoodKind(int goodKind) {
        this.goodKind = goodKind;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRowCountPerPage() {
        return rowCountPerPage;
    }

    public void setRowCountPerPage(String rowCountPerPage) {
        this.rowCountPerPage = rowCountPerPage;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
