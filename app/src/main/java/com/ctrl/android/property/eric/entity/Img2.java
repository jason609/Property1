package com.ctrl.android.property.eric.entity;

/**
 * 图片  上传后  获取的图片
 * Created by Eric on 2015/10/29.
 */
public class Img2 {

    private String imgId;//图片id
    private String imgUrl = "aa";//图片地址
    private String zipImgUrl = "aa";//压缩图Url

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getZipImgUrl() {
        return zipImgUrl;
    }

    public void setZipImgUrl(String zipImgUrl) {
        this.zipImgUrl = zipImgUrl;
    }

}
