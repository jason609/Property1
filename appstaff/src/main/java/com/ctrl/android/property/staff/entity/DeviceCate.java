package com.ctrl.android.property.staff.entity;

import java.util.List;

/**
 * 设备分类
 * Created by Eric on 2015/11/26.
 */
public class DeviceCate {

    private int flg = 1;//0:展开  1:不展开
    private String id;//分类id
    private String name;//分类名称
    private List<Device> listDevice;//设备列表

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlg() {
        return flg;
    }

    public void setFlg(int flg) {
        this.flg = flg;
    }

    public List<Device> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<Device> listDevice) {
        this.listDevice = listDevice;
    }
}
