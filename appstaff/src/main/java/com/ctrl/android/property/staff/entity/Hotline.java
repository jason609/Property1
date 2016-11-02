package com.ctrl.android.property.staff.entity;

import java.util.List;
import java.util.Map;

/**
 * 通讯录
 * Created by Eric on 2015/10/21.
 */
public class Hotline {

    /**分类名称*/
    private String category;
    /**分类 下的数量*/
    private int num;
    /**分类内容*/
    private List<Map<String,String>> listMap;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Map<String, String>> getListMap() {
        return listMap;
    }

    public void setListMap(List<Map<String, String>> listMap) {
        this.listMap = listMap;
    }
}
