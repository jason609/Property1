package com.ctrl.android.property.eric.entity;

/**
 * 用户实体
 * Created by Eric on 2015/10/30.
 */
public class ApkInfo {

    private String id;//Id
    private String apkName;//apk名称
    private String apkVersion;//apk版本号
    private String apkUrl;//apkURL
    private String isNew;//是否最新版本  1-最新  0-非最新版本
    private String fileSize;//apk文件大小（单位：M）
    private String remark;//更新内容
    private String updateTime;//更新时间
    private String qrImgUrl;//二维码图片URL
    private String versionCode;//版本代号（注：更新时安卓版本需要）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getQrImgUrl() {
        return qrImgUrl;
    }

    public void setQrImgUrl(String qrImgUrl) {
        this.qrImgUrl = qrImgUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
