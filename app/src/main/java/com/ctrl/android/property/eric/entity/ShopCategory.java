package com.ctrl.android.property.eric.entity;

import java.util.List;

/**
 * 周边商家
 * Created by Eric on 2015/11/4.
 */
public class ShopCategory {

    private String id;//周边商家id
    private String cate;//商家分类名称
    private String picUrl;//图片地址
    private List<Shop> aroundCompanyList;//商家列表

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public List<Shop> getAroundCompanyList() {
        return aroundCompanyList;
    }

    public void setAroundCompanyList(List<Shop> aroundCompanyList) {
        this.aroundCompanyList = aroundCompanyList;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
