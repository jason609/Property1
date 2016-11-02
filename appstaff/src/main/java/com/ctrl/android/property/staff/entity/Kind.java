package com.ctrl.android.property.staff.entity;

import java.io.Serializable;

/**
 *分类
 * Created by Administrator on 2015/10/8.
 */
public class Kind implements Serializable{
    private String id; //分类ID
    private String kindName;//分类名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
}
