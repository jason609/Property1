package com.ctrl.android.property.eric.entity;

/**
 * 广告
 * Created by Eric on 2015/11/11.
 */
public class Ad {

    private String id;//广告ID
    private String imgUrl;//广告图片URL
    private String targetUrl;//广告图片超链接URL

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
