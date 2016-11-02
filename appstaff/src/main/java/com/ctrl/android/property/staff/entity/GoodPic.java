package com.ctrl.android.property.staff.entity;

/**
 * 二手物品图片
 * Created by Administrator on 2015/10/28.
 */
public class GoodPic {
    private String id; //图片id
    private String originalImg; //原图片地址
    private String zipImgUrl; //缩略图片地址

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

    public String getZipImgUrl(){return zipImgUrl;}

    public void setZipImgUrl(String zipImgUrl){
        this.zipImgUrl=zipImgUrl;
    }
}
