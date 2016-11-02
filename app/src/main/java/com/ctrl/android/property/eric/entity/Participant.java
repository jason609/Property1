package com.ctrl.android.property.eric.entity;

/**
 * 活动参与者
 * Created by Eric on 2015/11/7.
 */
public class Participant {

    private String nickName = "";//昵称
    private String imgUrl = "aa";//头像URL

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
