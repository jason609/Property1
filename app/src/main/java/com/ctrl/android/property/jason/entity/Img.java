package com.ctrl.android.property.jason.entity;

/**
 * 二手物品图片
 * Created by Administrator on 2015/10/28.
 */
public class Img {
    private String id;//图片id
    private String originalImg = "aa";//图片地址
    private String sortNum;//顺序号
    private String zipImg = "aa";//缩略图Url
    private String createTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getZipImg() {
        return zipImg;
    }

    public void setZipImg(String zipImg) {
        this.zipImg = zipImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
