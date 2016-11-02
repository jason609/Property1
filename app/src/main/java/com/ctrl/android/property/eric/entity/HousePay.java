package com.ctrl.android.property.eric.entity;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by Eric on 2015/10/23.
 */
public class HousePay {

    /**选择状态*/
    private boolean check;
    /**小区名称*/
    private String name;
    /**地址*/
    private String address;
    private List<Map<String,String>> listMap;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Map<String, String>> getListMap() {
        return listMap;
    }

    public void setListMap(List<Map<String, String>> listMap) {
        this.listMap = listMap;
    }
}
