package com.ctrl.android.property.eric.entity;

/**
 * 版本信息 实体类
 * Created by Eric on 2015/6/12.
 */
public class VersionInfo {

    private String fileSize;//App文件大小
    private String apkVersion = "1.0.1";//App版本
    private String apkName;//App名称
    private String remark = "最新版本, 是否需要升级? ";//App备注
    private String isNew;//是否最新版本（0：否、1：是）
    private String versionCode = "2";//App内部版本号
    private String apkUrl = "http://www.pgyer.com/UnUZ";//App下载URL

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
