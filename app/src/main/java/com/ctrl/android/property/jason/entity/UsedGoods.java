package com.ctrl.android.property.jason.entity;

import java.util.List;

/**
 * 二手物品列表实体
 * Created by Administrator on 2015/10/28.
 */
public class UsedGoods {
    private String id;//二手物品id
    private String title;//宝贝标题
    private String proprietorName;//业主姓名
    private String communityAddress;//地点（社区地址）
    private String communityName;//地点（社区名称）
    private String building;//楼号
    private String unit;//单元号
    private String room;//房间号
    private double sellingPrice;//转让价/期望价格
    private String createTime;//发布时间
    private List<GoodPic> usedGoodPicSubList;//二手物品图片
    private String imgUrl;//发布者头像url地址

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommunityAddress() {
        return communityAddress;
    }

    public void setCommunityAddress(String communityAddress) {
        this.communityAddress = communityAddress;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
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

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<GoodPic> getUsedGoodPicSubList() {
        return usedGoodPicSubList;
    }

    public void setUsedGoodPicSubList(List<GoodPic> usedGoodPicSubList) {
        this.usedGoodPicSubList = usedGoodPicSubList;
    }
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}
