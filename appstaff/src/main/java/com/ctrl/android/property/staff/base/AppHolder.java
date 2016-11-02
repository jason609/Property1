package com.ctrl.android.property.staff.base;

import com.ctrl.android.property.staff.entity.ApkInfo;
import com.ctrl.android.property.staff.entity.Contactor;
import com.ctrl.android.property.staff.entity.StaffInfo;

/**
 * 全局控制类
 * 用于存储所有 全局控制变量
 * Created by Eric on 2015-09-14.
 */
public class AppHolder {

    private ApkInfo versionInfo=new ApkInfo();

    /** 私有化的构造方法，保证外部的类不能通过构造器来实例化*/
    private AppHolder() {
    }

    /**内部类，用于实现lzay机制*/
    private static class SingletonHolder {
        /**单例变量*/
        private static AppHolder instance = new AppHolder();
    }
    /**
     * 获取单例对象实例
     */
    public static AppHolder getInstance() {
        return SingletonHolder.instance;
    }


    /**下面是 app全局变量控制类**/
    private StaffInfo staffInfo = new StaffInfo();

    /**联系人*/
    private Contactor contactor = new Contactor();

    public ApkInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(ApkInfo versionInfo) {
        this.versionInfo = versionInfo;
    }


    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    public Contactor getContactor() {
        return contactor;
    }

    public void setContactor(Contactor contactor) {
        this.contactor = contactor;
    }
}
