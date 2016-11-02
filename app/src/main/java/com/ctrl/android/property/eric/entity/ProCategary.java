package com.ctrl.android.property.eric.entity;

/**
 * 商品分类列表
 * Created by Eric on 2015/11/12.
 */
public class ProCategary {

    private String id;//分类id
    private String name;//分类名称

    private boolean isChecked = false;//是否是当前 分类

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
